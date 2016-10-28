package com.edu.gvn.medicaltreatments.model;

/**
 * Created by hnc on 21/10/2016.
 */

public class DrugStore extends Entity {
    private String drugStoreId;
    private String drugStoreName;
    private String drugStoreNameClean;
    private String drugStoreManager;
    private String drugStoreManagerClean;
    private String drugStoreAddress;
    private String drugStoreAddressClean;

    private String cityId;
    private String districtId;
    private String introduce;
    private String licenseNumber;
    private String bookMark;

    public DrugStore( String drugStoreId, String drugStoreName,
                     String drugStoreNameClean, String drugStoreManager, String drugStoreManagerClean,
                     String drugStoreAddress, String drugStoreAddressClean, String cityId,
                     String districtId, String introduce, String licenseNumber, String bookMark) {
        this.drugStoreId = drugStoreId;
        this.drugStoreName = drugStoreName;
        this.drugStoreNameClean = drugStoreNameClean;
        this.drugStoreManager = drugStoreManager;
        this.drugStoreManagerClean = drugStoreManagerClean;
        this.drugStoreAddress = drugStoreAddress;
        this.drugStoreAddressClean = drugStoreAddressClean;
        this.cityId = cityId;
        this.districtId = districtId;
        this.introduce = introduce;
        this.licenseNumber = licenseNumber;
        this.bookMark = bookMark;
    }


    public String getDrugStoreId() {
        return drugStoreId;
    }

    public String getDrugStoreName() {
        return drugStoreName;
    }

    public String getDrugStoreNameClean() {
        return drugStoreNameClean;
    }

    public String getDrugStoreManager() {
        return drugStoreManager;
    }

    public String getDrugStoreManagerClean() {
        return drugStoreManagerClean;
    }

    public String getDrugStoreAddress() {
        return drugStoreAddress;
    }

    public String getDrugStoreAddressClean() {
        return drugStoreAddressClean;
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

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getBookMark() {
        return bookMark;
    }

    @Override
    public String toString() {
        return "DrugStore{" +
                "drugStoreId='" + drugStoreId + '\'' +
                ", drugStoreName='" + drugStoreName + '\'' +
                ", drugStoreNameClean='" + drugStoreNameClean + '\'' +
                ", drugStoreManager='" + drugStoreManager + '\'' +
                ", drugStoreManagerClean='" + drugStoreManagerClean + '\'' +
                ", drugStoreAddress='" + drugStoreAddress + '\'' +
                ", drugStoreAddressClean='" + drugStoreAddressClean + '\'' +
                ", cityId='" + cityId + '\'' +
                ", districtId='" + districtId + '\'' +
                ", introduce='" + introduce + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", bookMark='" + bookMark + '\'' +
                '}';
    }
}
