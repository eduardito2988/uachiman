package com.delhel.dorman.uachiman.Custom;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.delhel.dorman.uachiman.Detalle_encargos;
import com.delhel.dorman.uachiman.Clases.Modelo_encargo;
import com.delhel.dorman.uachiman.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 20/07/2016.
 */
public class AdaptadorRecycle extends RecyclerView.Adapter<AdaptadorRecycle.Holder> {

    List<Modelo_encargo> listado;
    Context context;

    public AdaptadorRecycle(List<Modelo_encargo> listado, Context context) {
        this.listado = listado;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_lista_encargo,parent,false);
        Holder hol = new Holder(view);
        return hol;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        holder.codigo.setText(listado.get(position).getEnt_codi_in20());
        holder.nombre.setText(listado.get(position).getEnc_deta_vc200());
        holder.cantida.setText(listado.get(position).getEnc_ingr_dati());

    }

    @Override
    public int getItemCount() {
        return listado.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView codigo , nombre,cantida ;

        public Holder(View itemView) {
            super(itemView);
            codigo=(TextView)itemView.findViewById(R.id.nom_invitado);
            nombre=(TextView)itemView.findViewById(R.id.dni_invitado);
            cantida=(TextView)itemView.findViewById(R.id.txt_hora);

            itemView.setOnClickListener(this);
        }




       @Override
        public void onClick(View view) {

            int position  =   getAdapterPosition();
           // String codigo = listado.get(position).getEnc_codi_in20();
            String nombres = listado.get(position).getEnc_deta_vc200();

            Intent intent = new Intent(context,Detalle_encargos.class);
            intent.putExtra("nombre",nombres);
            context.startActivity(intent);


        }


    }


    public void clear(){
        listado.clear();
        notifyDataSetChanged();
    }

    public void SetFiltrado(List<Modelo_encargo> listado_filter){

            listado = new ArrayList<>();
            listado.addAll(listado_filter);
            notifyDataSetChanged();

    }



}
