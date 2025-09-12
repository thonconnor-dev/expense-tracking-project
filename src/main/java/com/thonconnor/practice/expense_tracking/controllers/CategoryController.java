package com.thonconnor.practice.expense_tracking.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thonconnor.practice.expense_tracking.models.CategoriesModel;
import com.thonconnor.practice.expense_tracking.models.CategoryModel;
import com.thonconnor.practice.expense_tracking.models.ResponseResult;
import com.thonconnor.practice.expense_tracking.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Category API")
public class CategoryController {
  private final CategoryService categoryService;

  /**
   * Get all categories api entry point.
   * 
   * @return ResponseEntity containing a ResponseResult with CategoriesModel
   */
  @Operation(summary = "List all categories", description = "List all categories",
      responses = {
          @ApiResponse(responseCode = "200", description = "category list",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ResponseResult.class),
                  examples = @ExampleObject(name = "categories", value = """
                       {
                        "data": {
                          "categories": [
                            { "id": 1, "name": "Groceries", "type": "Expense" },
                            { "id": 2, "name": "Salary", "type": "Income" }
                          ]
                        },
                        "errors": null
                      }
                      """))),
          @ApiResponse(responseCode = "503", description = "server runtime error",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ResponseResult.class),
                  examples = @ExampleObject(name = "errors", value = """
                      {
                                "data": null,
                                "errors": [
                                  {
                                    "message": "server error",
                                    "detail": "error",
                                    "errorCode": "ERR001"
                                  }
                                ]
                              }
                               """)))})
  @GetMapping(path = "/categories", produces = "application/json")
  public ResponseEntity<ResponseResult<CategoriesModel>> getAllCategories() {
    log.info("get all categories");
    CategoriesModel categories = new CategoriesModel(categoryService.findAll());
    log.info("categories size: {}", categories.getCategories().size());
    return ResponseEntity.ok()
        .body(ResponseResult.<CategoriesModel>builder().data(categories).build());
  }

  /**
   * create new category api entry point
   * 
   * @param categoryRequest
   * @return category model
   */
  @Operation(summary = "create new category", description = "create new category",
      responses = {
          @ApiResponse(responseCode = "200", description = "new category",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ResponseResult.class),
                  examples = @ExampleObject(name = "category", value = """
                       {
                        "data":
                            { "id": 1, "name": "Groceries", "type": "Expense" },
                        "errors": null
                      }
                      """))),
          @ApiResponse(responseCode = "400", description = "bad request",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ResponseResult.class),
                  examples = @ExampleObject(name = "errors", value = """
                      {
                                "data": null,
                                "errors": [
                                  {
                                    "message": "error",
                                    "detail": "error",
                                    "errorCode": "ERR001"
                                  }
                                ]
                              }
                               """))),
          @ApiResponse(responseCode = "503", description = "server runtime error",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ResponseResult.class),
                  examples = @ExampleObject(name = "errors", value = """
                      {
                                "data": null,
                                "errors": [
                                  {
                                    "message": "error",
                                    "detail": "error",
                                    "errorCode": "ERR001"
                                  }
                                ]
                              }
                               """)))})
  @PostMapping(path = "/category", produces = "application/json")
  public ResponseEntity<ResponseResult<CategoryModel>> createCategory(
      @RequestBody CategoryModel categoryRequest) {
    log.info("create category");
    CategoryModel categoryModel = categoryService.createCategory(categoryRequest);
    return ResponseEntity.ok()
        .body(ResponseResult.<CategoryModel>builder().data(categoryModel).build());
  }

  /**
   * edit category api entry point
   * 
   * @param categoryModel
   * @return category model
   */

  @Operation(summary = "edit category", description = "edit category",
      responses = {
          @ApiResponse(responseCode = "200", description = "edit category",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ResponseResult.class),
                  examples = @ExampleObject(name = "category", value = """
                       {
                        "data":
                            { "id": 1, "name": "Groceries", "type": "Expense" },
                        "errors": null
                      }
                      """))),
          @ApiResponse(responseCode = "400", description = "bad request",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ResponseResult.class),
                  examples = @ExampleObject(name = "errors", value = """
                      {
                                "data": null,
                                "errors": [
                                  {
                                    "message": "error",
                                    "detail": "error",
                                    "errorCode": "ERR001"
                                  }
                                ]
                              }
                               """))),
          @ApiResponse(responseCode = "503", description = "server runtime error",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ResponseResult.class),
                  examples = @ExampleObject(name = "errors", value = """
                      {
                                "data": null,
                                "errors": [
                                  {
                                    "message": "error",
                                    "detail": "error",
                                    "errorCode": "ERR001"
                                  }
                                ]
                              }
                               """)))})
  @PutMapping(path = "category", produces = "application/json")
  public ResponseEntity<ResponseResult<CategoryModel>> editCategory(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = CategoryModel.class),
              examples = @ExampleObject(name = "category", value = """
                        { "name": "Groceries", "type": "Expense" }
                  """))) @RequestBody CategoryModel categoryModel) {
    log.info("edit cateogry");
    categoryService.editCategory(categoryModel);
    log.info("edited successfully");
    return ResponseEntity.ok()
        .body(ResponseResult.<CategoryModel>builder().data(categoryModel).build());
  }

}
