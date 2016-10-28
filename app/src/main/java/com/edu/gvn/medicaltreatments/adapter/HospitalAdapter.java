package com.edu.gvn.medicaltreatments.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.gvn.medicaltreatments.R;
import com.edu.gvn.medicaltreatments.common.RecyclerItemOnClickListener;
import com.edu.gvn.medicaltreatments.model.Hospital;

import java.util.ArrayList;

/**
 * Created by hnc on 24/10/2016.
 */

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Hospital> datas;
    private LayoutInflater inflater;
    protected RecyclerItemOnClickListener itemOnClickListener;

    public HospitalAdapter(Context context, ArrayList<Hospital> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_hospital, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtName.setText(datas.get(position).getHospitalName());
        holder.txtAddress.setText(datas.get(position).getHospitalAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnClickListener.onClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAddress;
        public ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txt_name_location);
            txtAddress = (TextView) itemView.findViewById(R.id.txt_address_location);

        }
    }
    public void setItemOnClickListener(RecyclerItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }
}
