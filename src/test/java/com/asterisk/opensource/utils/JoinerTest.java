package com.asterisk.opensource.utils;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;

/**
 * @author dongh38@ziroom.com
 * @version 1.0.0
 */
public class JoinerTest {

    @Test
    public void test_join() {
        Map<String,String> map = ImmutableMap.of("A","b","C","d");
        String join = Joiner.on("&").withKeyValueSeparator("=").join(map);
        System.out.println(join);
    }

}
