package com.aws.ccamilo.com.app.useapiserveless.domain.services;

import com.aws.ccamilo.com.app.useapiserveless.config.SnsConfig;
import com.aws.ccamilo.com.app.useapiserveless.domain.entity.User;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

public class NotificationService {

    private final SnsClient sns = SnsConfig.getClient();
    private final String topicArn = SnsConfig.getUserNotificationTopicArn();

    public void sendUserCreatedEmail(User user) {
        try {
            if (topicArn == null || topicArn.isBlank()) {
                System.out.println("SNS topic ARN vacío; no se enviará notificación.");
                return;
            }
            String subject = "Usuario creado";
            String message = "Hola " + user.getName() + ", tu usuario (" + user.getId() + ") fue creado correctamente.";

            sns.publish(PublishRequest.builder()
                    .topicArn(topicArn)
                    .subject(subject)
                    .message(message)
                    .build());

            System.out.println("SNS publicado a " + topicArn + " para " + user.getEmail());
        }
        catch (Exception e) {
            System.out.println("Error publicando en SNS: " + e.getMessage());
        }
    }
}
