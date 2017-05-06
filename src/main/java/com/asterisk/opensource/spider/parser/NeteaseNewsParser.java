package com.asterisk.opensource.spider.parser;

import com.alibaba.fastjson.JSON;
import com.asterisk.opensource.async.ExceptionHandlingAsyncTaskExecutor;
import com.asterisk.opensource.domain.NeteaseNews;
import com.asterisk.opensource.event.EventBusListener;
import com.asterisk.opensource.event.ExecutionEventBusFactory;
import com.asterisk.opensource.event.SpiderExecutionEvent;
import com.asterisk.opensource.utils.HttpUtil;
import com.asterisk.opensource.vo.NeteaseNewsVo;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.asterisk.opensource.constants.Constants.NETEASENEWS_EVENT_NAME;
import static com.asterisk.opensource.constants.Constants.NETEASE__NEWS_URLS;
import static java.util.stream.Collectors.toList;

/**
 * @author donghao
 * @Date 17/5/6
 * @Time 下午5:04
 */
@Component
public class NeteaseNewsParser implements EventBusListener {


    public NeteaseNewsParser() {
        ExecutionEventBusFactory.getInstance(NETEASENEWS_EVENT_NAME).register(this);
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ExceptionHandlingAsyncTaskExecutor asyncTaskExecutor;


   @Subscribe
   @AllowConcurrentEvents
    public void parser(SpiderExecutionEvent event) {
            String url = event.getUrl();
            if (Strings.isNullOrEmpty(url)) {
                return;
            }
            asyncTaskExecutor.execute(() -> {
                String result = HttpUtil.getRequest(url, "gbk");
                responseParser(result);
            });
    }


    public void responseParser(String result) {
        List<NeteaseNewsVo> neteaseNewsVoList = beanToJson(result);
        List<NeteaseNews> neteaseNewsList = trans(neteaseNewsVoList);

    }


    private List<NeteaseNews> trans(List<NeteaseNewsVo> list) {
        return list.stream()
                .map(vo -> new NeteaseNews(vo.getTitle(), format(vo.getKeywords()), vo.getCommenturl(), vo.getTlastid()))
                .collect(toList());
    }


    private List<NeteaseNewsVo> beanToJson(String result) {
        String[] tmpJsonStr = result.split("data_callback\\(");
        String json = tmpJsonStr[1].split("\\)")[0];
        return JSON.parseArray(json, NeteaseNewsVo.class);
    }

    private String format(Object object) {
        return JSON.toJSONString(object)
                .replace("\"", "")
                .replace("[", "")
                .replace("]", "")
                .replace("{", "")
                .replace("}", "");

    }


    @Override
    public String getName() {
        return NETEASENEWS_EVENT_NAME;
    }
}
