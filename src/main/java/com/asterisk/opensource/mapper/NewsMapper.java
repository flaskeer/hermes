package com.asterisk.opensource.mapper;


import com.asterisk.opensource.domain.News;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsMapper {

	int insert(News news);

	int update(News news);

	int merge(@Param("news") News news, @Param("fields") String... fields);

	int delete(Long id);

	News findOne(Long id);

	List<News> findAll();

}