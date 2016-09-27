package com.delhel.dorman.uachiman.Custom;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.delhel.dorman.uachiman.Clases.Repartidor;
import com.delhel.dorman.uachiman.R;

import java.util.List;

/**
 * Created by Usuario on 12/09/2016.
 */
public class CustomSpinnerUsuario extends BaseAdapter implements SpinnerAdapter {

    Context context;
    List<Repartidor> repartidor;

    public CustomSpinnerUsuario(Context context, List<Repartidor> repartidor) {
        this.context = context;
        this.repartidor = repartidor;
    }

    @Override
    public int getCount() {
        return (repartidor.size()==0) ? 0 : repartidor.size();
    }

    @Override
    public Object getItem(int position) {
        return repartidor.get(position);
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
            txt.setText(repartidor.get(position).getRvi_nomb_vc200());
        }else{
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(" "+repartidor.get(position).getRvi_dnii_in20()+" / "+ Capitalizar.ucWords(repartidor.get(position).getRvi_nomb_vc200()));
            txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.waiter, 0, 0  , 0);
        }

        txt.setTextColor(Color.parseColor("#000000"));

        if(position%2 == 1)
            txt.setBackgroundColor(Color.parseColor("#E5E3E3"));
        else
            txt.setBackgroundColor(Color.parseColor("#FCF9F9"));

        return  txt;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        TextView txt = new TextView(context);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.chevron_down, 0);
        txt.setText(Capitalizar.ucWords(repartidor.get(i).getRvi_nomb_vc200()));
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }
}
