package com.asterisk.opensource.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document(indexName = "news_index",type = "news_type",shards = 1,replicas = 0)
public class News {

	@Id
	private int id;
	private String url;

	private String keywords;
	private String title;
	private String context;


	public News(int id, String url, String keywords, String title) {
		this.id = id;
		this.url = url;
		this.keywords = keywords;
		this.title = title;
	}

	public News( String url, String keywords, String title) {
		this.url = url;
		this.keywords = keywords;
		this.title = title;
	}
}
