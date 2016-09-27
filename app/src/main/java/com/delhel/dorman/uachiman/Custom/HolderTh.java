package com.delhel.dorman.uachiman.Custom;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.delhel.dorman.uachiman.Clases.Tiempos;
import com.delhel.dorman.uachiman.Interface.RecyclerViewOnClickListener;
import com.delhel.dorman.uachiman.R;


import java.util.List;

/**
 * Created by Usuario on 19/08/2016.
 */
public class HolderTh extends RecyclerView.ViewHolder {

    List<Tiempos> tiempos;
    TextView descripcion;
    CheckBox check;

    public HolderTh(final View itemView, final List<Tiempos> tiempos, final RecyclerViewOnClickListener recyclerViewOnClickListener) {
        super(itemView);

        descripcion = (TextView) itemView.findViewById(R.id.descripcion);

        check = (CheckBox) itemView.findViewById(R.id.check);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean estado) {
                recyclerViewOnClickListener.onClickListener(estado,getAdapterPosition());
            }
        });

        descripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerViewOnClickListener.onClickListenerDescripcion(getAdapterPosition());

            }
        });



    }
}
