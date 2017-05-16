package com.asterisk.opensource.spider.collector;

import com.asterisk.opensource.utils.HttpUtil;
import com.asterisk.opensource.utils.MyStringUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author donghao
 * @Date 17/5/6
 * @Time 下午3:38
 */
public class NeteaseUrls {


    private static List<String> neteaseModules = ImmutableList.of("guonei", "guoji", "war");


    private static List<String> url(String module, List<String> positions) {
        return positions.stream().map(position -> "http://temp.163.com/special/00804KVA/cm_" + module + position + ".js?callback=data_callback").collect(toList());

    }

    private static List<String> allUrls() {
        return neteaseModules.stream()
                .map(neteaseModule -> url(neteaseModule, position()))
                .flatMap(Collection::stream)
                .collect(toList());
    }

    private static List<String> position() {
        List<String> positions = Lists.newArrayList();
        for (int i = 1; i <= 10; i++) {
            if (i == 1) {
                positions.add("");
            } else {
                positions.add("_0" + i);
            }
        }
        return positions;
    }

    public static List<String> urls() {
        return allUrls().parallelStream()
                .filter(url -> !MyStringUtil.isNullOrEmptyOr404(HttpUtil.getRequest(url)))
                .collect(toList());

    }


}
