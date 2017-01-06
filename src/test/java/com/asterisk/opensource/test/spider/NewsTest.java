package com.asterisk.opensource.test.spider;

import com.asterisk.opensource.utils.HTMLSpirit;
import com.asterisk.opensource.utils.HttpTookit;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;


public class NewsTest {

	@Test
	public void test_01() throws Exception {
		String url = "http://platform.sina.com.cn/news/news_list";
		Map<String,String> params = Maps.newHashMap();
		params.put("app_key", "2872801998");
		params.put("channel", "mil");
		params.put("cat_1", "dgby");
		params.put("show_all", "0");
		params.put("show_cat", "1");
		params.put("show_ext", "1");
		params.put("tag", "1");
		params.put("format", "json");
		params.put("page", "1");
		params.put("show_num", "10");
		String getDatat = HttpTookit.doGet(url, params, "utf-8");
		System.out.println(getDatat);
		String total = JsonPath.read(getDatat,"$['result']['total']");
		Integer num = Integer.parseInt(total);
		System.out.println(num);
	}
	
	@Test
	public void test_02() throws Exception {
		File file = new File(Resources.getResource("test.json").getFile());
		String json = FileUtils.readFileToString(file);
		List<JSONObject> jsonObjs = JsonPath.read(json,"$['result']['data'][*]");
		for (JSONObject jsonObject : jsonObjs) {
			System.out.println(jsonObject.get("url"));
		}
	}
	@Test
	public void test_03() throws Exception {
		String url = "http://platform.sina.com.cn/news/news_list?app_key=2872801998&show_cat=1&show_num=10&channel=mil&cat_1=dgby&format=json&tag=1&page={0}&show_all=0&show_ext=1";
		for(int x=0;x<10;x++){
			String message = MessageFormat.format(url, x);
			System.out.println(message);
		}
	}
	
	@Test
	public void test_04() throws Exception {
		File file = new File(Resources.getResource("test.html").getFile());
		String html = FileUtils.readFileToString(file);
		Document doc = Jsoup.parse(html);
		Element div = doc.select("#artibody.content").first();
		System.out.println(HTMLSpirit.delHTMLTag(div.html()));
	}
	
	@Test
	public void test_05() throws Exception {
		File file = new File(Resources.getResource("test.html").getFile());
		String text = FileUtils.readFileToString(file);
		Page page = new Page();
		page.setRawText(text);
		Html html = page.getHtml();
		String context = html.css("#artibody.content").get();
		System.out.println(HTMLSpirit.delHTMLTag(context));
	}

}
