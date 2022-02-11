package zisac.com.pe.salutem24.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.dataBase.URLDaoImplement;
import zisac.com.pe.salutem24.dataBase.URLDaoInterface;
import zisac.com.pe.salutem24.dialog.DatePickerFragment;
import zisac.com.pe.salutem24.entity.ConsultaEntity;
import zisac.com.pe.salutem24.entity.PacienteEntity;
import zisac.com.pe.salutem24.entity.TurnoEntity;
import zisac.com.pe.salutem24.entity.UsuarioEntity;
import zisac.com.pe.salutem24.utils.Constantes;
import zisac.com.pe.salutem24.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PagarConsultaFragment extends Fragment {

    OnClickOpcionFragmento optionSelected;
    EditText et_fecha;
    private View rootView;
    private ArrayList<TurnoEntity> turno;
    private ProgressDialog popup;
    private UsuarioEntity usuario;
    private PacienteEntity paciente;
    private EditText etMarca, etNroTarjeta, etCodigoSeguridad, etNombreTitular, etApellidoTitular, etCorreoTitular;
    private Button btn_pagar;

    public interface OnClickOpcionFragmento{
        void OnClickOpcionFragmento(int opcion, ArrayList datos);
    }

    public PagarConsultaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        turno = getArguments().getParcelableArrayList(Constantes.KEY_PAGO_CONSULTA);
        usuario = getArguments().getParcelable(Constantes.USUARIO);
        if(savedInstanceState!=null){
            turno = savedInstanceState.getParcelableArrayList(Constantes.KEY_PAGO_CONSULTA);
        }
        try {
            paciente = turno.get(0).getPaciente();
        }catch (Exception e){
            e.getMessage();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_pagar_consulta, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        etMarca = rootView.findViewById( R.id.etMarca );
        etNroTarjeta = rootView.findViewById( R.id.etNroTarjeta );
        et_fecha = rootView.findViewById( R.id.et_fecha_consulta );
        etCodigoSeguridad = rootView.findViewById(R.id.etCodigoSeguridad);
        etNombreTitular = rootView.findViewById(R.id.etNombreTitular);
        etApellidoTitular = rootView.findViewById(R.id.etApellidoTitular);
        etCorreoTitular = rootView.findViewById(R.id.etCorreoTitular);
        btn_pagar = rootView.findViewById(R.id.btn_pagar);

        String monto = turno.get(0).getImporte();
        btn_pagar.setText(monto);

        btn_pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validarCampos();
                if (!Utils.redOrWifiActivo(getActivity())) {
                    Toast.makeText(getActivity(), "VERIFIQUE SU CONEXIÓN A LA RED", Toast.LENGTH_LONG).show();
                    return ;
                }

                crearPopUpEspera();
                String pacienteId = turno.get(0).getPaciente().getPacienteId();
                String turnoDetalleId = turno.get(0).getTurnoDetalleEntity().getTurnoDetalleId();
                String inmediata = turno.get(0).getInmediata();
                InsertarConsulta insertConsulta = new InsertarConsulta();
                insertConsulta.execute(pacienteId, turnoDetalleId, inmediata);
            }
        });
    }

    private class InsertarConsulta extends AsyncTask<String,Void,ConsultaEntity> {
        private String consultaInmediata;
        @Override
        protected ConsultaEntity doInBackground(String... params) {
            String pacienteId=params[0], turnoDetalleId=params[1];
            ConsultaEntity consultaEntity = new ConsultaEntity();
            consultaInmediata =params[2];

            URLDaoInterface dao = new URLDaoImplement();
            String cadena = dao.postInsertarGet(pacienteId, turnoDetalleId, consultaInmediata); //recogemos todos los pacientes
            if (cadena != null) {
                String[] dosValores = cadena.split("-");
                String isSuccess = dosValores[0];
                String mensaje = dosValores[1];
                String consultaId = dosValores[2];
                if(isSuccess.equals("true")){
                    if(consultaInmediata.equals(Constantes.RESERVA_INMEDIATA)){
                        consultaEntity = dao.getConsulta(consultaId); //recogemos todos los pacientes
                    } else { //RESERVA_NORMAL
                        consultaEntity.setIsSuccess(isSuccess);
                        consultaEntity.setMessage(mensaje);
                    }
                } else {
                    consultaEntity = null;
                }
                //respuesta = "" + pacienteTmp.size();
            } else {
                consultaEntity = null;
            }
            return consultaEntity;
        }

        @Override
        protected void onPostExecute(ConsultaEntity consulta) {
            cerrarPopUpEspera();
            if(consulta != null) {
                if (consulta.getIsSuccess().equals("true")) {
                    if(consultaInmediata.equals(Constantes.RESERVA_NORMAL)) {
                        ArrayList<ConsultaEntity> datos = new ArrayList<>();
                        consulta.setConsultaInmediata(Constantes.RESERVA_NORMAL);
                        datos.add(consulta);

                        notifyOptionSelected(Constantes.OPCIONES_MENU, datos);
                        Toast.makeText(getContext(), consulta.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    if(consultaInmediata.equals(Constantes.RESERVA_INMEDIATA)) {
                        ArrayList<ConsultaEntity> datos = new ArrayList<>();
                        /*ConsultaEntity consulta = new ConsultaEntity();
                        consulta.setMedico(turno.get(0).getMedico());
                        consulta.setEspecialidad(turno.get(0).getEspecialidad());
                        consulta.setPaciente(turno.get(0).getPaciente().getApellidoPaterno());
                        consulta.setHorarioConsulta(turno.get(0).getMedico());
                        consulta.setConsultaInmediata(consultaInmediata);*/
                        //doctor, especialidad, paciente, hora
                        consulta.setConsultaInmediata(Constantes.RESERVA_INMEDIATA);
                        datos.add(consulta);
                        notifyOptionSelected(Constantes.OPCION_CONSULTA_ONLINE, datos);
                        Toast.makeText(getContext(), consulta.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "No se pudo registrar consulta, intente mas tarde", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "No se pudo registrar consulta, intente mas tarde", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void MonthYearShow( View view ) {
        final Calendar today = Calendar.getInstance();

        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(),
                new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        et_fecha.setText( (selectedMonth+1) + "/" + selectedYear );
                    }
                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

        builder.setActivatedMonth(Calendar.JULY)
                .setMinYear(today.get(Calendar.YEAR))
                .setActivatedYear(today.get(Calendar.YEAR))
                .setMaxYear(today.get(Calendar.YEAR) + 10)
                .setTitle("Selecciona mes y año")
                .build()
                .show();
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
