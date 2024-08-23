package com.glossy.evolchat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class PurchaseRequest {
    private int itemId;
    private int quantity;
    private String paymentMethod;
}
