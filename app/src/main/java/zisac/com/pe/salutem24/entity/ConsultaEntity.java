package zisac.com.pe.salutem24.entity;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import zisac.com.pe.salutem24.dataBase.ConsultaQuery;
import zisac.com.pe.salutem24.dataBase.EspecialidadQuery;

public class ConsultaEntity implements Parcelable {
    private String ConsultaId;
    private String PacienteId;
    private String Paciente;
    private String HistoriaClinicaId;
    private String EspecialidadId;
    private String Especialidad;
    private String Medico;
    private String CodigoSala;
    private String EstadoConsulta;
    private String FechaConsulta;
    private String HorarioConsulta;
    private String EstadoConsultaId;
    private String EstadoConsultaDesc;
    private String Total;
    private String UsuarioRegistro;
    private String FechaRegistro;
    private String Estado;
    private String IsSuccess;
    private String Message;
    private String Expiration;
    private String Token;
    private String NroCita;
    private String ConsultaInmediata;
    private View jitsiMeetView;

    private Context context;

    public ConsultaEntity() {
    }

    public ConsultaEntity(Context context) {
        this.context = context;
    }

    public ConsultaEntity(Parcel in) {
        String[] data= new String[23];
        in.readStringArray(data);
        this.ConsultaId=data[0];
        this.PacienteId=data[1];
        this.Paciente=data[2];
        this.HistoriaClinicaId=data[3];
        this.EspecialidadId=data[4];
        this.Especialidad=data[5];
        this.Medico=data[6];
        this.CodigoSala=data[7];
        this.EstadoConsulta=data[8];
        this.FechaConsulta=data[9];
        this.HorarioConsulta=data[10];
        this.EstadoConsultaId=data[11];
        this.EstadoConsultaDesc=data[12];
        this.Total=data[13];
        this.UsuarioRegistro=data[14];
        this.FechaRegistro=data[15];
        this.Estado=data[16];
        this.IsSuccess=data[17];
        this.Message=data[18];
        this.Expiration=data[19];
        this.Token=data[20];
        this.NroCita=data[21];
        this.ConsultaInmediata=data[22];
    }

    public static ConsultaEntity cursorToEntity(Context context, Cursor c)
    {
        ConsultaEntity entity = null;

        if (c != null)
        {
            entity = new ConsultaEntity(context);

            entity.setConsultaId(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_CONSULTA_ID)));
            entity.setPacienteId(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_PACIENTE_ID)));
            entity.setPaciente(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_PACIENTE)));
            entity.setHistoriaClinicaId(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_HISTORIACLINICA_ID)));
            entity.setEspecialidadId(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_ESPECIALIDAD_ID)));
            entity.setEspecialidad(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_ESPECIALIDAD)));
            entity.setMedico(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_MEDICO)));
            entity.setCodigoSala(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_CODIGO_SALA)));
            entity.setEstadoConsulta(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_ESTADO_CONSULTA)));
            entity.setFechaConsulta(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_FECHA_CONSULTA)));
            entity.setHorarioConsulta(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_HORARIO_CONSULTA)));
            entity.setEstadoConsultaId(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_ESTADO_CONSULTA_ID)));
            entity.setEstadoConsultaDesc(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_ESTADO_CONSULTA_DESC)));
            entity.setTotal(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_TOTAL)));
            entity.setUsuarioRegistro(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_USUARIO_REGISTRO)));
            entity.setFechaRegistro(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_FECHA_REGISTRO)));
            entity.setEstado(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_ESTADO)));
            entity.setIsSuccess(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_IS_SUCCESS)));
            entity.setMessage(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_MESSAGE)));
            entity.setExpiration(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_EXPIRATION)));
            entity.setToken(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_TOKEN)));
            entity.setNroCita(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_NRO_CITA)));
            entity.setConsultaInmediata(c.getString(c.getColumnIndex(ConsultaQuery.C_COLUMNA_CONSULTA_INMEDIATA)));


            //Log.e("USUARIO ENTITY",entity.toString());
        }

        return entity ;
    }

    public String getConsultaId() {
        return ConsultaId;
    }

    public void setConsultaId(String consultaId) {
        ConsultaId = consultaId;
    }

    public String getPacienteId() {
        return PacienteId;
    }

    public void setPacienteId(String pacienteId) {
        PacienteId = pacienteId;
    }

    public String getPaciente() {
        return Paciente;
    }

    public void setPaciente(String paciente) {
        Paciente = paciente;
    }

    public String getHistoriaClinicaId() {
        return HistoriaClinicaId;
    }

    public void setHistoriaClinicaId(String historiaClinicaId) {
        HistoriaClinicaId = historiaClinicaId;
    }

    public String getEspecialidadId() {
        return EspecialidadId;
    }

    public void setEspecialidadId(String especialidadId) {
        EspecialidadId = especialidadId;
    }

    public String getEspecialidad() {
        return Especialidad;
    }

    public void setEspecialidad(String especialidad) {
        Especialidad = especialidad;
    }

    public String getMedico() {
        return Medico;
    }

    public void setMedico(String medico) {
        Medico = medico;
    }

    public String getCodigoSala() {
        return CodigoSala;
    }

    public void setCodigoSala(String codigoSala) {
        CodigoSala = codigoSala;
    }

    public String getEstadoConsulta() {
        return EstadoConsulta;
    }

    public void setEstadoConsulta(String estadoConsulta) {
        EstadoConsulta = estadoConsulta;
    }

    public String getFechaConsulta() {
        return FechaConsulta;
    }

    public void setFechaConsulta(String fechaConsulta) {
        FechaConsulta = fechaConsulta;
    }

    public String getHorarioConsulta() {
        return HorarioConsulta;
    }

    public void setHorarioConsulta(String horarioConsulta) {
        HorarioConsulta = horarioConsulta;
    }

    public String getEstadoConsultaId() {
        return EstadoConsultaId;
    }

    public void setEstadoConsultaId(String estadoConsultaId) {
        EstadoConsultaId = estadoConsultaId;
    }

    public String getEstadoConsultaDesc() {
        return EstadoConsultaDesc;
    }

    public void setEstadoConsultaDesc(String estadoConsultaDesc) {
        EstadoConsultaDesc = estadoConsultaDesc;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getUsuarioRegistro() {
        return UsuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        UsuarioRegistro = usuarioRegistro;
    }

    public String getFechaRegistro() {
        return FechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        FechaRegistro = fechaRegistro;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        IsSuccess = isSuccess;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getExpiration() {
        return Expiration;
    }

    public void setExpiration(String expiration) {
        Expiration = expiration;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getNroCita() {
        return NroCita;
    }

    public void setNroCita(String nroCita) {
        NroCita = nroCita;
    }

    public String getConsultaInmediata() {
        return ConsultaInmediata;
    }

    public void setConsultaInmediata(String consultaInmediata) {
        ConsultaInmediata = consultaInmediata;
    }

    public View getJitsiMeetView() {
        return jitsiMeetView;
    }

    public void setJitsiMeetView(View jitsiMeetView) {
        this.jitsiMeetView = jitsiMeetView;
    }

    @Override
    public String toString() {
        return "ConsultaEntity{" +
                "ConsultaId='" + ConsultaId + '\'' +
                ", PacienteId='" + PacienteId + '\'' +
                ", Paciente='" + Paciente + '\'' +
                ", HistoriaClinicaId='" + HistoriaClinicaId + '\'' +
                ", EspecialidadId='" + EspecialidadId + '\'' +
                ", Especialidad='" + Especialidad + '\'' +
                ", Medico='" + Medico + '\'' +
                ", CodigoSala='" + CodigoSala + '\'' +
                ", EstadoConsulta='" + EstadoConsulta + '\'' +
                ", FechaConsulta='" + FechaConsulta + '\'' +
                ", HorarioConsulta='" + HorarioConsulta + '\'' +
                ", EstadoConsultaId='" + EstadoConsultaId + '\'' +
                ", EstadoConsultaDesc='" + EstadoConsultaDesc + '\'' +
                ", Total='" + Total + '\'' +
                ", UsuarioRegistro='" + UsuarioRegistro + '\'' +
                ", FechaRegistro='" + FechaRegistro + '\'' +
                ", Estado='" + Estado + '\'' +
                ", IsSuccess='" + IsSuccess + '\'' +
                ", Message='" + Message + '\'' +
                ", Expiration='" + Expiration + '\'' +
                ", Token='" + Token + '\'' +
                ", NroCita='" + NroCita + '\'' +
                ", ConsultaInmediata='" + ConsultaInmediata + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.ConsultaId, this.PacienteId, this.Paciente, this.HistoriaClinicaId, this.EspecialidadId,
        this.Especialidad, this.Medico, this.CodigoSala, this.EstadoConsulta, this.FechaConsulta, this.HorarioConsulta, this.EstadoConsultaId,
                this.EstadoConsultaDesc, this.Total, this.UsuarioRegistro, this.FechaRegistro,
                this.Estado, this.IsSuccess, this.Message, this.Expiration, this.Token, this.NroCita, this.ConsultaInmediata});
    }

    public static final Parcelable.Creator<ConsultaEntity> CREATOR= new Parcelable.Creator<ConsultaEntity>() {

        @Override
        public ConsultaEntity createFromParcel(Parcel source) {
            return new ConsultaEntity(source);
        }

        @Override
        public ConsultaEntity[] newArray(int size) {
            return new ConsultaEntity[0];
        }
    };
}
