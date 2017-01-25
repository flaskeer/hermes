package com.asterisk.opensource.service;

import com.asterisk.opensource.domain.News;
import com.asterisk.opensource.mapper.NewsMapper;
import com.asterisk.opensource.search.NewsSearchRepo;
import com.asterisk.opensource.utils.MyStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author donghao
 * @Date 17/1/25
 * @Time 上午10:40
 */
@Service
public class NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private NewsSearchRepo newsSearchRepo;

    private AtomicInteger counter = new AtomicInteger(0);


    public void insert(List<LinkedHashMap<String, String>> datas) {
        datas.forEach(data -> {
            News news = getNews(data);
            newsMapper.insert(news);
            news.setId(counter.getAndIncrement());
            newsSearchRepo.save(news);

        });
    }


    public News getNews(LinkedHashMap<String, String> data) {
        String url = data.get("url");
        String keywords = data.get("keywords");
        String title = data.get("title");
        title = MyStringUtil.decodeUnicode(title);
        return new News(url, keywords, title);
    }

}
