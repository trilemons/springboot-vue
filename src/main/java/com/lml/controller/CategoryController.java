package com.lml.controller;

import com.lml.pojo.Category;
import com.lml.pojo.Result;
import com.lml.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping
    public Result<?> add(@RequestBody @Validated(Category.Add.class) Category category){
        List<Category> list = categoryService.list();
        Set<String> categoryNameSet = new HashSet<>();
        for(Category cateGory : list){
            categoryNameSet.add(cateGory.getCategoryName());
        }
        if (!categoryNameSet.isEmpty()&&categoryNameSet.contains(category.getCategoryName())){
            return Result.error("该类别已存在");
        }
        categoryNameSet.clear();
        categoryService.add(category);
        return Result.success();
    }

    @GetMapping
    public Result<List<Category>> selectAll(){
        List<Category> list = categoryService.list();
        return Result.success(list);
    }


    @GetMapping("/detail")
    public Result<Category> selectById(@RequestParam int id){
        Category category = categoryService.findById(id);
        return Result.success(category);
    }

    @PutMapping
    public Result<?> update(@RequestBody @Validated(Category.Update.class) Category category){
        List<Category> list = categoryService.list();
        Set<String> categoryNameSet = new HashSet<>();
        for(Category cateGory : list){
            categoryNameSet.add(cateGory.getCategoryName());
        }
        if (!categoryNameSet.isEmpty()&&categoryNameSet.contains(category.getCategoryName())){
            return Result.error("该类别已存在");
        }
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping
    private Result<?> delete(@RequestParam int id){
        Category category = categoryService.findById(id);
        if (category==null)
            return Result.error("要删除的类别不存在");
        categoryService.deleteById(id);
        return Result.success();
    }



}
