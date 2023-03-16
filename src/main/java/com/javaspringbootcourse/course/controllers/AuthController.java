package com.javaspringbootcourse.course.controllers;

import com.javaspringbootcourse.course.dao.UserDao;
import com.javaspringbootcourse.course.models.User;
import com.javaspringbootcourse.course.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody User user) {
        User userLogin = userDao.getUserByCredentials(user);
        if (userLogin != null) {
            String tokenJwt = jwtUtil.create(String.valueOf(userLogin.getId()), userLogin.getEmail());
            return tokenJwt;
        }
        return "FAIL";
    }

}
