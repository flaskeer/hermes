package com.asterisk.opensource.spider.processor.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asterisk.opensource.domain.Food;
import com.asterisk.opensource.spider.processor.JsonProcessor;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author donghao
 * @Date 17/1/7
 * @Time 下午4:54
 */
public class FoodProcessor implements JsonProcessor<Food> {


    @Override
    public List<Food> jsonProcess(String json) {
        JSONArray jsonArray = JSON.parseArray(json);
        List<Food> foodList = Lists.newArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Food food = new Food();
            food.setImgSrc(jsonObject.getString("product_image"));
            food.setInfo(jsonObject.getString("name"));
            food.setDesc(jsonObject.getString("short_description"));
            food.setUnit(jsonObject.getString("entity_name"));
            food.setPrice(jsonObject.getDouble("price"));
            foodList.add(food);
        }
        return foodList;
    }
}
