package com.aws.ccamilo.com.app.useapiserveless.domain.services;


import com.aws.ccamilo.com.app.useapiserveless.dto.request.UserDTO;
import com.aws.ccamilo.com.app.useapiserveless.dto.response.UsersDTOResponse;

import java.util.List;

public interface IUserServices {

    void saveUser(UserDTO userDTO);
    List<UsersDTOResponse> findAllUsers();
    UsersDTOResponse findUserById(Long id);
    void updateUser(UserDTO userDTO);
    void deleteUser(Long id);
}
