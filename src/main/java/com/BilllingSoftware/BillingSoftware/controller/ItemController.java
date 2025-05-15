package com.BilllingSoftware.BillingSoftware.controller;

import com.BilllingSoftware.BillingSoftware.io.ItemRequest;
import com.BilllingSoftware.BillingSoftware.io.ItemResponse;
import com.BilllingSoftware.BillingSoftware.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/admin/items")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse add(@RequestPart("item") String request,
                            @RequestPart("file")MultipartFile file){
        ObjectMapper objectMapper = new ObjectMapper();
        ItemRequest itemRequest = null;
        try {
            itemRequest = objectMapper.readValue(request, ItemRequest.class);
            return itemService.add(itemRequest,file);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error occurred while processing Json");
        }
    }

    @GetMapping("/items")
    public List<ItemResponse> readItems(){
        return itemService.fetchItems();
    }

    @DeleteMapping("/admin/item/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItem(@PathVariable String itemId)
    {
        try {
            itemService.deleteItems(itemId);
        }catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Item not found");
        }
    }
}
