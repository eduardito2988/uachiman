package com.delhel.dorman.uachiman.Custom;

import android.content.Context;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Clases.Modelo_detalle_unididad_encargo;
import com.delhel.dorman.uachiman.Clases.Modelo_encargo;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Dao.Ddescargas;
import com.delhel.dorman.uachiman.Dao.Dencargos;
import com.delhel.dorman.uachiman.Printer.ConstantesPrinter;
import com.delhel.dorman.uachiman.Printer.ESCPos;
import com.delhel.dorman.uachiman.Printer.TypeTicket;
import com.delhel.dorman.uachiman.R;
import com.delhel.dorman.uachiman.Tablas.Tabla_detalle_encargos;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.zj.btsdk.BluetoothService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Usuario on 16/08/2016.
 */
public class AdaptadorRecycle_lista_encargos extends RecyclerView.Adapter<AdaptadorRecycle_lista_encargos.Holder> {

    List<Modelo_detalle_unididad_encargo> listado;
    Context context;
    public BluetoothService mService = null;

    public AdaptadorRecycle_lista_encargos(List<Modelo_detalle_unididad_encargo> listado, Context context, BluetoothService mService) {
        this.listado = listado;
        this.context = context;
        this.mService = mService;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_lista_encargo, parent, false);
        Holder hol = new Holder(view);
        return hol;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        holder.nombre.setText(listado.get(position).getEnc_deta_vc200());
        holder.hora.setText(listado.get(position).getEnc_ingr_dati());
        holder.tipo.setText(listado.get(position).getTen_deta_vc200());
        holder.puerta.setText(listado.get(position).getPuerta().toUpperCase());

    }

    /*
Añade una lista completa de items
*/
    public void addAll(List<Modelo_detalle_unididad_encargo> lista) {
        listado.addAll(listado);
        notifyDataSetChanged();
    }

    /*
    Permite limpiar todos los elementos del recycler
     */
    public void clear() {
        listado.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return listado.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nombre, hora, tipo , puerta;
        public ImageView boton;

        public Holder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.dni_invitado);
            hora = (TextView) itemView.findViewById(R.id.txt_hora);
            tipo = (TextView) itemView.findViewById(R.id.nom_invitado);
            boton = (ImageView) itemView.findViewById(R.id.btn_opsiones);
            puerta = (TextView) itemView.findViewById(R.id.lblpuerta);

            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    final int position = getAdapterPosition();
                    final int codigo_uni = listado.get(position).getUni_codi_in20();
                    final int codigo_enc = listado.get(position).getEnc_codi_in20();
                    Imprimir(view, codigo_uni, codigo_enc, position);

                }
            });

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
        }
    }

    public void Imprimir(final View view, final int cod_usu, final int cod_enc, final int position) {


        final String num_unidad = listado.get(position).getUni_numi_vc20();
        final String descripcion = listado.get(position).getEnc_deta_vc200();
        final String tipo_encar = listado.get(position).getTen_deta_vc200();
        final String fech_regis = listado.get(position).getEnc_ingr_dati();
        final String nom_unida = listado.get(position).getUsu_nomb_ch100();
        final String ape_unuda = listado.get(position).getUsu_apel_ch100();
        String nombre = nom_unida + " " + ape_unuda;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        String dia = df.format(c.getTime());


        AlertDialog.Builder builder;
        final AlertDialog alertDialog;
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.imprimir_entrega_encargo, null);

        TextView numero_unid = (TextView) layout.findViewById(R.id.num_unidad);
        TextView descrip = (TextView) layout.findViewById(R.id.detale_encargos);
        TextView tipo_enc = (TextView) layout.findViewById(R.id.tipo_entrega);
        TextView fech_ing = (TextView) layout.findViewById(R.id.fecha_ingreso);
        TextView fech_entr = (TextView) layout.findViewById(R.id.fecha_salida);
        TextView nombr_unida = (TextView) layout.findViewById(R.id.nom_endiad);


        numero_unid.setText(num_unidad);
        descrip.setText(descripcion);
        tipo_enc.setText(tipo_encar);
        fech_ing.setText(fech_regis);
        fech_entr.setText(dia);
        nombr_unida.setText(nombre);


        builder = new AlertDialog.Builder(view.getContext());
        builder.setView(layout);
        builder.setIcon(R.drawable.icon);
        alertDialog = builder.create();
        alertDialog.show();

        Button cancelar = (Button) layout.findViewById(R.id.btn_cancelar_imprecion);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

            }
        });

        Button imprimir = (Button) layout.findViewById(R.id.btn_imprimir_encargo);
        imprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EntregarEncargo(view, cod_usu, cod_enc, position);
                alertDialog.dismiss();

            }
        });
    }

    public void EntregarEncargo(final View view, int cod_usu, int cod_enc, final int position) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("cod_usuario", String.valueOf(cod_usu));
        requestParams.add("cod_encar", String.valueOf(cod_enc));

        RequestHandle post = client.post(Constantes.URL_ENTREGAR_ENCARGO, requestParams, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String msj;
                if (statusCode == 200) {

                    JSONObject o = null;


                    try {
                        JSONObject datos = new JSONObject(new String(responseBody));
                        msj = datos.getString("Mensaje");
                        // Log.e("resultado",""+msj);
                        listado.remove(position);
                        notifyDataSetChanged();

                        Toast.makeText(view.getContext(), "" + msj, Toast.LENGTH_SHORT).show();

                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put("entidad", "Delhel");
                        jsonObject.put("uni_numi_vc20", "21979");
                        jsonObject.put("puerta", "menriquez");
                        jsonObject.put("personal", "delhel delhel");
                        jsonObject.put("usuario", "Cuzcano Rivera");
                        jsonObject.put("fecha_ingreso", "2016-09-14 18:06:10");
                        jsonObject.put("fecha_entrega", "2016-09-14 18:06:20");
                        jsonObject.put("encargo", "sobre");
                        jsonObject.put("observacion", "Semi abierto");

                        ESCPos esc = new ESCPos();
                        SystemClock.sleep(1000);

                        mService.write(esc.setCharset(2));
                        mService.write(esc.feed(3));
                        mService.write(esc.setCharset(2));
                        mService.write(esc.setJustification(1));
                        mService.sendMessage(jsonObject.getString("entidad"), ConstantesPrinter.printer_charset);
                        mService.sendMessage(TypeTicket.ENCARGO.getTitle(context)
                                + "\n", ConstantesPrinter.printer_charset);
                        mService.write(esc.setCharacterSize(40)); // x 4
                        mService.sendMessage(jsonObject.getString("uni_numi_vc20")
                                .trim() + "\n", ConstantesPrinter.printer_charset);
                        mService.write(esc.setCharacterSize(0));
                        mService.write(esc.setJustification(0)); // x 1
                        mService.write(esc.printString(
                                context.getString(R.string.door) + "      ",
                                ConstantesPrinter.printer_charset));
                        mService.sendMessage(jsonObject.getString("puerta"), ConstantesPrinter.printer_charset);
                        mService.write(esc.printString(
                                context.getString(R.string.personal) + "  ",
                                ConstantesPrinter.printer_charset));
                        mService.sendMessage(jsonObject.getString("personal"), ConstantesPrinter.printer_charset); // json_inner.getString("conserje");
                        mService.write(esc.printString(
                                context.getString(R.string.user) + "   ", ConstantesPrinter.printer_charset));
                        mService.sendMessage(jsonObject.getString("usuario"), ConstantesPrinter.printer_charset);
                        mService.write(esc.printString(
                                context.getString(R.string.entry_date) + "   ",
                                ConstantesPrinter.printer_charset));
                        mService.sendMessage(jsonObject.getString("fecha_ingreso"),
                                ConstantesPrinter.printer_charset);
                        mService.write(esc.printString(
                                context.getString(R.string.delivery_date) + "   ",
                                ConstantesPrinter.printer_charset));
                        mService.sendMessage(jsonObject.getString("fecha_entrega"),
                                ConstantesPrinter.printer_charset);
                        mService.write(esc.printString(
                                context.getString(R.string.charge) + "   ",
                                ConstantesPrinter.printer_charset));
                        mService.sendMessage(jsonObject.getString("encargo"),
                                ConstantesPrinter.printer_charset);
                        mService.sendMessage(
                                "\n" + context.getString(R.string.observations)
                                        + " \n", ConstantesPrinter.printer_charset);
                        mService.sendMessage(jsonObject.getString("observacion"), ConstantesPrinter.printer_charset);
                        mService.write(esc.feed(3));

                        // JSONArray rpt = datos.optJSONArray(Constantes.RPT_ENCARGO_ENTREGA);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

        });

    }

    public void SetFiltrado(List<Modelo_encargo> listado_filter) {
        listado = new ArrayList<>();
        //      listado.addAll(listado_filter);
        notifyDataSetChanged();
    }

}
