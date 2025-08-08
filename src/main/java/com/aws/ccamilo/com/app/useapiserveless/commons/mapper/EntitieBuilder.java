package com.aws.ccamilo.com.app.useapiserveless.commons.mapper;

import com.aws.ccamilo.com.app.useapiserveless.commons.annotations.FieldMapping;
import com.aws.ccamilo.com.app.useapiserveless.exception.EntityMappingException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class EntitieBuilder {

    public static <E, D> D mapEntityToDto(E entity, Class<D> dtoClass) {
        try {
            D dto = dtoClass.getDeclaredConstructor().newInstance();
            Map<String, String> fieldMapping = getFieldMappings(dtoClass);
            for (Field entityField : entity.getClass().getDeclaredFields()) {
                if (Modifier.isStatic(entityField.getModifiers()) ||
                        Modifier.isFinal(entityField.getModifiers()) ||
                        Collection.class.isAssignableFrom(entityField.getType())) {
                    continue;
                }

                entityField.setAccessible(true);
                String dtoFieldName = fieldMapping.getOrDefault(entityField.getName(), entityField.getName());

                try {
                    Field dtoField = dtoClass.getDeclaredField(dtoFieldName);
                    if (Modifier.isStatic(dtoField.getModifiers()) || Modifier.isFinal(dtoField.getModifiers())) {
                        continue;
                    }
                    dtoField.setAccessible(true);
                    dtoField.set(dto, entityField.get(entity));
                } catch (NoSuchFieldException ignored) {
                    System.out.printf("Campo no encontrado en el DTO: " + dtoFieldName);
                }
            }
            return dto;

        } catch (Exception e) {
            throw new EntityMappingException("Error al mapear objetos: " + e.getMessage(), e);
        }
    }

    public static <D, E> E mapDtoToEntity(D dto, Class<E> entityClass) {
        try {
            E entity = entityClass.getDeclaredConstructor().newInstance();
            Map<String, String> fieldMapping = getFieldMappings(dto.getClass());
            for (Field dtoField : dto.getClass().getDeclaredFields()) {
                if (Modifier.isStatic(dtoField.getModifiers()) || Modifier.isFinal(dtoField.getModifiers())) {
                    continue;
                }
                dtoField.setAccessible(true);
                String entityFieldName = fieldMapping.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue().equals(dtoField.getName()))
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElse(dtoField.getName());
                try {
                    Field entityField = entityClass.getDeclaredField(entityFieldName);
                    entityField.setAccessible(true);
                    entityField.set(entity, dtoField.get(dto));
                }catch (NoSuchFieldException ignored) {
                    throw new EntityMappingException("Campo no mapeado", ignored);
                }
            }
            return entity;
        }catch (Exception e) {
            throw new EntityMappingException("Error mapping DTO to entity", e);
        }
    }

    private static Map<String, String> getFieldMappings(Class<?> clazz) {
        Map<String, String> mapping = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            FieldMapping annotation = field.getAnnotation(FieldMapping.class);
            if (annotation != null) {
                mapping.put(annotation.value(), field.getName());
            }
        }
        return mapping;
    }
}
