package zisac.com.pe.salutem24.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.adapter.MedioPagoAdapter;
import zisac.com.pe.salutem24.entity.MedioPago;
import zisac.com.pe.salutem24.entity.TurnoEntity;
import zisac.com.pe.salutem24.salutem.PruebaActivity;
import zisac.com.pe.salutem24.utils.Constantes;

public class MedioPagoFragment extends Fragment {
    View rootView;
    private ArrayList<TurnoEntity> turno;
    OnClickOpcionFragmento optionSelected;

    public interface OnClickOpcionFragmento{
        void OnClickOpcionFragmento(int opcion, ArrayList datos);
    }
    public MedioPagoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_medio_pago, container, false);

        ArrayList<MedioPago> medioPago = new ArrayList<MedioPago>();

        medioPago.add(new MedioPago("Tarjeta de crédito/débito", R.drawable.card));
        //medioPago.add(new MedioPago("PagoEfectivo", R.drawable.pagoefectivo));
        // Create an {@link DessertAdapter}, whose data source is a list of
        // {@link Dessert}s. The adapter knows how to create list item views for each item
        // in the list.
        MedioPagoAdapter adapter = new MedioPagoAdapter(getContext(), medioPago);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_mediopago);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Dessert dessert = desserts.get(i);
                switch(i) {
                    case 0:
                        SharedPreferences prefs = getContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
                        Gson gson = new Gson();
                        String json = prefs.getString("turno", null);
                        Type type = new TypeToken<ArrayList<TurnoEntity>>(){}.getType();
                        turno = gson.fromJson(json, type);
                        notifyOptionSelected(Constantes.OPCION_PAGOS, turno);
                        break;
                    case 1:
                        notifyOptionSelected(Constantes.OPCION_PAGOEFECTIVO, null);
                        break;
                }
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if(activity instanceof DatosPacienteFragment.OnClickOpcionFragmento)
        {
            optionSelected = (OnClickOpcionFragmento)activity;
        }
        else
            throw new ClassCastException();
    }

    private void notifyOptionSelected(int opcion, ArrayList datos){
        optionSelected.OnClickOpcionFragmento(opcion, datos);
    }

}