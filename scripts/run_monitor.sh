#!/bin/bash
# ==============================================================
# Script: run_monitor.sh
# Purpose: Automate Postman API monitoring runs via Newman CLI
# Author: Malsha [Your Last Name]
# ==============================================================

# Exit immediately if a command exits with a non-zero status
set -e

# === Configuration ===
COLLECTION="postman/collections/api_monitoring_collection.json"
ENVIRONMENT="postman/environments/staging_env.json"
REPORT_PATH="postman/reports/monitor_run_$(date +%Y%m%d_%H%M%S).html"

# === Logging ===
echo "============================================="
echo "Starting API Monitoring Run"
echo "Date: $(date)"
echo "Collection: $COLLECTION"
echo "Environment: $ENVIRONMENT"
echo "============================================="

# === Run Postman Collection ===
newman run "$COLLECTION" \
  -e "$ENVIRONMENT" \
  --reporters cli,html \
  --reporter-html-export "$REPORT_PATH"

# === Post-Run Summary ===
echo "============================================="
echo "API Monitoring Run Completed Successfully"
echo "Report saved to: $REPORT_PATH"
echo "============================================="
