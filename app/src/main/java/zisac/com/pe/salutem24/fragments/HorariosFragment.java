package zisac.com.pe.salutem24.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.icu.text.SymbolTable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.adapter.DominioDetalleAdapter;
import zisac.com.pe.salutem24.dataBase.CurriculumQuery;
import zisac.com.pe.salutem24.dataBase.EspecialidadQuery;
import zisac.com.pe.salutem24.dataBase.EstudiosQuery;
import zisac.com.pe.salutem24.dataBase.ExperienciaQuery;
import zisac.com.pe.salutem24.dataBase.MedicoQuery;
import zisac.com.pe.salutem24.dataBase.PacienteQuery;
import zisac.com.pe.salutem24.dataBase.TurnoDetalleQuery;
import zisac.com.pe.salutem24.dataBase.TurnoQuery;
import zisac.com.pe.salutem24.dataBase.URLDaoImplement;
import zisac.com.pe.salutem24.dataBase.URLDaoInterface;
import zisac.com.pe.salutem24.dialog.DatePickerFragment;
import zisac.com.pe.salutem24.entity.CurriculumEntity;
import zisac.com.pe.salutem24.entity.DominioDetalleEntity;
import zisac.com.pe.salutem24.entity.EspecialidadEntity;
import zisac.com.pe.salutem24.entity.EstudiosEntity;
import zisac.com.pe.salutem24.entity.ExperienciaEntity;
import zisac.com.pe.salutem24.entity.MedicoEntity;
import zisac.com.pe.salutem24.entity.PacienteEntity;
import zisac.com.pe.salutem24.entity.TurnoDetalleEntity;
import zisac.com.pe.salutem24.entity.TurnoEntity;
import zisac.com.pe.salutem24.entity.UsuarioEntity;
import zisac.com.pe.salutem24.utils.Constantes;
import zisac.com.pe.salutem24.utils.StringUtils;
import zisac.com.pe.salutem24.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class HorariosFragment extends Fragment {
    Spinner sp_medico;
    EditText et_fecha;
    Button btn_editarFiltros, btn_buscar;
    LinearLayout ly_filtros;
    TextView tv_cerrarFiltro;

    Dialog dialogCurriculum;

    OnClickOpcionFragmento optionSelected;
    private LinearLayout content_medicos_especialidad;
    private View rootView;
    private ArrayList<EspecialidadEntity> especialidad;
    private ArrayList<MedicoEntity> medicos;
    private ProgressDialog popup;
    private ArrayList<TurnoEntity> turnos;
    private String selectedNombreMedico="";
    private String selectedMedicoId="";
    private TextView tvFechaElegida;
    private CurriculumEntity curriculumEntity;
    private UsuarioEntity usuario;
    private ArrayList<PacienteEntity> pacientes;
    private TurnoEntity turnoElegido;

    public interface OnClickOpcionFragmento{
        void OnClickOpcionFragmento(int opcion, ArrayList datos);
    }

    public HorariosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        medicos = getArguments().getParcelableArrayList(Constantes.KEY_DETALLE_MEDICOS);
        usuario = getArguments().getParcelable(Constantes.USUARIO);
        if(savedInstanceState!=null){
            medicos = savedInstanceState.getParcelableArrayList(Constantes.KEY_DETALLE_MEDICOS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_horarios, container, false);
        content_medicos_especialidad = rootView.findViewById(R.id.content_medicos_especialidad);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dialogCurriculum = new Dialog( getActivity() );

        sp_medico = rootView.findViewById( R.id.sp_medico );
        et_fecha = rootView.findViewById( R.id.et_fecha );
        btn_editarFiltros = rootView.findViewById( R.id.btn_editarFiltros );
        ly_filtros = rootView.findViewById( R.id.ly_filtros );
        btn_buscar = rootView.findViewById( R.id.btn_buscar );
        tv_cerrarFiltro = rootView.findViewById( R.id.tv_cerrarFiltro );
        tvFechaElegida = rootView.findViewById(R.id.tvFechaElegida);
        tvFechaElegida.setText(StringUtils.mostrarddMMmmYYyy());

        et_fecha.setFocusable(false);
        et_fecha.setKeyListener(null);

        cargarDominioDetalle();
        addOnclickListener();
    }

    public void cargarDominioDetalle(){
        final ArrayList<DominioDetalleEntity> listVOs = new ArrayList<>();

        listVOs.add(new DominioDetalleEntity("0", "Todos los médicos"));

        DominioDetalleEntity dominioDetalleEntity;
        try {
            if (medicos.size() > 0) {
                for (int i = 0; i < medicos.size(); i++) {
                    final MedicoEntity comboMedicos = medicos.get(i);
                    dominioDetalleEntity = new DominioDetalleEntity();

                    dominioDetalleEntity.setDescripcionDetalle(comboMedicos.getApellidoMaterno() + " "  + comboMedicos.getApellidoMaterno() + " " + comboMedicos.getNombres());
                    dominioDetalleEntity.setValorDetalle(comboMedicos.getMedicoId());
                    listVOs.add(dominioDetalleEntity);
                }
            }
        } catch (Exception e) {
            //Log.e("ErrorCbo", e.getLocalizedMessage());
        }
        cargarListaTipo(listVOs);
    }

    public void cargarListaTipo(final ArrayList<DominioDetalleEntity> arrayList) {
        DominioDetalleAdapter adapter = new DominioDetalleAdapter(getActivity(), 0, arrayList);
        Spinner spinner = null ;

        try {
            spinner = rootView.findViewById(R.id.sp_medico);
        }catch (Exception e){
            spinner = null;
        }

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                try {
                    Object item = adapterView.getItemAtPosition(position);
                    if (item != null) {
                        selectedNombreMedico = arrayList.get(position).getDescripcionDetalle();
                        selectedMedicoId = arrayList.get(position).getValorDetalle();
                    }
                }catch (Exception e){
                    Log.e("Error ","" + e.getMessage());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void addOnclickListener(){
        et_fecha.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        } );

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

                if(selectedNombreMedico.equals("")) selectedMedicoId = "0";

                if(et_fecha != null) {
                    tvFechaElegida.setText(et_fecha.getText().toString());

                    if (!Utils.redOrWifiActivo(getActivity())) {
                        Toast.makeText(getActivity(), "VERIFIQUE SU CONEXIÓN A LA RED", Toast.LENGTH_LONG).show();
                        return ;
                    }
                    crearPopUpEspera();
                    ObtenerHorarios obtenerHorarios = new ObtenerHorarios();
                    obtenerHorarios.execute(medicos.get(0).getEspecialidadId(), selectedMedicoId, et_fecha.getText().toString());
                } else {
                    Toast.makeText(getContext(), "Elija una fecha", Toast.LENGTH_LONG).show();
                }
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

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                String dia = "0" + day;
                String mes = "0" + (month+1);
                final String selectedDate = dia.substring(dia.length()-2) + "/" + mes.substring(mes.length()-2)+ "/" + year;
                et_fecha.setText(selectedDate);
            }
        }, Constantes.FECHA_FILTRO_RESERVAR_CONSULTA);

        newFragment.show(getFragmentManager(), "datePicker");
    }

    private class ObtenerHorarios extends AsyncTask<String,Void,TurnoEntity> {
        ArrayList<TurnoEntity> turnoTmp;
        @Override
        protected TurnoEntity doInBackground(String... params) {
            String especialidadId=params[0], medicoId=params[1], fecha=params[2];

            TurnoEntity turnoEntity;
            URLDaoInterface dao = new URLDaoImplement();
            turnos = dao.getTurnoxEspMedicoFecha(especialidadId, medicoId, fecha); //recogemos todos los medicos
            if (turnos != null) {
                /*
                TurnoQuery turnoQuery = new TurnoQuery(getActivity());
                turnoTmp = turnoQuery.insertarTurnos(turnos); //inserta y recogemos solo las insertadas

                TurnoDetalleQuery turnoDetalleQuery = new TurnoDetalleQuery(getActivity());
                turnoDetalleQuery.insertarTurnosDetalle(turnos); //inserta y recogemos solo las insertadas*/

                if(turnos.size()>0) {
                    turnoEntity = turnos.get(0);
                } else {
                    turnoEntity = null;
                }
            } else {
                turnoEntity = null;
            }
            return turnoEntity;
        }

        @Override
        protected void onPostExecute(TurnoEntity turno) {
            cerrarPopUpEspera();
            if(turno != null) {
                if (turno.getIsSuccess().equals("true")) {
                    limpiarRecargarView();
                    Toast.makeText(getContext(), turno.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se pudo listar, intente mas tarde", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "No hay turnos en esta fecha", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void limpiarRecargarView(){
        content_medicos_especialidad.removeAllViews();
        if(turnos!=null){
            if(turnos.size()>0){
                generarFilasHorarios();
            }
        }
    }

    private void generarFilasHorarios(){
        int total = turnos.size();
        for(int i=0; i<total; i++){
            final TurnoEntity turno = turnos.get(i);

            //LayoutPrincipal VERTICAL
            LinearLayout.LayoutParams paramFila = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramFila.setMargins(0,0,0,20);
            LinearLayout fila = new LinearLayout(getActivity());
            fila.setOrientation(LinearLayout.VERTICAL);

            /********** hijoPrinciapl Layout orientation HORIZONTAL *****************/ //hijoLayoutPrincipal (hlp)
            LinearLayout.LayoutParams hijoLayoutPrincipal = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 9);
            //hijoLayoutPrincipal.setMargins(0,0,0,20);
            LinearLayout filaDoctor = new LinearLayout(getActivity());
            filaDoctor.setOrientation(LinearLayout.HORIZONTAL);

            //<LinearLayout>
            LinearLayout.LayoutParams paramDoc = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 6);
            LinearLayout layoutDoc = new LinearLayout(getActivity());
            layoutDoc.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams param1Hlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tvNombres = new TextView(getActivity());
            tvNombres.setTypeface(tvNombres.getTypeface(),Typeface.BOLD);
            tvNombres.setTextSize(15);
            tvNombres.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorblueDark));
            tvNombres.setText(turno.getMedico().toUpperCase());

            LinearLayout.LayoutParams param2Hlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tvEspecialidad = new TextView(getActivity());
            tvEspecialidad.setTypeface(tvNombres.getTypeface(),Typeface.BOLD);
            tvEspecialidad.setTextSize(14);
            tvEspecialidad.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorInput));
            tvEspecialidad.setText(turno.getEspecialidad());

            LinearLayout.LayoutParams param3Hlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tvHorario = new TextView(getActivity());
            tvHorario.setTypeface(tvNombres.getTypeface(),Typeface.BOLD);
            tvHorario.setTextSize(13);
            tvHorario.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorHintDark));
            tvHorario.setText("Horario: " + turno.getHorario());
            //</LinearLayout>

            //<LinearLayout>
            LinearLayout.LayoutParams paramFile = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3);
            LinearLayout layoutFile = new LinearLayout(getActivity());
            layoutFile.setGravity(View.FOCUS_RIGHT);
            //layoutFile.setPadding(0,0,10,0);

            LinearLayout.LayoutParams paramN = new LinearLayout.LayoutParams(120, 120);
            //LinearLayout.LayoutParams paramN = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 56);
            //paramN.gravity = Gravity.CENTER;
            //paramN.setMargins(0,0,20,0);
            ImageView ivCurriculum = new ImageView(getActivity());
            ivCurriculum.setBackgroundResource(R.drawable.curriculum);
            ivCurriculum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!Utils.redOrWifiActivo(getActivity())) {
                        Toast.makeText(getActivity(), "VERIFIQUE SU CONEXIÓN A LA RED", Toast.LENGTH_LONG).show();
                        return ;
                    }

                    ObtenerCurriculum obtenerCurriculum = new ObtenerCurriculum();
                    obtenerCurriculum.execute(turno.getMedicoId());
                }
            });
            //</LinearLayout>

            /************ hijoPrinciapl scrollbars NONE *****************/
            LinearLayout.LayoutParams hijoScrollPrincipal = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ScrollView scrollHorarios = new ScrollView(getActivity());
            scrollHorarios.setVerticalScrollBarEnabled(false);
            scrollHorarios.setHorizontalScrollBarEnabled(false);
            //addCHild1
            LinearLayout.LayoutParams paramScroll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            HorizontalScrollView hsvHijo1 = new HorizontalScrollView(getActivity());
            hsvHijo1.setVerticalScrollBarEnabled(false);
            hsvHijo1.setHorizontalScrollBarEnabled(false);
            //addChild2
            LinearLayout.LayoutParams paramLl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout llHijo2Horarios = new LinearLayout(getActivity());
            llHijo2Horarios.setOrientation(LinearLayout.HORIZONTAL);

            int totalTurnoDetalle = turno.getTurnosDetalle().size();
            if(totalTurnoDetalle>0) {
                for (int j = 0; j < totalTurnoDetalle; j++) {
                    TurnoDetalleEntity turnoDetalle = turno.getTurnosDetalle().get(j);

                    LinearLayout.LayoutParams paramsH = new LinearLayout.LayoutParams(155, 120);
                    paramsH.setMargins(0, 15, 6, 20);
                    paramsH.gravity = Gravity.CENTER;
                    Button btnHorario = new Button(getActivity());
                    btnHorario.setTypeface(tvNombres.getTypeface(), Typeface.BOLD);
                    //Estados de los turnos: 1-Libre , 2-Reservado , 3-Cancelado
                    if (turnoDetalle.getEstadoTurnoId().equals("1")) {
                        btnHorario.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.boton_secundary_sm));
                    }
                    if (turnoDetalle.getEstadoTurnoId().equals("2")) {
                        btnHorario.setEnabled(false);
                        btnHorario.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.boton_reservado)); //podría ser color rojo
                    }
                    if (turnoDetalle.getEstadoTurnoId().equals("3")) {
                        btnHorario.setEnabled(false);
                        btnHorario.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.boton_inactivo));
                    }
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if (sdk >= Build.VERSION_CODES.LOLLIPOP) {
                        btnHorario.setElevation(3);
                    }
                    btnHorario.setTextSize(12);
                    btnHorario.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                    btnHorario.setText(turnoDetalle.getHorario());
                    btnHorario.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!Utils.redOrWifiActivo(getActivity())) {
                                Toast.makeText(getActivity(), "VERIFIQUE SU CONEXIÓN A LA RED", Toast.LENGTH_LONG).show();
                                return;
                            }

                            turno.setTurnoDetalleEntity(new TurnoDetalleEntity());
                            turno.setTurnoDetalleEntity(turnoDetalle);

                            crearPopUpEspera();
                            ObtenerPacientes obtenerPacientes = new ObtenerPacientes();
                            obtenerPacientes.execute(turno);
                        }
                    });

                    llHijo2Horarios.addView(btnHorario, paramsH);
                }
            }

            /************ hijoPrincipal View *****************/
            LinearLayout.LayoutParams hijoViewPrincipal = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 2);
            hijoViewPrincipal.setMargins(0,5,0,5);
            View vLinea = new View(getActivity());
            vLinea.setBackground(ContextCompat.getDrawable(getActivity(), R.color.colorHint));

            //Agregagamo a FilaDoctor
            layoutDoc.addView(tvNombres, param1Hlp);
            layoutDoc.addView(tvEspecialidad, param2Hlp);
            layoutDoc.addView(tvHorario, param3Hlp);

            layoutFile.addView(ivCurriculum, paramN);

            filaDoctor.addView(layoutDoc, paramDoc);
            filaDoctor.addView(layoutFile, paramFile);

            //Agregagamo a scrollHorarios
            hsvHijo1.addView(llHijo2Horarios, paramLl);
            scrollHorarios.addView(hsvHijo1, paramScroll);

            //agregamos hijos principales
            fila.addView(filaDoctor, hijoLayoutPrincipal);
            fila.addView(scrollHorarios, hijoScrollPrincipal);
            fila.addView(vLinea, hijoViewPrincipal);
            //agregamos la fila
            content_medicos_especialidad.addView(fila,paramFila);
        }
    }

    private class ObtenerCurriculum extends AsyncTask<String,Void,CurriculumEntity> {
        CurriculumEntity currculumTemp;
        @Override
        protected CurriculumEntity doInBackground(String... params) {
            String medicoId = params[0];
            URLDaoInterface dao = new URLDaoImplement();
            curriculumEntity = dao.getCurriculumMedico(medicoId); //recogemos todos los medicos
            if (curriculumEntity != null) {
                CurriculumQuery curriculumQuery = new CurriculumQuery(getActivity());
                curriculumQuery.insertarCurriculum(curriculumEntity); //inserta y recogemos solo las insertadas

                if(curriculumEntity.getEstudios()!=null) {
                    if (curriculumEntity.getEstudios().size() > 0) {
                        EstudiosQuery estudiosQuery = new EstudiosQuery(getActivity());
                        estudiosQuery.insertarEstudios(curriculumEntity.getEstudios());
                    }
                }

                if(curriculumEntity.getExperiencia()!=null) {
                    if (curriculumEntity.getExperiencia().size() > 0) {
                        ExperienciaQuery experienciaQuery = new ExperienciaQuery(getActivity());
                        experienciaQuery.insertarExperiencia(curriculumEntity.getExperiencia());
                    }
                }
            }
            return curriculumEntity;
        }

        @Override
        protected void onPostExecute(CurriculumEntity curriculum) {
            cerrarPopUpEspera();
            if(curriculum != null) {
                if (curriculum.getIsSuccess().equals("true")) {
                        mostrarPopup(curriculum);
                        Toast.makeText(getContext(), curriculum.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se puede obtener curriculum, intente mas tarde", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(getContext(), "No se puede obtener curriculum, intente mas tarde", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void mostrarPopup( CurriculumEntity curriculumVitae){
        TextView txtClose, tvNombreDoctor, tvEspecialidadDoc, tvCmp, tvRne;
        LinearLayout content_estudios,content_experiencia;
        dialogCurriculum.setContentView( R.layout.custompoppup );
        content_estudios = dialogCurriculum.findViewById(R.id.content_estudios);
        content_experiencia = dialogCurriculum.findViewById(R.id.content_experiencia);

        txtClose = dialogCurriculum.findViewById( R.id.txtClose );
        tvNombreDoctor = dialogCurriculum.findViewById( R.id.tvNombreDoctor );
        tvEspecialidadDoc = dialogCurriculum.findViewById( R.id.tvEspecialidadDoc );
        tvCmp = dialogCurriculum.findViewById( R.id.tvCmp );
        tvRne = dialogCurriculum.findViewById( R.id.tvRne );

        tvNombreDoctor.setText("Dr." + curriculumVitae.getApellidoPaterno() + " "+ curriculumVitae.getApellidoMaterno() + ", " + curriculumVitae.getNombres());
        tvEspecialidadDoc.setText(curriculumVitae.getNombreEspecialidad());
        tvCmp.setText(curriculumVitae.getCmp());
        tvRne.setText(curriculumVitae.getRne());

        if(curriculumVitae.getEstudios() != null) {
            if (curriculumVitae.getEstudios().size() > 0){
                generarFilasEstudios(curriculumVitae.getEstudios(), content_estudios);
            }
        }
        if(curriculumVitae.getExperiencia() != null) {
            if (curriculumVitae.getExperiencia().size() > 0){
                generarFilasExperiencia(curriculumVitae.getExperiencia(), content_experiencia);
            }
        }
        txtClose.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCurriculum.dismiss();
            }
        } );

        dialogCurriculum.show();
    }

    private void generarFilasEstudios(ArrayList<EstudiosEntity> estudios, LinearLayout content_estudios){
        int total = estudios.size();
        for(int i=0; i<total; i++){
            final EstudiosEntity estudio = estudios.get(i);

            //LayoutPrincipal VERTICAL
            LinearLayout.LayoutParams paramFila = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramFila.setMargins(0,0,0,1);
            LinearLayout fila = new LinearLayout(getActivity());
            fila.setOrientation(LinearLayout.VERTICAL);

            /********** LinearLayout *****************/ //hijoLayoutPrincipal (hlp)
            LinearLayout.LayoutParams llParam1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //llParam1.setMargins(0,0,0,20);
            LinearLayout filaEstudio = new LinearLayout(getActivity());
            filaEstudio.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(16, 16);
            param1.gravity = Gravity.CENTER;
            param1.setMargins(0,0,5,0);
            ImageView ivVineta = new ImageView(getActivity());
            ivVineta.setBackgroundResource(R.drawable.ic_vineta);

            LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tvTitulo = new TextView(getActivity());
            tvTitulo.setTypeface(tvTitulo.getTypeface(),Typeface.BOLD);
            tvTitulo.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorInput));
            tvTitulo.setText(estudio.getTitulo());

            /************ TexView *****************/
            LinearLayout.LayoutParams llParam2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            TextView tvInstitucion = new TextView(getActivity());
            tvInstitucion.setText(estudio.getInstitucion());

            filaEstudio.addView(ivVineta, param1);
            filaEstudio.addView(tvTitulo, param2);

            //agregamos hijos principales
            fila.addView(filaEstudio, llParam1);
            fila.addView(tvInstitucion, llParam2);

            //agregamos la fila
            content_estudios.addView(fila,paramFila);
        }
    }

    private void generarFilasExperiencia(ArrayList<ExperienciaEntity> experiencias, LinearLayout content_experiencia){
        int total = experiencias.size();
        for(int i=0; i<total; i++){
            final ExperienciaEntity experiencia = experiencias.get(i);

            //LayoutPrincipal VERTICAL
            LinearLayout.LayoutParams paramFila = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramFila.setMargins(0,0,0,1);
            LinearLayout fila = new LinearLayout(getActivity());
            fila.setOrientation(LinearLayout.VERTICAL);

            /********** LinearLayout *****************/ //hijoLayoutPrincipal (hlp)
            LinearLayout.LayoutParams llParam1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //llParam1.setMargins(0,0,0,20);
            LinearLayout filaExpereincia = new LinearLayout(getActivity());
            filaExpereincia.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(16, 16);
            param1.gravity = Gravity.CENTER;
            param1.setMargins(0,0,5,0);
            ImageView ivVineta = new ImageView(getActivity());
            ivVineta.setBackgroundResource(R.drawable.ic_vineta);

            LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tvDescripcion = new TextView(getActivity());
            tvDescripcion.setTypeface(tvDescripcion.getTypeface(),Typeface.BOLD);
            tvDescripcion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorInput));
            tvDescripcion.setText(experiencia.getDescripcion());

            /************ TexView *****************/
            LinearLayout.LayoutParams llParam2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            TextView tvLugar = new TextView(getActivity());
            tvLugar.setText(experiencia.getLugar());

            filaExpereincia.addView(ivVineta, param1);
            filaExpereincia.addView(tvDescripcion, param2);

            //agregamos hijos principales
            fila.addView(filaExpereincia, llParam1);
            fila.addView(tvLugar, llParam2);

            //agregamos la fila
            content_experiencia.addView(fila,paramFila);
        }
    }

    private class ObtenerPacientes extends AsyncTask<TurnoEntity,Void,PacienteEntity> {
        ArrayList<PacienteEntity> pacientTmp;
        @Override
        protected PacienteEntity doInBackground(TurnoEntity... params) {
            turnoElegido = params[0];
            PacienteEntity pacienteEntity;
            /*String usuarioId, medicoId=params[0].getMedicoId();
            URLDaoInterface dao = new URLDaoImplement();
            for(int i=0; i<medicos.size(); i++){
                if(medicos.get(i).getMedicoId().equals(medicoId)){
                    usuarioId = medicos.get(i).getUsuarioId();
                    pacientes = dao.getPacientesxUsuario(usuarioId); //recogemos todos los pacientes
                    break;
                }
            }*/

            URLDaoInterface dao = new URLDaoImplement();
            pacientes = dao.getPacientesxUsuario(usuario.getUsuarioId()); //recogemos todos los pacientes
            if (pacientes != null) {
                PacienteQuery pacienteQuery = new PacienteQuery(getActivity());
                //pacienteTmp = especialidadQuery.insertarMedicoxEpecialidade(pacientes); //inserta y recogemos solo las insertadas
                //respuesta = "" + pacienteTmp.size();
                if(pacientes.size()>0) {
                    pacienteEntity = pacientes.get(0);
                }else{
                    pacienteEntity = null;
                }
            } else {
                pacienteEntity = null;
            }
            return pacienteEntity;
        }

        @Override
        protected void onPostExecute(PacienteEntity paciente) {
            cerrarPopUpEspera();
            if(paciente != null) {
                if (paciente.getIsSuccess().equals("true")) {
                    Toast.makeText(getContext(), paciente.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se pudo listar, intente mas tarde", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "No se pudo listar, intente mas tarde", Toast.LENGTH_LONG).show();
            }

            turnoElegido.setInmediata(Constantes.RESERVA_NORMAL);
            turnoElegido.setPacientesEntity(new ArrayList<>());
            if (pacientes.size() > 0) {
                turnoElegido.getPacientesEntity().addAll(pacientes);
            }
            ArrayList<TurnoEntity> datos = new ArrayList<>();
            datos.add(turnoElegido);
            notifyOptionSelected(Constantes.OPCION_DATOS_PACIENTES, datos);
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
