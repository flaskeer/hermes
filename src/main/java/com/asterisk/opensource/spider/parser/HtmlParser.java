package com.asterisk.opensource.spider.parser;

import com.asterisk.opensource.domain.News;
import com.asterisk.opensource.utils.HTMLSpirit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class HtmlParser {

	private News news;

	public HtmlParser(News news) {
		this.news = news;
	}

	public News parseHtmlToString(){
		try {
			Document doc = Jsoup.connect(news.getUrl())
				 .get();
			Element div = doc.select("#artibody.content").first();
			String context = HTMLSpirit.delHTMLTag(div.html());
			this.news.setContext(context);
			return news;
		} catch (IOException e) {
			e.printStackTrace();
			return news;
		}
	}
	
	public HtmlParser() {
		super();
	}
}
