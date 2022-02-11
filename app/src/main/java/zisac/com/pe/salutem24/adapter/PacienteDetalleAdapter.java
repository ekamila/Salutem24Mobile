package zisac.com.pe.salutem24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.entity.PacienteEntity;

public class PacienteDetalleAdapter  extends ArrayAdapter<PacienteEntity> {
    private Context mContext;
    private ArrayList<PacienteEntity> listState;
    private PacienteDetalleAdapter myAdapter;
    private String tipoDD;
    private boolean isFromView = false;

    public PacienteDetalleAdapter(Context context, int resource, List<PacienteEntity> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<PacienteEntity>) objects;
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
        final PacienteDetalleAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_combos, null);
            holder = new PacienteDetalleAdapter.ViewHolder();
            holder.mTextView = (TextView) convertView.findViewById(R.id.textSpinner);
            convertView.setTag(holder);
        } else {
            holder = (PacienteDetalleAdapter.ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(listState.get(position).getNombres());

        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;
    }
}
