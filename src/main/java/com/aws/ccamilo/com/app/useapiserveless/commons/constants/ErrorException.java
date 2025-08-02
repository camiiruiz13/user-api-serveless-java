package com.aws.ccamilo.com.app.useapiserveless.commons.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorException {

    USER_QUERY_ERROR_MESSAGE("Usuario no encontrado con identificador: ");
    private final String message;
}
