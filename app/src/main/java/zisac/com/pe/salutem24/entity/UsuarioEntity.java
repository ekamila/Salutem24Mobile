package zisac.com.pe.salutem24.entity;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import zisac.com.pe.salutem24.dataBase.UsuarioQuery;

public class UsuarioEntity implements Parcelable {
    private String IsSuccess;
    private String Message;
    private String Expiration;
    private String Token;
    private String UsuarioId;
    private String Usuario;
    private String Clave;
    private String TipoDocumento;
    private String NumeroDocumento;
    private String Nombres;
    private String ApellidoPaterno;
    private String ApellidoMaterno;
    private String Sexo;
    private String Correo;
    private String Perfil;
    private String PerfilId;
    private String Medico;
    private String MedicoId;
    private String Anfitrion;
    private String Foto;
    private String UsuarioRegistro;
    private String FechaRegistro;
    private MedicoEntity medicoEntity;
    private AnfitrionEntity anfitrionEntity;
    private String Estado;

    private Context context;

    public UsuarioEntity() {
    }

    public UsuarioEntity(Context context) {
        this.context = context;
    }

    public UsuarioEntity(Parcel in) {
        String[] data= new String[23];
        in.readStringArray(data);
        this.IsSuccess=data[0];
        this.Message=data[1];
        this.Expiration=data[2];
        this.Token=data[3];
        this.UsuarioId=data[4];
        this.Usuario=data[5];
        this.Clave=data[6];
        this.TipoDocumento=data[7];
        this.NumeroDocumento=data[8];
        this.Nombres=data[9];
        this.ApellidoPaterno=data[10];
        this.ApellidoMaterno=data[11];
        this.Sexo=data[12];
        this.Correo=data[13];
        this.Perfil=data[14];
        this.PerfilId=data[15];
        this.Medico=data[16];
        this.MedicoId=data[17];
        this.Anfitrion=data[18];
        this.Foto=data[19];
        this.UsuarioRegistro=data[20];
        this.FechaRegistro=data[21];
        this.Usuario=data[22];
    }

    public static UsuarioEntity cursorToEntity(Context context, Cursor c)
    {
        UsuarioEntity entity = null;

        if (c != null)
        {
            entity = new UsuarioEntity(context);

            entity.setIsSuccess(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_IS_SUCCESS)));
            entity.setMessage(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_MESSAGE)));
            entity.setExpiration(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_EXPIRATION)));
            entity.setToken(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_TOKEN)));
            entity.setUsuarioId(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_USUARIO_ID)));
            entity.setUsuario(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_USUARIO)));
            entity.setClave(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_CLAVE)));
            entity.setTipoDocumento(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_TIPO_DOCUMENTO)));
            entity.setNumeroDocumento(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_NUMERO_DOCUMENTO)));
            entity.setNombres(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_NOMBRES)));
            entity.setApellidoPaterno(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_APELLIDO_PATERNO)));
            entity.setApellidoMaterno(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_APELLIDO_MATERNO)));
            entity.setSexo(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_SEXO)));
            entity.setCorreo(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_CORREO)));
            entity.setPerfil(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_PERFIL)));
            entity.setPerfilId(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_PERFIL_ID)));
            entity.setMedico(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_MEDICO)));
            entity.setMedicoId(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_MEDICO_ID)));
            entity.setAnfitrion(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_ANFITRION)));
            entity.setFoto(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_FOTO)));
            entity.setUsuarioRegistro(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_USUARIO_REGISTRO)));
            entity.setFechaRegistro(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_FECHA_REGISTRO)));
            entity.setEstado(c.getString(c.getColumnIndex(UsuarioQuery.C_COLUMNA_ESTADO)));

            //Log.e("USUARIO ENTITY",entity.toString());
        }

        return entity ;
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

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getTipoDocumento() {
        return TipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        TipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return NumeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        NumeroDocumento = numeroDocumento;
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

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getPerfil() {
        return Perfil;
    }

    public void setPerfil(String perfil) {
        Perfil = perfil;
    }

    public String getPerfilId() {
        return PerfilId;
    }

    public void setPerfilId(String perfilId) {
        PerfilId = perfilId;
    }

    public String getMedico() {
        return Medico;
    }

    public void setMedico(String medico) {
        Medico = medico;
    }

    public String getMedicoId() {
        return MedicoId;
    }

    public void setMedicoId(String medicoId) {
        MedicoId = medicoId;
    }

    public String getAnfitrion() {
        return Anfitrion;
    }

    public void setAnfitrion(String anfitrion) {
        Anfitrion = anfitrion;
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

    public MedicoEntity getMedicoEntity() {
        return medicoEntity;
    }

    public void setMedicoEntity(MedicoEntity medicoEntity) {
        this.medicoEntity = medicoEntity;
    }

    public AnfitrionEntity getAnfitrionEntity() {
        return anfitrionEntity;
    }

    public void setAnfitrionEntity(AnfitrionEntity anfitrionEntity) {
        this.anfitrionEntity = anfitrionEntity;
    }

    @Override
    public String toString() {
        return "UsuarioEntity{" +
                "IsSuccess='" + IsSuccess + '\'' +
                ", Message='" + Message + '\'' +
                ", Expiration='" + Expiration + '\'' +
                ", Token='" + Token + '\'' +
                ", UsuarioId='" + UsuarioId + '\'' +
                ", Usuario='" + Usuario + '\'' +
                ", Clave='" + Clave + '\'' +
                ", TipoDocumento='" + TipoDocumento + '\'' +
                ", NumeroDocumento='" + NumeroDocumento + '\'' +
                ", Nombres='" + Nombres + '\'' +
                ", ApellidoPaterno='" + ApellidoPaterno + '\'' +
                ", ApellidoMaterno='" + ApellidoMaterno + '\'' +
                ", Sexo='" + Sexo + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Perfil='" + Perfil + '\'' +
                ", PerfilId='" + PerfilId + '\'' +
                ", Medico='" + Medico + '\'' +
                ", MedicoId='" + MedicoId + '\'' +
                ", Anfitrion='" + Anfitrion + '\'' +
                ", Foto='" + Foto + '\'' +
                ", UsuarioRegistro='" + UsuarioRegistro + '\'' +
                ", FechaRegistro='" + FechaRegistro + '\'' +
                ", Estado='" + Estado + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.IsSuccess, this.Message, this.Expiration, this.Token, this.UsuarioId, this.Usuario, this.Clave,
            this.TipoDocumento, this.NumeroDocumento, this.Nombres, this.ApellidoPaterno, this.ApellidoMaterno, this.Sexo, this.Correo, this.Perfil,
            this.PerfilId, this.Medico, this.MedicoId, this.Anfitrion, this.Foto, this.UsuarioRegistro, this.FechaRegistro, this.Usuario});
    }

    public static final Parcelable.Creator<UsuarioEntity> CREATOR= new Parcelable.Creator<UsuarioEntity>() {

        @Override
        public UsuarioEntity createFromParcel(Parcel source) {
            return new UsuarioEntity(source);
        }

        @Override
        public UsuarioEntity[] newArray(int size) {
            return new UsuarioEntity[0];
        }
    };
}

