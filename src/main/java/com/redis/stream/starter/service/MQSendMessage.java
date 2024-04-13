package com.redis.stream.starter.service;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MQSendMessage {

    @Value("${queue.mp4z.out}")
    private String queueToSend;

    @Autowired
    private final JmsTemplate jmsTemplate;

    public void sendMessage(String correlatorId, String messsage){
        jmsTemplate.send(queueToSend, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(messsage);
                message.setJMSCorrelationID(correlatorId);
                return message;
            }
        });
        System.out.println("Message response to MP4Z : " + messsage);
    }
}
