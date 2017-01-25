package com.asterisk.opensource.service;

import com.asterisk.opensource.domain.News;
import com.asterisk.opensource.mapper.NewsMapper;
import com.asterisk.opensource.utils.MyStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author donghao
 * @Date 17/1/25
 * @Time 上午10:40
 */
@Service
public class NewsService {

    @Autowired
    private NewsMapper newsMapper;


    public void insert(List<LinkedHashMap<String, String>> datas) {
        datas.forEach(data -> newsMapper.insert(getNews(data)));
    }


    public News getNews(LinkedHashMap<String, String> data) {
        String url = data.get("url");
        String keywords = data.get("keywords");
        String title = data.get("title");
        title = MyStringUtil.decodeUnicode(title);
        return new News(url, keywords, title);
    }

}
