package com.asterisk.opensource.test.config;

import com.asterisk.opensource.domain.NeteaseNews;
import com.asterisk.opensource.utils.HttpUtil;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by dudycoco on 17-1-9.
 */
public class ThreadTest {

    @Test
    public void testPostUrl()throws Exception{
        String url = "http://localhost:8000/doload";
        Map<String,String> headers = Maps.newHashMap();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.put("Referer", "http://news.163.com/");
        headers.put("Content-Type", "text/html; charset=GBK");
        headers.put("Cache-Control", "");
        Map<String,String> params = Maps.newHashMap();
        params.put("url", "http://news.163.com/domestic/");
        String result =HttpUtil.postRequest(url,params,headers);
        System.out.println(result);
    }

    @Test
    public void testGet()throws Exception {
        String url = "http://temp.163.com/special/00804KVA/cm_guonei_10.js";
        Map<String,String> params = Maps.newHashMap();
        params.put("callback", "data_callback");
        String getUrl = HttpUtil.getParameters(url, params, "utf-8");
        CloseableHttpClient client = HttpUtil.getHttpClient();
        HttpGet httpGet = new HttpGet(getUrl);
        CloseableHttpResponse response = client.execute(httpGet);
        System.out.println("statusCode:"+response.getStatusLine().getStatusCode());
//        String responseString = EntityUtils.toString(new GzipDecompressingEntity(response.getEntity()));
//        System.out.println(responseString);
        String result = EntityUtils.toString(response.getEntity(), "gbk");
//        String[] tmpList1 = result.split("data_callback\\(");
        System.out.println(result);
//        List<String> titiles = JsonPath.read(result, "$.data_callback[*].title");
//        System.out.println(titiles);
    }
    @Test
    public void testJson()throws Exception{
        File file = new File(Resources.getResource("test.txt").getFile());
        String jsonStr = FileUtils.readFileToString(file);
        String[] tmpJsonStr = jsonStr.split("data_callback\\(");
        String json = tmpJsonStr[1].split("\\)")[0];
        Gson gson = new Gson();
        List<NeteaseNews>list=gson.fromJson(json, new TypeToken<List<NeteaseNews>>(){}.getType());
        for(NeteaseNews neteaseNews:list){
            System.out.println(neteaseNews.getTitle());
            System.out.println(neteaseNews.getKeywords());
        }
//        jsonpath动态修改字节码
//        JSONArray jsonArray =  JsonPath.read(json, "$[0:]");
//        List<NeteaseNews> netLists = Lists.newArrayList();
//        System.out.println(netLists);
//        Object[] jsons= jsonArray.toArray();
//        Configuration conf = Configuration.builder()
//                .options(Option.ALWAYS_RETURN_LIST).build();
//        List<Map<String,Object>> list = JsonPath
//                .using(conf)
//                .parse(json)
//                .read("$[0:]", List.class);
//        System.out.println(list);
//        List<NeteaseNews> netLists = Lists.newArrayList();
//        for (Map<String,Object> map :list){
//            NeteaseNews neteaseNews = new NeteaseNews((String)map.get("title"),(String)map.get("keywords"),"",(String)map.get("tlastid"));
//            netLists.add(neteaseNews);
//        }
//        System.out.println(netLists);
    }
}
