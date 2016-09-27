package com.delhel.dorman.uachiman.Custom;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Clases.Modelo_residentes;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Usuario on 16/08/2016.
 */
public class AdaptadorRecycle_residentes extends RecyclerView.Adapter<AdaptadorRecycle_residentes.Holder> {

    List<Modelo_residentes> listado;
    Context context;


    public AdaptadorRecycle_residentes(List<Modelo_residentes> listado, Context context) {
        this.listado = listado;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_lista_residentes,parent,false);
        Holder hol = new Holder(view);
        return hol;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        holder.nombre.setText(listado.get(position).getNombre()+" "+ listado.get(position).getApellido());
        holder.dni.setText(listado.get(position).getDni());
        String imagen = Constantes.RUTA_IMAGEN+""+listado.get(position).getFoto();
        //   Picasso.with(context).load(listado.get(position).getFoto()).into(holder.foto);
        Picasso.with(context).load(imagen).error(R.drawable.sin_foto).into(holder.foto);


    }

    @Override
    public int getItemCount() {
        return listado.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView codigo , nombre,dni ;
        final ImageView foto;
        public Button boton ;

        public Holder(View itemView) {

            super(itemView);

            nombre=(TextView)itemView.findViewById(R.id.nom_residente);
            dni=(TextView)itemView.findViewById(R.id.dni);
            foto=(ImageView)itemView.findViewById(R.id.foto_residente);
            boton =(Button)itemView.findViewById(R.id.btn_asistensia);

            itemView.setOnClickListener(this);


            foto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position  =   getAdapterPosition();
                    String nom = listado.get(position).getNombre();
                    String imagen = Constantes.RUTA_IMAGEN+""+listado.get(position).getFoto();

                    AlertDialog.Builder builder;
                    AlertDialog alertDialog;

                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View layout = inflater.inflate(R.layout.imagen_dialog, null);

                    TextView text = (TextView) layout.findViewById(R.id.texto_dialog);
                    text.setText(nom);

                    ImageView image = (ImageView) layout.findViewById(R.id.imagen_dialog);
                   // image.setImageResource(R.drawable.avatar_roy);
                    Picasso.with(context).load(imagen).error(R.drawable.sin_foto).into(image);
                    builder = new AlertDialog.Builder(context);
                    builder.setView(layout);
                    alertDialog = builder.create();
                    alertDialog.show();



                    // Toast.makeText(context, "clik en la foto", Toast.LENGTH_SHORT).show();
                }
            });



        }

        @Override
        public void onClick(View view) {
            int position  =   getAdapterPosition();
            String codigo = listado.get(position).getCodigo();
            Toast.makeText(context, "clik en item"+codigo, Toast.LENGTH_SHORT).show();

        }









    }







}
