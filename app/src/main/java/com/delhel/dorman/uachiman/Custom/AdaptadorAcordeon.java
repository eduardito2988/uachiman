package com.delhel.dorman.uachiman.Custom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Clases.Acordeon.ChildrenEncargo;
import com.delhel.dorman.uachiman.Clases.Acordeon.HeaderEncargo;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Detalle_encargos;
import com.delhel.dorman.uachiman.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Usuario on 15/08/2016.
 */
public class AdaptadorAcordeon extends BaseExpandableListAdapter {

    Context _context;
    List<HeaderEncargo> _listDataHeader; // header titles
    HashMap<HeaderEncargo, List<ChildrenEncargo>> _listDataChild;

    public static ProgressDialog dialogo;


    public AdaptadorAcordeon(Context context, List<HeaderEncargo> listDataHeader, HashMap<HeaderEncargo, List<ChildrenEncargo>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final Capitalizar capitalizar = new Capitalizar();

        ChildrenEncargo childrenEncargo = (ChildrenEncargo) getChild(groupPosition, childPosition);

        String CANT = childrenEncargo.getCantida();
        String CHIL = childrenEncargo.getUsuario();

        /* variables necesarios para el detalle de los encargos del usuario */
        final int cod_unidad = childrenEncargo.getCod_unidad();
        final String unidad = childrenEncargo.getUnidad();
        final int cod_usuario = childrenEncargo.getCod_usuario();
        final String usuario = CHIL;
        final String foto = Constantes.RUTA_IMAGEN + "" + childrenEncargo.getFoto();


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView cantida = (TextView) convertView.findViewById(R.id.cant_enc);
        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        txtListChild.setText(capitalizar.ucWords(CHIL));
        cantida.setText(CANT);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogo = new ProgressDialog(_context);
                dialogo.setMessage("Espere un momento ...");
                dialogo.setProgressStyle(dialogo.STYLE_SPINNER);
                dialogo.show();

                Intent intent = new Intent(_context, Detalle_encargos.class);
                intent.putExtra("cod_unidad", cod_unidad);
                intent.putExtra("unidad", String.valueOf(unidad));
                intent.putExtra("usuario", capitalizar.ucWords(usuario));
                intent.putExtra("cod_usuario", cod_usuario);
                intent.putExtra("foto", foto);

                _context.startActivity(intent);

            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }


    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        HeaderEncargo headerEncargo = (HeaderEncargo) getGroup(groupPosition);

        Capitalizar capitalizar = new Capitalizar();

        if (convertView == null) {
            LayoutInflater infalInflater = LayoutInflater.from(this._context);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        TextView lblcantidad = (TextView) convertView.findViewById(R.id.cantidades);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerEncargo.getUni_numi_vc20() + " | " + capitalizar.ucWords(headerEncargo.getUsuario()));
        lblcantidad.setText(String.valueOf(headerEncargo.getCantidad()));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void SetFiltrado(List<HeaderEncargo> listado_filter) {
        _listDataHeader = new ArrayList<>();
        _listDataHeader.addAll(listado_filter);
        notifyDataSetChanged();
    }

    public void refrescar2(List<HeaderEncargo> listDataHeader) {

        Toast.makeText(_context, "ESTOY AQUI!", Toast.LENGTH_SHORT).show();
        _listDataHeader.clear();
        _listDataHeader = new ArrayList<>();
        _listDataHeader.addAll(listDataHeader);
         notifyDataSetChanged();

    }

    public void refrescar5(){
        notifyDataSetChanged();
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }
}
