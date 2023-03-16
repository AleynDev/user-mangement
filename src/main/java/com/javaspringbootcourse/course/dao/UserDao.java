package com.javaspringbootcourse.course.dao;

import com.javaspringbootcourse.course.models.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    void delete(Long id);

    void register(User user);

    User getUserByCredentials(User user);
}
