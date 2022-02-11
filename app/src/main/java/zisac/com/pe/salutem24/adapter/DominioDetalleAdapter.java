package zisac.com.pe.salutem24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.entity.DominioDetalleEntity;
import zisac.com.pe.salutem24.entity.MedicoEntity;

public class DominioDetalleAdapter extends ArrayAdapter<DominioDetalleEntity> {
    private Context mContext;
    private ArrayList<DominioDetalleEntity> listState;
    private DominioDetalleAdapter myAdapter;
    private String tipoDD;
    private boolean isFromView = false;

    public DominioDetalleAdapter(Context context, int resource, List<DominioDetalleEntity> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<DominioDetalleEntity>) objects;
        this.myAdapter = this;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_combos, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView.findViewById(R.id.textSpinner);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(listState.get(position).getDescripcionDetalle());

        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
    }
}
