package com.glossy.evolchat.model;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(unique = true, nullable = false)
    private int itemId;

    @Column(nullable = false)
    private String itemName;

    @Column
    private String description;

    @Column(nullable = false)
    private boolean isConsumable;
}
