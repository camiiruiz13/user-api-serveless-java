package com.aws.ccamilo.com.app.useapiserveless.domain.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private String id;
    private String name;
    private String email;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    @DynamoDbAttribute("name")
    public String getName() {
        return name;
    }


    @DynamoDbAttribute("email")
    public String getEmail() {
        return email;
    }

}
