package com.edu.gvn.medicaltreatments.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.edu.gvn.medicaltreatments.R;
import com.edu.gvn.medicaltreatments.activity.MainActivity;
import com.edu.gvn.medicaltreatments.adapter.LocationPagerAdapter;
import com.edu.gvn.medicaltreatments.common.Utils;
import com.edu.gvn.medicaltreatments.model.City;
import com.edu.gvn.medicaltreatments.model.District;
import com.edu.gvn.medicaltreatments.model.DrugStore;
import com.edu.gvn.medicaltreatments.model.Hospital;

import java.util.ArrayList;

/**
 * Created by hnc on 24/10/2016.
 */


public class FindLocationFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private Activity mActivity;

    private AutoCompleteTextView mAutoCityName;
    private Spinner mDistrict;
    private Button mBtnDrugStore, mBtnHospital;
    private EditText mEdtSearch;

    private ArrayAdapter<String> autoTextAdapter;
    private ArrayAdapter<String> spinnerDistrictsAdapter;

    private ArrayList<City> cityDatas;
    private ArrayList<String> listLocation;
    private ArrayList<District> districtsData;
    private ArrayList<String> listDistrict;

    private ViewPager mTypeLocation;
    private LocationPagerAdapter locationPagerAdapter;

    private int idCurrentCity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        mActivity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cityDatas = new ArrayList<>();
        districtsData = new ArrayList<>();
        listLocation = new ArrayList<>();
        listDistrict = new ArrayList<>();

        locationPagerAdapter = new LocationPagerAdapter(getChildFragmentManager());
        autoTextAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, listLocation);
        spinnerDistrictsAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, listDistrict);
        spinnerDistrictsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find_loaction, container, false);
        mAutoCityName = (AutoCompleteTextView) v.findViewById(R.id.auto_city_name);
        mDistrict = (Spinner) v.findViewById(R.id.spn_district);
        mBtnDrugStore = (Button) v.findViewById(R.id.btn_drugstore);
        mBtnHospital = (Button) v.findViewById(R.id.btn_hospital);
        mEdtSearch = (EditText) v.findViewById(R.id.edt_search);
        mTypeLocation = (ViewPager) v.findViewById(R.id.pager_type_location);

        mAutoCityName.setOnItemClickListener(autoTextViewOnItemClickListener);
        mDistrict.setOnItemSelectedListener(spinnerOnItemSelectedListener);
        mBtnDrugStore.setOnClickListener(this);
        mBtnHospital.setOnClickListener(this);

        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTypeLocation.setAdapter(locationPagerAdapter);

        cityDatas.addAll(((MainActivity) getActivity()).getDbManager().getAllCity());
        listLocation.addAll(convertCityDataToNameCity(cityDatas));
        autoTextAdapter.notifyDataSetChanged();

        mAutoCityName.setAdapter(autoTextAdapter);
        mDistrict.setAdapter(spinnerDistrictsAdapter);

        mAutoCityName.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int citySize = cityDatas.size();

                String search = s.toString().toLowerCase();
                for (int i = 0; i < citySize; i++) {

                    String name = cityDatas.get(i).getCityName().toLowerCase();
                    String nameClean = cityDatas.get(i).getCityNameClean().toLowerCase();

                    if (search.equals(name) || search.equals(nameClean)) {
                        String temp  = cityDatas.get(i).getCityName();
                        progressAutoTextViewItemClick(temp);

                        String textToSet = " " + temp;
                        mAutoCityName.setText(textToSet);
                        mAutoCityName.setSelection(textToSet.length());
                        mAutoCityName.setSelected(true);
                        break;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });


    }


    // AutoText click
    private AdapterView.OnItemClickListener autoTextViewOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // Lấy tên của thành phố có trong autoText
            String cityName = parent.getItemAtPosition(position).toString();
            progressAutoTextViewItemClick(cityName);
        }

    };

    // Spinner item selected
    private AdapterView.OnItemSelectedListener spinnerOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            // Khởi tạo 2 arrayList để chứa data của hospital và drugstore
            ArrayList<DrugStore> drugStoresData = new ArrayList<>();
            ArrayList<Hospital> hospitalsData = new ArrayList<>();

            // Nếu positon == 0 là default : hiển thị toàn bộ nhà thuốc, bênh viện của Tỉnh thành
            if (position == 0) {

                //  lấy tất cả các nhà thuốc trên tỉnh thành có id là idCurrentCity
                drugStoresData.addAll(((MainActivity) getActivity()).getDbManager().getAllDrugStoreOfCity(idCurrentCity));
                //  lấy tất cả các bệnh viện trên tỉnh thành có id là idCurrentCity
                hospitalsData.addAll(((MainActivity) getActivity()).getDbManager().getAllHospitalOfCity(idCurrentCity));

            } else {

                // lấy id của quận huyện hiện tại
                int positionIdOfDistrict = Integer.parseInt(districtsData.get(position).getDistrictId());

                // lấy tất cả các nhà thuốc có id_quận_huyện truyền vào
                drugStoresData.addAll(((MainActivity) getActivity()).getDbManager().getAllDrugStoreOfDistrict(positionIdOfDistrict));
                // lấy tất cả các bệnh viện có id_quận huyện truyền vào
                hospitalsData.addAll(((MainActivity) getActivity()).getDbManager().getAllHospitalOfDistrict(positionIdOfDistrict));

            }


            // notifiData trên 2 pager của viewpager
            locationPagerAdapter.drugStoreNotifi(drugStoresData);
            locationPagerAdapter.hospitalNotifi(hospitalsData);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_drugstore:
                mTypeLocation.setCurrentItem(0);
                break;
            case R.id.btn_hospital:
                mTypeLocation.setCurrentItem(1);
                break;
        }
    }


    private ArrayList<String> convertCityDataToNameCity(ArrayList<City> cityDatas) {
        ArrayList<String> datas = new ArrayList<>();
        if (listLocation.size() != 0) {
            listLocation.clear();
        }
        int size = cityDatas.size();
        for (int i = 0; i < size; i++) {
            String location = cityDatas.get(i).getCityName();
            datas.add(location);
        }
        return datas;
    }

    private ArrayList<String> convertDistrictDataNameDistrict(ArrayList<District> districtsData) {
        ArrayList<String> datas = new ArrayList<>();
        if (listDistrict.size() != 0) {
            listDistrict.clear();
        }

        // Thêm 1 thằng default để show toàn bộ data của tỉnh thành
        datas.add("Default");
        int size = districtsData.size();
        for (int i = 0; i < size; i++) {
            String districName = districtsData.get(i).getDistrictName();
            datas.add(districName);
        }
        return datas;
    }


    private void progressAutoTextViewItemClick(String cityName) {
        // Lấy positon của thành phố đó trong mảng String của autoText
        // Postion này trùng vs positon của mảng city trc khi convert
        int positionTemp = listLocation.indexOf(cityName);
        int idOfCity = Integer.parseInt(cityDatas.get(positionTemp).getCityId());


        districtsData.clear();
        districtsData.addAll(((MainActivity) getActivity()).getDbManager().getAllDistrictsOfCity(idOfCity));
        listDistrict.clear();
        listDistrict.addAll(convertDistrictDataNameDistrict(districtsData));
        spinnerDistrictsAdapter.notifyDataSetChanged();

        idCurrentCity = idOfCity;

        mDistrict.setSelection(0);

        Utils.hideKeyboard(getActivity());

    }

}

