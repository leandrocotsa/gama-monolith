package com.thesis.gama.dto;

import com.thesis.gama.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemSetDTO {
    private int qty;
    private int productId;

}