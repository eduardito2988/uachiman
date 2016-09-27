package com.delhel.dorman.uachiman.Custom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Clases.Modelo_invitado;
import com.delhel.dorman.uachiman.R;

import java.util.List;

/**
 * Created by Usuario on 16/08/2016.
 */
public class AdaptadorRecycle_add_invitado extends RecyclerView.Adapter<AdaptadorRecycle_add_invitado.Holder> {

    List<Modelo_invitado> listado;
    Context context;


    public AdaptadorRecycle_add_invitado(List<Modelo_invitado> listado, Context context) {

        this.listado = listado;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_lista_autocomplet_invitado,parent,false);
        Holder hol = new Holder(view);
        return hol;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        holder.nombre.setText(listado.get(position).getNombre());
        holder.dni.setText(listado.get(position).getDni());

        //   Picasso.with(context).load(listado.get(position).getFoto()).into(holder.foto);
      //  Picasso.with(context).load(listado.get(position).getFoto()).transform(new CircleTransform()).into(holder.foto);


    }

    @Override
    public int getItemCount() {
        return listado.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nombre,dni ;

        public Holder(View itemView) {
            super(itemView);

            nombre=(TextView)itemView.findViewById(R.id.nom_invitado);
            dni=(TextView)itemView.findViewById(R.id.dni_invitado);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {

            Toast.makeText(view.getContext(), "SELECCIONADO", Toast.LENGTH_SHORT).show();
        }
    }







}
