package com.javaspringbootcourse.course.controllers;

import com.javaspringbootcourse.course.dao.UserDao;
import com.javaspringbootcourse.course.models.User;
import com.javaspringbootcourse.course.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    /**
     * Dependency Injection, DI.
     * Create an object inside the variable.
     * If more exist, share the object in memory.
     */
    @Autowired
    private UserDao userDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/users/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public List<User> getUsers(@RequestHeader(value = "Authorization") String token) {
        if (!validateToken(token)) {
            return null;
        }
        return userDao.getUsers();
    }

    @RequestMapping(value = "api/users", method = RequestMethod.POST)
    public void registerUser(@RequestBody User user) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        // iteraciones, memoria, paralelismo, texto a encriptar
        String hash = argon2.hash(1, 1024, 1, user.getPassword());
        user.setPassword(hash);
        userDao.register(user);
    }

    @RequestMapping(value = "api/users/{id}", method = RequestMethod.DELETE)
    void deleteUser(@RequestHeader(value = "Authorization") String token, @PathVariable Long id) {
        if (!validateToken(token)) {
            return;
        }
        userDao.delete(id);
    }

    private boolean validateToken(String token) {
        String userId = jwtUtil.getKey(token);
        return userId != null;
    }

}
