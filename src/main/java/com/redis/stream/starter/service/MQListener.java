package com.redis.stream.starter.service;

import com.redis.stream.starter.component.FlowComponent;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.management.JMException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MQListener {

    private final FlowComponent flowComponent;

    @JmsListener(destination = "${queue.mp4z.in}")
    public void onMessage(Message message){
        System.out.println("Received request from LMP4Z : " + message);
        Map<String, String> map = new HashMap<String, String>();

        try {
            map.put("correlatorId", message.getJMSCorrelationID());
            map.put("data", ((TextMessage) message).getText());
            flowComponent.doInFlow(map);
        } catch (JMSException e) {
            System.out.println("Error JMS " + e.getMessage());
        }

    }

}
