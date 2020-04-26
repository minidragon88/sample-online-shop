package com.phu.onlineshop.service;

import com.phu.onlineshop.model.User;
import com.phu.onlineshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
    @Autowired
    private UserRepository repository;

    public List<User> findAll()
    {
        return repository.findAll();
    }
}
