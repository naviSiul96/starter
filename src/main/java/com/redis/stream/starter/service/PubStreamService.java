package com.redis.stream.starter.service;

import com.redis.stream.starter.dto.DataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class PubStreamService {

    @Value("${stream.key}")
    private String streamKey;

    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisTemplate<String, String> syncRedisTemplate;

    //async
    public void publishEvent(Map message){
        //DataDto data = new DataDto("123","Luis V",34);
        ObjectRecord<String, Map> record = StreamRecords.newRecord()
                .ofObject(message)
                .withStreamKey(streamKey);

        this.redisTemplate
                .opsForStream()
                .add(record)
                .subscribe(System.out::println);

        System.out.println("Stream message sended : " + message);
    }

    //sync
    public RecordId syncPublishEvent()  {
        DataDto data = new DataDto("123","Luis V",34);
        System.out.println("send data:" + data.toString());

        ObjectRecord<String, DataDto> record = StreamRecords.newRecord()
                .ofObject(data)
                .withStreamKey(streamKey);

        RecordId recordId = this.syncRedisTemplate.opsForStream()
                .add(record);

       System.out.println("recordId: "+  recordId);

        if (Objects.isNull(recordId)) {
            System.out.println("error sending event: " + data.toString());
            return null;
        }

        return recordId;
    }

}
