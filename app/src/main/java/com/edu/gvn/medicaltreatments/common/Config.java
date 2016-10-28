package com.edu.gvn.medicaltreatments.common;

/**
 * Created by hnc on 21/10/2016.
 */

public class Config {
    public static final boolean DEBUG = true;
    public static final String NAME_LOCATION_TO_COORDINATES = "http://maps.googleapis.com/maps/api/geocode/json?address=" ;
    public class DbConstants {

        //Tên các cột của bảng Tỉnh Thành
        public static final String CITY_CITY_ID = "tinhthanh_id";
        public static final String CITY_NAME = "name";
        public static final String CITY_NAME_CLEAN = "name_clean";

        //Tên các cột của bảng Quận huyện
        public static final String DISTRICT_DISTRICT_ID = "quanhuyen_id";
        public static final String DISTRICT_NAME = "name";
        public static final String DISTRICT_NAME_CLEAN = "name_clean";
        public static final String DISTRICT_CITY_ID = "tinhthanh_id";

        //Tên các cột của  bảng Nhà thuốc
        public static final String DRUGSTORE_DRUGSTORE_ID = "nhathuoc_id";
        public static final String DRUGSTORE_NAME = "name";
        public static final String DRUGSTORE_NAME_CLEAN = "name_clean";
        public static final String DRUGSTORE_MANAGER_NAME = "chunhathuoc";
        public static final String DRUGSTORE_MANAGER_NAME_CLEAN = "chunhathuoc_clean";
        public static final String DRUGSTORE_ADDRESS = "diachi";
        public static final String DRUGSTORE_ADDRESS_CLEAN = "diachi_clean";
        public static final String DRUGSTORE_CITY_ID = "tinhthanh_id";
        public static final String DRUGSTORE_DISTRICT_ID = "quanhuyen_id";
        public static final String DRUGSTORE_INTRODUCE = "gioithieu";
        public static final String DRUGSTORE_LICENSE_NUMBER = "sogiayphep";
        public static final String DRUGSTORE_BOOK_MARK = "bookmark";

        //Tên các cột của bảng Bệnh viện
        public static final String HOSPITAL_ID = "benhvien_id";
        public static final String HOSPITAL_NAME = "name";
        public static final String HOSPITAL_NAME_CLEAN = "name_clean";
        public static final String HOSPITAL_ADDRESS = "diachi";
        public static final String HOSPITAL_ADDRESS_CLEAN = "diachi_clean";
        public static final String HOSPITAL_CITY_ID = "tinhthanh_id";
        public static final String HOSPITAL_DISTRICT_ID = "quanhuyen_id";
        public static final String HOSPITAL_INTRODUCE = "gioithieu";
        public static final String HOSPITAL_BOOKMARK = "bookmark";

        //Tên các cột của bảng Bookmark_Bệnh_viện
        public static final String BOOKMARK_HOSPITAL_DRUGSTORE_ID = "benhvien_id";

        // Tên các cột của bảng Bookmark_Nha_thuoc
        public static final String BOOKMARK_DRUGSTORE_ID = "nhathuoc_id";
    }
}
