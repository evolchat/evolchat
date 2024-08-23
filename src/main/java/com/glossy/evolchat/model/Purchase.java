package com.glossy.evolchat.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase")
@Data
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User 엔티티

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Items item; // Items 엔티티

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDateTime purchaseDate;

    // 기본 생성자
    public Purchase() {}

    // 생성자
    public Purchase(User user, Items item, int quantity, LocalDateTime purchaseDate) {
        this.user = user;
        this.item = item;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }
}
