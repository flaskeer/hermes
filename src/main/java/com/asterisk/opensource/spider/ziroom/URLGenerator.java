package com.asterisk.opensource.spider.ziroom;

import com.asterisk.opensource.utils.JsoupUtil;
import com.google.common.collect.Maps;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @author donghao
 * @version 1.0
 * @date 2017/6/3
 */
public class URLGenerator {

    private static final String URL_INDEX = "http://www.ziroom.com/z/nl/z2-o1.html";

    public Map<String, String> headers(String url) {
        Map<String, String> headers = Maps.newHashMap();
        headers.put("Cookie", "PHPSESSID=131go25qbinutejmv4hke20uc7; gr_user_id=42ab919d-44fd-4203-9cf0-65283bb3bc13; CURRENT_CITY_NAME=%E5%8C%97%E4%BA%AC; uid=8Ceg_cZmybgsrWXeKnDJXX4c8yScl-6sFK-wOun9sGQhUZadAOFJr9Sh30K7MbHx0DRpzzVAhNJFVlX6n0xUW_Iws5HyHQ5QVFcXhDhoE5RqTudWQvcI246EWOCsprCq6lo0S8cGrMaA7lDRmasLdoGK1a_x8tJU9QV_-ICwXga1m6WCg1MuoIFVAS3CTr9UpT-ZkkQw9RtkRTe9f1fLndBbn6iJLq9TfPFu_U1Tqt-O-3lONQ8lewPyhHaaZ6eqH8tEWsA9idk4nCKNma7LgHD9lRTkmUfZd25ScOqqLdccxp5Rjil9Ba2vd1tLmZ1lLeCxToOjViLg8ja3CtRj5dzKzjZGf1IJKNILzgCHCBKqI2efPFtGTfCdCD6wp5AWCVfhrCXk0hljqvgHTNjy7lWTPblA8WyU-pPYxBUwPmoLYR0xLs1qnm39wsEy2CHuJWnANtXtlC6vxhBiWeYFlDgVLEVyB_FqE4TQkuRg0bOEirS0IOw.; gr_session_id_8da2730aaedd7628=4ae00c16-6c8b-4688-9269-780c7d1807ff; Hm_lvt_038002b56790c097b74c818a80e3a68e=1496456910; Hm_lpvt_038002b56790c097b74c818a80e3a68e=1496488346; CURRENT_CITY_CODE=110000");
        headers.put("Connection", "keep-alive");
        headers.put("Referer", url);
        return headers;
    }


    public List<String> beiJingUrlGenerator() {
        return IntStream
                .range(1, 50)
                .boxed()
                .map(pageSize -> "http://www.ziroom.com/z/nl/z2-o1.html?p=" + pageSize).collect(toList());
    }

    public List<String> urlGenerator() {
        Document document = JsoupUtil.getDocument(URL_INDEX, headers(URL_INDEX));
        return document.select(".selection_con a").stream()
                .map(element -> element.attr("href"))
                .filter(href -> !href.contains("javascript"))
                .map(href -> {
                    if (href.startsWith("//")) {
                        return "http:" + href;
                    } else {
                        return href;
                    }
                })
                .collect(toList());
    }
}
