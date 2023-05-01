package zisac.com.pe.salutem24.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.adapter.DominioDetalleAdapter;
import zisac.com.pe.salutem24.adapter.PacienteDetalleAdapter;
import zisac.com.pe.salutem24.dataBase.DominioDetalleQuery;
import zisac.com.pe.salutem24.dataBase.PacienteQuery;
import zisac.com.pe.salutem24.dataBase.URLDaoImplement;
import zisac.com.pe.salutem24.dataBase.URLDaoInterface;
import zisac.com.pe.salutem24.dialog.DatePickerFragment;
import zisac.com.pe.salutem24.entity.DominioDetalleEntity;
import zisac.com.pe.salutem24.entity.ExperienciaEntity;
import zisac.com.pe.salutem24.entity.PacienteEntity;
import zisac.com.pe.salutem24.entity.TurnoEntity;
import zisac.com.pe.salutem24.entity.UsuarioEntity;
import zisac.com.pe.salutem24.utils.Constantes;
import zisac.com.pe.salutem24.utils.StringUtils;
import zisac.com.pe.salutem24.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatosPacienteFragment extends Fragment {
    Spinner sp_paciente, sp_genero;
    EditText et_fecha;

    OnClickOpcionFragmento optionSelected;

    private View rootView;
    private ArrayList<TurnoEntity> turno;
    private ProgressDialog popup;
    private ArrayList<PacienteEntity> pacientes;
    private String selectedNombrePaciente="", selectedSexo="";
    private String selectedPacienteId = "0", selectedSexoId="";
    private LinearLayout ly_filtro_paciente;
    private TextView nroDni,nombres, apellidoPaterno, apellidoMaterno, nroCelular;
    private Button btn_afiliar;
    private PacienteEntity sexoPaciente;
    private String accion="";
    private UsuarioEntity usuario;
    private String pacienteId="", documento="", documentoId="", correoUsuario="";

    public interface OnClickOpcionFragmento{
        void OnClickOpcionFragmento(int opcion, ArrayList datos);
    }
    public DatosPacienteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        turno = getArguments().getParcelableArrayList(Constantes.KEY_DATOS_PACIENTE);
        //Log.i("Turno", String.valueOf(turno));
        //Log.i("Turno detalle", turno.get(0).getTurnoDetalleEntity().getTurnoDetalleId());
        usuario = getArguments().getParcelable(Constantes.USUARIO);
        if(savedInstanceState!=null){
            turno = savedInstanceState.getParcelableArrayList(Constantes.KEY_DATOS_PACIENTE);
        }
        try {
            pacientes = turno.get(0).getPacientesEntity();
        }catch (Exception e){
            pacientes = null;
            e.getMessage();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_datos_paciente, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sp_paciente = rootView.findViewById( R.id.sp_paciente );
        sp_genero = rootView.findViewById( R.id.sp_genero );
        et_fecha = rootView.findViewById( R.id.et_fecha );
        btn_afiliar = rootView.findViewById( R.id.btn_afiliar );
        ly_filtro_paciente = rootView.findViewById( R.id.ly_filtro_paciente);

        nroDni = rootView.findViewById(R.id.nroDni);
        nombres = rootView.findViewById(R.id.nombres);
        apellidoPaterno = rootView.findViewById(R.id.apellidoPaterno);
        apellidoMaterno = rootView.findViewById(R.id.apellidoMaterno);
        nroCelular = rootView.findViewById(R.id.nroCelular);

        et_fecha.setFocusable(false);
        et_fecha.setKeyListener(null);

        addOnclickListener();

        cargarDominioDetalle(Constantes.DOMINIO_DETALLE_SEXO);
        cargarDominioDetalle(Constantes.DOMINIO_DETALLE_PACIENTES);

        if(pacientes!=null){
            if(pacientes.size()>0){
                ly_filtro_paciente.setVisibility(View.VISIBLE);
            }
        }
    }

    private void addOnclickListener() {
        et_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btn_afiliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String validacion = validarCampos();
                String[] boolMsj = validacion.split(",");
                if(boolMsj[0].equals("true")){
                    turno.get(0).setPaciente(new PacienteEntity());
                    PacienteEntity datosPaciente = new PacienteEntity();
                    datosPaciente.setUsuarioId(usuario.getUsuarioId());
                    datosPaciente.setNombres(nombres.getText().toString());
                    datosPaciente.setApellidoPaterno(apellidoPaterno.getText().toString());
                    datosPaciente.setApellidoMaterno(apellidoMaterno.getText().toString());
                    datosPaciente.setNumeroDocumento(nroDni.getText().toString());
                    datosPaciente.setCelular(nroCelular.getText().toString());
                    datosPaciente.setSexo(selectedSexoId.equals("1") ? "M" : "F");
                    datosPaciente.setFechaNacimiento(et_fecha.getText().toString());
                    Log.e("et_Fec", String.valueOf(datosPaciente));
                    turno.get(0).setPaciente(datosPaciente);

                    if (!Utils.redOrWifiActivo(getActivity())) {
                        Toast.makeText(getActivity(), "VERIFIQUE SU CONEXIÓN A LA RED", Toast.LENGTH_LONG).show();
                        return ;
                    }

                    crearPopUpEspera();
                    InsertarPaciente obtenerIdPaciente = new InsertarPaciente();
                    obtenerIdPaciente.execute(turno.get(0).getPaciente());

                    /*if(selectedPacienteId.equals("0")) {
                        if (!Utils.redOrWifiActivo(getActivity())) {
                            Toast.makeText(getActivity(), "VERIFIQUE SU CONEXIÓN A LA RED", Toast.LENGTH_LONG).show();
                            return ;
                        }

                        crearPopUpEspera();
                        InsertarPaciente obtenerIdPaciente = new InsertarPaciente();
                        obtenerIdPaciente.execute(turno.get(0).getPaciente());
                    } else {
                        turno.get(0).getPaciente().setPacienteId(pacienteId);
                        turno.get(0).getPaciente().setDocumento(documento);
                        turno.get(0).getPaciente().setDocumentoId(documentoId);
                        turno.get(0).getPaciente().setUsuario(correoUsuario);
                        notifyOptionSelected(Constantes.OPCION_PAGOS, turno);
                    }*/
                } else if(boolMsj[0].equals("false")){
                    Toast.makeText(getContext(), boolMsj[1], Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void cargarDominioDetalle(String tipo){
        final ArrayList<DominioDetalleEntity> listVOs = new ArrayList<>();
        if(tipo.equals(Constantes.DOMINIO_DETALLE_SEXO)) listVOs.add(new DominioDetalleEntity("0", "Seleccione su género"));
        if(tipo.equals(Constantes.DOMINIO_DETALLE_PACIENTES)) listVOs.add(new DominioDetalleEntity("0", "Seleccione un paciente"));

        DominioDetalleEntity dominioDetalleEntity;

        if(tipo.equals(Constantes.DOMINIO_DETALLE_PACIENTES)) {
            try {
                if(pacientes!=null) {
                    if (pacientes.size() > 0) {
                        for (int i = 0; i < pacientes.size(); i++) {
                            final PacienteEntity comboPacientes = pacientes.get(i);
                            dominioDetalleEntity = new DominioDetalleEntity();

                            dominioDetalleEntity.setDescripcionDetalle(comboPacientes.getApellidoPaterno() + " " + comboPacientes.getApellidoMaterno() + " " + comboPacientes.getNombres());
                            dominioDetalleEntity.setValorDetalle(comboPacientes.getPacienteId());
                            listVOs.add(dominioDetalleEntity);
                        }
                    }
                }
            } catch (Exception e) {
                //Log.e("ErrorCbo", e.getLocalizedMessage());
            }
        }
        if(tipo.equals(Constantes.DOMINIO_DETALLE_SEXO)) {
            try {

                DominioDetalleQuery dominioDetalleQuery = new DominioDetalleQuery(getActivity());
                ArrayList<DominioDetalleEntity> listDominioDetalle = dominioDetalleQuery.listDDTipo(tipo);

                if (listDominioDetalle.size() > 0) {
                    for (int i = 0; i < listDominioDetalle.size(); i++) {
                        final DominioDetalleEntity comboOcurrenciaEntity = listDominioDetalle.get(i);
                        dominioDetalleEntity = new DominioDetalleEntity();
                        dominioDetalleEntity.setDescripcionDetalle(comboOcurrenciaEntity.getDescripcionDetalle());
                        dominioDetalleEntity.setValorDetalle(comboOcurrenciaEntity.getValorDetalle());
                        listVOs.add(dominioDetalleEntity);
                    }
                }
            } catch (Exception e) {
                //Log.e("ErrorCbo", e.getLocalizedMessage());
            }
        }
        cargarListaTipo(listVOs, tipo);
    }

    public void cargarListaTipo(final ArrayList<DominioDetalleEntity> arrayList, String tipo) {
        DominioDetalleAdapter adapter = new DominioDetalleAdapter(getActivity(), 0, arrayList);
        Spinner spinner = null ;
        try {
            if (tipo.equals(Constantes.DOMINIO_DETALLE_SEXO)) spinner = rootView.findViewById(R.id.sp_genero);
            if (tipo.equals(Constantes.DOMINIO_DETALLE_PACIENTES)) spinner = rootView.findViewById(R.id.sp_paciente);
        }catch (Exception e){
            e.getMessage();
        }

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                try {
                    Object item = adapterView.getItemAtPosition(position);
                    if (item != null) {
                        if (tipo.equals(Constantes.DOMINIO_DETALLE_PACIENTES)) {
                            selectedNombrePaciente = arrayList.get(position).getDescripcionDetalle();
                            selectedPacienteId = arrayList.get(position).getValorDetalle();
                            limpiarRecargarView(selectedPacienteId);
                        }
                        if (tipo.equals(Constantes.DOMINIO_DETALLE_SEXO)) {
                            selectedSexo = arrayList.get(position).getDescripcionDetalle();
                            selectedSexoId = arrayList.get(position).getValorDetalle();
                        }
                    }
                }catch (Exception e){
                    Log.e("ExepContrato","" + e.getMessage());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
            }
        });

        if(accion.equals("setearSexo")) {
            if (tipo.equals(Constantes.DOMINIO_DETALLE_SEXO)) {
                if (sexoPaciente.getSexo() != null) {
                    String sexo = sexoPaciente.getSexo().equals("Masculino")? "1" : "2";
                    if(!sexoPaciente.getSexo().equals("")) spinner.setSelection(Integer.parseInt(sexo));
                }
            }
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                String dia = "0" + day;
                String mes = "0" + (month+1);
                final String selectedDate = year + "-" + mes.substring(mes.length()-2)  + "-"  + dia.substring(dia.length()-2);
                et_fecha.setText(selectedDate);
            }
        }, Constantes.FECHA_NACIMIENTO_AGREGAR_PACIENTE);

        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void limpiarRecargarView(String selectedPacienteId){
        //content_pacientes.removeAllViews();

        if(pacientes!=null){
            if(pacientes.size()>0){
                asignarValores(selectedPacienteId);
            }
        }
    }

    private void asignarValores(String selectedPacienteId){
        //Log.e("idPaci", ""+ selectedPacienteId);
        nroDni = rootView.findViewById(R.id.nroDni);
        nombres = rootView.findViewById(R.id.nombres);
        apellidoPaterno = rootView.findViewById(R.id.apellidoPaterno);
        apellidoMaterno = rootView.findViewById(R.id.apellidoMaterno);
        nroCelular = rootView.findViewById(R.id.nroCelular);

        if(selectedPacienteId.equals("0")){
            //Log.e("selecteaaaa", ""+ selectedPacienteId);
            setearCamposEnVacio();
        }

        if(pacientes != null){
            int totalPacientes =pacientes.size();
            if(totalPacientes>0){
                for(int i=0; i<pacientes.size(); i++){
                    if(pacientes.get(i).getPacienteId().equals(selectedPacienteId)){
                        pacienteId = pacientes.get(i).getPacienteId();
                        documento = pacientes.get(i).getDocumento();
                        documentoId = pacientes.get(i).getDocumentoId();
                        correoUsuario = pacientes.get(i).getUsuario();

                        nroDni.setText(pacientes.get(i).getNumeroDocumento());
                        nombres.setText(pacientes.get(i).getNombres());
                        apellidoPaterno.setText(pacientes.get(i).getApellidoPaterno());
                        apellidoMaterno.setText(pacientes.get(i).getApellidoMaterno());
                        nroCelular.setText(pacientes.get(i).getCelular());
                        et_fecha.setText(pacientes.get(i).getFechaNacimiento());

                        //seteamos sexo
                        sexoPaciente = new PacienteEntity();
                        sexoPaciente.setSexo(pacientes.get(i).getSexo());
                        accion = "setearSexo";
                        cargarDominioDetalle(Constantes.DOMINIO_DETALLE_SEXO);
                        break;
                    }
                }
            }
        }
    }

    private void setearCamposEnVacio(){
        nroDni.setText("");
        nombres.setText("");
        apellidoPaterno.setText("");
        apellidoMaterno.setText("");
        nroCelular.setText("");
        et_fecha.setText("");
        accion = "noSetearSexo"; //para que no asigne id
        cargarDominioDetalle(Constantes.DOMINIO_DETALLE_SEXO);
    }

    private String validarCampos(){
        String boolAndMsj = "";
        if(nroDni.getText() == null){
            boolAndMsj = "false,Ingrese Dni";
        } else if(nroDni.getText().toString().trim().equals("")){
            boolAndMsj = "false,Ingrese Dni";
        } else if(nombres.getText() == null){
            boolAndMsj = "false,Ingrese Nombre";
        } else if(nombres.getText().toString().trim().equals("")){
            boolAndMsj = "false,Ingrese Nombre";
        }else if(apellidoPaterno.getText() == null){
            boolAndMsj = "false,Ingrese Apellido Paterno";
        } else if(apellidoPaterno.getText().toString().trim().equals("")){
            boolAndMsj = "false,Ingrese Apellido Paterno";
        } else if(apellidoMaterno.getText() == null){
            boolAndMsj = "false,Ingrese Apellido Materno";
        } else if(apellidoMaterno.getText().toString().trim().equals("")){
            boolAndMsj = "false,Ingrese Apellido Materno";
        } else if(selectedSexoId.equals("0")){
            boolAndMsj = "false,Ingrese Sexo";
        } else if(et_fecha.getText() == null){
            boolAndMsj = "false,Ingrese Fecha Nacimiento";
        } else if(et_fecha.getText().toString().equals("")){
            boolAndMsj = "false,Ingrese Fecha Nacimiento";
        } else if(nroCelular.getText() == null){
            boolAndMsj = "false,Ingrese Celular";
        } else if(nroCelular.getText().toString().trim().equals("")){
            boolAndMsj = "false,Ingrese Celular";
        } else {
            boolAndMsj = "true,correcto";
        }

        return boolAndMsj;
    }

    private class InsertarPaciente extends AsyncTask<PacienteEntity,Void,PacienteEntity> {
        ArrayList<PacienteEntity> pacientTmp;
        @Override
        protected PacienteEntity doInBackground(PacienteEntity... params) {
            PacienteEntity pacienteInsertar = params[0];

            URLDaoInterface dao = new URLDaoImplement();
            PacienteEntity pacienteEntity = dao.postInsertarGetPacienteId(pacienteInsertar); //recogemos todos los pacientes
            if (pacienteEntity != null) {
                PacienteQuery pacienteQuery = new PacienteQuery(getActivity());
                //pacienteTmp = especialidadQuery.insertarMedicoxEpecialidade(pacientes); //inserta y recogemos solo las insertadas
                //respuesta = "" + pacienteTmp.size();
            } else {
                pacienteEntity = null;
            }
            return pacienteEntity;
        }

        @Override
        protected void onPostExecute(PacienteEntity paciente) {
            cerrarPopUpEspera();
            if(paciente != null) {
                if(paciente.getIsSuccess() != null) {
                    if (paciente.getIsSuccess().equals("true")) {
                        turno.get(0).getPaciente().setPacienteId(paciente.getPacienteId());
                        //notifyOptionSelected(Constantes.OPCION_PAGOS, turno);
                        notifyOptionSelected(Constantes.OPCION_MEDIO_PAGO, turno);

                        SharedPreferences preferences = getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(turno);
                        editor.putString("turno", json);
                        editor.commit();
                        Toast.makeText(getContext(), paciente.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "No se pudo insertar, intente mas tarde", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "No se pudo agregar al paciente, intente mas tarde", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "No se pudo insertar, intente mas tarde", Toast.LENGTH_LONG).show();
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
