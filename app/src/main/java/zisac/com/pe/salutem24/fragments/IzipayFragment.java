package zisac.com.pe.salutem24.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import zisac.com.pe.salutem24.R;

import zisac.com.pe.salutem24.dataBase.URLDaoImplement;
import zisac.com.pe.salutem24.dataBase.URLDaoInterface;
import zisac.com.pe.salutem24.entity.EspecialidadEntity;
import zisac.com.pe.salutem24.entity.PaymentData;
import zisac.com.pe.salutem24.entity.TurnoEntity;
import zisac.com.pe.salutem24.utils.Constantes;
import zisac.com.pe.salutem24.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IzipayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IzipayFragment extends Fragment {
    private View view;
    private WebView webView;
    private ProgressDialog popup;
    OnClickOpcionFragmento optionSelected;
    private ArrayList<TurnoEntity> turno;
    private Button btn_irconsulta;

    public IzipayFragment() {
        // Required empty public constructor
    }

    public interface OnClickOpcionFragmento{
        void OnClickOpcionFragmento(int opcion, ArrayList datos);
    }

    // TODO: Rename and change types and number of parameters
    public static IzipayFragment newInstance(String param1, String param2) {
        IzipayFragment fragment = new IzipayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_izipay,container, false);
        webView = (WebView)view.findViewById(R.id.webView_ip);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        //Obtener datos
        SharedPreferences prefs = getContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        String nombres = prefs.getString("nombres", "");
        String apellidos = prefs.getString("apellidos", "");
        String direccion = prefs.getString("direccion", "");
        String correo = prefs.getString("correo", "");

        Gson gson = new Gson();
        String json = prefs.getString("turno", null);
        Type type = new TypeToken<ArrayList<TurnoEntity>>(){}.getType();
        turno = gson.fromJson(json, type);
        //Log.i("detalle ID", turno.get(0).getTurnoDetalleEntity().getTurnoDetalleId());
        //Log.i("paciente ID", turno.get(0).getPaciente().getPacienteId());

        //URL: redirige-pago-app/:nombres/:apellidos/:direccion/:correo/:pacienteid/:importe/:turnodetalleid
        String url = Constantes.HTTP_WEB + "redirige-pago-app/" + nombres + "/" + apellidos+"/" + direccion + "/" + correo + "/" +
                turno.get(0).getPaciente().getPacienteId() + "/" + turno.get(0).getImporte() + "/" + turno.get(0).getTurnoDetalleEntity().getTurnoDetalleId();
        //Log.i("Url", url);
        webView.loadUrl(url);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_irconsulta = view.findViewById(R.id.btn_irconsulta);

        btn_irconsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.redOrWifiActivo(getActivity())) {
                    Toast.makeText(getActivity(), "VERIFIQUE SU CONEXIÓN A LA RED", Toast.LENGTH_LONG).show();
                    return ;
                }
                crearPopUpEspera();
                ObtenerEspecialidades obtenerEspecialidades = new ObtenerEspecialidades();
                obtenerEspecialidades.execute(Constantes.OPCION_CONSULTA_PROGRAMADA);
            }
        });
    }

    private class ObtenerEspecialidades extends AsyncTask<Integer,Void, EspecialidadEntity> {
        ArrayList<EspecialidadEntity> especialidadesTemp;
        private ArrayList<EspecialidadEntity> especialidades;
        private Integer irFragment;

        @Override
        protected EspecialidadEntity doInBackground(Integer... params) {
            irFragment = params[0];
            EspecialidadEntity especialidadEntity;
            URLDaoInterface dao = new URLDaoImplement();
            especialidades = dao.getEspecialidades(); //recogemos todas las especialidades
            if (especialidades != null) {
                //EspecialidadQuery especialidadQuery = new EspecialidadQuery(getActivity());
                //especialidadesTemp = especialidadQuery.insertarEpecialidades(especialidades); //inserta y recogemos solo las insertadas
                if(especialidades.size()>0) {
                    especialidadEntity = especialidades.get(0);
                }else{
                    especialidadEntity = null;
                }
            } else {
                especialidadEntity = new EspecialidadEntity();
            }
            return especialidadEntity;
        }

        @Override
        protected void onPostExecute(EspecialidadEntity especialidad) {
            cerrarPopUpEspera();

            if(especialidad!=null) {
                if (especialidad.getIsSuccess().equals("true")) {
                    notifyOptionSelected(irFragment, especialidades);
                    //Toast.makeText(getContext(), "Se listo satisfactoriamente", Toast.LENGTH_LONG).show();
                    //Toast.makeText(getContext(), especialidad.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Intente más tarde", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "No hay especialistas", Toast.LENGTH_LONG).show();
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
    }
    private void notifyOptionSelected(int opcion, ArrayList datos){
        optionSelected.OnClickOpcionFragmento(opcion, datos);
    }
}