package zisac.com.pe.salutem24.entity;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import zisac.com.pe.salutem24.dataBase.TurnoQuery;

public class TurnoEntity implements Parcelable {
    private String TurnoId;
    private String EspecialidadId;
    private String Especialidad;
    private String MedicoId;
    private String Medico;
    private String Horario;
    private String FechaTurno;
    private String Importe;
    private String Foto;
    private String UsuarioRegistro;
    private String FechaRegistro;
    private String Estado;
    private String IsSuccess;
    private String Message;
    private String Expiration;
    private String Token;
    private String Inmediata;
    private ArrayList<TurnoDetalleEntity> turnosDetalle;
    private ArrayList<PacienteEntity> pacientesEntity;
    private TurnoDetalleEntity turnoDetalleEntity;
    private PacienteEntity paciente;

    private Context context;

    public TurnoEntity() {
    }

    public TurnoEntity(Context context) {
        this.context = context;
    }

    public TurnoEntity(Parcel in) {
        String[] data= new String[17];
        in.readStringArray(data);
        this.TurnoId=data[0];
        this.EspecialidadId=data[1];
        this.Especialidad=data[2];
        this.MedicoId=data[3];
        this.Medico=data[4];
        this.Horario=data[5];
        this.FechaTurno=data[6];
        this.Importe=data[7];
        this.Foto=data[8];
        this.UsuarioRegistro=data[9];
        this.FechaRegistro=data[10];
        this.Estado=data[11];
        this.IsSuccess=data[12];
        this.Message=data[13];
        this.Expiration=data[14];
        this.Token=data[15];
        this.Inmediata=data[16];
    }


    public static TurnoEntity cursorToEntity(Context context, Cursor c)
    {
        TurnoEntity entity = null;

        if (c != null)
        {
            entity = new TurnoEntity(context);

            entity.setTurnoId(c.getString(c.getColumnIndex(TurnoQuery.C_COLUMNA_TURNO_ID)));
            entity.setEspecialidadId(c.getString(c.getColumnIndex(TurnoQuery.C_COLUMNA_ESPECIALIDAD_ID)));
            entity.setEspecialidad(c.getString(c.getColumnIndex(TurnoQuery.C_COLUMNA_ESPECIALIDAD)));
            entity.setMedicoId(c.getString(c.getColumnIndex(TurnoQuery.C_COLUMNA_MEDICOID)));
            entity.setMedico(c.getString(c.getColumnIndex(TurnoQuery.C_COLUMNA_MEDICO)));
            entity.setHorario(c.getString(c.getColumnIndex(TurnoQuery.C_COLUMNA_HORARIO)));
            entity.setFechaTurno(c.getString(c.getColumnIndex(TurnoQuery.C_COLUMNA_FECHATURNO)));
            entity.setImporte(c.getString(c.getColumnIndex(TurnoQuery.C_COLUMNA_IMPORTE)));
            entity.setFoto(c.getString(c.getColumnIndex(TurnoQuery.C_COLUMNA_FOTO)));
            entity.setUsuarioRegistro(c.getString(c.getColumnIndex(TurnoQuery.C_COLUMNA_USUARIO_REGISTRO)));
            entity.setFechaRegistro(c.getString(c.getColumnIndex(TurnoQuery.C_COLUMNA_FECHA_REGISTRO)));
            entity.setEstado(c.getString(c.getColumnIndex(TurnoQuery.C_COLUMNA_ESTADO)));
            entity.setIsSuccess(c.getString(c.getColumnIndex(TurnoQuery.C_COLUMNA_IS_SUCCESS)));
            entity.setMessage(c.getString(c.getColumnIndex(TurnoQuery.C_COLUMNA_MESSAGE)));
            entity.setExpiration(c.getString(c.getColumnIndex(TurnoQuery.C_COLUMNA_EXPIRATION)));
            entity.setToken(c.getString(c.getColumnIndex(TurnoQuery.C_COLUMNA_TOKEN)));
            entity.setInmediata(c.getString(c.getColumnIndex(TurnoQuery.C_COLUMNA_INMEDIATA)));

            //Log.e("USUARIO ENTITY",entity.toString());
        }

        return entity ;
    }

    public String getTurnoId() {
        return TurnoId;
    }

    public void setTurnoId(String turnoId) {
        TurnoId = turnoId;
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

    public String getMedicoId() {
        return MedicoId;
    }

    public void setMedicoId(String medicoId) {
        MedicoId = medicoId;
    }

    public String getMedico() {
        return Medico;
    }

    public void setMedico(String medico) {
        Medico = medico;
    }

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String horario) {
        Horario = horario;
    }

    public String getFechaTurno() {
        return FechaTurno;
    }

    public void setFechaTurno(String fechaTurno) {
        FechaTurno = fechaTurno;
    }

    public String getImporte() {
        return Importe;
    }

    public void setImporte(String importe) {
        Importe = importe;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
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

    public ArrayList<TurnoDetalleEntity> getTurnosDetalle() {
        return turnosDetalle;
    }

    public void setTurnosDetalle(ArrayList<TurnoDetalleEntity> turnosDetalle) {
        this.turnosDetalle = turnosDetalle;
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

    public ArrayList<PacienteEntity> getPacientesEntity() {
        return pacientesEntity;
    }

    public void setPacientesEntity(ArrayList<PacienteEntity> pacientesEntity) {
        this.pacientesEntity = pacientesEntity;
    }

    public TurnoDetalleEntity getTurnoDetalleEntity() {
        return turnoDetalleEntity;
    }

    public void setTurnoDetalleEntity(TurnoDetalleEntity turnoDetalleEntity) {
        this.turnoDetalleEntity = turnoDetalleEntity;
    }

    public PacienteEntity getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteEntity paciente) {
        this.paciente = paciente;
    }

    public String getInmediata() {
        return Inmediata;
    }

    public void setInmediata(String inmediata) {
        Inmediata = inmediata;
    }

    @Override
    public String toString() {
        return "TurnoEntity{" +
                "TurnoId='" + TurnoId + '\'' +
                ", EspecialidadId='" + EspecialidadId + '\'' +
                ", Especialidad='" + Especialidad + '\'' +
                ", MedicoId='" + MedicoId + '\'' +
                ", Medico='" + Medico + '\'' +
                ", Horario='" + Horario + '\'' +
                ", FechaTurno='" + FechaTurno + '\'' +
                ", Importe='" + Importe + '\'' +
                ", Foto='" + Foto + '\'' +
                ", UsuarioRegistro='" + UsuarioRegistro + '\'' +
                ", FechaRegistro='" + FechaRegistro + '\'' +
                ", Estado='" + Estado + '\'' +
                ", IsSuccess='" + IsSuccess + '\'' +
                ", Message='" + Message + '\'' +
                ", Expiration='" + Expiration + '\'' +
                ", Token='" + Token + '\'' +
                ", Inmediata='" + Inmediata + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.TurnoId, this.EspecialidadId, this.Especialidad, this.MedicoId, this.Medico, this.Horario,
                this.FechaTurno, this.Importe, this.Foto, this.UsuarioRegistro, this.FechaRegistro, this.Estado,
                this.IsSuccess, this.Message, this.Expiration, this.Token, this.Inmediata});
    }

    public static final Parcelable.Creator<TurnoEntity> CREATOR= new Parcelable.Creator<TurnoEntity>() {

        @Override
        public TurnoEntity createFromParcel(Parcel source) {
            return new TurnoEntity(source);
        }

        @Override
        public TurnoEntity[] newArray(int size) {
            return new TurnoEntity[0];
        }
    };
}


