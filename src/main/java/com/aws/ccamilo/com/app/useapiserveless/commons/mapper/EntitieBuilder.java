package com.aws.ccamilo.com.app.useapiserveless.commons.mapper;

import com.aws.ccamilo.com.app.useapiserveless.exception.EntityMappingException;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


public class EntitieBuilder {

    public static <T> T map(Object source, Class<T> targetClass, Map<String, String> fieldMapping) {
        try {
            T targetObject = targetClass.getDeclaredConstructor().newInstance();

            for (Field sourceField : source.getClass().getDeclaredFields()) {
                sourceField.setAccessible(true);
                Object value = sourceField.get(source);

                String targetFieldName = fieldMapping.getOrDefault(sourceField.getName(), sourceField.getName());

                try {
                    Field targetField = targetClass.getDeclaredField(targetFieldName);
                    targetField.setAccessible(true);
                    targetField.set(targetObject, value);
                } catch (NoSuchFieldException ignored) {
                    System.out.printf("Campo '%s' no existe en %s%n", targetFieldName, targetClass.getSimpleName());
                }
            }

            return targetObject;

        } catch (Exception e) {
            throw new EntityMappingException("Error al mapear objetos: " + e.getMessage(), e);
        }
    }


    public static <T> T map(Object source, Class<T> targetClass) {
        return map(source, targetClass, Map.of());
    }


    public static <T> List<T> mapList(List<?> sourceList, Class<T> targetClass, Map<String, String> fieldMapping) {
        return sourceList.stream()
                .map(item -> map(item, targetClass, fieldMapping))
                .collect(Collectors.toList());
    }


    public static <T> List<T> mapList(List<?> sourceList, Class<T> targetClass) {
        return mapList(sourceList, targetClass, Map.of());
    }
}
