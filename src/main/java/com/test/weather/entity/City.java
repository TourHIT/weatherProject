package com.test.weather.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 获取城市天气情况的结构体
 */
@ConfigurationProperties(prefix = "city")
@Component
@Data
public class City {
    private String name;
    private List<String> cities;

}
