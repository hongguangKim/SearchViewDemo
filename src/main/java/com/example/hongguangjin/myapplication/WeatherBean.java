package com.example.hongguangjin.myapplication;

import java.util.List;
import java.util.Map;

/**
 * Created by hongguang.jin on 2016/10/28.
 */
public class WeatherBean {
    private List<Map<String, Object>> list;
    private String liveWeather;
    private String cityDescription;
    private String cityName;
    private String todayWeather;
    private String upateWeatherTime;
    private String todayEnvironment;

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCityDescription(String cityDescription) {
        this.cityDescription = cityDescription;
    }

    public void setLiveWeather(String liveWeather) {
        this.liveWeather = liveWeather;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    public String getLiveWeather() {
        return liveWeather;
    }

    public String getCityDescription() {
        return cityDescription;
    }

    public String getCityName() {
        return cityName;
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setTodayWeather(String todayWeather) {
        this.todayWeather = todayWeather;
    }

    public void setUpateWeatherTime(String upateWeatherTime) {
        this.upateWeatherTime = upateWeatherTime;
    }

    public void setTodayEnvironment(String todayEnvironment) {
        this.todayEnvironment = todayEnvironment;
    }

    public String getTodayWeather() {
        return todayWeather;
    }

    public String getUpateWeatherTime() {
        return upateWeatherTime;
    }

    public String getTodayEnvironment() {
        return todayEnvironment;
    }
}
