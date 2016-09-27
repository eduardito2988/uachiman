package com.delhel.dorman.uachiman.Custom;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Detalle_encargos;
import com.delhel.dorman.uachiman.R;
import com.delhel.dorman.uachiman.Clases.Modelo_departamento_con_encargo;
import com.delhel.dorman.uachiman.Clases.Modelo_encargo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 16/08/2016.
 */
public class AdaptadorRecycle_Entidades_con_encargos extends RecyclerView.Adapter<AdaptadorRecycle_Entidades_con_encargos.Holder> {

    List<Modelo_departamento_con_encargo> listado;
    Context context;


    public AdaptadorRecycle_Entidades_con_encargos(List<Modelo_departamento_con_encargo> listado, Context context) {
        this.listado = listado;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_lista_salida_encargo, parent, false);
        Holder hol = new Holder(view);
        return hol;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        String nombre = listado.get(position).getNombre()+" "+listado.get(position).getApellid();
        String cant = String.valueOf(listado.get(position).getCantidad());
        holder.nomusu.setText(nombre);
        holder.entidad.setText(listado.get(position).getUni_numi());
        holder.cantida.setText(cant);

    }

    @Override
    public int getItemCount() {
        return listado.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView entidad, nomusu, cantida;

        public Holder(View itemView) {
            super(itemView);

            entidad = (TextView) itemView.findViewById(R.id.num_departamento);
            nomusu = (TextView) itemView.findViewById(R.id.nom_entidad);
            cantida = (TextView) itemView.findViewById(R.id.cantidades);
            itemView.setOnClickListener(this);



        }

        @Override
        public void onClick(View view) {

            int position  =   getAdapterPosition();
            int codigo = listado.get(position).getCod_usuario();
            String unidad = listado.get(position).getUni_numi();
            int uni_num = listado.get(position).getNum_unidad();
            String nombres = listado.get(position).getNombre()+" "+listado.get(position).getApellid();
            String foto = Constantes.RUTA_IMAGEN+""+listado.get(position).getFoto();

            Toast.makeText(context, "-->"+foto, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context,Detalle_encargos.class);

            intent.putExtra("codigo",String.valueOf(codigo));
            intent.putExtra("unidad",String.valueOf(unidad));
            intent.putExtra("num_unida",String.valueOf(uni_num));
            intent.putExtra("nombre",nombres);
            intent.putExtra("foto",foto);
            context.startActivity(intent);

            Log.e("cod","-->"+unidad+"--"+codigo);
           // Toast.makeText(context, "long"+codigo, Toast.LENGTH_SHORT).show();



        }
    }

    /*
  Añade una lista completa de items
   */
    public void addAll(List<Modelo_departamento_con_encargo> lista){
        listado.addAll(lista);
        notifyDataSetChanged();
    }

    /*
    Permite limpiar todos los elementos del recycler
     */
    public void clear(){
        listado.clear();
        notifyDataSetChanged();
    }

    public void clear2(){
        notifyDataSetChanged();
    }




    public void SetFiltrado(List<Modelo_encargo> listado_filter) {

        listado = new ArrayList<>();
        //listado.addAll(listado_filter);
        notifyDataSetChanged();

    }

}
