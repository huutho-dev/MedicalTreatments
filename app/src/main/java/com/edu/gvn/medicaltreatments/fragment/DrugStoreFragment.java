package com.edu.gvn.medicaltreatments.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.gvn.medicaltreatments.R;
import com.edu.gvn.medicaltreatments.activity.MainActivity;
import com.edu.gvn.medicaltreatments.adapter.DrugStoreAdapter;
import com.edu.gvn.medicaltreatments.common.RecyclerItemOnClickListener;
import com.edu.gvn.medicaltreatments.model.DrugStore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrugStoreFragment extends Fragment {
    private Context context;
    private RecyclerView rvDrugStore;
    private DrugStoreAdapter mAdapter;
    private ArrayList<DrugStore> datas;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datas = new ArrayList<>();
        mAdapter = new DrugStoreAdapter(context, datas);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drug_store, container, false);
        rvDrugStore = (RecyclerView) v.findViewById(R.id.rv_drug_store);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvDrugStore.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDrugStore.setAdapter(mAdapter);

        mAdapter.setItemOnClickListener(new RecyclerItemOnClickListener() {
            @Override
            public void onClick(View v, int position) {

                String name = datas.get(position).getDrugStoreName();
                String add = datas.get(position).getDrugStoreAddress();

                String cityId = datas.get(position).getCityId();
                String nameCity = ((MainActivity) getActivity()).getDbManager().getCityName(cityId);

                String districtId = datas.get(position).getDistrictId();
                String nameDistrict = ((MainActivity) getActivity()).getDbManager().getDistrictName(districtId);

                StringBuilder addressBuilder = new StringBuilder();
                addressBuilder.append(add).append(nameDistrict).append(nameCity);

                ((MainActivity) getActivity()).replaceFragment(R.id.container, MapFragment.newInstance(name, addressBuilder.toString()));
            }
        });
    }

    public void notifiDataChange(ArrayList<DrugStore> data) {
        datas.clear();
        datas.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

}
