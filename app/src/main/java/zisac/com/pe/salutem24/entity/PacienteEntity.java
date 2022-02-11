package zisac.com.pe.salutem24.entity;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import zisac.com.pe.salutem24.dataBase.PacienteQuery;

public class PacienteEntity implements Parcelable {
    private String PacienteId;
    private String UsuarioId;
    private String Usuario;
    private String Nombres;
    private String ApellidoPaterno;
    private String ApellidoMaterno;
    private String DocumentoId;
    private String Documento;
    private String NumeroDocumento;
    private String Celular;
    private String Sexo;
    private String FechaNacimiento;
    private String Total;
    private String UsuarioRegistro;
    private String FechaRegistro;
    private String Estado;
    private String IsSuccess;
    private String Message;
    private String Expiration;
    private String Token;

    private Context context;

    public PacienteEntity(String pacienteId, String nombres) {
        PacienteId = pacienteId;
        Nombres = nombres;
    }

    public PacienteEntity() {
    }

    public PacienteEntity(Context context) {
        this.context = context;
    }

    public PacienteEntity(Parcel in) {
        String[] data= new String[20];
        in.readStringArray(data);
        this.PacienteId=data[0];
        this.UsuarioId=data[1];
        this.Usuario=data[2];
        this.Nombres=data[3];
        this.ApellidoPaterno=data[4];
        this.ApellidoMaterno=data[5];
        this.DocumentoId=data[6];
        this.Documento=data[7];
        this.NumeroDocumento=data[8];
        this.Celular=data[9];
        this.Sexo=data[10];
        this.FechaNacimiento=data[11];
        this.Total=data[12];
        this.UsuarioRegistro=data[13];
        this.FechaRegistro=data[14];
        this.Estado=data[15];
        this.IsSuccess=data[16];
        this.Message=data[17];
        this.Expiration=data[18];
        this.Token=data[19];
    }

    public static PacienteEntity cursorToEntity(Context context, Cursor c)
    {
        PacienteEntity entity = null;

        if (c != null)
        {
            entity = new PacienteEntity(context);

            entity.setPacienteId(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_PACIENTE_ID)));
            entity.setUsuarioId(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_USUARIOID)));
            entity.setUsuario(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_USUARIO)));
            entity.setNombres(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_NOMBRES)));
            entity.setApellidoPaterno(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_APELLIDO_PATERNO)));
            entity.setApellidoMaterno(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_APELLIDO_MATERNO)));
            entity.setDocumentoId(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_DOCUMENTO_ID)));
            entity.setDocumento(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_DOCUMENTO)));
            entity.setNumeroDocumento(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_NUMERO_DOCUMENTO)));
            entity.setCelular(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_CELULAR)));
            entity.setSexo(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_SEXO)));
            entity.setFechaNacimiento(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_FECHA_NACIMIENTO)));
            entity.setTotal(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_TOTAL)));
            entity.setUsuarioRegistro(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_USUARIO_REGISTRO)));
            entity.setFechaNacimiento(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_FECHA_REGISTRO)));
            entity.setEstado(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_ESTADO)));
            entity.setIsSuccess(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_IS_SUCCESS)));
            entity.setMessage(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_MESSAGE)));
            entity.setExpiration(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_EXPIRATION)));
            entity.setToken(c.getString(c.getColumnIndex(PacienteQuery.C_COLUMNA_TOKEN)));

            //Log.e("USUARIO ENTITY",entity.toString());
        }

        return entity ;
    }

    public String getPacienteId() {
        return PacienteId;
    }

    public void setPacienteId(String pacienteId) {
        PacienteId = pacienteId;
    }

    public String getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        UsuarioId = usuarioId;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
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

    public String getDocumentoId() {
        return DocumentoId;
    }

    public void setDocumentoId(String documentoId) {
        DocumentoId = documentoId;
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String documento) {
        Documento = documento;
    }

    public String getNumeroDocumento() {
        return NumeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        NumeroDocumento = numeroDocumento;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }

    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
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

    @Override
    public String toString() {
        return "PacienteEntity{" +
                "PacienteId='" + PacienteId + '\'' +
                ", UsuarioId='" + UsuarioId + '\'' +
                ", Usuario='" + Usuario + '\'' +
                ", Nombres='" + Nombres + '\'' +
                ", ApellidoPaterno='" + ApellidoPaterno + '\'' +
                ", ApellidoMaterno='" + ApellidoMaterno + '\'' +
                ", DocumentoId='" + DocumentoId + '\'' +
                ", Documento='" + Documento + '\'' +
                ", NumeroDocumento='" + NumeroDocumento + '\'' +
                ", Celular='" + Celular + '\'' +
                ", Sexo='" + Sexo + '\'' +
                ", FechaNacimiento='" + FechaNacimiento + '\'' +
                ", Total='" + Total + '\'' +
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
        dest.writeStringArray(new String[]{this.PacienteId, this.UsuarioId, this.Usuario, this.Nombres, this.ApellidoPaterno, this.ApellidoMaterno,
        this.DocumentoId, this.Documento, this.NumeroDocumento, this.Celular, this.Sexo, this.FechaNacimiento, this.Total, this.UsuarioRegistro,
        this.FechaRegistro, this.Estado, this.IsSuccess, this.Message, this.Expiration, this.Token});
    }

    public static final Parcelable.Creator<PacienteEntity> CREATOR= new Parcelable.Creator<PacienteEntity>() {

        @Override
        public PacienteEntity createFromParcel(Parcel source) {
            return new PacienteEntity(source);
        }

        @Override
        public PacienteEntity[] newArray(int size) {
            return new PacienteEntity[0];
        }
    };
}

