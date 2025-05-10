package com.BilllingSoftware.BillingSoftware.service.Impl;

import com.BilllingSoftware.BillingSoftware.entity.CategoryEntity;
import com.BilllingSoftware.BillingSoftware.io.CategoryRequest;
import com.BilllingSoftware.BillingSoftware.io.CategoryResponse;
import com.BilllingSoftware.BillingSoftware.repository.CategoryRepository;
import com.BilllingSoftware.BillingSoftware.repository.ItemRepository;
import com.BilllingSoftware.BillingSoftware.service.CategoryService;
import com.BilllingSoftware.BillingSoftware.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;
    private final ItemRepository itemRepository;
    @Override
    public CategoryResponse add(CategoryRequest request, MultipartFile file) {
        String imageUrl = fileUploadService.UploadFile(file);
        CategoryEntity newCategory = convertToEntity(request);
        newCategory.setImgUrl(imageUrl);
        newCategory = categoryRepository.save(newCategory);
        return convertToResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> read() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryEntity -> convertToResponse(categoryEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String categoryId) {
        CategoryEntity existingCategory = categoryRepository.findByCategoryId(categoryId).orElseThrow(()->new RuntimeException("Category not found: "+categoryId));
        fileUploadService.deleteFile(existingCategory.getImgUrl());
        categoryRepository.delete(existingCategory);
    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
        Integer items = itemRepository.countByCategoryId(newCategory.getId());
        return CategoryResponse.builder().categoryId(newCategory.getCategoryId())
                .name(newCategory.getName())
                .description(newCategory.getDescription())
                .bgColor(newCategory.getBgColor())
                .imageUrl(newCategory.getImgUrl())
                .createdAt(newCategory.getCreatedAt())
                .updatedAt(newCategory.getUpdatedAt())
                .items(items)
                .build();
    }

    private CategoryEntity convertToEntity(CategoryRequest request) {
        return CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .bgColor(request.getBgColor())
                .build();
    }
}
