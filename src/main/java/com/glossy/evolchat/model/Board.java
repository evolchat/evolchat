package com.glossy.evolchat.model;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(unique = true, nullable = false)
    private int boardId;

    @Column(unique = true, nullable = false)
    private String boardName;

    private String description;
}
