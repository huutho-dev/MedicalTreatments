package com.edu.gvn.medicaltreatments.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edu.gvn.medicaltreatments.common.Config;
import com.edu.gvn.medicaltreatments.common.ReferencesUtils;
import com.edu.gvn.medicaltreatments.model.City;
import com.edu.gvn.medicaltreatments.model.District;
import com.edu.gvn.medicaltreatments.model.DrugStore;
import com.edu.gvn.medicaltreatments.model.Hospital;

import java.util.ArrayList;

/**
 * Created by hnc on 21/10/2016.
 */

public class MedicinesDbManager {
    public static final String DB_NAME = "MEDICINES.sqlite";

    private static final String TB_CITY = "tbl_tinhthanh";
    private static final String TB_DISTRICT = "tbl_quanhuyen";
    private static final String TB_DRUGSTORE = "tbl_nhathuoc";
    private static final String TB_HOSPITAL = "tbl_benhvien";
    private static final String TB_BOOKMARK_DRUGSTORE = "tbl_bookmark_nhathuoc";
    private static final String TB_BOOKMARK_HOSPITAL = "tbl_bookmark_benhvien";

    private SQLiteDatabase db;
    private Context context;

    public MedicinesDbManager(Context context) {
        this.context = context;
    }

    public ArrayList<City> getAllCity() {
        ArrayList<City> listCity = new ArrayList<>();
        openDb(context);

        Cursor cursorCity = db.query(TB_CITY, null, null, null, null, null, null);
        cursorCity.moveToFirst();

        while (!cursorCity.isAfterLast()) {
            String cityId = cursorCity.getString(cursorCity.getColumnIndex(Config.DbConstants.CITY_CITY_ID));
            String cityName = cursorCity.getString(cursorCity.getColumnIndex(Config.DbConstants.CITY_NAME));
            String cityNameClean = cursorCity.getString(cursorCity.getColumnIndex(Config.DbConstants.CITY_NAME_CLEAN));

            listCity.add(new City(cityId, cityName, cityNameClean));
            cursorCity.moveToNext();
        }
        cursorCity.close();
        closeDb();
        return listCity;
    }

    public ArrayList<District> getAllDistrictsOfCity(int idCity) {
        ArrayList<District> listDistrict = new ArrayList<>();
        openDb(context);

        String table = TB_DISTRICT;
        String whereSelection = Config.DbConstants.DISTRICT_CITY_ID + "=?";
        String[] selectionArgs = {String.valueOf(idCity)};

        Cursor cursorDistrict = db.query(table, null, whereSelection, selectionArgs, null, null, null);
        cursorDistrict.moveToFirst();
        while (!cursorDistrict.isAfterLast()) {
            String districtId = cursorDistrict.getString(cursorDistrict.getColumnIndex(Config.DbConstants.DISTRICT_DISTRICT_ID));
            String districtName = cursorDistrict.getString(cursorDistrict.getColumnIndex(Config.DbConstants.DISTRICT_NAME));
            String districNameClean = cursorDistrict.getString(cursorDistrict.getColumnIndex(Config.DbConstants.DISTRICT_NAME_CLEAN));
            String cityId = cursorDistrict.getString(cursorDistrict.getColumnIndex(Config.DbConstants.DISTRICT_CITY_ID));

            listDistrict.add(new District(districtId, districtName, districNameClean, cityId));
            cursorDistrict.moveToNext();
        }
        cursorDistrict.close();
        closeDb();
        return listDistrict;
    }

    public ArrayList<DrugStore> getAllDrugStoreOfDistrict(int idDistrict) {
        ArrayList<DrugStore> listDrugStore = new ArrayList<>();
        openDb(context);

        Cursor cursorDrug = db.query(
                TB_DRUGSTORE,
                null,
                Config.DbConstants.DRUGSTORE_DISTRICT_ID + "=?",
                new String[]{String.valueOf(idDistrict)},
                null,
                null,
                null);
        cursorDrug.moveToFirst();
        while (!cursorDrug.isAfterLast()) {

            String drugStoreId = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_DRUGSTORE_ID));
            String drugStoreName = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_NAME));
            String drugStoreNameClean = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_NAME_CLEAN));
            String drugStoreManager = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_MANAGER_NAME));
            String drugStoreManagerClean = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_MANAGER_NAME_CLEAN));
            String drugStoreAddress = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_ADDRESS));
            String drugStoreAddressClean = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_ADDRESS_CLEAN));

            String cityId = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_CITY_ID));
            String districtId = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_DISTRICT_ID));
            String introduce = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_INTRODUCE));
            String licenseNumber = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_LICENSE_NUMBER));
            String bookMark = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_BOOK_MARK));

            listDrugStore.add(new DrugStore(drugStoreId, drugStoreName, drugStoreNameClean,
                    drugStoreManager, drugStoreManagerClean, drugStoreAddress, drugStoreAddressClean,
                    cityId, districtId, introduce, licenseNumber, bookMark));
            cursorDrug.moveToNext();
        }
        cursorDrug.close();
        closeDb();
        return listDrugStore;
    }

    public ArrayList<Hospital> getAllHospitalOfDistrict(int idDistric) {
        ArrayList<Hospital> listHostpital = new ArrayList<>();
        openDb(context);
        Cursor cursorHospital = db.query(
                TB_HOSPITAL,
                null,
                Config.DbConstants.HOSPITAL_DISTRICT_ID + "=?",
                new String[]{String.valueOf(idDistric)},
                null,
                null,
                null);
        cursorHospital.moveToFirst();
        while (!cursorHospital.isAfterLast()) {

            String hospitalId = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_ID));
            String hospitalName = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_NAME));
            String hostpitalNameClean = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_NAME_CLEAN));
            String hospitalAddress = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_ADDRESS));
            String hospitalAddressClean = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_ADDRESS_CLEAN));
            String cityId = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_CITY_ID));
            String districtId = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_DISTRICT_ID));
            String introduce = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_INTRODUCE));
            String bookmark = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_BOOKMARK));

            listHostpital.add(new Hospital(hospitalId, hospitalName, hostpitalNameClean,
                    hospitalAddress, hospitalAddressClean, cityId, districtId, introduce, bookmark));

            cursorHospital.moveToNext();
        }
        cursorHospital.close();
        closeDb();

        return listHostpital;
    }

    public ArrayList<DrugStore> getAllDrugStoreOfCity(int idCity) {
        ArrayList<DrugStore> listDrugStore = new ArrayList<>();
        openDb(context);

        Cursor cursorDrug = db.query(
                TB_DRUGSTORE,
                null,
                Config.DbConstants.DRUGSTORE_CITY_ID + "=?",
                new String[]{String.valueOf(idCity)},
                null,
                null,
                null);
        cursorDrug.moveToFirst();
        while (!cursorDrug.isAfterLast()) {

            String drugStoreId = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_DRUGSTORE_ID));
            String drugStoreName = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_NAME));
            String drugStoreNameClean = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_NAME_CLEAN));
            String drugStoreManager = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_MANAGER_NAME));
            String drugStoreManagerClean = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_MANAGER_NAME_CLEAN));
            String drugStoreAddress = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_ADDRESS));
            String drugStoreAddressClean = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_ADDRESS_CLEAN));

            String cityId = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_CITY_ID));
            String districtId = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_DISTRICT_ID));
            String introduce = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_INTRODUCE));
            String licenseNumber = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_LICENSE_NUMBER));
            String bookMark = cursorDrug.getString(cursorDrug.getColumnIndex(Config.DbConstants.DRUGSTORE_BOOK_MARK));

            listDrugStore.add(new DrugStore(drugStoreId, drugStoreName, drugStoreNameClean,
                    drugStoreManager, drugStoreManagerClean, drugStoreAddress, drugStoreAddressClean,
                    cityId, districtId, introduce, licenseNumber, bookMark));
            cursorDrug.moveToNext();
        }
        cursorDrug.close();
        closeDb();
        return listDrugStore;
    }

    public ArrayList<Hospital> getAllHospitalOfCity(int idCity) {
        ArrayList<Hospital> listHostpital = new ArrayList<>();
        openDb(context);
        Cursor cursorHospital = db.query(
                TB_HOSPITAL,
                null,
                Config.DbConstants.HOSPITAL_CITY_ID + "=?",
                new String[]{String.valueOf(idCity)},
                null,
                null,
                null);
        cursorHospital.moveToFirst();
        while (!cursorHospital.isAfterLast()) {

            String hospitalId = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_ID));
            String hospitalName = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_NAME));
            String hostpitalNameClean = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_NAME_CLEAN));
            String hospitalAddress = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_ADDRESS));
            String hospitalAddressClean = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_ADDRESS_CLEAN));
            String cityId = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_CITY_ID));
            String districtId = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_DISTRICT_ID));
            String introduce = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_INTRODUCE));
            String bookmark = cursorHospital.getString(cursorHospital.getColumnIndex(Config.DbConstants.HOSPITAL_BOOKMARK));

            listHostpital.add(new Hospital(hospitalId, hospitalName, hostpitalNameClean,
                    hospitalAddress, hospitalAddressClean, cityId, districtId, introduce, bookmark));

            cursorHospital.moveToNext();
        }
        cursorHospital.close();
        closeDb();

        return listHostpital;
    }

    public int getIdCity(String cityName) {
        int id = -1;

        openDb(context);
        Cursor cursor = db.query(
                TB_CITY,
                new String[]{Config.DbConstants.CITY_CITY_ID, Config.DbConstants.CITY_NAME},
                Config.DbConstants.CITY_NAME + "=?",
                new String[]{cityName},
                null,
                null,
                null);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Config.DbConstants.CITY_CITY_ID)));
            cursor.moveToNext();
        }
        cursor.close();
        closeDb();
        return id;
    }

    public String getCityName(String idCity) {
        String name = null;
        openDb(context);
        Cursor cursor = db.query(
                TB_CITY,
                new String[]{Config.DbConstants.CITY_CITY_ID, Config.DbConstants.CITY_NAME},
                Config.DbConstants.CITY_CITY_ID + "=?",
                new String[]{String.valueOf(idCity)},
                null,
                null,
                null);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            name = cursor.getString(cursor.getColumnIndex(Config.DbConstants.CITY_NAME));
            cursor.moveToNext();
        }
        cursor.close();
        closeDb();
        return name;
    }

    public String getDistrictName(String idDistrict) {
        String name = null;
        openDb(context);
        Cursor cursor = db.query(
                TB_DISTRICT,
                new String[]{Config.DbConstants.DISTRICT_DISTRICT_ID, Config.DbConstants.DISTRICT_NAME},
                Config.DbConstants.DISTRICT_DISTRICT_ID + "=?",
                new String[]{String.valueOf(idDistrict)},
                null,
                null,
                null);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            name = cursor.getString(cursor.getColumnIndex(Config.DbConstants.CITY_NAME));
            cursor.moveToNext();
        }
        cursor.close();
        closeDb();
        return name;
    }

    private void closeDb() {
        db.close();
    }

    private void openDb(Context context) {
        String dbPath = ReferencesUtils.getInstance(context).getPartMedical();
        db = context.openOrCreateDatabase(dbPath, Context.MODE_PRIVATE, null);
    }


}
