package com.edu.gvn.medicaltreatments.model;

/**
 * Created by hnc on 21/10/2016.
 */

public class City extends Entity {
    private String cityId ;
    private String cityName;
    private String cityNameClean;

    public City( String cityId, String cityName, String cityNameClean) {

        this.cityId = cityId;
        this.cityName = cityName;
        this.cityNameClean = cityNameClean;
    }


    public String getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityNameClean() {
        return cityNameClean;
    }

    @Override
    public String toString() {
        return "City{" +
                ", cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", cityNameClean='" + cityNameClean + '\'' +
                '}';
    }
}
