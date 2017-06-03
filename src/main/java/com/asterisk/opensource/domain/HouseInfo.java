package com.asterisk.opensource.domain;

import lombok.*;

/**
 * Created by donghao on 2017/6/3.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HouseInfo {

    private Long id;

    private String imgUrl;

    private String houseInfo;

    private String houseAddress;

    private String houseOverview;

    private String housePosition;

    private String houseTags;

    private String housePrice;

    private String roomInfoUrl;

}
