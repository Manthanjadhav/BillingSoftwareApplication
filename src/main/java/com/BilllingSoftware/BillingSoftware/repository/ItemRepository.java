package com.BilllingSoftware.BillingSoftware.repository;

import com.BilllingSoftware.BillingSoftware.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    Optional<ItemEntity> findByItemId(String itemId);
    Integer countByCategoryId(long id);
}

