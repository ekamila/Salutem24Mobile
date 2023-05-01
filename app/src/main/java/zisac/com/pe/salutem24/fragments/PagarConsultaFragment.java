package zisac.com.pe.salutem24.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.dataBase.URLDaoImplement;
import zisac.com.pe.salutem24.dataBase.URLDaoInterface;
import zisac.com.pe.salutem24.dialog.DatePickerFragment;
import zisac.com.pe.salutem24.entity.ConsultaEntity;
import zisac.com.pe.salutem24.entity.PacienteEntity;
import zisac.com.pe.salutem24.entity.PagoEntity;
import zisac.com.pe.salutem24.entity.TurnoEntity;
import zisac.com.pe.salutem24.entity.UsuarioEntity;
import zisac.com.pe.salutem24.salutem.LoginActivity;
import zisac.com.pe.salutem24.salutem.PruebaActivity;
import zisac.com.pe.salutem24.utils.Constantes;
import zisac.com.pe.salutem24.utils.DateInputMask;
import zisac.com.pe.salutem24.utils.StringUtils;
import zisac.com.pe.salutem24.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PagarConsultaFragment extends Fragment {

    OnClickOpcionFragmento optionSelected;
    private View rootView;
    private ArrayList<TurnoEntity> turno;
    private ProgressDialog popup;
    private UsuarioEntity usuario;
    private PacienteEntity paciente;
    //private EditText et_fecha, etNroTarjeta, etCodigoSeguridad,
    private EditText etNombreTitular, etApellidoTitular, etCorreoTitular, etDireccionTitular;
    //private Button btn_pagar;
    private Button btn_continuar_ip;


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

        //etNroTarjeta = rootView.findViewById( R.id.etNroTarjeta );
        //et_fecha = rootView.findViewById( R.id.et_fecha );
        //etCodigoSeguridad = rootView.findViewById(R.id.etCodigoSeguridad);
        etNombreTitular = rootView.findViewById(R.id.etNombreTitular);
        etApellidoTitular = rootView.findViewById(R.id.etApellidoTitular);
        etCorreoTitular = rootView.findViewById(R.id.etCorreoTitular);
        etDireccionTitular = rootView.findViewById(R.id.etDireccionTitular);
        //btn_pagar = rootView.findViewById(R.id.btn_pagar);
        btn_continuar_ip = rootView.findViewById(R.id.btn_continuar_ip);
        //et_fecha = rootView.findViewById(R.id.et_fecha);
        //new DateInputMask(et_fecha);
        String monto = turno.get(0).getImporte();

        //btn_pagar.setText("Pagar S/. "+monto);
        btn_continuar_ip.setText("Pagar S/. "+monto);

        //btn_pagar.setOnClickListener(new View.OnClickListener() {
        btn_continuar_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validarCampos();
                if (!Utils.redOrWifiActivo(getActivity())) {
                    Toast.makeText(getActivity(), "VERIFIQUE SU CONEXIÓN A LA RED", Toast.LENGTH_LONG).show();
                    return ;
                }

                SharedPreferences prefs = getContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor;
                editor = prefs.edit();
                editor.putString("nombres", etNombreTitular.getText().toString());
                editor.putString("apellidos", etApellidoTitular.getText().toString());
                editor.putString("direccion", etDireccionTitular.getText().toString());
                editor.putString("correo", etCorreoTitular.getText().toString());
                editor.commit();

                notifyOptionSelected(Constantes.OPCION_IZIPAY, null);
            }
        });
    }

    /*private void LlenarSpinners(View rootView){
        tarjeta = rootView.findViewById(R.id.etMarca);
        mes = rootView.findViewById(R.id.et_mes_exp);
        year = rootView.findViewById(R.id.et_year_exp);

        ArrayAdapter<CharSequence> adapter_t = ArrayAdapter.createFromResource(getActivity(), R.array.tarjetas, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_m = ArrayAdapter.createFromResource(getActivity(), R.array.mes, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_y = ArrayAdapter.createFromResource(getActivity(), R.array.year, android.R.layout.simple_spinner_item);

        tarjeta.setAdapter(adapter_t);
        mes.setAdapter(adapter_m);
        year.setAdapter(adapter_y);
    }*/

    private class InsertarConsulta extends AsyncTask<String,Void,ConsultaEntity> {
        private String consultaInmediata;
        @Override
        protected ConsultaEntity doInBackground(String... params) {
            String pacienteId=params[0], turnoDetalleId=params[1];
            ConsultaEntity consultaEntity = new ConsultaEntity();
            PagoEntity pago = new PagoEntity();
            pago.setPago_numero("PRUEBA8080");
            consultaInmediata =params[2];
            String importe = turno.get(0).getImporte();
            String fecha_consulta = turno.get(0).getFechaTurno();

            URLDaoInterface dao = new URLDaoImplement();

            int pago_id = dao.postInsertarPago(pago);

            String cadena = dao.postInsertarGet(pacienteId, turnoDetalleId, pago_id, consultaInmediata, importe, fecha_consulta); //recogemos todos los pacientes
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
                        //et_fecha.setText( (selectedMonth+1) + "/" + selectedYear );
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

    public int RetornaMes(String nombre){
        int mes;
        switch (nombre){
            case "Enero":
                mes = 1;
                break;
            case "Febrero":
                mes = 2;
                break;
            case "Marzo":
                mes = 3;
                break;
            case "Abril":
                mes = 4;
                break;
            case "Mayo":
                mes = 5;
                break;
            case "Junio":
                mes = 6;
                break;
            case "Julio":
                mes = 7;
                break;
            case "Agosto":
                mes = 8;
                break;
            case "Setiembre":
                mes = 9;
                break;
            case "Octubre":
                mes = 10;
                break;
            case "Noviembre":
                mes = 11;
                break;
            case "Diciembre":
                mes = 12;
                break;
            default: mes = 1;
        }
        return mes;
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
