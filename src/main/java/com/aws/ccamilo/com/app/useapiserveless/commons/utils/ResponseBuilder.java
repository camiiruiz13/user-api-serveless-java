package com.aws.ccamilo.com.app.useapiserveless.commons.utils;

import com.aws.ccamilo.com.app.useapiserveless.handler.response.ApiGatewayResponse;
import com.aws.ccamilo.com.app.useapiserveless.handler.response.ErrorResponseDTO;
import com.aws.ccamilo.com.app.useapiserveless.handler.response.SuccesResponseDTO;

import java.util.Map;

public class ResponseBuilder {

    private static final Map<String, String> DEFAULT_HEADERS = Map.of("Content-Type", "application/json");

        public static ApiGatewayResponse ok(Object data, String message) {
        SuccesResponseDTO body = new SuccesResponseDTO(data, message);
        return ApiGatewayResponse.builder()
                .statusCode(200)
                .headers(DEFAULT_HEADERS)
                .body(body)
                .build();
    }

    public static ApiGatewayResponse created(String message) {
        SuccesResponseDTO body = new SuccesResponseDTO(null, message);
        return ApiGatewayResponse.builder()
                .statusCode(201)
                .headers(DEFAULT_HEADERS)
                .body(body)
                .build();
    }

    public static ApiGatewayResponse noContent(String message) {
        ErrorResponseDTO body = new ErrorResponseDTO(message, null);
        return ApiGatewayResponse.builder()
                .statusCode(204)
                .headers(DEFAULT_HEADERS)
                .body(body)
                .build();
    }

    public static ApiGatewayResponse error(int statusCode, String errorCode, String message) {
        ErrorResponseDTO error = new ErrorResponseDTO(errorCode, message);
        return ApiGatewayResponse.builder()
                .statusCode(statusCode)
                .headers(DEFAULT_HEADERS)
                .body(error)
                .build();
    }
}
