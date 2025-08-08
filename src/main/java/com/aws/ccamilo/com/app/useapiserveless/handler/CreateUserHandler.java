package com.aws.ccamilo.com.app.useapiserveless.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.ccamilo.com.app.useapiserveless.commons.constants.ErrorException;
import com.aws.ccamilo.com.app.useapiserveless.commons.utils.ResponseBuilder;
import com.aws.ccamilo.com.app.useapiserveless.domain.services.IUserServices;
import com.aws.ccamilo.com.app.useapiserveless.domain.services.impl.UserServices;
import com.aws.ccamilo.com.app.useapiserveless.dto.request.UserDTO;
import com.aws.ccamilo.com.app.useapiserveless.facade.IUserFacade;
import com.aws.ccamilo.com.app.useapiserveless.facade.impl.UserFacade;
import com.aws.ccamilo.com.app.useapiserveless.handler.response.ApiGatewayResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;


public class CreateUserHandler  implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final IUserServices userServices;

    public CreateUserHandler() {
        IUserFacade userFacade = new UserFacade();
        this.userServices = new UserServices(userFacade);
    }

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> stringObjectMap, Context context) {
        System.out.println("CreateUserHandler - Request received: " + stringObjectMap);
        try {
            String body = (String) stringObjectMap.get("body");
            UserDTO userDTO = mapper.readValue(body, UserDTO.class);
            System.out.println("CreateUserHandler - Parsed body: " + userDTO);
            userServices.saveUser(userDTO);
            System.out.println("Usuario creado ");
            return ResponseBuilder.created(ErrorException.USER_REGISTERED_SUCCESS_MESSAGE.getMessage());
        } catch (Exception ex) {
            System.err.println("CreateUserHandler - Error: " + ex.getMessage());
            return ResponseBuilder.error(400, ex.getMessage(), ErrorException.USER_SAVE_ERROR_MESSAGE.getMessage());
        }
    }
}
