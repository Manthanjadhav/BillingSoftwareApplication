package com.BilllingSoftware.BillingSoftware.service;

import com.BilllingSoftware.BillingSoftware.io.ItemRequest;
import com.BilllingSoftware.BillingSoftware.io.ItemResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
    ItemResponse add(ItemRequest request, MultipartFile file);
    List<ItemResponse> fetchItems();

    void deleteItems(String itemId);
}
