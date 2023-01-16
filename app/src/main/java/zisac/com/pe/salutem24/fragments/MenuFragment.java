package zisac.com.pe.salutem24.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import javax.crypto.Cipher;

import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.dataBase.EspecialidadQuery;
import zisac.com.pe.salutem24.dataBase.URLDaoImplement;
import zisac.com.pe.salutem24.dataBase.URLDaoInterface;
import zisac.com.pe.salutem24.entity.EspecialidadEntity;
import zisac.com.pe.salutem24.entity.TurnoDetalleEntity;
import zisac.com.pe.salutem24.entity.TurnoEntity;
import zisac.com.pe.salutem24.entity.UsuarioEntity;
import zisac.com.pe.salutem24.salutem.Registro;
import zisac.com.pe.salutem24.utils.Constantes;
import zisac.com.pe.salutem24.utils.DateInputMask;
import zisac.com.pe.salutem24.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    private ProgressDialog popup;
    OnClickOpcionFragmento optionSelected;
    private View rootView;
    private Button btn_especialidades, btn_atencion, btn_consultas;
    private UsuarioEntity usuario;

    public MenuFragment() {
        // Required empty public constructor
    }

    public interface OnClickOpcionFragmento{
        void OnClickOpcionFragmento(int opcion, ArrayList datos);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usuario = getArguments().getParcelable(Constantes.USUARIO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn_especialidades = rootView.findViewById( R.id.btn_especialidades );
        btn_atencion = rootView.findViewById( R.id.btn_atencion );
        btn_consultas = rootView.findViewById( R.id.btn_consultas );

        addOnclickListener();
    }

    private void addOnclickListener(){
        btn_especialidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utils.redOrWifiActivo(getActivity())) {
                    Toast.makeText(getActivity(), "VERIFIQUE SU CONEXIÓN A LA RED", Toast.LENGTH_LONG).show();
                    return ;
                }

                crearPopUpEspera();
                ObtenerEspecialidades obtenerEspecialidades = new ObtenerEspecialidades();
                obtenerEspecialidades.execute(Constantes.OPCIONES_ESPECIALIDADES);
            }
        });

        btn_atencion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utils.redOrWifiActivo(getActivity())) {
                    Toast.makeText(getActivity(), "VERIFIQUE SU CONEXIÓN A LA RED", Toast.LENGTH_LONG).show();
                    return ;
                }

                crearPopUpEspera();
                ExisteMedicoDisponible existeMedicoDisponible = new ExisteMedicoDisponible();
                existeMedicoDisponible.execute();
            }
        });

        btn_consultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    private class ExisteMedicoDisponible extends AsyncTask<String, Void, TurnoEntity> {
        @Override
        protected TurnoEntity doInBackground(String... params) {
            TurnoEntity turnoEntity;
            URLDaoInterface dao = new URLDaoImplement();
            turnoEntity = dao.existeMedicoDisponible(usuario.getUsuarioId());

            return turnoEntity;
        }

        @Override
        protected void onPostExecute(TurnoEntity turno) {
            cerrarPopUpEspera();
            if(turno!=null) {
                if (turno.getIsSuccess().equals("true")) {
                    turno.setInmediata(Constantes.RESERVA_INMEDIATA);
                    ArrayList<TurnoEntity> datos = new ArrayList<>();
                    datos.add(turno);
                    notifyOptionSelected(Constantes.OPCION_DATOS_PACIENTES, datos);

                    Toast.makeText(getContext(), turno.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    //NO HAY MEDICO DISPONIBLE, POPUP
                    advertencia();
                    //Toast.makeText(getContext(), "No hay medico disponible", Toast.LENGTH_LONG).show();
                }
            } else {
                /*turno = new TurnoEntity();
                turno.setInmediata(Constantes.RESERVA_INMEDIATA);
                turno.setTurnoId("1");
                turno.setEspecialidadId("1");
                turno.setEspecialidad("Medicina General");
                turno.setMedicoId("11");
                turno.setMedico("Dr. Del Valle Yago");
                turno.setHorario("09:00-13:00");
                turno.setFechaTurno("2020-08-27");
                turno.setImporte("50.00");

                turno.setTurnoDetalleEntity(new TurnoDetalleEntity());
                turno.getTurnoDetalleEntity().setTurnoDetalleId("4");
                turno.getTurnoDetalleEntity().setEstadoTurno("Libre");
                turno.getTurnoDetalleEntity().setEstadoTurnoId("1");

                ArrayList<TurnoEntity> datos = new ArrayList<>();
                datos.add(turno);
                notifyOptionSelected(Constantes.OPCION_DATOS_PACIENTES, datos);*/
                    //NO HAY MEDICO DISPONIBLE, POPUP
                advertencia();
                //Toast.makeText(getContext(), "No hay medico disponible", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void advertencia(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("No hay medicos disponibles");
        dialog.setMessage("Seleccione un horario y reserve su consulta");
        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //dialog.setNegativeButton("NO", null);
        dialog.show();
    }

    private class ObtenerEspecialidades extends AsyncTask<Integer,Void,EspecialidadEntity> {
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
        else
            throw new ClassCastException();
    }

    private void notifyOptionSelected(int opcion, ArrayList datos){
        optionSelected.OnClickOpcionFragmento(opcion, datos);
    }
}
