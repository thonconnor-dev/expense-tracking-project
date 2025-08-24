package com.thonconnor.practice.expense_tracking.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thonconnor.practice.expense_tracking.models.CategoriesModel;
import com.thonconnor.practice.expense_tracking.models.CategoryModel;
import com.thonconnor.practice.expense_tracking.models.ResponseResult;
import com.thonconnor.practice.expense_tracking.services.CategoryService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * Get all categories api entry point.
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

    /**
     * create new category api entry point
     * 
     * @param categoryRequest
     * @return category model
     */
    @PostMapping(path = "/category", produces = "application/json")
    public ResponseEntity<ResponseResult<CategoryModel>> createCategory(@RequestBody CategoryModel categoryRequest) {
        log.info("create category");
        CategoryModel categoryModel = categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok().body(ResponseResult.<CategoryModel>builder().data(categoryModel).build());
    }

    /**
     * edit category api entry point
     * 
     * @param categoryModel
     * @return category model
     */
    @PutMapping(path = "category", produces = "application/json")
    public ResponseEntity<ResponseResult<CategoryModel>> editCategory(@RequestBody CategoryModel categoryModel) {
        log.info("edit cateogry");
        categoryService.editCategory(categoryModel);
        log.info("edited successfully");
        return ResponseEntity.ok().body(ResponseResult.<CategoryModel>builder().data(categoryModel).build());
    }

}
