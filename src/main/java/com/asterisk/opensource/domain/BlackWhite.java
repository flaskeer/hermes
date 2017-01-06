package com.asterisk.opensource.domain;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class BlackWhite {

    private int id;

    /**
     * 0 代表 黑名单
     * 1 代表白名单
     */
    private int blackWhiteType;


    /**
     * 服务器地址
     */
    private String server;

}
