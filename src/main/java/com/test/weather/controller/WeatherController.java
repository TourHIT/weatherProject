package com.test.weather.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.weather.entity.City;
import com.test.weather.entity.ReqEntity;
import com.test.weather.entity.WeatherEntity;
import com.test.weather.entity.WeatherResEntity;
import jdk.nashorn.internal.ir.RuntimeNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import sun.misc.Request;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

import static com.test.weather.util.DateUtils.getTime;

@Slf4j
@Controller
@RequestMapping("/")
public class WeatherController {
    @Value("${weather.url}")
    private String weatherUrl;
    @Value("${weather.appid}")
    private String appid;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private City city; // 需要显示的城市列表可在application.yml中配置

    @RequestMapping("/weather")
    public String hello(Model model) {
        model.addAttribute("cities", city.getCities());
        return "weather";
    }

    /**
     * 获取城市天气的接口
     * @param reqEntity
     * @return
     */
    @RequestMapping("/search")
    @ResponseBody
    public WeatherEntity search(@RequestBody ReqEntity reqEntity) {

        String uri = weatherUrl + "q=" + reqEntity.getCity()+"&appid="+appid;
        WeatherResEntity weatherResEntity =  this.getWeatherResponse(uri);
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setCity(reqEntity.getCity());
        if(weatherResEntity == null){
            return weatherEntity;
        }
        weatherEntity.setCode(weatherResEntity.getCod());
        if(weatherResEntity.getMain()!=null){
            weatherEntity.setTemperature(String.valueOf(Math.round(weatherResEntity.getMain().getTemp() - 273.15)) +"°C");
        }
        if(weatherResEntity.getWind()!=null){
            weatherEntity.setWind(String.valueOf(Math.round(weatherResEntity.getWind().getSpeed()*3.6)) +"km/h");
        }
        if(weatherResEntity.getWeather()!=null&&weatherResEntity.getWeather().size()>0){
            weatherEntity.setWeather(weatherResEntity.getWeather().get(0).getMain());
        }
        if(weatherResEntity.getDt()>0){
            weatherEntity.setUpdatedTime(getTime(weatherResEntity.getDt()));
        }
        return weatherEntity;
    }

    /**
     * 使用第三方api获取城市天气数据
     * @param uri
     * @return
     */
    private WeatherResEntity getWeatherResponse(String uri) {
        ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);
        WeatherResEntity resp = null;
        String strBody = null;
        if (respString.getStatusCodeValue() == 200) {
            strBody = respString.getBody();
        }
        try {
            resp = new Gson().fromJson(strBody, new TypeToken<WeatherResEntity>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

}