package com.shoppingxpress.products.config.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductEventPublisher {

    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;

    public void publish(ProductEvent event) {
        kafkaTemplate.send("product-events", event.eventId().toString(), event)
                .whenComplete((response, exception) -> {
                    if (exception != null)
                        log.error("Kafka send failed", exception);
                    else
                        log.info("Sent {} offset={}", event.eventType(), response.getRecordMetadata().offset());
                });
    }
}
