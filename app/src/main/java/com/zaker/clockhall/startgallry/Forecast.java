package com.zaker.clockhall.startgallry;

/**
 * Created by yarolegovich on 08.03.2017.
 */

public class Forecast {

    private final int cityName;
    private final int cityIcon;
    private final Weather weather;

    public Forecast(int cityName, int cityIcon, Weather weather) {
        this.cityName = cityName;
        this.cityIcon = cityIcon;
        this.weather = weather;
    }

    public int getCityName() {
        return cityName;
    }

    public int getCityIcon() {
        return cityIcon;
    }

    public Weather getWeather() {
        return weather;
    }
}
