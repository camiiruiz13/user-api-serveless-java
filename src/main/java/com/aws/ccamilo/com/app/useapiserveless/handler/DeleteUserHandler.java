package com.aws.ccamilo.com.app.useapiserveless.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.ccamilo.com.app.useapiserveless.commons.constants.ErrorException;
import com.aws.ccamilo.com.app.useapiserveless.commons.utils.ResponseBuilder;
import com.aws.ccamilo.com.app.useapiserveless.config.DynamoDbConfig;
import com.aws.ccamilo.com.app.useapiserveless.domain.dao.IUserRepository;
import com.aws.ccamilo.com.app.useapiserveless.domain.dao.impl.DynamoUserRepository;
import com.aws.ccamilo.com.app.useapiserveless.domain.services.IUserServices;
import com.aws.ccamilo.com.app.useapiserveless.domain.services.impl.UserServices;
import com.aws.ccamilo.com.app.useapiserveless.facade.IUserFacade;
import com.aws.ccamilo.com.app.useapiserveless.facade.impl.UserFacade;
import com.aws.ccamilo.com.app.useapiserveless.handler.response.ApiGatewayResponse;


import java.util.Map;


public class DeleteUserHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final IUserServices userServices;

    public DeleteUserHandler() {
        var dynamoClient = DynamoDbConfig.getClient();
        IUserRepository userRepository = new DynamoUserRepository(dynamoClient);
        IUserFacade userFacade = new UserFacade(userRepository);
        this.userServices = new UserServices(userFacade);
    }

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> stringObjectMap, Context context) {
        Map<String, String> pathParams = (Map<String, String>) stringObjectMap.get("pathParameters");
        String id = pathParams.get("id");

        try {
            userServices.deleteUser(id);
            System.out.println("Usuario eliminado exitosamente");
            return ResponseBuilder.ok(null, ErrorException.USER_DELETED_SUCCESS_MESSAGE.getMessage());
        } catch (Exception ex) {
            System.err.println("Error al elimnar: " + ex.getMessage());
            return ResponseBuilder.error(404, ex.getMessage(), ErrorException.USER_QUERY_ERROR_MESSAGE.getMessage() + id);
        }
    }
}
