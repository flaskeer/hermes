package com.asterisk.opensource.domain;


import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class News {

	private String id; 
	private String url;
	private String keywords;
	private String title;
	private String context;


	public News(String id, String url, String keywords, String title) {
		this.id = id;
		this.url = url;
		this.keywords = keywords;
		this.title = title;
	}


}
