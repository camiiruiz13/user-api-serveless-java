package com.aws.ccamilo.com.app.useapiserveless.config;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDbConfig {

    private static DynamoDbClient instance;

    public static DynamoDbClient getClient() {
        if (instance == null) {
            String region = System.getenv("AWS_REGION");
            if (region == null || region.isEmpty()) {
                region = "us-east-1"; // fallback local
            }

            instance = DynamoDbClient.builder()
                    .region(Region.of(region))
                    .build();
        }
        return instance;
    }

    public static String getTableName() {
        String tableName = System.getenv("DYNAMODB_TABLE_NAME");
        if (tableName == null || tableName.isEmpty()) {
            throw new IllegalStateException("Environment variable DYNAMODB_TABLE_NAME is not set");
        }
        return tableName;
    }

}
