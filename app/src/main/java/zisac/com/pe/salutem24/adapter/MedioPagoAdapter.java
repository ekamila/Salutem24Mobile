package zisac.com.pe.salutem24.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.entity.MedioPago;

public class MedioPagoAdapter extends ArrayAdapter<MedioPago> {
    private static final String LOG_TAG = MedioPagoAdapter.class.getSimpleName();

    public MedioPagoAdapter(Context context, ArrayList<MedioPago> desserts) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, desserts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_medio_pago, parent, false);
        }

        // Get the {@link Dessert} object located at this position in the list
        MedioPago pago = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.pago_name);
        // Get the version name from the current Dessert object and
        // set this text on the name TextView
        nameTextView.setText(pago.getDescripcion());

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.list_item_icon);
        // Get the image resource ID from the current Dessert object and
        // set the image to iconView
        iconView.setImageResource(pago.getImagen());

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
