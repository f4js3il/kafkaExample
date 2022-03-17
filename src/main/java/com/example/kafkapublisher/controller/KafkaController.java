package com.example.kafkapublisher.controller;

import com.example.kafkapublisher.Payload.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
public class KafkaController {
    private KafkaTemplate<String, Object> kafkaTemplate;
    private String topic="shalu";
    private Random random = new Random();
    List<String> messages = new ArrayList<>();
    User userFromTopic = null;

    @Autowired
    public KafkaController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/publish/{name}")
    public String publishMessage(@PathVariable String name){
        kafkaTemplate.send(topic,"Hi "+name+" is in kafka now");
        return "Data Published";
    }

    @GetMapping("/publishJson/{name}")
    public String publishJson(@PathVariable String name){
        User user = new User(random.nextInt(25),name, Arrays.asList("400","Luella St"));
        kafkaTemplate.send(topic,user);
        return "Data Published";
    }

    @GetMapping("/messages")
    public List<String> getMessage(){
        return messages;
    }

    @KafkaListener(groupId = "shalu-1", topics="shalu", containerFactory ="concurrentKafkaListenerContainerFactory")
    public List<String> getMessagesFromTopic(String data){
        messages.add(data);
        return messages;
    }

    @GetMapping("/jsonmessages")
    public User getJsonMessage(){
        return userFromTopic;
    }

    @KafkaListener(groupId = "shalu-2", topics="shalu", containerFactory ="userConcurrentKafkaListenerContainerFactory")
    public User getJsonFromTopic(User user){
        userFromTopic = user;
        return userFromTopic;
    }


}
