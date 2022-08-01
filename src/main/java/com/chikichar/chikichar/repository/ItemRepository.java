package com.chikichar.chikichar.repository;

import com.chikichar.chikichar.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
