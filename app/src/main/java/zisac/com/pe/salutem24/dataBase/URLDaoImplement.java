package zisac.com.pe.salutem24.dataBase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import zisac.com.pe.salutem24.entity.AnfitrionEntity;
import zisac.com.pe.salutem24.entity.ConsultaEntity;
import zisac.com.pe.salutem24.entity.CurriculumEntity;
import zisac.com.pe.salutem24.entity.EspecialidadEntity;
import zisac.com.pe.salutem24.entity.EstudiosEntity;
import zisac.com.pe.salutem24.entity.ExperienciaEntity;
import zisac.com.pe.salutem24.entity.MedicoEntity;
import zisac.com.pe.salutem24.entity.PacienteEntity;
import zisac.com.pe.salutem24.entity.TurnoDetalleEntity;
import zisac.com.pe.salutem24.entity.TurnoEntity;
import zisac.com.pe.salutem24.entity.UsuarioEntity;
import zisac.com.pe.salutem24.utils.Constantes;
import zisac.com.pe.salutem24.utils.Utils;

public class URLDaoImplement implements URLDaoInterface {
    private Utils utils;

    public URLDaoImplement() {
        utils = new Utils();
    }

    @Override
    public UsuarioEntity getRespuestaLogin(String correo, String clave) {
        UsuarioEntity usuarioEntity;

        try{
            usuarioEntity = new UsuarioEntity();

            String url = Constantes.HTTP_CABECERA + Constantes.URL_LOGIN;
            JSONObject joLogin = new JSONObject();
            try {
                joLogin.put("correo", correo);
                joLogin.put("contrasena", clave);
            } catch (JSONException e) {
                joLogin = null;
                e.printStackTrace();
            }

            //Log.e("postLogin", joLogin.toString());
            String result = Utils.POST(url, joLogin);
            Log.e("getLogin", result);

            JSONObject datosUsuario=null;
            if(result!=null) {
                if(!result.equals("")) {
                    datosUsuario = new JSONObject(result);
                }
            }

            if(datosUsuario!=null) {
                usuarioEntity.setIsSuccess(utils.getValueStringOrNull(datosUsuario, "isSuccess"));
                usuarioEntity.setMessage(utils.getValueStringOrNull(datosUsuario, "message"));
                usuarioEntity.setExpiration(utils.getValueStringOrNull(datosUsuario, "expiration"));
                usuarioEntity.setToken(utils.getValueStringOrNull(datosUsuario, "token"));

                JSONObject datosEspecificoUsuario = datosUsuario.getJSONObject("data");

                if (datosEspecificoUsuario != null) {
                    usuarioEntity.setUsuarioId(utils.getValueStringOrNull(datosEspecificoUsuario, "usuario_id"));
                    usuarioEntity.setCorreo(utils.getValueStringOrNull(datosEspecificoUsuario, "correo"));
                    usuarioEntity.setPerfilId(utils.getValueStringOrNull(datosEspecificoUsuario, "perfil_id"));
                    usuarioEntity.setPerfil(utils.getValueStringOrNull(datosEspecificoUsuario, "perfil"));
                    usuarioEntity.setMedicoId(utils.getValueStringOrNull(datosEspecificoUsuario, "medico_id"));
                    usuarioEntity.setMedico(utils.getValueStringOrNull(datosEspecificoUsuario, "medico"));
                    usuarioEntity.setAnfitrion(utils.getValueStringOrNull(datosEspecificoUsuario, "anfitrion"));
                    usuarioEntity.setNombres(utils.getValueStringOrNull(datosEspecificoUsuario, "nombres"));
                    usuarioEntity.setApellidoPaterno(utils.getValueStringOrNull(datosEspecificoUsuario, "apellido_paterno"));
                    usuarioEntity.setApellidoMaterno(utils.getValueStringOrNull(datosEspecificoUsuario, "apellido_materno"));
                    usuarioEntity.setFoto(utils.getValueStringOrNull(datosEspecificoUsuario, "foto"));
                }
            }
        }catch(Exception e){
            //Log.getStackTraceString(e);
            Log.e("ExpectionLogin","" + e.getMessage());
            usuarioEntity = null;
        }

        return usuarioEntity;
    }

    @Override
    public UsuarioEntity getRegistrarUsuario(String nombre, String correo, String clave) {
        UsuarioEntity usuarioEntity;

        try{
            usuarioEntity = new UsuarioEntity();

            String url = Constantes.HTTP_CABECERA + Constantes.URL_INSERTAR;
            JSONObject joRegister = new JSONObject();
            try {
                joRegister.put("correo", correo);
                joRegister.put("contrasena", clave);
                joRegister.put("nombres", nombre);
            } catch (JSONException e) {
                joRegister = null;
                e.printStackTrace();
            }

            String result = Utils.POST(url, joRegister);

            JSONObject datosUsuario=null;
            if(result!=null) {
                if(!result.equals("")) {
                    datosUsuario = new JSONObject(result);
                }
            }

            if(datosUsuario!=null) {
                usuarioEntity.setIsSuccess(utils.getValueStringOrNull(datosUsuario, "isSuccess"));
                usuarioEntity.setMessage(utils.getValueStringOrNull(datosUsuario, "message"));
                usuarioEntity.setExpiration(utils.getValueStringOrNull(datosUsuario, "expiration"));
                usuarioEntity.setToken(utils.getValueStringOrNull(datosUsuario, "token"));

                JSONObject datosEspecificoUsuario = datosUsuario.getJSONObject("data");

                if(datosEspecificoUsuario.length()>0) {
                    usuarioEntity.setUsuarioId(utils.getValueStringOrNull(datosEspecificoUsuario, "usuario_id"));
                    usuarioEntity.setCorreo(utils.getValueStringOrNull(datosEspecificoUsuario, "correo"));
                    usuarioEntity.setPerfilId(utils.getValueStringOrNull(datosEspecificoUsuario, "perfil_id"));
                    usuarioEntity.setPerfil(utils.getValueStringOrNull(datosEspecificoUsuario, "perfil"));
                    usuarioEntity.setMedicoId(utils.getValueStringOrNull(datosEspecificoUsuario, "medico_id"));

                    JSONObject datosMedico = datosEspecificoUsuario.getJSONObject("medico");
                    if(datosMedico.length()>0) {
                        usuarioEntity.setMedicoEntity(new MedicoEntity());
                        usuarioEntity.getMedicoEntity().setMedicoId(utils.getValueStringOrNull(datosMedico, "medico_id"));
                        usuarioEntity.getMedicoEntity().setEspecialidadId(utils.getValueStringOrNull(datosMedico, "especialidad_id"));
                        usuarioEntity.getMedicoEntity().setUsuarioId(utils.getValueStringOrNull(datosMedico, "usuario_id"));
                        usuarioEntity.getMedicoEntity().setUsuario(utils.getValueStringOrNull(datosMedico, "usuario"));
                        usuarioEntity.getMedicoEntity().setNombres(utils.getValueStringOrNull(datosMedico, "nombres"));
                        usuarioEntity.getMedicoEntity().setApellidoPaterno(utils.getValueStringOrNull(datosMedico, "apellido_paterno"));
                        usuarioEntity.getMedicoEntity().setApellidoMaterno(utils.getValueStringOrNull(datosMedico, "apellido_materno"));
                        usuarioEntity.getMedicoEntity().setDocumentoId(utils.getValueStringOrNull(datosMedico, "documento_id"));
                        usuarioEntity.getMedicoEntity().setNumeroDocumento(utils.getValueStringOrNull(datosMedico, "numero_documento"));
                        usuarioEntity.getMedicoEntity().setCmp(utils.getValueStringOrNull(datosMedico, "cmp"));
                        usuarioEntity.getMedicoEntity().setCelular(utils.getValueStringOrNull(datosMedico, "celular"));
                        usuarioEntity.getMedicoEntity().setCorreo(utils.getValueStringOrNull(datosMedico, "correo"));
                        usuarioEntity.getMedicoEntity().setSexo(utils.getValueStringOrNull(datosMedico, "sexo"));
                        usuarioEntity.getMedicoEntity().setFoto(utils.getValueStringOrNull(datosMedico, "foto"));
                    }


                    JSONObject datosAnfitrion = datosEspecificoUsuario.getJSONObject("anfitrion");
                    if(datosAnfitrion.length()>0) {
                        usuarioEntity.setAnfitrionEntity(new AnfitrionEntity());
                        usuarioEntity.getAnfitrionEntity().setAnfitrionId(utils.getValueStringOrNull(datosAnfitrion, "anfitrion_id"));
                        usuarioEntity.getAnfitrionEntity().setUsuarioId(utils.getValueStringOrNull(datosAnfitrion, "usuario_id"));
                        usuarioEntity.getAnfitrionEntity().setNombre(utils.getValueStringOrNull(datosAnfitrion, "nombre"));
                        usuarioEntity.getAnfitrionEntity().setContrasena(utils.getValueStringOrNull(datosAnfitrion, "contrasena"));
                    }

                    usuarioEntity.setNombres(utils.getValueStringOrNull(datosEspecificoUsuario, "nombres"));
                    usuarioEntity.setApellidoPaterno(utils.getValueStringOrNull(datosEspecificoUsuario, "apellido_paterno"));
                    usuarioEntity.setApellidoMaterno(utils.getValueStringOrNull(datosEspecificoUsuario, "apellido_materno"));
                    usuarioEntity.setFoto(utils.getValueStringOrNull(datosEspecificoUsuario, "foto"));
                }
            }
        }catch(Exception e){
            //Log.getStackTraceString(e);
            Log.e("ExpectionLogin","" + e.getMessage());
            usuarioEntity = null;
        }

        return usuarioEntity;
    }

    @Override
    public ArrayList<EspecialidadEntity> getEspecialidades() {
        ArrayList<EspecialidadEntity> especialidades;

        try{
            especialidades = new ArrayList<>();
            String url = Constantes.HTTP_CABECERA + Constantes.URL_ESPECIALIDADES;
            JSONObject objectEspecialidad = utils.getJSONfromURL(url);

            JSONArray arrayEspecialidades = objectEspecialidad.getJSONArray("data");

            if(arrayEspecialidades.length()>0){
                for (int i = 0; i < arrayEspecialidades.length(); i++) {
                    JSONObject datosEspecialidad= arrayEspecialidades.getJSONObject(i);
                    EspecialidadEntity especialidadEntity = new EspecialidadEntity();

                    especialidadEntity.setEspecialidadId(utils.getValueStringOrNull(datosEspecialidad, "especialidad_id"));
                    especialidadEntity.setCodigo(utils.getValueStringOrNull(datosEspecialidad, "codigo_especialidad"));
                    especialidadEntity.setNombre(utils.getValueStringOrNull(datosEspecialidad, "nombre_especialidad"));
                    especialidadEntity.setDescripcion(utils.getValueStringOrNull(datosEspecialidad, "descripcion"));
                    especialidadEntity.setImporte(utils.getValueStringOrNull(datosEspecialidad, "importe"));
                    especialidadEntity.setFoto(utils.getValueStringOrNull(datosEspecialidad, "foto"));
                    especialidadEntity.setTotal(utils.getValueStringOrNull(datosEspecialidad, "total"));

                    if(i==0){
                        especialidadEntity.setIsSuccess(utils.getValueStringOrNull(objectEspecialidad, "isSuccess"));
                        especialidadEntity.setMessage(utils.getValueStringOrNull(objectEspecialidad, "message"));
                        //hay dos totales diferentes uno en la parte principal y otro en data.
                    }

                    Bitmap bmp = null;
                    try{
                        String urlPost = Constantes.HTTP_CABECERA + especialidadEntity.getFoto();
                        URL urlImage = new URL(urlPost);
                        bmp = BitmapFactory.decodeStream(urlImage.openConnection().getInputStream());
                    }catch(Exception e){
                        Log.getStackTraceString(e);
                    }
                    especialidadEntity.setBitmapResource(bmp);

                    especialidades.add(especialidadEntity);
                }
                //Log.e("Solicitud ", solicitudes.toString());
            } else {
                especialidades = null;
            }
        }catch(Exception e){
            Log.getStackTraceString(e);
            especialidades = null;
        }

        return especialidades;
    }

    @Override
    public ArrayList<MedicoEntity> getMedicosxEspecialidad(String especialidad) {
        ArrayList<MedicoEntity> medicos;

        try{
            medicos = new ArrayList<>();
            String url = Constantes.HTTP_CABECERA + Constantes.URL_MEDICOS_ESPECIALIDAD + especialidad;
            JSONObject objectMedico = utils.getJSONfromURL(url);
            JSONArray arrayMedicos = objectMedico.getJSONArray("data");

            if(arrayMedicos.length()>0){
                for (int i = 0; i < arrayMedicos.length(); i++) {
                    JSONObject datosMedicos= arrayMedicos.getJSONObject(i);
                    MedicoEntity medicoEntity = new MedicoEntity();

                    medicoEntity.setMedicoId(utils.getValueStringOrNull(datosMedicos, "medico_id"));
                    medicoEntity.setUsuarioId(utils.getValueStringOrNull(datosMedicos, "usuario_id"));
                    medicoEntity.setEspecialidadId(utils.getValueStringOrNull(datosMedicos, "especialidad_id"));
                    medicoEntity.setEspecialidad(utils.getValueStringOrNull(datosMedicos, "especialidad"));
                    medicoEntity.setUsuario(utils.getValueStringOrNull(datosMedicos, "usuario"));
                    medicoEntity.setNombres(utils.getValueStringOrNull(datosMedicos, "nombres"));
                    medicoEntity.setApellidoPaterno(utils.getValueStringOrNull(datosMedicos, "apellido_paterno"));
                    medicoEntity.setApellidoMaterno(utils.getValueStringOrNull(datosMedicos, "apellido_materno"));
                    medicoEntity.setDocumento(utils.getValueStringOrNull(datosMedicos, "documento"));
                    medicoEntity.setDocumentoId(utils.getValueStringOrNull(datosMedicos, "documento_id"));
                    medicoEntity.setNumeroDocumento(utils.getValueStringOrNull(datosMedicos, "numero_documento"));
                    medicoEntity.setCmp(utils.getValueStringOrNull(datosMedicos, "cmp"));
                    medicoEntity.setCelular(utils.getValueStringOrNull(datosMedicos, "celular"));
                    medicoEntity.setCorreo(utils.getValueStringOrNull(datosMedicos, "correo"));
                    medicoEntity.setSexo(utils.getValueStringOrNull(datosMedicos, "sexo"));
                    medicoEntity.setFoto(utils.getValueStringOrNull(datosMedicos, "foto"));
                    medicoEntity.setTotal(utils.getValueStringOrNull(datosMedicos, "total"));
                    if(i==0){
                        medicoEntity.setIsSuccess(utils.getValueStringOrNull(objectMedico, "isSuccess"));
                        medicoEntity.setMessage(utils.getValueStringOrNull(objectMedico, "message"));
                        //hay dos totales diferentes uno en la parte principal y otro en data.
                    }
                    medicos.add(medicoEntity);
                }
            } else {
                medicos = new ArrayList<>();
            }
        }catch(Exception e){
            Log.getStackTraceString(e);
            medicos = null;
        }

        return medicos;
    }

    @Override
    public ArrayList<TurnoEntity> getTurnoxEspMedicoFecha(String especialidad, String medico, String fecha) {
        ArrayList<TurnoEntity> turnos;

        try{
            turnos = new ArrayList<>();
            String url = Constantes.HTTP_CABECERA + Constantes.URL_TURNO_FECHA_MEDICO + especialidad
                    + "&medico=" + medico  + "&fecha=" + fecha + "&pageSize=100&pageNum=1" ;
            JSONObject objectTurno = utils.getJSONfromURL(url);
            JSONArray arrayTurnos = objectTurno.getJSONArray("data");

            if(arrayTurnos.length()>0){
                for (int i = 0; i < arrayTurnos.length(); i++) {
                    JSONObject datosTurnos= arrayTurnos.getJSONObject(i);
                    TurnoEntity turnoEntity = new TurnoEntity();

                    turnoEntity.setTurnoId(utils.getValueStringOrNull(datosTurnos, "turno_id"));
                    turnoEntity.setEspecialidadId(utils.getValueStringOrNull(datosTurnos, "especialidad_id"));
                    turnoEntity.setEspecialidad(utils.getValueStringOrNull(datosTurnos, "especialidad"));
                    turnoEntity.setMedicoId(utils.getValueStringOrNull(datosTurnos, "medico_id"));
                    turnoEntity.setMedico(utils.getValueStringOrNull(datosTurnos, "medico"));
                    turnoEntity.setHorario(utils.getValueStringOrNull(datosTurnos, "horario"));
                    turnoEntity.setFechaTurno(utils.getValueStringOrNull(datosTurnos, "fecha_turno"));
                    turnoEntity.setImporte(utils.getValueStringOrNull(datosTurnos, "importe"));
                    turnoEntity.setFoto(utils.getValueStringOrNull(datosTurnos, "foto"));
                    if(i==0){
                        turnoEntity.setIsSuccess(utils.getValueStringOrNull(objectTurno, "isSuccess"));
                        turnoEntity.setMessage(utils.getValueStringOrNull(objectTurno, "message"));
                        //hay dos totales diferentes uno en la parte principal y otro en data.
                    }
                    JSONArray arrayTurnoDetalle = datosTurnos.getJSONArray("turno_detalle");

                    if(arrayTurnoDetalle.length()>0){
                        turnoEntity.setTurnosDetalle(new ArrayList<>());
                        for (int j = 0; j < arrayTurnoDetalle.length(); j++) {
                            JSONObject datosTurnoDetalle= arrayTurnoDetalle.getJSONObject(j);

                            TurnoDetalleEntity turnoDetalleEntity = new TurnoDetalleEntity();
                            turnoDetalleEntity.setTurnoDetalleId(utils.getValueStringOrNull(datosTurnoDetalle, "turno_detalle_id"));
                            turnoDetalleEntity.setTurnoId(utils.getValueStringOrNull(datosTurnoDetalle, "turno_id"));
                            turnoDetalleEntity.setHorario(utils.getValueStringOrNull(datosTurnoDetalle, "horario"));
                            turnoDetalleEntity.setEstadoTurno(utils.getValueStringOrNull(datosTurnoDetalle, "estado_turno"));
                            turnoDetalleEntity.setEstadoTurnoId(utils.getValueStringOrNull(datosTurnoDetalle, "estado_turno_id"));
                            turnoEntity.getTurnosDetalle().add(turnoDetalleEntity);
                        }
                    }
                    turnos.add(turnoEntity);
                }

                //Log.e("TurnoDetalle", "" + turnos.get(0).getTurnosDetalle().toString());
            } else {
                turnos = new ArrayList<>();
            }
        }catch(Exception e){
            Log.getStackTraceString(e);
            turnos = null;
        }

        return turnos;
    }

    @Override
    public CurriculumEntity getCurriculumMedico(String medicoId) {
        CurriculumEntity curriculumEntity; ;

        try{
            curriculumEntity = new CurriculumEntity();
            String url = Constantes.HTTP_CABECERA + Constantes.URL_CURRICULUM_MEDICO + medicoId;
            JSONObject objectTurno = utils.getJSONfromURL(url);
            //turnoEntity.setTurnoId(utils.getValueStringOrNull(objectTurno, "message"));
            JSONObject datosCurriculum = objectTurno.getJSONObject("data");

            if(datosCurriculum.length()>0){
                curriculumEntity.setMedicoId(utils.getValueStringOrNull(datosCurriculum, "medico_id"));
                curriculumEntity.setFoto(utils.getValueStringOrNull(datosCurriculum, "foto"));
                curriculumEntity.setNombres(utils.getValueStringOrNull(datosCurriculum, "nombres"));
                curriculumEntity.setApellidoPaterno(utils.getValueStringOrNull(datosCurriculum, "apellido_paterno"));
                curriculumEntity.setApellidoMaterno(utils.getValueStringOrNull(datosCurriculum, "apellido_materno"));
                curriculumEntity.setCmp(utils.getValueStringOrNull(datosCurriculum, "cmp"));
                curriculumEntity.setRne(utils.getValueStringOrNull(datosCurriculum, "rne"));
                curriculumEntity.setNombreEspecialidad(utils.getValueStringOrNull(datosCurriculum, "nombre_especialidad"));

                curriculumEntity.setIsSuccess(utils.getValueStringOrNull(objectTurno, "isSuccess"));
                curriculumEntity.setMessage(utils.getValueStringOrNull(objectTurno, "message"));

                JSONArray arrayEstudios = datosCurriculum.getJSONArray("estudios");
                if(arrayEstudios.length()>0){
                    curriculumEntity.setEstudios(new ArrayList<>());
                    for (int j = 0; j < arrayEstudios.length(); j++) {
                        JSONObject datosEstudios= arrayEstudios.getJSONObject(j);

                        EstudiosEntity estudiosEntity = new EstudiosEntity();
                        estudiosEntity.setMedicoId(utils.getValueStringOrNull(datosEstudios, "medico_id"));
                        estudiosEntity.setTitulo(utils.getValueStringOrNull(datosEstudios, "titulo"));
                        estudiosEntity.setInstitucion(utils.getValueStringOrNull(datosEstudios, "institucion"));
                        curriculumEntity.getEstudios().add(estudiosEntity);
                    }
                }

                JSONArray arrayExperiencia = datosCurriculum.getJSONArray("experiencia");
                if(arrayExperiencia.length()>0){
                    curriculumEntity.setExperiencia(new ArrayList<>());
                    for (int j = 0; j < arrayExperiencia.length(); j++) {
                        JSONObject datosExperiencia= arrayExperiencia.getJSONObject(j);

                        ExperienciaEntity experienciaEntity = new ExperienciaEntity();
                        experienciaEntity.setMedicoId(utils.getValueStringOrNull(datosExperiencia, "medico_id"));
                        experienciaEntity.setDescripcion(utils.getValueStringOrNull(datosExperiencia, "descripcion"));
                        experienciaEntity.setLugar(utils.getValueStringOrNull(datosExperiencia, "lugar"));
                        curriculumEntity.getExperiencia().add(experienciaEntity);
                    }
                }
                //Log.e("Curriculum ", curriculumEntity.toString());
            } else {
                curriculumEntity = new CurriculumEntity();
            }
        }catch(Exception e){
            Log.getStackTraceString(e);
            curriculumEntity = null;
        }

        return curriculumEntity;
    }

    @Override
    public ArrayList<PacienteEntity> getPacientesxUsuario(String usuarioId) {
        ArrayList<PacienteEntity> pacientes;

        try{
            pacientes = new ArrayList<>();
            String url = Constantes.HTTP_CABECERA + Constantes.URL_LISTAR_PACIENTE + usuarioId;
            //Log.e("url", "" + url);
            JSONObject objectPaciente = utils.getJSONfromURL(url);
            JSONArray arrayPacientes = objectPaciente.getJSONArray("data");

            if(arrayPacientes.length()>0){
                for (int i = 0; i < arrayPacientes.length(); i++) {
                    JSONObject datosPacientes= arrayPacientes.getJSONObject(i);
                    PacienteEntity pacienteEntity = new PacienteEntity();

                    pacienteEntity.setPacienteId(utils.getValueStringOrNull(datosPacientes, "paciente_id"));
                    pacienteEntity.setUsuarioId(utils.getValueStringOrNull(datosPacientes, "usuario_id"));
                    pacienteEntity.setUsuario(utils.getValueStringOrNull(datosPacientes, "usuario"));
                    pacienteEntity.setNombres(utils.getValueStringOrNull(datosPacientes, "nombres"));
                    pacienteEntity.setApellidoPaterno(utils.getValueStringOrNull(datosPacientes, "apellido_paterno"));
                    pacienteEntity.setApellidoMaterno(utils.getValueStringOrNull(datosPacientes, "apellido_materno"));
                    pacienteEntity.setDocumentoId(utils.getValueStringOrNull(datosPacientes, "documento_id"));
                    pacienteEntity.setDocumento(utils.getValueStringOrNull(datosPacientes, "documento"));
                    pacienteEntity.setNumeroDocumento(utils.getValueStringOrNull(datosPacientes, "numero_documento"));
                    pacienteEntity.setCelular(utils.getValueStringOrNull(datosPacientes, "celular"));
                    pacienteEntity.setSexo(utils.getValueStringOrNull(datosPacientes, "sexo"));
                    pacienteEntity.setFechaNacimiento(utils.getValueStringOrNull(datosPacientes, "fecha_nacimiento"));
                    pacienteEntity.setTotal(utils.getValueStringOrNull(datosPacientes, "total"));

                    if(i==0){
                        pacienteEntity.setIsSuccess(utils.getValueStringOrNull(objectPaciente, "isSuccess"));
                        pacienteEntity.setMessage(utils.getValueStringOrNull(objectPaciente, "message"));
                    }

                    pacientes.add(pacienteEntity);
                }
                //Log.e("pacientes", "" + pacientes.toString());
            } else {
                pacientes = new ArrayList<>();
            }
        }catch(Exception e){
            Log.getStackTraceString(e);
            pacientes = null;
        }

        return pacientes;
    }

    @Override
    public PacienteEntity postInsertarGetPacienteId(PacienteEntity paciente) {
        PacienteEntity pacienteEntity;

        try{
            pacienteEntity = new PacienteEntity();

            String url = Constantes.HTTP_CABECERA + Constantes.URL_INSERTAR_PACIENTE;
            JSONObject joPaciente = new JSONObject();
            try {
                joPaciente.put("usuario_id", Integer.parseInt(paciente.getUsuarioId()));
                joPaciente.put("nombres", paciente.getNombres());
                joPaciente.put("apellido_paterno", paciente.getApellidoPaterno());
                joPaciente.put("apellido_materno", paciente.getApellidoMaterno());
                joPaciente.put("numero_documento", paciente.getNumeroDocumento());
                joPaciente.put("parentesco_id", 1);
                joPaciente.put("celular", paciente.getCelular());
                joPaciente.put("sexo", paciente.getSexo());
                joPaciente.put("fecha_nacimiento", paciente.getFechaNacimiento());
            } catch (JSONException e) {
                joPaciente = null;
                e.printStackTrace();
            }

            //Log.e("envioPacienteId", "json_" + joPaciente.toString());
            String result = Utils.POST(url, joPaciente);
            //Log.e("resultPacienteId", "post_" + result);

            JSONObject datosInsert=null;
            if(result!=null) {
                if(!result.equals("")) {
                    datosInsert = new JSONObject(result);
                }
            }

            if(datosInsert!=null) {
                pacienteEntity.setIsSuccess(utils.getValueStringOrNull(datosInsert, "isSuccess"));
                pacienteEntity.setMessage(utils.getValueStringOrNull(datosInsert, "message"));
                pacienteEntity.setPacienteId(utils.getValueStringOrNull(datosInsert, "paciente_id"));
            }
        }catch(Exception e){
            //Log.getStackTraceString(e);
            Log.e("ExepInsertPaciente","" + e.getMessage());
            pacienteEntity = null;
        }

        return pacienteEntity;
    }

    @Override
    public String postInsertarGet(String pacienteId, String turnoDetalleId, String inmediata) {
        String cadena=null, mensaje, consultaId, isSucces;

        try{

            String url = Constantes.HTTP_CABECERA + Constantes.URL_REGISTRAR_CONSULTA;
            JSONObject joConsulta = new JSONObject();
            try {
                joConsulta.put("paciente_id", Integer.parseInt(pacienteId));
                joConsulta.put("turno_detalle_id", Integer.parseInt(turnoDetalleId));
                joConsulta.put("inmediata", inmediata.equals(Constantes.RESERVA_INMEDIATA));
                //joConsulta.put("inmediata", false);
            } catch (JSONException e) {
                joConsulta = null;
                e.printStackTrace();
            }

            //Log.e("envioPagar", "json_" + joConsulta.toString());
            String result = Utils.POST(url, joConsulta);
            //Log.e("resultPagar", "post_" + result);

            JSONObject datosInsert=null;
            if(result!=null) {
                if(!result.equals("")) {
                    datosInsert = new JSONObject(result);
                }
            }

            if(datosInsert!=null) {
                isSucces = utils.getValueStringOrNull(datosInsert, "isSuccess");
                mensaje = utils.getValueStringOrNull(datosInsert, "message");
                consultaId = utils.getValueStringOrNull(datosInsert, "consulta_id");
                cadena = isSucces + "-" + mensaje + "-" + consultaId;
            }
        }catch(Exception e){
            //Log.getStackTraceString(e);
            Log.e("ExepInsertPaciente","" + e.getMessage());
            cadena = null;
        }

        return cadena;
    }

    @Override
    public ArrayList<ConsultaEntity> getListarConsultasProgramadas(String usuario, String especialidad, String estado, String orden) {
        ArrayList<ConsultaEntity> consultas;

        try{
            consultas = new ArrayList<>();
            String url = Constantes.HTTP_CABECERA + Constantes.URL_CONSULTA_LISTAR  + usuario + "&especialidad=" + especialidad + "&estado=" + estado + "&orden=" + orden;
            //Log.e("ConsultasProgramadas", "" + url);
            JSONObject objectConsulta = utils.getJSONfromURL(url);
            JSONArray arrayConsultas = objectConsulta.getJSONArray("data");

            if(arrayConsultas.length()>0){
                for (int i = 0; i < arrayConsultas.length(); i++) {
                    JSONObject datosMedicos= arrayConsultas.getJSONObject(i);
                    ConsultaEntity consultaEntity = new ConsultaEntity();

                    consultaEntity.setConsultaId(utils.getValueStringOrNull(datosMedicos, "consulta_id"));
                    consultaEntity.setPacienteId(utils.getValueStringOrNull(datosMedicos, "paciente_id"));
                    consultaEntity.setPaciente(utils.getValueStringOrNull(datosMedicos, "paciente"));
                    consultaEntity.setHistoriaClinicaId(utils.getValueStringOrNull(datosMedicos, "historia_clinica_id"));
                    consultaEntity.setEspecialidadId(utils.getValueStringOrNull(datosMedicos, "especialidad_id"));
                    consultaEntity.setEspecialidad(utils.getValueStringOrNull(datosMedicos, "especialidad"));
                    consultaEntity.setMedico(utils.getValueStringOrNull(datosMedicos, "medico"));
                    consultaEntity.setCodigoSala(utils.getValueStringOrNull(datosMedicos, "codigo_sala"));
                    consultaEntity.setEstadoConsulta(utils.getValueStringOrNull(datosMedicos, "estado_consulta"));
                    consultaEntity.setFechaConsulta(utils.getValueStringOrNull(datosMedicos, "fecha_consulta"));
                    consultaEntity.setHorarioConsulta(utils.getValueStringOrNull(datosMedicos, "horario_consulta"));
                    consultaEntity.setEstadoConsultaId(utils.getValueStringOrNull(datosMedicos, "estado_consulta_id"));
                    consultaEntity.setEstadoConsultaDesc(utils.getValueStringOrNull(datosMedicos, "estado_consulta_desc"));
                    consultaEntity.setTotal(utils.getValueStringOrNull(datosMedicos, "total"));
                    //consultaEntity.setNroCita(utils.getValueStringOrNull(datosMedicos, "cita"));
                    if(i==0){
                        consultaEntity.setIsSuccess(utils.getValueStringOrNull(objectConsulta, "isSuccess"));
                        consultaEntity.setMessage(utils.getValueStringOrNull(objectConsulta, "message"));
                        //hay dos totales diferentes uno en la parte principal y otro en data.
                    }
                    consultas.add(consultaEntity);
                }

                //Log.e("Consultas","" + consultas.toString());
            } else {
                consultas = new ArrayList<>();
            }
        }catch(Exception e){
            Log.getStackTraceString(e);
            consultas = null;
        }

        return consultas;
    }

    @Override
    public ImageView getImage(String urlFoto, Context context) {
        ImageView ivFoto;

        try{
            ivFoto = new ImageView(context);
            String urlPost = Constantes.HTTP_CABECERA + urlFoto;
            URL url = new URL(urlPost);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            ivFoto.setImageBitmap(bmp);
        }catch(Exception e){
            Log.getStackTraceString(e);
            ivFoto = null;
        }

        return ivFoto;
    }

    @Override
    public TurnoEntity existeMedicoDisponible(String usuarioId) {
        TurnoEntity turnoEntity;

        try{
            turnoEntity = new TurnoEntity();
            String url = Constantes.HTTP_CABECERA + Constantes.URL_CONSULTA_INMEDIATA;
            JSONObject objectConsulta = utils.getJSONfromURL(url);

            if(objectConsulta!=null) {
                turnoEntity.setIsSuccess(utils.getValueStringOrNull(objectConsulta, "isSuccess"));
                turnoEntity.setMessage(utils.getValueStringOrNull(objectConsulta, "message"));

                JSONObject datosTurno = objectConsulta.getJSONObject("data");
                if(datosTurno!=null) {
                    turnoEntity.setTurnoId(utils.getValueStringOrNull(datosTurno, "turno_id"));
                    turnoEntity.setEspecialidadId(utils.getValueStringOrNull(datosTurno, "especialidad_id"));
                    turnoEntity.setEspecialidad(utils.getValueStringOrNull(datosTurno, "especialidad"));
                    turnoEntity.setMedicoId(utils.getValueStringOrNull(datosTurno, "medico_id"));
                    turnoEntity.setMedico(utils.getValueStringOrNull(datosTurno, "medico"));
                    turnoEntity.setHorario(utils.getValueStringOrNull(datosTurno, "horario"));
                    turnoEntity.setFechaTurno(utils.getValueStringOrNull(datosTurno, "fecha_turno"));
                    turnoEntity.setImporte(utils.getValueStringOrNull(datosTurno, "importe"));
                    turnoEntity.setFoto(utils.getValueStringOrNull(datosTurno, "foto"));

                    turnoEntity.setTurnoDetalleEntity(new TurnoDetalleEntity());
                    turnoEntity.getTurnoDetalleEntity().setTurnoDetalleId(utils.getValueStringOrNull(datosTurno, "turno_detalle_id"));
                    turnoEntity.getTurnoDetalleEntity().setEstadoTurno(utils.getValueStringOrNull(datosTurno, "estado_turno"));
                    turnoEntity.getTurnoDetalleEntity().setEstadoTurnoId(utils.getValueStringOrNull(datosTurno, "estado_turno_id"));
                }


            }
        }catch(Exception e){
            //Log.getStackTraceString(e);
            Log.e("ERROR","" + e.getMessage());
            turnoEntity = null;
        }

        if(turnoEntity!=null) {
            if (turnoEntity.getIsSuccess().equals("true")) {
                ArrayList<PacienteEntity> pacientes;

                try {
                    pacientes = new ArrayList<>();
                    String url = Constantes.HTTP_CABECERA + Constantes.URL_LISTAR_PACIENTE + usuarioId;
                    //Log.e("url", "" + url);
                    JSONObject objectPaciente = utils.getJSONfromURL(url);
                    JSONArray arrayPacientes = objectPaciente.getJSONArray("data");

                    if (arrayPacientes.length() > 0) {
                        for (int i = 0; i < arrayPacientes.length(); i++) {
                            JSONObject datosPacientes = arrayPacientes.getJSONObject(i);
                            PacienteEntity pacienteEntity = new PacienteEntity();

                            pacienteEntity.setPacienteId(utils.getValueStringOrNull(datosPacientes, "paciente_id"));
                            pacienteEntity.setUsuarioId(utils.getValueStringOrNull(datosPacientes, "usuario_id"));
                            pacienteEntity.setUsuario(utils.getValueStringOrNull(datosPacientes, "usuario"));
                            pacienteEntity.setNombres(utils.getValueStringOrNull(datosPacientes, "nombres"));
                            pacienteEntity.setApellidoPaterno(utils.getValueStringOrNull(datosPacientes, "apellido_paterno"));
                            pacienteEntity.setApellidoMaterno(utils.getValueStringOrNull(datosPacientes, "apellido_materno"));
                            pacienteEntity.setDocumentoId(utils.getValueStringOrNull(datosPacientes, "documento_id"));
                            pacienteEntity.setDocumento(utils.getValueStringOrNull(datosPacientes, "documento"));
                            pacienteEntity.setNumeroDocumento(utils.getValueStringOrNull(datosPacientes, "numero_documento"));
                            pacienteEntity.setCelular(utils.getValueStringOrNull(datosPacientes, "celular"));
                            pacienteEntity.setSexo(utils.getValueStringOrNull(datosPacientes, "sexo"));
                            pacienteEntity.setFechaNacimiento(utils.getValueStringOrNull(datosPacientes, "fecha_nacimiento"));
                            pacienteEntity.setTotal(utils.getValueStringOrNull(datosPacientes, "total"));

                            if (i == 0) {
                                pacienteEntity.setIsSuccess(utils.getValueStringOrNull(objectPaciente, "isSuccess"));
                                pacienteEntity.setMessage(utils.getValueStringOrNull(objectPaciente, "message"));
                            }

                            pacientes.add(pacienteEntity);
                        }
                        //Log.e("pacientes", "" + pacientes.toString());
                    } else {
                        pacientes = new ArrayList<>();
                    }
                } catch (Exception e) {
                    Log.getStackTraceString(e);
                    pacientes = null;
                }

                turnoEntity.setPacientesEntity(new ArrayList<>());
                if (pacientes.size() > 0) {
                    turnoEntity.getPacientesEntity().addAll(pacientes);
                }
            }
        }

        return turnoEntity;
    }

    @Override
    public ConsultaEntity getConsulta(String consultaId) {
        ConsultaEntity consultaEntity; ;

        try{
            consultaEntity = new ConsultaEntity();
            String url = Constantes.HTTP_CABECERA + Constantes.URL_CONSULTA + consultaId;
            JSONObject objectTurno = utils.getJSONfromURL(url);
            //turnoEntity.setTurnoId(utils.getValueStringOrNull(objectTurno, "message"));
            JSONObject datosCurriculum = objectTurno.getJSONObject("data");

            if(datosCurriculum.length()>0){
                consultaEntity.setConsultaId(utils.getValueStringOrNull(datosCurriculum, "consulta_id"));
                consultaEntity.setPacienteId(utils.getValueStringOrNull(datosCurriculum, "paciente_id"));
                consultaEntity.setPaciente(utils.getValueStringOrNull(datosCurriculum, "paciente"));
                consultaEntity.setHistoriaClinicaId(utils.getValueStringOrNull(datosCurriculum, "historia_clinica_id"));
                consultaEntity.setEspecialidadId(utils.getValueStringOrNull(datosCurriculum, "especialidad_id"));
                consultaEntity.setEspecialidad(utils.getValueStringOrNull(datosCurriculum, "especialidad"));
                consultaEntity.setMedico(utils.getValueStringOrNull(datosCurriculum, "medico"));
                consultaEntity.setCodigoSala(utils.getValueStringOrNull(datosCurriculum, "codigo_sala"));
                consultaEntity.setEstadoConsulta(utils.getValueStringOrNull(datosCurriculum, "estado_consulta"));
                //consultaEntity.setTurnoDetalleId(utils.getValueStringOrNull(datosCurriculum, "turno_detalle_id")); Este campo para que  mandan?
                consultaEntity.setFechaConsulta(utils.getValueStringOrNull(datosCurriculum, "fecha_consulta"));
                consultaEntity.setHorarioConsulta(utils.getValueStringOrNull(datosCurriculum, "horario_consulta"));
                consultaEntity.setEstadoConsultaId(utils.getValueStringOrNull(datosCurriculum, "estado_consulta_id"));
                //consultaEntity.setEstadoConsultaDesc(utils.getValueStringOrNull(datosCurriculum, "estadp_consulta_desc")); No viene este campo, deberia venir.
                consultaEntity.setTotal(utils.getValueStringOrNull(datosCurriculum, "total"));

                consultaEntity.setIsSuccess(utils.getValueStringOrNull(objectTurno, "isSuccess"));
                consultaEntity.setMessage(utils.getValueStringOrNull(objectTurno, "message"));

                //Log.e("Consulta ", curriculumEntity.toString());
            } else {
                consultaEntity = new ConsultaEntity();
            }
        }catch(Exception e){
            Log.getStackTraceString(e);
            consultaEntity = null;
        }

        return consultaEntity;
    }
}

