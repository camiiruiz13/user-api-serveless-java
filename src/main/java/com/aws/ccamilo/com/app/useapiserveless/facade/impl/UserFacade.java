package com.aws.ccamilo.com.app.useapiserveless.facade.impl;

import com.aws.ccamilo.com.app.useapiserveless.commons.constants.ErrorException;
import com.aws.ccamilo.com.app.useapiserveless.domain.model.User;
import com.aws.ccamilo.com.app.useapiserveless.exception.UserNotFoundException;
import com.aws.ccamilo.com.app.useapiserveless.facade.IUserFacade;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
public class UserFacade implements IUserFacade {

    private static final List<User> users = new ArrayList<>();
    private static final AtomicLong idGenerator = new AtomicLong(1);
    @Override
    public User save(User user) {
        user.setId(idGenerator.getAndIncrement());
        users.add(user);
        return user;
    }

    @Override
    public User findUserById(Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(ErrorException.USER_QUERY_ERROR_MESSAGE.getMessage()));
    }

    @Override
    public List<User> findAll(User user) {
        return new ArrayList<>(users);
    }

    @Override
    public User updateUser(User user) {
        User existingUser = findUserById(user.getId());
        existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
        return existingUser;
    }

    @Override
    public User deleteUser(Long id) {
        User user = findUserById(id);
        users.remove(user);
        return user;
    }
}
