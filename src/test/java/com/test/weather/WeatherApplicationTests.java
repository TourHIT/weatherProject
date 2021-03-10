package com.test.weather;

import com.test.weather.controller.WeatherController;
import com.test.weather.entity.City;
import com.test.weather.entity.ReqEntity;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
@SpringBootTest
class WeatherApplicationTests {
    @Autowired
    private WeatherController userController;
    @Autowired
    private City city; // 需要显示的城市列表可在application.yml中配置

    @Test
    void contextLoads() {
        List<String> cities = city.getCities();
        if(cities.size()>0){
            ReqEntity reqEntity = new ReqEntity();
            reqEntity.setCity(cities.get(0));
            userController.search(reqEntity);
            if(userController.search(reqEntity).getCode() !=200){
                fail("api error");
            }
        }
    }

}
