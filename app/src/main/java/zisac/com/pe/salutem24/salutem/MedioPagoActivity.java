package zisac.com.pe.salutem24.salutem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.adapter.MedioPagoAdapter;
import zisac.com.pe.salutem24.entity.MedioPago;

public class MedioPagoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medio_pago);

        // Create an ArrayList of Dessert objects
        ArrayList<MedioPago> medioPago = new ArrayList<MedioPago>();

        medioPago.add(new MedioPago("Tarjeta de crédito/débito", R.drawable.card));
        medioPago.add(new MedioPago("PagoEfectivo", R.drawable.pagoefectivo));

        // Create an {@link DessertAdapter}, whose data source is a list of
        // {@link Dessert}s. The adapter knows how to create list item views for each item
        // in the list.
        MedioPagoAdapter flavorAdapter = new MedioPagoAdapter(this, medioPago);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) findViewById(R.id.listview_mediopago);
        listView.setAdapter(flavorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Dessert dessert = desserts.get(i);
                /*switch(i) {
                    case 0:
                        Intent izipay = new Intent(MedioPagoActivity.this, IzipayActivity.class);
                        startActivity(izipay);
                        break;
                    case 1:
                        Intent pagoefectivo = new Intent(MedioPagoActivity.this, PagoEfectivoActivity.class);
                        startActivity(pagoefectivo);
                        break;
                }*/
            }
        });
    }
}