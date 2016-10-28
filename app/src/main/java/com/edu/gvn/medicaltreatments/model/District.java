package com.edu.gvn.medicaltreatments.model;

/**
 * Created by hnc on 21/10/2016.
 */

public class District extends Entity {
    private String districtId;
    private String districtName;
    private String districNameClean;
    private String cityId;

    public District(String districtId, String districtName, String districNameClean, String cityId) {
        this.districtId = districtId;
        this.districtName = districtName;
        this.districNameClean = districNameClean;
        this.cityId = cityId;
    }


    public String getDistrictId() {
        return districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public String getDistricNameClean() {
        return districNameClean;
    }

    public String getCityId() {
        return cityId;
    }
}
