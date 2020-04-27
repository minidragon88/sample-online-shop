package com.phu.onlineshop.controller;

import com.phu.onlineshop.model.user.User;
import com.phu.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController
{
    @Autowired
    private UserService userSerive;

    @GetMapping("/users")
    public List<User> getAll()
    {
        return userSerive.findAll();
    }
}
