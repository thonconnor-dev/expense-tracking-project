package com.thonconnor.practice.expense_tracking.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thonconnor.practice.expense_tracking.models.CategoriesModel;
import com.thonconnor.practice.expense_tracking.models.ResponseResult;
import com.thonconnor.practice.expense_tracking.services.CategoryService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * Get all categories.
     * 
     * @return ResponseEntity containing a ResponseResult with CategoriesModel
     */
    @GetMapping(path = "/categories", produces = "application/json")
    public ResponseEntity<ResponseResult<CategoriesModel>> getAllCategories() {
        log.info("get all categories");
        CategoriesModel categories = new CategoriesModel(categoryService.findAll());
        log.info("categories size: {}", categories.getCategories().size());
        return ResponseEntity.ok().body(ResponseResult.<CategoriesModel>builder()
                .data(categories)
                .build());
    }
}
