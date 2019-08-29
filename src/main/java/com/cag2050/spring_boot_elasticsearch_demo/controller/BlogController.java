package com.cag2050.spring_boot_elasticsearch_demo.controller;

import com.cag2050.spring_boot_elasticsearch_demo.model.BlogModel;
import com.cag2050.spring_boot_elasticsearch_demo.repository.BlogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
public class BlogController {
    @Autowired
    private BlogRepository blogRepository;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @PostMapping("/blog/add")
    public void add(@RequestBody BlogModel blogModel) {
        blogRepository.save(blogModel);
    }

    @GetMapping("/get/{id}")
    public BlogModel getBlogById(@PathVariable String id) {
        Optional<BlogModel> blogModelOptional = blogRepository.findById(id);
        if (blogModelOptional.isPresent()) {
            BlogModel blogModel = blogModelOptional.get();
            return blogModel;
        }
        return null;
    }

    @PostMapping("/blog/update")
    public String updateById(@RequestBody BlogModel blogModel) {
        String id = blogModel.getId();
        if (StringUtils.isEmpty(id)) {
            return "缺少id";
        }
        blogRepository.save(blogModel);
        return "success";
    }

    @PostMapping("/blog/delete/{id}")
    public String deleteById (@PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            return "error";
        }
        blogRepository.deleteById(id);
        return "success";
    }
}
