package com.BilllingSoftware.BillingSoftware.service.Impl;

import com.BilllingSoftware.BillingSoftware.entity.CategoryEntity;
import com.BilllingSoftware.BillingSoftware.entity.ItemEntity;
import com.BilllingSoftware.BillingSoftware.io.ItemRequest;
import com.BilllingSoftware.BillingSoftware.io.ItemResponse;
import com.BilllingSoftware.BillingSoftware.repository.CategoryRepository;
import com.BilllingSoftware.BillingSoftware.repository.ItemRepository;
import com.BilllingSoftware.BillingSoftware.service.FileUploadService;
import com.BilllingSoftware.BillingSoftware.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final FileUploadService fileUploadService;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemResponse add(ItemRequest request, MultipartFile file) {
        String imgUrl = fileUploadService.UploadFile(file);
        ItemEntity newItem = convertToEntity(request);
        CategoryEntity existingCategory = categoryRepository.findByCategoryId(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found : " + request.getCategoryId()));
        newItem.setCategory(existingCategory);
        newItem.setImgUrl(imgUrl);
        newItem = itemRepository.save(newItem);
        return converToResponse(newItem);
    }

    private ItemResponse converToResponse(ItemEntity newItem) {
        return ItemResponse.builder()
                .ItemId(newItem.getItemId())
                .name(newItem.getName())
                .price(newItem.getPrice())
                .description(newItem.getDescription())
                .createdAt(newItem.getCreatedAt())
                .updatedAt(newItem.getUpdatedAt())
                .imgUrl(newItem.getImgUrl())
                .categoryName(newItem.getCategory().getName())
                .categoryId(newItem.getCategory().getCategoryId())
                .build();
    }

    private ItemEntity convertToEntity(ItemRequest request) {
        return ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }

    @Override
    public List<ItemResponse> fetchItems() {
        return itemRepository.findAll()
                .stream()
                .map((itemEntity)->converToResponse(itemEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItems(String itemId) {
        ItemEntity existingItem = itemRepository.findByItemId(itemId)
                .orElseThrow(()->new RuntimeException("Item not Found "+itemId ));

        boolean isfileDeleted = fileUploadService.deleteFile(existingItem.getImgUrl());

        if(isfileDeleted)
        {
            itemRepository.delete(existingItem);
        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Unable to delete Image");
        }
    }
}
