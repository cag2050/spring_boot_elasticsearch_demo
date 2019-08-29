package com.cag2050.spring_boot_elasticsearch_demo.repository;

import com.cag2050.spring_boot_elasticsearch_demo.model.BlogModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BlogRepository extends ElasticsearchRepository<BlogModel, String> {
}
