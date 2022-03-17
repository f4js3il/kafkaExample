package com.example.kafkapublisher.config;

import com.example.kafkapublisher.Payload.User;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    //for producer
    @Bean
    public ProducerFactory<String,Object> producerFactory(){
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<String, Object>(configs);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(){
    return new KafkaTemplate<String, Object>(producerFactory());
    }

    //for consumer string
    @Bean
    public ConsumerFactory<String, String> consumerFactory(){
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "shalu-1");
    return new DefaultKafkaConsumerFactory<>(configs);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory(){
         ConcurrentKafkaListenerContainerFactory<String, String>  concurrentKafkaListenerContainerFactory=new ConcurrentKafkaListenerContainerFactory<> ();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
        return concurrentKafkaListenerContainerFactory;
    }

    //for Consumer Json
    @Bean
    public ConsumerFactory<String, User> userConsumerFactory(){
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "shalu-2");
        return new DefaultKafkaConsumerFactory<>(configs,new StringDeserializer(), new JsonDeserializer<>(User.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, User> userConcurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, User>  concurrentKafkaListenerContainerFactory=new ConcurrentKafkaListenerContainerFactory<String, User> ();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(userConsumerFactory());
        return concurrentKafkaListenerContainerFactory;
    }

}
