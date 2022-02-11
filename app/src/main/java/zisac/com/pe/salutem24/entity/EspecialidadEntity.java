package zisac.com.pe.salutem24.entity;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import zisac.com.pe.salutem24.dataBase.EspecialidadQuery;

public class EspecialidadEntity implements Parcelable {
    private String EspecialidadId;
    private String Codigo;
    private String Nombre;
    private String Descripcion;
    private String Importe;
    private String Foto;
    private String Total;
    private String UsuarioRegistro;
    private String FechaRegistro;
    private String Estado;
    private String IsSuccess;
    private String Message;
    private String Expiration;
    private String Token;
    private Bitmap bitmapResource;

    private Context context;

    public EspecialidadEntity() {
    }

    public EspecialidadEntity(Context context) {
        this.context = context;
    }

    public EspecialidadEntity(Parcel in) {
        String[] data= new String[14];
        in.readStringArray(data);
        this.EspecialidadId=data[0];
        this.Codigo=data[1];
        this.Nombre=data[2];
        this.Descripcion=data[3];
        this.Importe=data[4];
        this.Foto=data[5];
        this.Total=data[6];
        this.UsuarioRegistro=data[7];
        this.FechaRegistro=data[8];
        this.Estado=data[9];
        this.IsSuccess=data[10];
        this.Message=data[11];
        this.Expiration=data[12];
        this.Token=data[13];
    }

    public static EspecialidadEntity cursorToEntity(Context context, Cursor c)
    {
        EspecialidadEntity entity = null;

        if (c != null)
        {
            entity = new EspecialidadEntity(context);

            entity.setEspecialidadId(c.getString(c.getColumnIndex(EspecialidadQuery.C_COLUMNA_ESPECIALIDAD_ID)));
            entity.setCodigo(c.getString(c.getColumnIndex(EspecialidadQuery.C_COLUMNA_CODIGO)));
            entity.setCodigo(c.getString(c.getColumnIndex(EspecialidadQuery.C_COLUMNA_NOMBRE)));
            entity.setCodigo(c.getString(c.getColumnIndex(EspecialidadQuery.C_COLUMNA_DESCRIPCION)));
            entity.setCodigo(c.getString(c.getColumnIndex(EspecialidadQuery.C_COLUMNA_IMPORTE)));
            entity.setCodigo(c.getString(c.getColumnIndex(EspecialidadQuery.C_COLUMNA_FOTO)));
            entity.setCodigo(c.getString(c.getColumnIndex(EspecialidadQuery.C_COLUMNA_TOTAL)));
            entity.setCodigo(c.getString(c.getColumnIndex(EspecialidadQuery.C_COLUMNA_USUARIO_REGISTRO)));
            entity.setCodigo(c.getString(c.getColumnIndex(EspecialidadQuery.C_COLUMNA_FECHA_REGISTRO)));
            entity.setEstado(c.getString(c.getColumnIndex(EspecialidadQuery.C_COLUMNA_ESTADO)));
            entity.setIsSuccess(c.getString(c.getColumnIndex(EspecialidadQuery.C_COLUMNA_IS_SUCCESS)));
            entity.setMessage(c.getString(c.getColumnIndex(EspecialidadQuery.C_COLUMNA_MESSAGE)));
            entity.setExpiration(c.getString(c.getColumnIndex(EspecialidadQuery.C_COLUMNA_EXPIRATION)));
            entity.setToken(c.getString(c.getColumnIndex(EspecialidadQuery.C_COLUMNA_TOKEN)));

            //Log.e("USUARIO ENTITY",entity.toString());
        }

        return entity ;
    }

    public String getEspecialidadId() {
        return EspecialidadId;
    }

    public void setEspecialidadId(String especialidadId) {
        EspecialidadId = especialidadId;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
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
    }public String getIsSuccess() {
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

    public Bitmap getBitmapResource() {
        return bitmapResource;
    }

    public void setBitmapResource(Bitmap bitmapResource) {
        this.bitmapResource = bitmapResource;
    }

    @Override
    public String toString() {
        return "EspecialidadEntity{" +
                "EspecialidadId='" + EspecialidadId + '\'' +
                ", Codigo='" + Codigo + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", Descripcion='" + Descripcion + '\'' +
                ", Importe='" + Importe + '\'' +
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
        dest.writeStringArray(new String[]{this.EspecialidadId, this.Codigo, this.Nombre, this.Descripcion, this.Importe,
        this.Foto, this.Total, this.UsuarioRegistro, this.FechaRegistro, this.Estado, this.IsSuccess, this.Message, this.Expiration, this.Token});
    }

    public static final Parcelable.Creator<EspecialidadEntity> CREATOR= new Parcelable.Creator<EspecialidadEntity>() {

        @Override
        public EspecialidadEntity createFromParcel(Parcel source) {
            return new EspecialidadEntity(source);
        }

        @Override
        public EspecialidadEntity[] newArray(int size) {
            return new EspecialidadEntity[0];
        }
    };
}