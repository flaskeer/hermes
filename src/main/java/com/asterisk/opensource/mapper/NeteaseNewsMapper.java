package com.asterisk.opensource.mapper;


import com.asterisk.opensource.domain.NeteaseNews;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NeteaseNewsMapper {

	int insert(NeteaseNews neteaseNews);

	int update(NeteaseNews neteaseNews);

	int merge(@Param("neteaseNews") NeteaseNews neteaseNews, @Param("fields") String... fields);

	int delete(Long id);

	NeteaseNews findOne(Long id);

	List<NeteaseNews> findAll();

}