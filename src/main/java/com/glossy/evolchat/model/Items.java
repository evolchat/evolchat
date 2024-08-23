package com.glossy.evolchat.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "items")
@Data
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "item_id", unique = true, nullable = false)
    private int itemId;

    @Column(name = "ccash", nullable = false)
    private int ccash;

    @Column(name = "ggold_chip", nullable = false)
    private int ggoldChip;

    @Column(name = "bbetting_points", nullable = false)
    private int bbettingPoints;

    // 기본 생성자
    public Items() {}

    // 생성자
    public Items(int itemId, int ccash, int ggoldChip, int bbettingPoints) {
        this.itemId = itemId;
        this.ccash = ccash;
        this.ggoldChip = ggoldChip;
        this.bbettingPoints = bbettingPoints;
    }
}
