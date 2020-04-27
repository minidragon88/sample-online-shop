package com.phu.onlineshop.controller;

import com.phu.onlineshop.APIResponse;
import com.phu.onlineshop.Utils;
import com.phu.onlineshop.model.log.UserActionLog;
import com.phu.onlineshop.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class LogController
{
    @Autowired
    private LogService logService;

    @GetMapping("/logs")
    public List<UserActionLog> getAll()
    {
        return logService.findAll();
    }

    @PostMapping("/logs")
    public ResponseEntity<APIResponse<String>> putLog(@RequestHeader final Map<String, String> headers, @RequestBody final UserActionLog log)
    {
        final String uuid = UUID.randomUUID().toString();
        if (StringUtils.isEmpty(log.getAction())) {
            final String errorMessage = "action is a required field";
            return new APIResponse<String>(HttpStatus.BAD_REQUEST.value(), errorMessage, null).toResponseEntity();
        }
        if (StringUtils.isEmpty(log.getData())) {
            final String errorMessage = "data is a required field";
            return new APIResponse<String>(HttpStatus.BAD_REQUEST.value(), errorMessage, null).toResponseEntity();
        }
        log.setUuid(uuid);
        log.setTime(System.currentTimeMillis());
        log.setUsername(Utils.getCurrentUsername(headers));
        logService.addLog(log);
        return new APIResponse<String>(HttpStatus.CREATED.value(), null, "created").toResponseEntity();
    }
}
