package com.spring.user.management.controller;

import com.spring.user.management.dao.UserDAOImpl;
import com.spring.user.management.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserDAOImpl userDAO;

    @GetMapping
    public List<User> getAllUsers(){
        return userDAO.getAll();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId){
        return userDAO.getOne(userId);
    }

    @PostMapping
    public String addUser(@RequestBody User user){
        return userDAO.addUser(user)+" user added";
    }

    @PutMapping("/{userId}")
    public String modifyUser(@PathVariable int userId , @RequestBody User user){
        return userDAO.modifyUser(user , userId)+" user modified";
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable int userId){
        return userDAO.deleteUser(userId)+" user deleted";
    }
}
