package com.delhel.dorman.uachiman.Custom;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.delhel.dorman.uachiman.Clases.Puertas;
import com.delhel.dorman.uachiman.R;

import java.util.List;

/**
 * Created by Usuario on 17/08/2016.
 */
public class CustomSpinnerAdapterPuertas extends BaseAdapter implements SpinnerAdapter {

    Context context;
    List<Puertas> puertas;

    public CustomSpinnerAdapterPuertas(Context context, List<Puertas> puertas) {
        this.context = context;
        this.puertas = puertas;
    }

    @Override
    public int getCount() {
        return puertas.size();
    }

    @Override
    public Object getItem(int i) {
        return puertas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long)i;
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
            txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.open_exit_door, 0, 0  , 0);
        }

        txt.setText("  "+puertas.get(position).getPue_nomb_vc90());
        txt.setTextColor(Color.parseColor("#000000"));

        if(position%2 == 1)
            txt.setBackgroundColor(Color.parseColor("#E5E3E3"));
        else
            txt.setBackgroundColor(Color.parseColor("#FCF9F9"));

        return  txt;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView txt = new TextView(context);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.chevron_down, 0);
        txt.setText(puertas.get(i).getPue_nomb_vc90());
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }
}
