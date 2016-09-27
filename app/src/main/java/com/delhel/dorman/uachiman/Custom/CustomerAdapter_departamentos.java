package com.delhel.dorman.uachiman.Custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.delhel.dorman.uachiman.R;
import com.delhel.dorman.uachiman.Clases.Modelo_departamentos;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Usuario on 22/08/2016.
 */
public class CustomerAdapter_departamentos extends ArrayAdapter<Modelo_departamentos> {
    private LayoutInflater layoutInflater;
    List<Modelo_departamentos> mCustomers;

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((Modelo_departamentos)resultValue).getUni_numi_vc20();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {

                ArrayList<Modelo_departamentos> suggestions = new ArrayList<Modelo_departamentos>();

                for (Modelo_departamentos customer : mCustomers) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (customer.getUsu_nomb_ch100().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(customer);
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<Modelo_departamentos>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(mCustomers);
            }
            notifyDataSetChanged();
        }
    };

    public CustomerAdapter_departamentos(Context context, int textViewResourceId, List<Modelo_departamentos> customers) {
        super(context, textViewResourceId, customers);
        // copy all the customers into a master list
        mCustomers = new ArrayList<Modelo_departamentos>(customers.size());
        mCustomers.addAll(customers);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.customer_auto, null);
        }

        Modelo_departamentos customer = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.customerNameLabel);
        String nombre = customer.getUni_numi_vc20()+" | "+customer.getUs_pel_ch100()+" "+customer.getUsu_nomb_ch100();
        name.setText(nombre);


        return view;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }




}