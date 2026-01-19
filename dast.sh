#!/bin/bash

# Dummy DAST Scan Script
echo "Starting Dummy DAST Scan..."

TARGET_URL=${1:-"http://localhost:80"}
FAILED=0

echo "Targeting: $TARGET_URL"

# Function to check endpoint
check_endpoint() {
    local endpoint=$1
    local expected_code=$2
    local url="${TARGET_URL}${endpoint}"
    
    echo "Checking $url..."
    response=$(curl -s -o /dev/null -w "%{http_code}" "$url")
    
    if [ "$response" -eq "$expected_code" ]; then
        echo "✅ [PASS] $endpoint returned $response"
    else
        echo "❌ [FAIL] $endpoint returned $response (Expected $expected_code)"
        FAILED=1
    fi
}

# Wait for service to be ready (simple retry loop)
echo "Waiting for service to be up..."
for i in {1..30}; do
    if curl -s -f "${TARGET_URL}/health" > /dev/null; then
        echo "Service is up!"
        break
    fi
    echo "Waiting for service... ($i/30)"
    sleep 2
done

# Perform Checks
check_endpoint "/health" 200


if [ $FAILED -eq 0 ]; then
    echo "DAST Scan Completed Successfully."
    exit 0
else
    echo "DAST Scan Failed."
    exit 1
fi
