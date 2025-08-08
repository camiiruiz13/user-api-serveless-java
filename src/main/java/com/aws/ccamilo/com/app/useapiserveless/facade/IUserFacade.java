package com.aws.ccamilo.com.app.useapiserveless.facade;

import com.aws.ccamilo.com.app.useapiserveless.domain.model.User;

import java.util.List;

public interface IUserFacade {

    User save(User user);
    User findUserById(Long id);
    List<User> findAll(User user);
    User updateUser(User user);
    User deleteUser(Long id);


}
