package com.aws.ccamilo.com.app.useapiserveless.commons.utils;

import com.aws.ccamilo.com.app.useapiserveless.handler.response.ApiGatewayResponse;
import com.aws.ccamilo.com.app.useapiserveless.handler.response.ErrorResponseDTO;
import com.aws.ccamilo.com.app.useapiserveless.handler.response.SuccesResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ResponseBuilder {

    private static final Map<String, String> DEFAULT_HEADERS = Map.of("Content-Type", "application/json");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static ApiGatewayResponse ok(Object data, String message) {
        return buildResponse(200, new SuccesResponseDTO(data, message));
    }

    public static ApiGatewayResponse created(String message) {
        return buildResponse(201, new SuccesResponseDTO(null, message));
    }

    public static ApiGatewayResponse noContent(String message) {
        return buildResponse(204, new ErrorResponseDTO(message, null));
    }

    public static ApiGatewayResponse error(int statusCode, String errorCode, String message) {
        return buildResponse(statusCode, new ErrorResponseDTO(errorCode, message));
    }


    private static ApiGatewayResponse buildResponse(int statusCode, Object bodyObject) {
        try {
            String body = OBJECT_MAPPER.writeValueAsString(bodyObject);
            return ApiGatewayResponse.builder()
                    .statusCode(statusCode)
                    .headers(DEFAULT_HEADERS)
                    .body(body)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error al serializar la respuesta", e);
        }
    }
}
