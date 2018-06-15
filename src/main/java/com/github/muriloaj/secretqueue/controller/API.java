package com.github.muriloaj.secretqueue.controller;

import com.github.muriloaj.secretqueue.Runner;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class API {
    @RequestMapping(value = "send")
    public String sendMessage(){
        
        return "OK";
    }
}
