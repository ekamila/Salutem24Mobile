package zisac.com.pe.salutem24.entity;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import zisac.com.pe.salutem24.dataBase.MedicoQuery;

public class MedicoEntity implements Parcelable {
    private String MedicoId;
    private String EspecialidadId;
    private String Especialidad;
    private String UsuarioId;
    private String Usuario;
    private String Nombres;
    private String ApellidoPaterno;
    private String ApellidoMaterno;
    private String Documento;
    private String DocumentoId;
    private String NumeroDocumento;
    private String Cmp;
    private String Celular;
    private String Correo;
    private String Sexo;
    private String Foto;
    private String Total;
    private String UsuarioRegistro;
    private String FechaRegistro;
    private String Estado;
    private String IsSuccess;
    private String Message;
    private String Expiration;
    private String Token;

    private Context context;

    public MedicoEntity() {
    }

    public MedicoEntity(String medicoId, String nombres) {
        MedicoId = medicoId;
        Nombres = nombres;
    }

    public MedicoEntity(Context context) {
        this.context = context;
    }

    public MedicoEntity(Parcel in) {
        String[] data= new String[24];
        in.readStringArray(data);
        this.MedicoId=data[0];
        this.EspecialidadId=data[1];
        this.Especialidad=data[2];
        this.UsuarioId=data[3];
        this.Usuario=data[4];
        this.Nombres=data[5];
        this.ApellidoPaterno=data[6];
        this.ApellidoPaterno=data[7];
        this.Documento=data[8];
        this.DocumentoId=data[9];
        this.NumeroDocumento=data[10];
        this.Cmp=data[11];
        this.Celular=data[12];
        this.Correo=data[13];
        this.Sexo=data[14];
        this.Total=data[15];
        this.Foto=data[16];
        this.UsuarioRegistro=data[17];
        this.FechaRegistro=data[18];
        this.Estado=data[19];
        this.IsSuccess=data[20];
        this.Message=data[21];
        this.Expiration=data[22];
        this.Token=data[23];
    }

    public static MedicoEntity cursorToEntity(Context context, Cursor c)
    {
        MedicoEntity entity = null;

        if (c != null)
        {
            entity = new MedicoEntity(context);

            entity.setMedicoId(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_MEDICO_ID)));
            entity.setEspecialidadId(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_ESPECIALIDAD_ID)));
            entity.setEspecialidad(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_ESPECIALIDAD)));
            entity.setUsuarioId(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_USUARIO_ID)));
            entity.setUsuario(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_USUARIO)));
            entity.setNombres(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_NOMBRES)));
            entity.setApellidoPaterno(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_APELLIDO_PATERNO)));
            entity.setApellidoMaterno(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_APELLIDO_MATERNO)));
            entity.setDocumento(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_DOCUMENTO)));
            entity.setDocumentoId(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_DOCUMENTO_ID)));
            entity.setNumeroDocumento(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_NUMERO_DOCUMENTO)));
            entity.setCmp(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_CMP)));
            entity.setCelular(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_CELULAR)));
            entity.setCorreo(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_CORREO)));
            entity.setSexo(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_SEXO)));
            entity.setFoto(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_FOTO)));
            entity.setTotal(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_TOTAL)));
            entity.setUsuarioRegistro(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_USUARIO_REGISTRO)));
            entity.setFechaRegistro(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_FECHA_REGISTRO)));
            entity.setEstado(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_ESTADO)));
            entity.setIsSuccess(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_IS_SUCCESS)));
            entity.setMessage(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_MESSAGE)));
            entity.setExpiration(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_EXPIRATION)));
            entity.setToken(c.getString(c.getColumnIndex(MedicoQuery.C_COLUMNA_TOKEN)));

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

    public String getEspecialidadId() {
        return EspecialidadId;
    }

    public void setEspecialidadId(String especialidadId) {
        EspecialidadId = especialidadId;
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

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String documento) {
        Documento = documento;
    }

    public String getDocumentoId() {
        return DocumentoId;
    }

    public void setDocumentoId(String documentoId) {
        DocumentoId = documentoId;
    }

    public String getNumeroDocumento() {
        return NumeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        NumeroDocumento = numeroDocumento;
    }

    public String getCmp() {
        return Cmp;
    }

    public void setCmp(String cmp) {
        Cmp = cmp;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
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

    public String getEspecialidad() {
        return Especialidad;
    }

    public void setEspecialidad(String especialidad) {
        Especialidad = especialidad;
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
        return "MedicoEntity{" +
                "MedicoId='" + MedicoId + '\'' +
                ", EspecialidadId='" + EspecialidadId + '\'' +
                ", Especialidad='" + Especialidad + '\'' +
                ", UsuarioId='" + UsuarioId + '\'' +
                ", Usuario='" + Usuario + '\'' +
                ", Nombres='" + Nombres + '\'' +
                ", ApellidoPaterno='" + ApellidoPaterno + '\'' +
                ", ApellidoMaterno='" + ApellidoMaterno + '\'' +
                ", Documento='" + Documento + '\'' +
                ", DocumentoId='" + DocumentoId + '\'' +
                ", NumeroDocumento='" + NumeroDocumento + '\'' +
                ", Cmp='" + Cmp + '\'' +
                ", Celular='" + Celular + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Sexo='" + Sexo + '\'' +
                ", Foto='" + Foto + '\'' +
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
        dest.writeStringArray(new String[]{this.MedicoId, this.EspecialidadId, this.Especialidad, this.UsuarioId, this.Usuario, this.Nombres,
        this.ApellidoPaterno, this.ApellidoPaterno, this.Documento, this.DocumentoId, this.NumeroDocumento, this.Cmp, this.Celular,
        this.Correo, this.Sexo, this.Foto, this.Total, this.UsuarioRegistro, this.FechaRegistro, this.Estado, this.IsSuccess, this.Message, this.Expiration, this.Token});
    }

    public static final Parcelable.Creator<MedicoEntity> CREATOR= new Parcelable.Creator<MedicoEntity>() {

        @Override
        public MedicoEntity createFromParcel(Parcel source) {
            return new MedicoEntity(source);
        }

        @Override
        public MedicoEntity[] newArray(int size) {
            return new MedicoEntity[0];
        }
    };
}

