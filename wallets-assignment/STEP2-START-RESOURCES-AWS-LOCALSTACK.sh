#!/bin/bash
set -e

echo "==== Creating resources on LocalStack ===="

# SNS Topic
aws sns create-topic \
    --name transactions \
    --endpoint-url=http://localhost:4566 --region us-east-1

# SQS queues
aws sqs create-queue \
    --queue-name transactions-wallets \
    --endpoint-url=http://localhost:4566 --region us-east-1

aws sqs create-queue \
    --queue-name transactions-wallets-dlq \
    --endpoint-url=http://localhost:4566 --region us-east-1

# Subscribe SQS to SNS
aws sns subscribe \
    --topic-arn arn:aws:sns:us-east-1:000000000000:transactions \
    --protocol sqs \
    --notification-endpoint arn:aws:sqs:us-east-1:000000000000:transactions-wallets \
    --endpoint-url=http://localhost:4566 --region us-east-1

# Set DLQ policy
aws sqs set-queue-attributes \
    --queue-url http://localhost:4566/000000000000/transactions-wallets \
    --attributes '{"RedrivePolicy":"{\"deadLetterTargetArn\":\"arn:aws:sqs:us-east-1:000000000000:transactions-wallets-dlq\",\"maxReceiveCount\":\"3\"}"}' \
    --endpoint-url=http://localhost:4566 --region us-east-1

## create cloudwatch logs
aws --endpoint-url=http://localhost:4566 logs create-log-group --log-group-name ms-transactions-logs --region us-east-1
aws --endpoint-url=http://localhost:4566 logs create-log-stream --log-group-name ms-transactions-logs --log-stream-name transactions-stream --region us-east-1

# DynamoDB table
aws dynamodb create-table \
    --table-name client \
    --attribute-definitions \
        AttributeName=id,AttributeType=N \
        AttributeName=email,AttributeType=S \
        AttributeName=document,AttributeType=S \
    --key-schema \
        AttributeName=id,KeyType=HASH \
    --provisioned-throughput \
        ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --global-secondary-indexes '[
        {
            "IndexName": "email-index",
            "KeySchema": [
                {"AttributeName":"email","KeyType":"HASH"}
            ],
            "Projection": {"ProjectionType":"ALL"},
            "ProvisionedThroughput": {
                "ReadCapacityUnits": 5,
                "WriteCapacityUnits": 5
            }
        },
        {
            "IndexName": "document-index",
            "KeySchema": [
                {"AttributeName":"document","KeyType":"HASH"}
            ],
            "Projection": {"ProjectionType":"ALL"},
            "ProvisionedThroughput": {
                "ReadCapacityUnits": 5,
                "WriteCapacityUnits": 5
            }
        }
    ]' \
    --endpoint-url http://localhost:4566 --region us-east-1

# SES Create Verify E-mail
aws --endpoint-url=http://localhost:4566 \
    ses verify-email-identity \
    --email-address "cbarbosagomesfilho@gmail.com" \
    --region us-east-1



# Create S3 Bucket to  Lambda Function
aws --endpoint-url=http://localhost:4566 s3 mb s3://lambda-functions-bucket --region us-east-1

# Consulting bucket S3

echo "==== Resources created with successfully ===="
