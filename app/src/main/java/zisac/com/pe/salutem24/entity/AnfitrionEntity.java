package zisac.com.pe.salutem24.entity;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import zisac.com.pe.salutem24.dataBase.AnfitrionQuery;
import zisac.com.pe.salutem24.dataBase.UsuarioQuery;

public class AnfitrionEntity implements Parcelable {
    private String AnfitrionId;
    private String UsuarioId;
    private String Nombre;
    private String Contrasena;
    private String UsuarioRegistro;
    private String FechaRegistro;
    private String Estado;
    private Context context;

    public AnfitrionEntity() {
    }

    public AnfitrionEntity(Context context) {
        this.context = context;
    }

    public AnfitrionEntity(Parcel in) {
        String[] data= new String[7];
        in.readStringArray(data);
        this.AnfitrionId=data[0];
        this.UsuarioId=data[1];
        this.Nombre=data[2];
        this.Contrasena=data[3];
        this.UsuarioRegistro=data[4];
        this.FechaRegistro=data[5];
        this.Estado=data[6];
    }

    public static AnfitrionEntity cursorToEntity(Context context, Cursor c)
    {
        AnfitrionEntity entity = null;

        if (c != null)
        {
            entity = new AnfitrionEntity(context);

            entity.setAnfitrionId(c.getString(c.getColumnIndex(AnfitrionQuery.C_COLUMNA_ANFITRION_ID)));
            entity.setUsuarioId(c.getString(c.getColumnIndex(AnfitrionQuery.C_COLUMNA_USUARIO_ID)));
            entity.setNombre(c.getString(c.getColumnIndex(AnfitrionQuery.C_COLUMNA_NOMBRE)));
            entity.setContrasena(c.getString(c.getColumnIndex(AnfitrionQuery.C_COLUMNA_CONTRASENA)));
            entity.setUsuarioRegistro(c.getString(c.getColumnIndex(AnfitrionQuery.C_COLUMNA_USUARIO_REGISTRO)));
            entity.setFechaRegistro(c.getString(c.getColumnIndex(AnfitrionQuery.C_COLUMNA_FECHA_REGISTRO)));
            entity.setEstado(c.getString(c.getColumnIndex(AnfitrionQuery.C_COLUMNA_ESTADO)));
            //Log.e("USUARIO ENTITY",entity.toString());
        }

        return entity ;
    }

    public String getAnfitrionId() {
        return AnfitrionId;
    }

    public void setAnfitrionId(String anfitrionId) {
        AnfitrionId = anfitrionId;
    }

    public String getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        UsuarioId = usuarioId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
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

    @Override
    public String toString() {
        return "AnfitrionEntity{" +
                "AnfitrionId='" + AnfitrionId + '\'' +
                ", UsuarioId='" + UsuarioId + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", Contrasena='" + Contrasena + '\'' +
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
        dest.writeStringArray(new String[]{this.AnfitrionId, this.UsuarioId, this.Nombre, this.Contrasena,
                this.UsuarioRegistro, this.FechaRegistro, this.Estado});
    }

    public static final Parcelable.Creator<AnfitrionEntity> CREATOR= new Parcelable.Creator<AnfitrionEntity>() {

        @Override
        public AnfitrionEntity createFromParcel(Parcel source) {
            return new AnfitrionEntity(source);
        }

        @Override
        public AnfitrionEntity[] newArray(int size) {
            return new AnfitrionEntity[0];
        }
    };
}
