package com.aws.ccamilo.com.app.useapiserveless.domain.services.impl;

import com.aws.ccamilo.com.app.useapiserveless.commons.mapper.EntitieBuilder;
import com.aws.ccamilo.com.app.useapiserveless.domain.entity.User;
import com.aws.ccamilo.com.app.useapiserveless.domain.services.IUserServices;
import com.aws.ccamilo.com.app.useapiserveless.dto.request.UserDTO;
import com.aws.ccamilo.com.app.useapiserveless.dto.response.UsersDTOResponse;
import com.aws.ccamilo.com.app.useapiserveless.facade.IUserFacade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


public class UserServices  implements IUserServices {

    private final IUserFacade userFacade;



    public UserServices(IUserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        String generatedId = UUID.randomUUID().toString();
        userDTO.setIdUser(generatedId);
        System.out.println("DTO:" + userDTO);
        User user = EntitieBuilder.mapDtoToEntity(userDTO, User.class);
        userFacade.save(user);
    }

    @Override
    public List<UsersDTOResponse> findAllUsers() {
        return userFacade.findAll(null).stream()
                .map(user -> EntitieBuilder.mapEntityToDto(user, UsersDTOResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsersDTOResponse findUserById(String id) {
        User user = userFacade.findUserById(id);
        return EntitieBuilder.mapEntityToDto(user, UsersDTOResponse.class);
    }

    @Override
    public void updateUser(UserDTO userDTO) {

        User user = EntitieBuilder.mapDtoToEntity(userDTO, User.class);
        userFacade.updateUser(user);

    }

    @Override
    public void deleteUser(String id) {
        userFacade.deleteUser(id);
    }
}
