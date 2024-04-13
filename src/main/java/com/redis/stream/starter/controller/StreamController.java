package com.redis.stream.starter.controller;

import com.redis.stream.starter.service.PubStreamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stream")
public class StreamController {

    @Autowired
    private PubStreamService streamService;

    @PostMapping("/send")
    public String sendStream(){
        System.out.println("Enviando mensaje");
        streamService.syncPublishEvent();
        return "OK";
    }
}
