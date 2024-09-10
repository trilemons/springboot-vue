package com.lml.controller;

import com.lml.pojo.Category;
import com.lml.pojo.Result;
import com.lml.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody @Validated Category category){
        categoryService.add(category);

        return Result.success();
    }

    @GetMapping
    public Result<List> selectAll(){
        List<Category> list = categoryService.list();
        return Result.success(list);
    }

    @GetMapping("/detail")
    public Result<Category> selectById(@RequestParam int id){
        Category category = categoryService.findById(id);
        return Result.success(category);
    }

    @PutMapping
    public Result update(@RequestBody @Validated Category category){
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping
    private Result delete(@RequestParam int id){
        categoryService.deleteById(id);
        return Result.success();
    }



}
