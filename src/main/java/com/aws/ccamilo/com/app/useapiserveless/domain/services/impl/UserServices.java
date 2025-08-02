package com.aws.ccamilo.com.app.useapiserveless.domain.services.impl;

import com.aws.ccamilo.com.app.useapiserveless.commons.mapper.EntitieBuilder;
import com.aws.ccamilo.com.app.useapiserveless.domain.model.User;
import com.aws.ccamilo.com.app.useapiserveless.domain.services.IUserServices;
import com.aws.ccamilo.com.app.useapiserveless.dto.request.UserDTO;
import com.aws.ccamilo.com.app.useapiserveless.dto.response.UsersDTOResponse;
import com.aws.ccamilo.com.app.useapiserveless.facade.IUserFacade;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserServices  implements IUserServices {

    private final IUserFacade userFacade;

    private static final Map<String, String> ENTITY_TO_RESPONSE_MAPPING = Map.of("id", "idUser");
    private static final Map<String, String> RESPONSE_TO_ENTITY_MAPPING = Map.of("idUser", "id");

    @Override
    public void saveUser(UserDTO userDTO) {

        User user = EntitieBuilder.map(userDTO, User.class);
        userFacade.save(user);
    }

    @Override
    public List<UsersDTOResponse> findAllUsers() {
        return userFacade.findAll(null).stream()
                .map(user -> EntitieBuilder.map(user, UsersDTOResponse.class, ENTITY_TO_RESPONSE_MAPPING))
                .collect(Collectors.toList());
    }

    @Override
    public UsersDTOResponse findUserById(Long id) {
        User user = userFacade.findUserById(id);
        return EntitieBuilder.map(user, UsersDTOResponse.class, ENTITY_TO_RESPONSE_MAPPING);
    }

    @Override
    public void updateUser(UserDTO userDTO) {

        User user = EntitieBuilder.map(userDTO, User.class);
        userFacade.updateUser(user);

    }

    @Override
    public void deleteUser(Long id) {
        userFacade.deleteUser(id);
    }
}
