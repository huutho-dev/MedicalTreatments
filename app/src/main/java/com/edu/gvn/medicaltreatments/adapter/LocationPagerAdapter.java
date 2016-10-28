package com.edu.gvn.medicaltreatments.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.edu.gvn.medicaltreatments.fragment.DrugStoreFragment;
import com.edu.gvn.medicaltreatments.fragment.HospitalFragment;
import com.edu.gvn.medicaltreatments.model.DrugStore;
import com.edu.gvn.medicaltreatments.model.Hospital;

import java.util.ArrayList;

/**
 * Created by hnc on 24/10/2016.
 */

public class LocationPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> listFragment = new ArrayList<>();
    private ArrayList<String> listTitle = new ArrayList<>();

    private DrugStoreFragment drugStoreFragment;
    private HospitalFragment hospitalFragment;

    public LocationPagerAdapter(FragmentManager fm) {
        super(fm);

        drugStoreFragment = new DrugStoreFragment();
        hospitalFragment = new HospitalFragment();

        listFragment.add(drugStoreFragment);
        listFragment.add(hospitalFragment);

        listTitle.add("DrugStore");
        listTitle.add("Hospital");
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position);
    }

    public void drugStoreNotifi(ArrayList<DrugStore> drugStores) {
        drugStoreFragment.notifiDataChange(drugStores);
    }

    public void hospitalNotifi(ArrayList<Hospital> hospitals) {
        hospitalFragment.notifiDataChange(hospitals);
    }
}
