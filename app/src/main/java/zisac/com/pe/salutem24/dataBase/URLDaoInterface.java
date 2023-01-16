package zisac.com.pe.salutem24.dataBase;

import android.content.Context;
import android.widget.ImageView;

import java.util.ArrayList;

import zisac.com.pe.salutem24.entity.ConsultaEntity;
import zisac.com.pe.salutem24.entity.ConsultaSesionEntity;
import zisac.com.pe.salutem24.entity.CurriculumEntity;
import zisac.com.pe.salutem24.entity.EspecialidadEntity;
import zisac.com.pe.salutem24.entity.MedicoEntity;
import zisac.com.pe.salutem24.entity.PacienteEntity;
import zisac.com.pe.salutem24.entity.PagoEntity;
import zisac.com.pe.salutem24.entity.TurnoEntity;
import zisac.com.pe.salutem24.entity.UsuarioEntity;

public interface URLDaoInterface {
    UsuarioEntity getRespuestaLogin(String correo, String clave);
    UsuarioEntity getRegistrarUsuario(String nombre, String correo, String clave);
    ArrayList<EspecialidadEntity> getEspecialidades();
    ArrayList<MedicoEntity> getMedicosxEspecialidad(String especialidad);
    ArrayList<TurnoEntity> getTurnoxEspMedicoFecha(String especialidad, String medico, String fecha);
    CurriculumEntity getCurriculumMedico(String medicoId);
    ArrayList<PacienteEntity> getPacientesxUsuario(String usuarioId);
    PacienteEntity postInsertarGetPacienteId(PacienteEntity paciente);
    String postInsertarGet(String pacienteId, String turnoDetalleId, int pago_id, String inmediata, String importe, String fecha_consulta);
    ArrayList<ConsultaEntity> getListarConsultasProgramadas(String usuario, String especialidad, String estado, String orden);
    ImageView getImage(String urlFoto, Context context);
    TurnoEntity existeMedicoDisponible(String usuarioId);
    ConsultaEntity getConsulta(String consultaId);
    ConsultaSesionEntity getConsultaSesion(String consultaId);
    int postInsertarPago(PagoEntity pago);
}
