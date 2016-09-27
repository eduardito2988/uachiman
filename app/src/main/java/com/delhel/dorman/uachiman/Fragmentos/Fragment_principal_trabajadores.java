package com.delhel.dorman.uachiman.Fragmentos;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Custom.AdaptadorRecycle_trabajadores;
import com.delhel.dorman.uachiman.Custom.CircleTransform;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Dao.Dencargos;
import com.delhel.dorman.uachiman.Clases.Modelo_horario_trabajadores;
import com.delhel.dorman.uachiman.Clases.Modelo_perfil_trabajadores;
import com.delhel.dorman.uachiman.R;
import com.delhel.dorman.uachiman.Sp.QuickstartPreferences;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_principal_trabajadores extends Fragment  {


    public Fragment_principal_trabajadores() {
        // Required empty public constructor
    }

    View rootView;
    SearchView searchView;
    RecyclerView recyclerView;
    List<Modelo_perfil_trabajadores> list;
    AdaptadorRecycle_trabajadores trabajadores;
    Dencargos myDaoSQLite;
    EditText dni;
    TextView apellido,nombre,ingreso,salida,dia;
    ImageView img_trabjador,cheken;
    Button btnbuscar;
    ProgressDialog pDialog;
    String img_tra;
    int ENTIDAD,PUERTA;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_principal_trabajadores, container, false);

        getActivity().setTitle("TRABAJADORES");// TITULO FRAGMENT

        SharedPreferences sharedPreferencesE = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ENTIDAD= sharedPreferencesE.getInt(QuickstartPreferences.COD_ENTIDAD, 0);
        PUERTA= sharedPreferencesE.getInt(QuickstartPreferences.COD_PUERTA, 0);

        dni =(EditText)rootView.findViewById(R.id.bucardni);
        btnbuscar = (Button) rootView.findViewById(R.id.btnbuscardni);
        apellido = (TextView) rootView.findViewById(R.id.texto_Apellidos_tra);
        nombre = (TextView) rootView.findViewById(R.id.txt_nombre_tra);
        ingreso = (TextView) rootView.findViewById(R.id.ingreso_tra);
        salida = (TextView) rootView.findViewById(R.id.salida_tra);
        dia = (TextView) rootView.findViewById(R.id.dia_tra);
        img_trabjador = (ImageView) rootView.findViewById(R.id.img_trabjador);
        cheken = (ImageView) rootView.findViewById(R.id.chek_estado);


        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(btnbuscar.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);


        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(dni.length()!=0){

                    if(dni.length()<8){

                        Toast.makeText(getActivity(), "Ingrese DNI Correcto 8 digitos", Toast.LENGTH_SHORT).show();
                    }else{

                         String dnitrabajador = dni.getText().toString();
                        ConsultarTrabajador(dnitrabajador,ENTIDAD);

                        Log.e("Entidad","-->"+ENTIDAD);



                    }

                }else{
                    Toast.makeText(getActivity(), "Ingrese dni", Toast.LENGTH_SHORT).show();
                }


            }
        });


        recyclerView = (RecyclerView)rootView.findViewById(R.id.id_recycle_trabajador);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return  rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public  void ConsultarTrabajador(String dni ,int entida){


        /** POST ENVIO A NUVE*/
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("dni", dni);
        requestParams.add("entidad", String.valueOf(entida));

       Log.e("datos","DNI"+dni+"entidad"+entida);

        RequestHandle post = client.post(Constantes.URL_CONSULTA_DNI_TRBAJADOR, requestParams, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("Buscando, espere...");
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setCancelable(true);
                //  pDialog.show();
                pDialog.setProgress(0);
                pDialog.show();

            }

            @Override
            public void onFinish() {
                super.onFinish();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String  msje;
                if (statusCode == 200) {
                    JSONObject o = null;
                    try {
                        JSONObject datos = new JSONObject(new String(responseBody));
                        String msj = datos.getString("Mensajes");

                        JSONArray en = datos.optJSONArray(Constantes.DATOS_DATOS_TRABAJADOR);

                        if(msj.length()>0) {
                            pDialog.dismiss();
                            Toast.makeText(getActivity(), "No se encontro usuario", Toast.LENGTH_SHORT).show();
                        }else {

                            JSONObject jsonTrabajador = en.getJSONObject(0);

                            String inicio = jsonTrabajador.getString("hor_pent_time");
                            String salir = jsonTrabajador.getString("hor_psali_time");
                            String estado = jsonTrabajador.getString("acceso");

                            apellido.setText(jsonTrabajador.getString("usu_apel_ch100"));
                            nombre.setText(jsonTrabajador.getString("usu_nomb_ch100"));
                            dia.setText(jsonTrabajador.getString("dia_semana").toUpperCase());
                            ingreso.setText(inicio);
                            salida.setText(salir);
                            img_tra = Constantes.RUTA_IMAGEN_TRABAJADOR + jsonTrabajador.getString("usu_foto_long");

                            Picasso.with(getActivity())
                                    .load(img_tra)
                                    .error(R.drawable.sin_foto)
                                    .transform(new CircleTransform()).into(img_trabjador);

                            /*
                            int horaing= Integer.parseInt(formato(inicio,0,2));
                            int horafin = Integer.parseInt(formato(salir,0,2));

                            Calendar calendario = new GregorianCalendar();
                            int horas =calendario.get(Calendar.HOUR_OF_DAY);*/

                            if (estado.equals("NO")) {
                                Picasso.with(getActivity()).load(R.drawable.denegado2).into(cheken);
                            } else if (estado.equals("SI")) {
                                Picasso.with(getActivity()).load(R.drawable.verificado).into(cheken);
                            }

                            JSONArray hora = datos.optJSONArray(Constantes.DATOS_HORARIOS_TRABAJADOR);
                            final List<Modelo_horario_trabajadores> listado = new ArrayList<Modelo_horario_trabajadores>();

                            for (int i = 0; i < hora.length(); i++) {
                                JSONObject jsonHorarios = hora.getJSONObject(i);

                                Modelo_horario_trabajadores horario = new Modelo_horario_trabajadores();
                                horario.setDia(jsonHorarios.getString("dia_semana"));
                                horario.setHorainicio(jsonHorarios.getString("hor_pent_time"));
                                horario.setRefrigerio(jsonHorarios.getString("hor_refr_time"));
                                horario.setHorafin(jsonHorarios.getString("hor_psali_time"));
                                listado.add(horario);
                                //   Log.e("nombre","->"+jsonEncargo.getString("usu_nomb_ch100"));
                            }


                            trabajadores = new AdaptadorRecycle_trabajadores(listado, getActivity());
                            trabajadores.clear();
                            recyclerView.setAdapter(trabajadores);

                            img_trabjador.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    String nom = nombre.getText().toString() + " " + apellido.getText().toString();

                                    AlertDialog.Builder builder;
                                    AlertDialog alertDialog;
                                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    View layout = inflater.inflate(R.layout.imagen_dialog, null);
                                    TextView text = (TextView) layout.findViewById(R.id.texto_dialog);
                                    text.setText(nom);
                                    ImageView image = (ImageView) layout.findViewById(R.id.imagen_dialog);
                                    Picasso.with(getActivity()).load(img_tra).error(R.drawable.sin_foto).into(image);
                                    builder = new AlertDialog.Builder(getActivity());
                                    builder.setView(layout);
                                    alertDialog = builder.create();
                                    alertDialog.show();

                                }
                            });

                            LinearLayout linear = (LinearLayout) rootView.findViewById(R.id.con_contenido);
                            linear.setVisibility(View.VISIBLE);

                            pDialog.dismiss();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
               pDialog.dismiss();
                Toast.makeText(getActivity(), "Error de Busqueda.", Toast.LENGTH_SHORT).show();
            }

        });

}

    public String formato (String hora,int star,int end){

        String sCadena = hora;
        String horas = sCadena.substring(star,end);

        return horas;
    }


    private List<Modelo_perfil_trabajadores> filter(List<Modelo_perfil_trabajadores> lisModelos, String texto){

        texto=texto.toLowerCase();

        final List<Modelo_perfil_trabajadores> filtrando = new ArrayList<>();

        for(Modelo_perfil_trabajadores mod : lisModelos){

            final String dato = mod.getDni().toLowerCase();

            if(dato.contains(texto)){
                filtrando.add(mod);
            }
        }
        return  filtrando;
    }





}
