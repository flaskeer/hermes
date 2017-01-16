package com.asterisk.opensource.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by dudycoco on 17-1-12.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class NeteaseNewsVo {

    private String title;
    private List<?> keywords;
    private String commenturl;
    private String tlastid;

    public NeteaseNewsVo(String title, List keywords, String commenturl, String tlastid){
        this.title = title;
        this.keywords = keywords;
        this.commenturl = commenturl;
        this.tlastid = tlastid;
    }

}
