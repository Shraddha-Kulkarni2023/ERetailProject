package com.example.order_service.Kafka;

import com.example.order_service.entity.OutboxEvent;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String,String> kafkaTemplate;

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void publishOutbox() {

        List<OutboxEvent> events = outboxRepository.findByPublishedFalse();
        for(OutboxEvent event : events) {

            try {
                kafkaTemplate.send(new ProducerRecord<>(event.getType(), event.getAggregateId(), event.getPayload())).get();
                event.setPublished(true);
                event.setPublishedAt(java.time.Instant.now());
                outboxRepository.save(event);
            } catch (Exception e) {

                System.err.println("Failed to publish event id : " + event.getId() + " due to " + e.getMessage());
            }
        }

        }
}
