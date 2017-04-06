package com.asterisk.opensource.spider.parser;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.File;

@Slf4j
public class JsonPipeline implements Pipeline {

    @Override
    @SneakyThrows
    public void process(ResultItems resultItems, Task task) {
        File file = new File("result.json");
        String data = new Gson().toJson(resultItems.get("news"));
        FileUtils.writeStringToFile(file,data + "\n",true);
    }

}
