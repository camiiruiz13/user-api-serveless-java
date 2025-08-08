package com.aws.ccamilo.com.app.useapiserveless.facade.impl;

import com.aws.ccamilo.com.app.useapiserveless.commons.constants.ErrorException;
import com.aws.ccamilo.com.app.useapiserveless.domain.dao.IUserRepository;
import com.aws.ccamilo.com.app.useapiserveless.domain.entity.User;
import com.aws.ccamilo.com.app.useapiserveless.exception.UserNotFoundException;
import com.aws.ccamilo.com.app.useapiserveless.facade.IUserFacade;

import java.util.ArrayList;
import java.util.List;


public class UserFacade implements IUserFacade {

    private final IUserRepository repository;

    public UserFacade(IUserRepository repository) {
        this.repository = repository;
    }
    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User findUserById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorException.USER_QUERY_ERROR_MESSAGE.getMessage()));
    }

    @Override
    public List<User> findAll(User user) {
        return repository.findAll();
    }

    @Override
    public User updateUser(User user) {
        User existingUser = findUserById(user.getId());
        existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
        return repository.save(existingUser);
    }

    @Override
    public User deleteUser(String id) {
        User user = findUserById(id);
        repository.deleteById(id);
        return user;
    }
}
