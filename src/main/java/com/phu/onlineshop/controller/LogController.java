package com.phu.onlineshop.controller;

import com.phu.onlineshop.model.log.UserActionLog;
import com.phu.onlineshop.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class LogController
{
    @Autowired
    private LogService logService;

    @GetMapping("/logs")
    public List<UserActionLog> findAll()
    {
        return logService.findAll();
    }
}
