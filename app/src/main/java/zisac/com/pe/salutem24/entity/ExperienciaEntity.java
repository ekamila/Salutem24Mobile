package zisac.com.pe.salutem24.entity;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import zisac.com.pe.salutem24.dataBase.ExperienciaQuery;

public class ExperienciaEntity implements Parcelable {
    private String MedicoId;
    private String Descripcion;
    private String Lugar;
    private String UsuarioRegistro;
    private String FechaRegistro;
    private String Estado;

    private Context context;

    public ExperienciaEntity() {
    }

    public ExperienciaEntity(Context context) {
        this.context = context;
    }

    public ExperienciaEntity(Parcel in) {
        String[] data= new String[6];
        in.readStringArray(data);
        this.MedicoId=data[0];
        this.Descripcion=data[1];
        this.Lugar=data[2];
        this.UsuarioRegistro=data[3];
        this.FechaRegistro=data[4];
        this.Estado=data[5];
    }

    public static ExperienciaEntity cursorToEntity(Context context, Cursor c)
    {
        ExperienciaEntity entity = null;

        if (c != null)
        {
            entity = new ExperienciaEntity(context);

            entity.setMedicoId(c.getString(c.getColumnIndex(ExperienciaQuery.C_COLUMNA_MEDICO_ID)));
            entity.setDescripcion(c.getString(c.getColumnIndex(ExperienciaQuery.C_COLUMNA_DESCRIPCION)));
            entity.setLugar(c.getString(c.getColumnIndex(ExperienciaQuery.C_COLUMNA_LUGAR)));
            entity.setUsuarioRegistro(c.getString(c.getColumnIndex(ExperienciaQuery.C_COLUMNA_USUARIO_REGISTRO)));
            entity.setFechaRegistro(c.getString(c.getColumnIndex(ExperienciaQuery.C_COLUMNA_FECHA_REGISTRO)));
            entity.setEstado(c.getString(c.getColumnIndex(ExperienciaQuery.C_COLUMNA_ESTADO)));

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

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getLugar() {
        return Lugar;
    }

    public void setLugar(String lugar) {
        Lugar = lugar;
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
        return "ExperienciaEntity{" +
                "MedicoId='" + MedicoId + '\'' +
                ", Descripcion='" + Descripcion + '\'' +
                ", Lugar='" + Lugar + '\'' +
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
        dest.writeStringArray(new String[]{this.MedicoId, this.Descripcion, this.Lugar,
        this.UsuarioRegistro, this.FechaRegistro, this.Estado});
    }

    public static final Parcelable.Creator<ExperienciaEntity> CREATOR= new Parcelable.Creator<ExperienciaEntity>() {

        @Override
        public ExperienciaEntity createFromParcel(Parcel source) {
            return new ExperienciaEntity(source);
        }

        @Override
        public ExperienciaEntity[] newArray(int size) {
            return new ExperienciaEntity[0];
        }
    };
}


