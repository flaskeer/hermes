package com.asterisk.opensource.domain;


import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class News {

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
