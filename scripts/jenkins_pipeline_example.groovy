// =============================================================
// Jenkins Pipeline: API Monitoring Dashboard
// Author: Malsha [Your Last Name]
// Description: Automates Postman + Datadog API health checks
// =============================================================

pipeline {
  agent any

  environment {
    DATADOG_API_KEY = credentials('datadog_api_key')
  }

  stages {

    stage('Checkout Repository') {
      steps {
        echo 'Cloning project repository...'
        checkout scm
      }
    }

    stage('Run API Monitoring Tests') {
      steps {
        echo 'Executing Postman tests via Newman...'
        sh 'chmod +x scripts/run_monitor.sh'
        sh './scripts/run_monitor.sh'
      }
    }

    stage('Publish Test Reports') {
      steps {
        echo 'Archiving HTML report...'
        archiveArtifacts artifacts: 'postman/reports/*.html', fingerprint: true
      }
    }

    stage('Send Summary to Datadog') {
      steps {
        echo 'Sending test summary to Datadog...'
        sh '''
        curl -X POST "https://api.datadoghq.com/api/v1/events?api_key=$DATADOG_API_KEY" \
        -H "Content-Type: application/json" \
        -d '{
          "title": "API Monitoring Result",
          "text": "Latest API monitor run completed successfully. Reports archived in Jenkins.",
          "alert_type": "success"
        }'
        '''
      }
    }
  }

  post {
    always {
      echo 'Pipeline completed — reports and metrics updated.'
    }
  }
}
