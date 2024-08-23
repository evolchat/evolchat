package com.glossy.evolchat.service;

import com.glossy.evolchat.model.Items;
import com.glossy.evolchat.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Items findById(int itemId) {
        Optional<Items> item = itemRepository.findById((long) itemId);
        return item.orElse(null); // 아이템이 없으면 null 반환
    }
}