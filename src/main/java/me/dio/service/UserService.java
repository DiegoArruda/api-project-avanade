package me.dio.service;

import me.dio.domain.model.User;

public interface UserService extends CrudService<Long, User> {

    User findById(Long id);

    User findByEmail(String email);
}
