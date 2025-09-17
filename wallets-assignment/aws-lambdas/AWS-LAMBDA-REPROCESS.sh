#!/bin/bash

# --- Configurations ---

BUCKET_NAME="lambda-functions-bucket"
JAR_FILE="aws-lambdas-1.0-SNAPSHOT.jar"
JAR_PATH="target/$JAR_FILE"
FUNCTION_NAME="dlq-reprocess-transactions"

#Handler to execute
HANDLER="br.com.acme.lambda.DLQReprocessLambda::handleRequest"

# 1.Generate JAR "uber" ou "fat jar"
echo "Generate package Lambda..."
mvn clean package

# Verify build successfully
if [ $? -ne 0 ]; then
    echo "Error to build on Maven. Aborted."
    exit 1
fi

echo "Size generate package: $(ls -lh $JAR_PATH | awk '{print $5}')"

# 2. Faz o upload do JAR para o S3 do LocalStack
echo "Upload  JAR to S3..."
aws --endpoint-url=http://localhost:4566 s3 cp $JAR_PATH s3://$BUCKET_NAME/

# 3. Create Lambda Function
echo "Create Lambda Function on object S3..."
aws --endpoint-url=http://localhost:4566 lambda create-function \
  --function-name $FUNCTION_NAME \
  --handler $HANDLER \
  --runtime java17 \
  --code S3Bucket=$BUCKET_NAME,S3Key=$JAR_FILE  --role arn:aws:iam::000000000000:role/lambda-executor \
  --region us-east-1 \
  --timeout 30

echo "Deploy Finished."

