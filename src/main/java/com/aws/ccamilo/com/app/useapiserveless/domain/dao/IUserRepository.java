package com.aws.ccamilo.com.app.useapiserveless.domain.dao;

import com.aws.ccamilo.com.app.useapiserveless.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    User save(User user);
    Optional<User> findById(String id);
    List<User> findAll();
    void deleteById(String id);
}
