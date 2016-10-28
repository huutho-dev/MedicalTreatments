package com.edu.gvn.medicaltreatments.model;

/**
 * Created by hnc on 21/10/2016.
 */

public class Hospital extends Entity {
    private String hospitalId;
    private String hospitalName;
    private String hostpitalNameClean;
    private String hospitalAddress;
    private String hospitalAddressClean;
    private String cityId;
    private String districtId;
    private String introduce;
    private String bookmark;

    public Hospital(String hospitalId, String hospitalName, String hostpitalNameClean, String hospitalAddress,
                    String hospitalAddressClean, String cityId, String districtId, String introduce, String bookmark) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.hostpitalNameClean = hostpitalNameClean;
        this.hospitalAddress = hospitalAddress;
        this.hospitalAddressClean = hospitalAddressClean;
        this.cityId = cityId;
        this.districtId = districtId;
        this.introduce = introduce;
        this.bookmark = bookmark;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getHostpitalNameClean() {
        return hostpitalNameClean;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public String getHospitalAddressClean() {
        return hospitalAddressClean;
    }

    public String getCityId() {
        return cityId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getBookmark() {
        return bookmark;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "hospitalId='" + hospitalId + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", hostpitalNameClean='" + hostpitalNameClean + '\'' +
                ", hospitalAddress='" + hospitalAddress + '\'' +
                ", hospitalAddressClean='" + hospitalAddressClean + '\'' +
                ", cityId='" + cityId + '\'' +
                ", districtId='" + districtId + '\'' +
                ", introduce='" + introduce + '\'' +
                ", bookmark='" + bookmark + '\'' +
                '}';
    }
}
