package com.glossy.evolchat.repository;

import com.glossy.evolchat.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Items, Long> {
    Items findByItemId(int itemId);
}
