package com.aws.ccamilo.com.app.useapiserveless.domain.dao.impl;

import com.aws.ccamilo.com.app.useapiserveless.config.DynamoDbConfig;
import com.aws.ccamilo.com.app.useapiserveless.domain.dao.IUserRepository;
import com.aws.ccamilo.com.app.useapiserveless.domain.entity.User;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DynamoUserRepository implements IUserRepository {

    private final DynamoDbTable<User> userTable;

    public DynamoUserRepository(DynamoDbClient client) {
        String tableName = DynamoDbConfig.getTableName();

        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(client)
                .build();

        this.userTable = enhancedClient.table(tableName, TableSchema.fromBean(User.class));
        ensureTableExists(client, tableName);
    }

    @Override
    public User save(User user) {
        userTable.putItem(user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        Key key = Key.builder().partitionValue(id).build();
        User user = userTable.getItem(key);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        return userTable.scan()
                .items()
                .stream()
                .collect(Collectors.toList());
    }


    @Override
    public void deleteById(String id) {
        Key key = Key.builder().partitionValue(id).build();
        userTable.deleteItem(key);
    }

    /**
     * Crea la tabla si no existe. Se ejecuta solo una vez al instanciar el repositorio.
     */
    private void ensureTableExists(DynamoDbClient client, String tableName) {
        try {
            client.describeTable(DescribeTableRequest.builder()
                    .tableName(tableName)
                    .build());
            System.out.println("La tabla '" + tableName + "' ya existe.");
        } catch (ResourceNotFoundException e) {
            System.out.println("La tabla '" + tableName + "' no existe. Creando...");

            CreateTableRequest request = CreateTableRequest.builder()
                    .tableName(tableName)
                    .keySchema(KeySchemaElement.builder()
                            .attributeName("id")
                            .keyType(KeyType.HASH)
                            .build())
                    .attributeDefinitions(AttributeDefinition.builder()
                            .attributeName("id")
                            .attributeType(ScalarAttributeType.N) // porque id es Long
                            .build())
                    .billingMode(BillingMode.PAY_PER_REQUEST)
                    .build();

            client.createTable(request);
            System.out.println("Tabla '" + tableName + "' creada exitosamente.");
        }
    }
}
