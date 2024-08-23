package com.glossy.evolchat.config;

import com.glossy.evolchat.model.Items;
import com.glossy.evolchat.repository.ItemRepository;
import com.glossy.evolchat.service.ItemService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemServiceConfiguration {

    @Bean
    public ItemService itemService(ItemRepository itemRepository) {
        return new ItemService() {
            @Override
            public Items findById(int itemId) {
                return itemRepository.findByItemId(itemId);
            }
        };
    }
}
