package com.delhel.dorman.uachiman.Custom;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.delhel.dorman.uachiman.Clases.Modelo_tipo_encargo;
import com.delhel.dorman.uachiman.R;

import java.util.List;

/**
 * Created by Usuario on 22/09/2016.
 */
public class CustomSpinnerAdapteTencargo extends BaseAdapter implements SpinnerAdapter {

    Context context;
    List<Modelo_tipo_encargo> modelo_tipo_encargos;

    public CustomSpinnerAdapteTencargo(Context context,List<Modelo_tipo_encargo> modelo_tipo_encargos) {
        this.modelo_tipo_encargos = modelo_tipo_encargos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return modelo_tipo_encargos.size();
    }

    @Override
    public Object getItem(int position) {
        return modelo_tipo_encargos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(context);
        txt.setPadding(25, 25, 25, 25);
        txt.setTextSize(18);

        if(position==0) {
            txt.setGravity(Gravity.CENTER);
        }else{
            txt.setGravity(Gravity.CENTER_VERTICAL);
        }

        txt.setText("  "+modelo_tipo_encargos.get(position).getDetalle());
        txt.setTextColor(Color.parseColor("#000000"));

        if(position%2 == 1)
            txt.setBackgroundColor(Color.parseColor("#E5E3E3"));
        else
            txt.setBackgroundColor(Color.parseColor("#FCF9F9"));

        return  txt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(context);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.chevron_down, 0);
        txt.setText(modelo_tipo_encargos.get(position).getDetalle());
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }
}
