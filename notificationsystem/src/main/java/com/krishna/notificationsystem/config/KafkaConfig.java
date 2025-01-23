package com.krishna.notificationsystem.config;

import com.krishna.notificationsystem.model.Notification;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Notification> kafkaListenerContainerFactory(
            ConsumerFactory<String, Notification> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Notification> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);
        factory.setConcurrency(1);
        return factory;
    }
}
