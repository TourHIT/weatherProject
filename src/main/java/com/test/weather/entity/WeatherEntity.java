package com.test.weather.entity;

import lombok.Data;

/**
 * 获取城市天气信息前端显示结构体
 */
@Data
public class WeatherEntity {

    private int code; // 返回码
    private String city;  // 城市
    private String updatedTime;  // 数据更新时间
    private String weather;     // 天气情况
    private String temperature;   // 温度
    private String wind;    // 风速
}
