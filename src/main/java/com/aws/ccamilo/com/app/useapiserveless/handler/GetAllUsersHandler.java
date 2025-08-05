package com.aws.ccamilo.com.app.useapiserveless.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.ccamilo.com.app.useapiserveless.commons.constants.ErrorException;
import com.aws.ccamilo.com.app.useapiserveless.commons.utils.ResponseBuilder;
import com.aws.ccamilo.com.app.useapiserveless.domain.services.IUserServices;
import com.aws.ccamilo.com.app.useapiserveless.domain.services.impl.UserServices;
import com.aws.ccamilo.com.app.useapiserveless.dto.response.UsersDTOResponse;
import com.aws.ccamilo.com.app.useapiserveless.facade.IUserFacade;
import com.aws.ccamilo.com.app.useapiserveless.facade.impl.UserFacade;
import com.aws.ccamilo.com.app.useapiserveless.handler.response.ApiGatewayResponse;


import java.util.List;
import java.util.Map;


public class GetAllUsersHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final IUserServices userServices;

    public GetAllUsersHandler() {
        IUserFacade userFacade = new UserFacade();
        this.userServices = new UserServices(userFacade);

    }

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> stringObjectMap, Context context) {
        List<UsersDTOResponse> users =  userServices.findAllUsers();
        return ResponseBuilder.ok(users, ErrorException.USER_QUERY_SUCCESS_MESSAGE.getMessage());

    }
}
