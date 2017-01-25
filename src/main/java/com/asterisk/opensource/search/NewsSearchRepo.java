package com.asterisk.opensource.search;

import com.asterisk.opensource.domain.News;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * @author donghao
 * @Date 17/1/25
 * @Time 上午11:59
 */
public interface NewsSearchRepo extends ElasticsearchCrudRepository<News,Integer> {

    News findByKeywords(String keywords);
}
