# Datadog Dashboard Setup

This guide explains how to integrate Postman monitor results with **Datadog** and build a live dashboard for API uptime and performance.

---

## 🚀 Integration Overview

1. **Postman Monitors** run on a schedule (every 5 min or hourly).  
2. Each monitor sends a **webhook event** to Datadog’s API endpoint.  
3. Datadog ingests the events and visualizes:
   - Uptime %
   - Response time (ms)
   - Error rate (4xx / 5xx)
   - SLA violations  

---

## ⚙️ Configuration Steps

### 1️⃣ Create a Webhook in Postman
- Go to your collection → **Monitors → Integrations → Webhooks**
- Add the Datadog API endpoint:  
https://api.datadoghq.com/api/v1/events?api_key=
<YOUR_DATADOG_API_KEY>

- Choose the **POST** method.  
- In the request body, add:
```json
{
  "title": "API Monitor Result",
  "text": "Collection {{collectionName}} status: {{status}} – {{responseTime}} ms",
  "alert_type": "{{#if failures}}error{{else}}success{{/if}}"
}
