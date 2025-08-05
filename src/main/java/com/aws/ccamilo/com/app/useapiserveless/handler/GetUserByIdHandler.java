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

import java.util.Map;


public class GetUserByIdHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final IUserServices userServices;

    public GetUserByIdHandler() {
        IUserFacade userFacade = new UserFacade();
        this.userServices = new UserServices(userFacade);
    }

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> stringObjectMap, Context context) {
        Map<String, String> pathParams = (Map<String, String>) stringObjectMap.get("pathParameters");
        Long id = Long.parseLong(pathParams.get("id"));
        try {
            UsersDTOResponse response = userServices.findUserById(id);
            return ResponseBuilder.ok(response, ErrorException.USER_QUERY_SUCCESS_MESSAGE.getMessage());
        } catch (Exception ex) {
            return ResponseBuilder.error(404, ex.getMessage(), ErrorException.USER_QUERY_ERROR_MESSAGE.getMessage() + id);
        }
    }
}
