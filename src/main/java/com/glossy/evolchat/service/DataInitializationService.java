package com.glossy.evolchat.service;

import com.glossy.evolchat.model.Items;
import com.glossy.evolchat.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class DataInitializationService {

    private final ItemRepository itemRepository;

    public DataInitializationService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        initializeData();
    }

    private void initializeData() {
        if (itemRepository.count() == 0) {
            itemRepository.save(new Items(1, 19800, 198, 0));
            itemRepository.save(new Items(2, 19800, 198, 0));
            itemRepository.save(new Items(3, 19800, 198, 0));
            itemRepository.save(new Items(4, 19800, 198, 0));
            itemRepository.save(new Items(5, 19800, 198, 1000000000));
            itemRepository.save(new Items(6, 19800, 198, 0));
            itemRepository.save(new Items(7, 19800, 198, 0));
            itemRepository.save(new Items(8, 19800, 198, 0));
            itemRepository.save(new Items(9, 19800, 198, 0));
            itemRepository.save(new Items(10, 19800, 198, 0));
            itemRepository.save(new Items(11, 19800, 198, 0));
            itemRepository.save(new Items(12, 19800, 198, 0));
            itemRepository.save(new Items(13, 19800, 198, 0));
            itemRepository.save(new Items(14, 19800, 198, 0));
            itemRepository.save(new Items(15, 19800, 198, 0));
            itemRepository.save(new Items(16, 19800, 198, 0));
            itemRepository.save(new Items(17, 19800, 198, 0));
        }
    }
}
