package com.delhel.dorman.uachiman.Custom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delhel.dorman.uachiman.Clases.Tiempos;
import com.delhel.dorman.uachiman.Interface.RecyclerViewOnClickListener;
import com.delhel.dorman.uachiman.R;

import java.util.List;

/**
 * Created by Usuario on 19/08/2016.
 */
public class CustomThAdaoter extends RecyclerView.Adapter<HolderTh> {

    Context context;
    List<Tiempos> data;
    private RecyclerViewOnClickListener mrecyclerViewOnClickListener;


    public CustomThAdaoter(Context context, List<Tiempos> tiempos) {
        this.context = context;
        this.data = tiempos;
    }

    @Override
    public HolderTh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_thorario, null);
        HolderTh holderTh = new HolderTh(view, data , mrecyclerViewOnClickListener);
        return holderTh;
    }

    @Override
    public void onBindViewHolder(HolderTh holder, int position) {
        Tiempos tiempos = data.get(position);
        holder.descripcion.setText(tiempos.getDescripcion());
        holder.check.setChecked(tiempos.isChecked());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public void setCheckBox(int position){
        //Update status of checkbox
        Tiempos tiempos = data.get(position);
        tiempos.setChecked(!tiempos.isChecked());
        notifyDataSetChanged();
    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener r){
        mrecyclerViewOnClickListener = r;
    }
}
