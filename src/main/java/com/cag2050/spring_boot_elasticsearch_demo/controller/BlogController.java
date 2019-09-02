package com.cag2050.spring_boot_elasticsearch_demo.controller;

import com.cag2050.spring_boot_elasticsearch_demo.model.BlogModel;
import com.cag2050.spring_boot_elasticsearch_demo.util.JsonResult;
import com.cag2050.spring_boot_elasticsearch_demo.repository.BlogRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class BlogController {
    @Autowired
    private BlogRepository blogRepository;

    private Log log = LogFactory.getLog(this.getClass());

    @PostMapping("/blog/add")
    // 如果 blogModel 实体参数中没有 id 字段，即数据没有自然的 ID， Elasticsearch 可以帮我们自动生成 ID；此时，blogModel 中的 id 为 null，_id 为自动生成的
    // ID，查询、更新、删除时，可以使用此自动生成的 ID。
    // 参考：https://www.elastic.co/guide/cn/elasticsearch/guide/cn/index-doc.html#index-doc
    public JsonResult<Object> add(@Validated @RequestBody BlogModel blogModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            int len = list.size();
            for (int i = 0; i < len; i++) {
                FieldError fieldError = (FieldError)list.get(i);
                log.error("对象 " + fieldError.getObjectName() + " 的字段 " + fieldError.getField() + "：" + fieldError.getDefaultMessage());
                return new JsonResult<>("someCode",
                        "对象" + fieldError.getObjectName() + "的字段" + fieldError.getField() +
                        "：" + fieldError.getDefaultMessage());
            }
        }
        blogRepository.save(blogModel);
        return new JsonResult<>();
    }

    @PostMapping("/get/{id}")
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
