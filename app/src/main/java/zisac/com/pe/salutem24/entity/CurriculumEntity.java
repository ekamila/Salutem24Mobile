package zisac.com.pe.salutem24.entity;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import zisac.com.pe.salutem24.dataBase.CurriculumQuery;

public class CurriculumEntity implements Parcelable {
    private String MedicoId;
    private String Foto;
    private String Nombres;
    private String ApellidoPaterno;
    private String ApellidoMaterno;
    private String Cmp;
    private String Rne;
    private String NombreEspecialidad;
    private String EstadoTurno;
    private String UsuarioRegistro;
    private String FechaRegistro;
    private String Estado;
    private ArrayList<EstudiosEntity> estudios;
    private ArrayList<ExperienciaEntity> experiencia;
    private String IsSuccess;
    private String Message;
    private String Expiration;
    private String Token;

    public CurriculumEntity() {
    }

    private Context context;

    public CurriculumEntity(Context context) {
        this.context = context;
    }

    public CurriculumEntity(Parcel in) {
        String[] data= new String[16];
        in.readStringArray(data);
        this.MedicoId=data[0];
        this.Foto=data[1];
        this.Nombres=data[2];
        this.ApellidoPaterno=data[3];
        this.ApellidoMaterno=data[4];
        this.Cmp=data[5];
        this.Rne=data[6];
        this.NombreEspecialidad=data[7];
        this.EstadoTurno=data[8];
        this.UsuarioRegistro=data[9];
        this.FechaRegistro=data[10];
        this.Estado=data[11];
        this.IsSuccess=data[12];
        this.Message=data[13];
        this.Expiration=data[14];
        this.Token=data[15];
    }

    public static CurriculumEntity cursorToEntity(Context context, Cursor c)
    {
        CurriculumEntity entity = null;

        if (c != null)
        {
            entity = new CurriculumEntity(context);

            entity.setMedicoId(c.getString(c.getColumnIndex(CurriculumQuery.C_COLUMNA_MEDICO_ID)));
            entity.setFoto(c.getString(c.getColumnIndex(CurriculumQuery.C_COLUMNA_FOTO)));
            entity.setNombres(c.getString(c.getColumnIndex(CurriculumQuery.C_COLUMNA_NOMBRES)));
            entity.setApellidoPaterno(c.getString(c.getColumnIndex(CurriculumQuery.C_COLUMNA_APELLIDO_PATERNO)));
            entity.setApellidoMaterno(c.getString(c.getColumnIndex(CurriculumQuery.C_COLUMNA_APELLIDO_MATERNO)));
            entity.setCmp(c.getString(c.getColumnIndex(CurriculumQuery.C_COLUMNA_CMP)));
            entity.setRne(c.getString(c.getColumnIndex(CurriculumQuery.C_COLUMNA_RNE)));
            entity.setNombreEspecialidad(c.getString(c.getColumnIndex(CurriculumQuery.C_COLUMNA_NOMBRE_ESPECIALIDAD)));
            entity.setEstadoTurno(c.getString(c.getColumnIndex(CurriculumQuery.C_COLUMNA_ESTADO_TURNO)));
            entity.setUsuarioRegistro(c.getString(c.getColumnIndex(CurriculumQuery.C_COLUMNA_USUARIO_REGISTRO)));
            entity.setFechaRegistro(c.getString(c.getColumnIndex(CurriculumQuery.C_COLUMNA_FECHA_REGISTRO)));
            entity.setFoto(c.getString(c.getColumnIndex(CurriculumQuery.C_COLUMNA_ESTADO)));
            entity.setIsSuccess(c.getString(c.getColumnIndex(CurriculumQuery.C_COLUMNA_IS_SUCCESS)));
            entity.setMessage(c.getString(c.getColumnIndex(CurriculumQuery.C_COLUMNA_MESSAGE)));
            entity.setExpiration(c.getString(c.getColumnIndex(CurriculumQuery.C_COLUMNA_EXPIRATION)));
            entity.setToken(c.getString(c.getColumnIndex(CurriculumQuery.C_COLUMNA_TOKEN)));

            //Log.e("USUARIO ENTITY",entity.toString());
        }

        return entity ;
    }

    public String getMedicoId() {
        return MedicoId;
    }

    public void setMedicoId(String medicoId) {
        MedicoId = medicoId;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        ApellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        ApellidoMaterno = apellidoMaterno;
    }

    public String getCmp() {
        return Cmp;
    }

    public void setCmp(String cmp) {
        Cmp = cmp;
    }

    public String getRne() {
        return Rne;
    }

    public void setRne(String rne) {
        Rne = rne;
    }

    public String getNombreEspecialidad() {
        return NombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        NombreEspecialidad = nombreEspecialidad;
    }

    public String getEstadoTurno() {
        return EstadoTurno;
    }

    public void setEstadoTurno(String estadoTurno) {
        EstadoTurno = estadoTurno;
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

    public ArrayList<EstudiosEntity> getEstudios() {
        return estudios;
    }

    public void setEstudios(ArrayList<EstudiosEntity> estudios) {
        this.estudios = estudios;
    }

    public ArrayList<ExperienciaEntity> getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(ArrayList<ExperienciaEntity> experiencia) {
        this.experiencia = experiencia;
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

    @Override
    public String toString() {
        return "CurriculumEntity{" +
                "MedicoId='" + MedicoId + '\'' +
                ", Foto='" + Foto + '\'' +
                ", Nombres='" + Nombres + '\'' +
                ", ApellidoPaterno='" + ApellidoPaterno + '\'' +
                ", ApellidoMaterno='" + ApellidoMaterno + '\'' +
                ", Cmp='" + Cmp + '\'' +
                ", Rne='" + Rne + '\'' +
                ", NombreEspecialidad='" + NombreEspecialidad + '\'' +
                ", EstadoTurno='" + EstadoTurno + '\'' +
                ", UsuarioRegistro='" + UsuarioRegistro + '\'' +
                ", FechaRegistro='" + FechaRegistro + '\'' +
                ", Estado='" + Estado + '\'' +
                ", IsSuccess='" + IsSuccess + '\'' +
                ", Message='" + Message + '\'' +
                ", Expiration='" + Expiration + '\'' +
                ", Token='" + Token + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.MedicoId, this.Foto, this.Nombres, this.ApellidoPaterno, this.ApellidoMaterno, this.Cmp,
        this.Rne, this.NombreEspecialidad, this.EstadoTurno, this.UsuarioRegistro, this.FechaRegistro, this.Estado,
                this.IsSuccess, this.Message, this.Expiration, this.Token});
    }

    public static final Parcelable.Creator<CurriculumEntity> CREATOR= new Parcelable.Creator<CurriculumEntity>() {

        @Override
        public CurriculumEntity createFromParcel(Parcel source) {
            return new CurriculumEntity(source);
        }

        @Override
        public CurriculumEntity[] newArray(int size) {
            return new CurriculumEntity[0];
        }
    };
}

