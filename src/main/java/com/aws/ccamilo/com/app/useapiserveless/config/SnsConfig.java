package com.aws.ccamilo.com.app.useapiserveless.config;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

public class SnsConfig {

    private static SnsClient instance;

    public static SnsClient getClient() {
        if (instance == null) {
            String region = System.getenv("AWS_REGION");
            if (region == null || region.isBlank()) region = "us-east-1";
            instance = SnsClient.builder().region(Region.of(region)).build();
        }
        return instance;
    }

    public static String getUserNotificationTopicArn() {
        String arn = System.getenv("USER_NOTIFICATION_TOPIC_ARN");
        if (arn == null || arn.isBlank()) {
            System.out.println("USER_NOTIFICATION_TOPIC_ARN no está definida");
        }
        return arn;
    }
}
