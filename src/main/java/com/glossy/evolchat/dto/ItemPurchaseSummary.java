package com.glossy.evolchat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPurchaseSummary {

    private int itemId;
    private String type;
    private String name;
    private String description;
    private long totalQuantity;

    public ItemPurchaseSummary(int itemId, String type, String name, String description, long totalQuantity) {
        this.itemId = itemId;
        this.type = type;
        this.name = name;
        this.description = description;
        this.totalQuantity = totalQuantity;
    }
}
