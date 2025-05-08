package com.BilllingSoftware.BillingSoftware.service;

import com.BilllingSoftware.BillingSoftware.io.CategoryRequest;
import com.BilllingSoftware.BillingSoftware.io.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    CategoryResponse add(CategoryRequest request, MultipartFile file);
    List<CategoryResponse> read();

    void delete(String categoryId);
}
