package com.spring.user.management.dao;

import com.spring.user.management.model.User;

import java.util.List;

public interface UserDAO {

    List<User> getAll();

    User getOne(int userId);

    int addUser(User user);

    int modifyUser(User user , int userId);

    int deleteUser(int userId);
}
