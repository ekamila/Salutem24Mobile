package zisac.com.pe.salutem24.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.adapter.DominioDetalleAdapter;
import zisac.com.pe.salutem24.dataBase.ConsultaQuery;
import zisac.com.pe.salutem24.dataBase.DominioDetalleQuery;
import zisac.com.pe.salutem24.dataBase.URLDaoImplement;
import zisac.com.pe.salutem24.dataBase.URLDaoInterface;
import zisac.com.pe.salutem24.dialog.DatePickerFragment;
import zisac.com.pe.salutem24.entity.ConsultaEntity;
import zisac.com.pe.salutem24.entity.DominioDetalleEntity;
import zisac.com.pe.salutem24.entity.EspecialidadEntity;
import zisac.com.pe.salutem24.entity.UsuarioEntity;
import zisac.com.pe.salutem24.utils.Constantes;
import zisac.com.pe.salutem24.utils.StringUtils;
import zisac.com.pe.salutem24.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultaPragramadaFragment extends Fragment {
    private ProgressDialog popup;
    MenuFragment.OnClickOpcionFragmento optionSelected;
    private View rootView;
    private Button btn_editarFiltros, btn_buscar;
    private UsuarioEntity usuario;
    private TextView tv_cerrarFiltro, tv_estado, tv_orden;
    private LinearLayout ly_filtros;
    private ArrayList<EspecialidadEntity> especialidades;
    private LinearLayout content_consultas;
    private String selectedNombreEspecialidad, selectedEspecialidadId, selectedEstado, selectedEstadoId, selectedOrden, selectedOrdenId;
    private ArrayList<ConsultaEntity> consultas;

    public ConsultaPragramadaFragment() {
        // Required empty public constructor
    }

    public interface OnClickOpcionFragmento{
        void OnClickOpcionFragmento(int opcion, ArrayList datos);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        especialidades = getArguments().getParcelableArrayList(Constantes.KEY_CONSULTA_PROGRAMADA);
        usuario = getArguments().getParcelable(Constantes.USUARIO);
        if(savedInstanceState!=null){
            especialidades = savedInstanceState.getParcelableArrayList(Constantes.KEY_CONSULTA_PROGRAMADA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_consulta_pragramada, container, false);
        content_consultas = rootView.findViewById(R.id.content_consultas);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //etFechaConsulta = rootView.findViewById( R.id.etFechaConsulta );
        tv_cerrarFiltro = rootView.findViewById( R.id.tv_cerrarFiltro );
        btn_buscar = rootView.findViewById( R.id.btn_buscar );
        btn_editarFiltros = rootView.findViewById( R.id.btn_editarFiltros );
        ly_filtros  = rootView.findViewById( R.id.ly_filtros );
        tv_estado = rootView.findViewById( R.id.tv_estado );
        tv_orden = rootView.findViewById( R.id.tv_orden );
        /*tvFechaMiConsulta = rootView.findViewById( R.id.tvFechaMiConsulta );
        tvFechaMiConsulta.setText(StringUtils.mostrarddMMmmYYyy());

        etFechaConsulta.setFocusable(false);
        etFechaConsulta.setKeyListener(null);*/

        addOnclickListener();

        cargarDominioDetalle(Constantes.DOMINIO_DETALLE_ESPECIALIDADES);
        cargarDominioDetalle(Constantes.DOMINIO_DETALLE_ESTADOS);
        cargarDominioDetalle(Constantes.DOMINIO_DETALLE_ORDENAR);

        selectedEspecialidadId = "0";
        selectedEstadoId = "1";
        selectedOrdenId="0";
        buscarObtenerConsultas();
    }

    private void addOnclickListener() {
        /*etFechaConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });*/

        btn_editarFiltros.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ly_filtros.setVisibility( View.VISIBLE );
                btn_editarFiltros.setVisibility( View.GONE );
            }
        } );

        btn_buscar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ly_filtros.setVisibility( View.GONE );
                btn_editarFiltros.setVisibility( View.VISIBLE );

                //if(etFechaConsulta != null) {
                    //tvFechaMiConsulta.setText(etFechaConsulta.getText().toString());
                    tv_estado.setText(selectedEstado);
                    tv_orden.setText(selectedOrden);
                   buscarObtenerConsultas();
                //} else {
                    //Toast.makeText(getContext(), "Elija una fecha", Toast.LENGTH_LONG).show();
                //}
            }
        } );

        tv_cerrarFiltro.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ly_filtros.setVisibility( View.GONE );
                btn_editarFiltros.setVisibility( View.VISIBLE );
            }
        } );
    }

    private void buscarObtenerConsultas(){
        if (!Utils.redOrWifiActivo(getActivity())) {
            Toast.makeText(getActivity(), "VERIFIQUE SU CONEXIÓN A LA RED", Toast.LENGTH_LONG).show();
            return ;
        }
        crearPopUpEspera();
        ObtenerConsultas obtenerHorarios = new ObtenerConsultas();
        obtenerHorarios.execute(usuario.getUsuarioId(), selectedEspecialidadId, selectedEstadoId, selectedOrdenId);
    }

    public void cargarDominioDetalle(String tipo){
        final ArrayList<DominioDetalleEntity> listVOs = new ArrayList<>();
        if(tipo.equals(Constantes.DOMINIO_DETALLE_ESTADOS)){
            listVOs.add(new DominioDetalleEntity("1", "Pendiente"));
            listVOs.add(new DominioDetalleEntity("2", "Atendida"));
            listVOs.add(new DominioDetalleEntity("3", "Cancelada"));
        }
        if(tipo.equals(Constantes.DOMINIO_DETALLE_ESPECIALIDADES)) listVOs.add(new DominioDetalleEntity("0", "Seleccione una especialidad"));
        if(tipo.equals(Constantes.DOMINIO_DETALLE_ORDENAR)) {
            listVOs.add(new DominioDetalleEntity("0", "Más Recientes"));
            listVOs.add(new DominioDetalleEntity("1", "Más Antiguas"));
        }

        DominioDetalleEntity dominioDetalleEntity;

        if(tipo.equals(Constantes.DOMINIO_DETALLE_ESPECIALIDADES)) {
            try {
                if(especialidades!=null) {
                    if (especialidades.size() > 0) {
                        for (int i = 0; i < especialidades.size(); i++) {
                            final EspecialidadEntity comboPacientes = especialidades.get(i);
                            dominioDetalleEntity = new DominioDetalleEntity();

                            dominioDetalleEntity.setDescripcionDetalle(comboPacientes.getNombre());
                            dominioDetalleEntity.setValorDetalle(comboPacientes.getEspecialidadId());
                            listVOs.add(dominioDetalleEntity);
                        }
                    }
                }
            } catch (Exception e) {
                //Log.e("ErrorCbo", e.getLocalizedMessage());
            }
        }
        /*if(tipo.equals(Constantes.DOMINIO_DETALLE_ESTADOS)) {
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
        }*/
        cargarListaTipo(listVOs, tipo);
    }

    public void cargarListaTipo(final ArrayList<DominioDetalleEntity> arrayList, String tipo) {
        DominioDetalleAdapter adapter = new DominioDetalleAdapter(getActivity(), 0, arrayList);
        Spinner spinner = null ;
        try {
            if (tipo.equals(Constantes.DOMINIO_DETALLE_ESPECIALIDADES)) spinner = rootView.findViewById(R.id.sp_especialidad);
            if (tipo.equals(Constantes.DOMINIO_DETALLE_ESTADOS)) spinner = rootView.findViewById(R.id.sp_estado);
            if (tipo.equals(Constantes.DOMINIO_DETALLE_ORDENAR)) spinner = rootView.findViewById(R.id.sp_ordenar);
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
                        if (tipo.equals(Constantes.DOMINIO_DETALLE_ESPECIALIDADES)) {
                            selectedNombreEspecialidad = arrayList.get(position).getDescripcionDetalle();
                            selectedEspecialidadId = arrayList.get(position).getValorDetalle();
                            //limpiarRecargarView(selectedPacienteId);
                        }
                        if (tipo.equals(Constantes.DOMINIO_DETALLE_ESTADOS)) {
                            selectedEstado = arrayList.get(position).getDescripcionDetalle();
                            selectedEstadoId = arrayList.get(position).getValorDetalle();
                        }
                        if (tipo.equals(Constantes.DOMINIO_DETALLE_ORDENAR)) {
                            selectedOrden = arrayList.get(position).getDescripcionDetalle();
                            selectedOrdenId = arrayList.get(position).getValorDetalle();
                        }
                    }
                }catch (Exception e){
                    Log.e("EXECspinner","" + e.getMessage());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
            }
        });

        /*if(accion.equals("setearSexo")) {
            if (tipo.equals(Constantes.DOMINIO_DETALLE_SEXO)) {
                if (sexoPaciente.getSexo() != null) {
                    String sexo = sexoPaciente.getSexo().equals("Masculino")? "1" : "2";
                    if(!sexoPaciente.getSexo().equals("")) spinner.setSelection(Integer.parseInt(sexo));
                }
            }
        }*/
    }

    /*private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                String dia = "0" + day;
                String mes = "0" + (month+1);
                final String selectedDate = dia.substring(dia.length()-2) + "/" + mes.substring(mes.length()-2)+ "/" + year;
                etFechaConsulta.setText(selectedDate);
            }
        }, Constantes.FECHA_PROGRAMADA);

        newFragment.show(getFragmentManager(), "datePicker");
    }*/
    private class ObtenerConsultas extends AsyncTask<String,Void, ConsultaEntity> {
        ArrayList<ConsultaEntity> consultaTmp;
        @Override
        protected ConsultaEntity doInBackground(String... params) {
            String usuarioId=params[0], especialidadId=params[1], estadoId=params[2], ordenId=params[3];

            ConsultaEntity consultaEntity;
            URLDaoInterface dao = new URLDaoImplement();
            consultas = dao.getListarConsultasProgramadas(usuarioId, especialidadId, estadoId, ordenId); //recogemos todos los medicos
            if (consultas != null) {
                //ConsultaQuery turnoQuery = new ConsultaQuery(getActivity());
                //consultaTmp = turnoQuery.insertarTurnos(consultas); //inserta y recogemos solo las insertadas
                //respuesta = "" + turnoTmp.size();

                if(consultas.size()>0) {
                    consultaEntity = consultas.get(0);
                }else{
                    consultaEntity = null;
                }
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
                    limpiarRecargarView();
                    //Toast.makeText(getContext(), consulta.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se pudo listar, intente más tarde", Toast.LENGTH_LONG).show();
                }
            } else {
                content_consultas.removeAllViews();
                Toast.makeText(getContext(), "No hay turnos en esta fecha", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void limpiarRecargarView(){
        content_consultas.removeAllViews();
        if(consultas!=null){
            if(consultas.size()>0){
                generarFilasConsultas();
            }
        }
    }

    private void generarFilasConsultas(){
        Collections.sort(consultas, new Comparator<ConsultaEntity>() {
            @Override
            public int compare(ConsultaEntity consultaEntity, ConsultaEntity t1) {
                if(selectedOrdenId.equals("0")){
                    //Recientes
                    return  Integer.valueOf(t1.getConsultaId()).compareTo((Integer.valueOf(consultaEntity.getConsultaId())));
                } else {//Más antiguas
                    return Integer.valueOf(consultaEntity.getConsultaId()).compareTo((Integer.valueOf(t1.getConsultaId())));
                }
            }
        });

        int total = consultas.size();

        for(int i=0; i<total; i++){
            final ConsultaEntity consulta = consultas.get(i);

            //LayoutPrincipal VERTICAL
            LinearLayout.LayoutParams paramFila = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout fila = new LinearLayout(getActivity());
            fila.setOrientation(LinearLayout.VERTICAL);

            /********** hijoPrinciapl Layout ****************/ //hijoLayoutPrincipal (hlp)
            LinearLayout.LayoutParams hijoLayoutPrincipal = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout filaDatosConsulta = new LinearLayout(getActivity());
            filaDatosConsulta.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tvMedico = new TextView(getActivity());
            tvMedico.setTypeface(tvMedico.getTypeface(), Typeface.BOLD);
            tvMedico.setTextSize(16);
            tvMedico.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorblueDark));
            tvMedico.setText(consulta.getMedico().toUpperCase());

            LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tvEspecialidad = new TextView(getActivity());
            tvEspecialidad.setTypeface(tvEspecialidad.getTypeface(),Typeface.BOLD);
            tvEspecialidad.setTextSize(15);
            tvEspecialidad.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorInput));
            String txtCons = "("+consulta.getEspecialidad() + ")";
            tvEspecialidad.setText(txtCons);

            LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tvNrocita = new TextView(getActivity());
            tvNrocita.setTypeface(tvNrocita.getTypeface(),Typeface.BOLD);
            tvNrocita.setTextSize(14);
            tvNrocita.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorHintDark));
            StringBuilder txtNrocita = new StringBuilder();
            txtNrocita.append("Nro de Cita: CT -").append(consulta.getConsultaId());
            tvNrocita.setText(txtNrocita);

            LinearLayout.LayoutParams param4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tvPaciente = new TextView(getActivity());
            tvPaciente.setTypeface(tvPaciente.getTypeface(),Typeface.BOLD);
            tvPaciente.setTextSize(14);
            tvPaciente.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorHintDark));
            tvPaciente.setText("Paciente: " + consulta.getPaciente());

            LinearLayout.LayoutParams param5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tvHora = new TextView(getActivity());
            tvHora.setTypeface(tvPaciente.getTypeface(),Typeface.BOLD);
            tvHora.setTextSize(14);
            tvHora.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorHintDark));
            StringBuilder txtDate = new StringBuilder();
            txtDate.append("Fecha: ").append(consulta.getFechaConsulta()).append(" Hora: ").append(consulta.getHorarioConsulta()).append("hrs.");
            tvHora.setText(txtDate);

            /************ hijoPrinciapl button *****************/
            LinearLayout.LayoutParams hijoButton = new LinearLayout.LayoutParams(360, 120);
            hijoButton.gravity = Gravity.CENTER;
            hijoButton.setMargins(10,10,10,10);
            Button btnIngresar = new Button(getActivity());
            btnIngresar.setTypeface(btnIngresar.getTypeface(), Typeface.BOLD);
            btnIngresar.setTextSize(12);
            btnIngresar.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
            btnIngresar.setText("Ingresar");
            btnIngresar.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.boton_secundary_sm));
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk >= Build.VERSION_CODES.LOLLIPOP) {
                btnIngresar.setElevation(3);
            }
            btnIngresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!Utils.redOrWifiActivo(getActivity())) {
                        Toast.makeText(getActivity(), "VERIFIQUE SU CONEXIÓN A LA RED", Toast.LENGTH_LONG).show();
                        return ;
                    }

                    ArrayList<ConsultaEntity> datos = new ArrayList<>();
                    consulta.setConsultaInmediata(Constantes.RESERVA_NORMAL);
                    datos.add(consulta);

                    notifyOptionSelected(Constantes.OPCION_CONSULTA_ONLINE, datos);
                    //Log.i("value" , datos.get(0).getConsultaId());
                }
            });
            if(!selectedEstadoId.equals("1")){
                btnIngresar.setVisibility(View.GONE);
            }
            /************ hijoPrincipal View *****************/
            LinearLayout.LayoutParams hijoViewPrincipal = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 2);
            hijoViewPrincipal.setMargins(0,5,0,15);
            View vLinea = new View(getActivity());
            vLinea.setBackground(ContextCompat.getDrawable(getActivity(), R.color.colorHint));


            //agregamos hijos principales
            filaDatosConsulta.addView(tvMedico, param1);
            filaDatosConsulta.addView(tvEspecialidad, param2);
            filaDatosConsulta.addView(tvNrocita, param3);
            filaDatosConsulta.addView(tvPaciente, param4);
            filaDatosConsulta.addView(tvHora, param5);

            //agregamos hijos principales
            fila.addView(filaDatosConsulta, hijoLayoutPrincipal);
            fila.addView(btnIngresar, hijoButton);
            fila.addView(vLinea, hijoViewPrincipal);
            //agregamos la fila
            content_consultas.addView(fila,paramFila);
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
        if(activity instanceof MenuFragment.OnClickOpcionFragmento)
        {
            optionSelected = (MenuFragment.OnClickOpcionFragmento)activity;
        }
        else
            throw new ClassCastException();
    }

    private void notifyOptionSelected(int opcion, ArrayList datos){
        optionSelected.OnClickOpcionFragmento(opcion, datos);
    }
}
