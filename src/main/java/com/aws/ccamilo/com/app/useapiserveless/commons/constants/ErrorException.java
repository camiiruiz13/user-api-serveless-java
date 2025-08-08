package com.aws.ccamilo.com.app.useapiserveless.commons.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorException {
    USER_DELETED_SUCCESS_MESSAGE("Usuario eliminado correctamente"),
    USER_QUERY_ERROR_MESSAGE("Usuario no encontrado con identificador: "),
    USER_PERSISTENCE_ERROR_MESSAGE("Ocurrió un error al guardar o actualizar el usuario en la base de datos."),
    USER_QUERY_SUCCESS_MESSAGE ("Consulta exitosa."),
    USER_REGISTERED_SUCCESS_MESSAGE("Se ha registrado el usuario exitosamente."),
    USER_SAVE_ERROR_MESSAGE("Error al guardar el usuario."),
    USER_UPDATED("Usuario actualizado correctamente");
    private final String message;
}
