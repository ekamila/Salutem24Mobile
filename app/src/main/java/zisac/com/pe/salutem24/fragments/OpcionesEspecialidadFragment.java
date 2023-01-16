package zisac.com.pe.salutem24.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.viewpager.widget.ViewPager;
import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.dataBase.EspecialidadQuery;
import zisac.com.pe.salutem24.dataBase.MedicoQuery;
import zisac.com.pe.salutem24.dataBase.URLDaoImplement;
import zisac.com.pe.salutem24.dataBase.URLDaoInterface;
import zisac.com.pe.salutem24.entity.EspecialidadEntity;
import zisac.com.pe.salutem24.entity.EstudiosEntity;
import zisac.com.pe.salutem24.entity.MedicoEntity;
import zisac.com.pe.salutem24.entity.UsuarioEntity;
import zisac.com.pe.salutem24.utils.Constantes;
import zisac.com.pe.salutem24.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpcionesEspecialidadFragment extends Fragment {
    OnClickOpcionFragmento optionSelected;
    private LinearLayout content_especialidades;
    private View rootView;
    private ArrayList<EspecialidadEntity> especialidades;
    private ProgressDialog popup;
    private UsuarioEntity usuario;

    public interface OnClickOpcionFragmento{
        void OnClickOpcionFragmento(int opcion, ArrayList datos);
    }

    public OpcionesEspecialidadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        especialidades = getArguments().getParcelableArrayList(Constantes.KEY_DETALLE_ESPECIALIDADES);
        usuario = getArguments().getParcelable(Constantes.USUARIO);
        if(savedInstanceState!=null){
            especialidades = savedInstanceState.getParcelableArrayList(Constantes.KEY_DETALLE_ESPECIALIDADES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_opciones_especialidad, container, false);
        content_especialidades = rootView.findViewById(R.id.content_especialidades);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try{
            if(especialidades!=null){
                if(especialidades.size()>0){
                    generarFilasEspecialidad();
                }
            }
        }catch(Exception e){
            Log.e("ERROR", Log.getStackTraceString(e));
        }
    }

    private void generarFilasEspecialidad(){
        int total = especialidades.size();
        for(int i=0; i<total; i++){
            final EspecialidadEntity especialidad = especialidades.get(i);

            LinearLayout.LayoutParams paramFila = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 120);
            paramFila.setMargins(10,20,10,10);
            LinearLayout fila = new LinearLayout(getActivity());

            fila.setOrientation(LinearLayout.HORIZONTAL);
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                fila.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.background_card_orange) );
            } else {
                fila.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.background_card_orange));
            }
            if(sdk >= Build.VERSION_CODES.LOLLIPOP) {
                fila.setElevation(6);
            }
            fila.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!Utils.redOrWifiActivo(getActivity())) {
                        Toast.makeText(getActivity(), "VERIFIQUE SU CONEXIÓN A LA RED", Toast.LENGTH_LONG).show();
                        return ;
                    }

                    crearPopUpEspera();
                    ObtenerMedicos obtenerMedicos = new ObtenerMedicos();
                    obtenerMedicos.execute(especialidad);
                }
            });

            LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(60, 60);
            param1.gravity = Gravity.CENTER;
            param1.setMargins(0,0,20,0);
            ImageView imageTmp = new ImageView(getActivity());
            imageTmp.setPadding(12, 12, 12, 12);
            imageTmp.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.background_circle));
            imageTmp.setImageBitmap(especialidad.getBitmapResource());
            if(sdk >= Build.VERSION_CODES.LOLLIPOP) {
                imageTmp.setElevation(6);
            }

            LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            param2.gravity = Gravity.CENTER;
            TextView nombreEspecialidad = new TextView(getActivity());
            nombreEspecialidad.setTextSize(18);
            nombreEspecialidad.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
            nombreEspecialidad.setText(especialidad.getNombre());

            fila.addView(imageTmp,param1);
            fila.addView(nombreEspecialidad,param2);
            //agregamos la fila
            content_especialidades.addView(fila,paramFila);
        }
    }

    private class ObtenerMedicos extends AsyncTask<EspecialidadEntity,Void,MedicoEntity> {
        ArrayList<MedicoEntity> medicoTemp;
        ArrayList<MedicoEntity> medicos;
        @Override
        protected MedicoEntity doInBackground(EspecialidadEntity... params) {
            MedicoEntity medicoEntity;
            URLDaoInterface dao = new URLDaoImplement();
            medicos = dao.getMedicosxEspecialidad(params[0].getEspecialidadId()); //recogemos todos los medicos
            if (medicos != null) {
                MedicoQuery especialidadQuery = new MedicoQuery(getActivity());
                medicoTemp = especialidadQuery.insertarMedicoxEpecialidade(medicos); //inserta y recogemos solo las insertadas

                //respuesta = "" + medicoTemp.size();
                medicoEntity = medicos.get(0);
            } else {
                medicoEntity = null;
            }
            return medicoEntity;
        }

        @Override
        protected void onPostExecute(MedicoEntity medico) {
            cerrarPopUpEspera();
            if(medico != null) {
                if (medico.getIsSuccess().equals("true")) {
                    notifyOptionSelected(Constantes.OPCIONES_HORARIOS, medicos);
                    //Toast.makeText(getContext(), medico.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se pudo listar, intente más tarde", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "No se pudo listar, intente más tarde", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void crearPopUpEspera(){
        if(popup==null) {
            popup = new ProgressDialog(getActivity());
            popup.setIndeterminate(true);
            popup.setCancelable(false);
            popup.show();
            popup.setContentView(R.layout.custom_progress_dialog);
        }
    }
    private void cerrarPopUpEspera(){
        if(popup!=null && popup.isShowing()){
            popup.dismiss();
        }
        popup = null;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if(activity instanceof OnClickOpcionFragmento)
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
