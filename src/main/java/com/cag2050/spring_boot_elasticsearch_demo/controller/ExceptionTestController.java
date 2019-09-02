package com.cag2050.spring_boot_elasticsearch_demo.controller;

import com.cag2050.spring_boot_elasticsearch_demo.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionTestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/exception/nullpointer")
    public JsonResult testNullPointerException() {
        String str = null;
        str.length();
        return new JsonResult();
    }

    @PostMapping("/exception/params")
    public JsonResult testParams(@RequestParam("name") String name,
                           @RequestParam("pass") String pass) {
        logger.info("name：{}", name);
        logger.info("pass：{}", pass);
        return new JsonResult();
    }
}
