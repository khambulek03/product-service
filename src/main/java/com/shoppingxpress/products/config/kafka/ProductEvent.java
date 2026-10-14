package com.shoppingxpress.products.config.kafka;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductEvent(
      UUID eventId,
      EventType eventType,
      String productName,
      String productSku,
      BigDecimal price,
      Long units,
      String supplier
) {

}
