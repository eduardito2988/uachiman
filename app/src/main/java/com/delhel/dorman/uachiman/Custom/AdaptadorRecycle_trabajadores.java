package com.delhel.dorman.uachiman.Custom;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delhel.dorman.uachiman.Clases.Modelo_horario_trabajadores;
import com.delhel.dorman.uachiman.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Usuario on 17/08/2016.
 */
public class AdaptadorRecycle_trabajadores extends RecyclerView.Adapter<AdaptadorRecycle_trabajadores.Holder> {

    List<Modelo_horario_trabajadores> listado;
    Context context;
    String rutaimg;


    public AdaptadorRecycle_trabajadores(List<Modelo_horario_trabajadores> listado, Context context) {
        this.listado = listado;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_lista_trabajadores, parent, false);
        Holder hol = new Holder(view);
        return hol;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        String ingresos, salidas,dia;
        ingresos = listado.get(position).getHorainicio();
        salidas = listado.get(position).getHorafin();
        dia = listado.get(position).getDia();

        holder.dia.setText(dia);
        holder.ingreso.setText(ingresos);
        holder.salida.setText(salidas);

        //obtener dia
        Capitalizar mayuscula = new Capitalizar();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEEE");
        String hoy = df.format(c.getTime());
        String diasemana = mayuscula.ucFirst(hoy);
        String diahoy = mayuscula.ucFirst(dia);

        if(diahoy.equals(diasemana)){

            holder.car.setBackgroundResource(R.drawable.boton_rojo_cuadra);
            holder.dia.setTextColor(Color.parseColor("#FFFFFF"));
            holder.ingreso.setTextColor(Color.parseColor("#FFFFFF"));
            holder.salida.setTextColor(Color.parseColor("#FFFFFF"));
            holder.text_ingreso.setTextColor(Color.parseColor("#FFFFFF"));
            holder.text_salida.setTextColor(Color.parseColor("#FFFFFF"));

        }



//        holder.foto.setText(listado.get(position).getCantidad());


    }

    @Override
    public int getItemCount() {
        return listado.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView dia,ingreso,salida,text_ingreso,text_salida;
        public LinearLayout car;
        public Holder(View itemView) {
            super(itemView);

            dia = (TextView) itemView.findViewById(R.id.dia);
            ingreso = (TextView) itemView.findViewById(R.id.hora_ingreso);
            salida = (TextView) itemView.findViewById(R.id.hora_salida);
            car = (LinearLayout) itemView.findViewById(R.id.fila_hora);
            text_ingreso =(TextView) itemView.findViewById(R.id.text_ingreso);
            text_salida =(TextView) itemView.findViewById(R.id.text_salida);


        }
    }


    public void SetFiltrado(List<Modelo_horario_trabajadores> listado_filter) {

        listado = new ArrayList<>();
        listado.addAll(listado_filter);
        notifyDataSetChanged();

    }

    /*
Añade una lista completa de items
*/
    public void addAll(List<Modelo_horario_trabajadores> lista){
        listado.addAll(listado);
        notifyDataSetChanged();
    }

    /*
    Permite limpiar todos los elementos del recycler
     */
    public void clear(){
        //listado.clear();
        notifyDataSetChanged();
    }


}


