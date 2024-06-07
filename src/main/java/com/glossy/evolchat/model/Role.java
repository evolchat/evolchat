package com.glossy.evolchat.model;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(unique = true, nullable = false)
    private int roleId;

    @Column(unique = true, nullable = false)
    private String roleName;
}
