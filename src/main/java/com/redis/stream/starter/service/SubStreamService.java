package com.redis.stream.starter.service;

import com.redis.stream.starter.component.FlowComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SubStreamService implements StreamListener<String, ObjectRecord<String, Map>> {

    private final FlowComponent flowComponent;

    @Override
    public void onMessage(ObjectRecord<String, Map> record) {
        System.out.println("Received stream message : " + record.getValue());
        flowComponent.doOutFlow("123456", record.getValue().toString());
    }
}
