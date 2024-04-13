package com.redis.stream.starter.component;

import com.redis.stream.starter.service.MQSendMessage;
import com.redis.stream.starter.service.PubStreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FlowComponent {

    private final PubStreamService pubStreamService;

    private final MQSendMessage sendMessage;

    public void doInFlow(Map messageIn){
        pubStreamService.publishEvent(messageIn);
    }

    public void doOutFlow(String correlatorId, String messageOut){
        sendMessage.sendMessage(correlatorId, messageOut);
    }

}
