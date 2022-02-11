package zisac.com.pe.salutem24.entity;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import zisac.com.pe.salutem24.dataBase.EstudiosQuery;

public class EstudiosEntity implements Parcelable {
    private String MedicoId;
    private String Titulo;
    private String Institucion;
    private String UsuarioRegistro;
    private String FechaRegistro;
    private String Estado;

    private Context context;

    public EstudiosEntity() {
    }

    public EstudiosEntity(Context context) {
        this.context = context;
    }

    public EstudiosEntity(Parcel in) {
        String[] data= new String[6];
        in.readStringArray(data);
        this.MedicoId=data[0];
        this.Titulo=data[1];
        this.Institucion=data[2];
        this.UsuarioRegistro=data[3];
        this.FechaRegistro=data[4];
        this.Estado=data[5];
    }

    public static EstudiosEntity cursorToEntity(Context context, Cursor c)
    {
        EstudiosEntity entity = null;

        if (c != null)
        {
            entity = new EstudiosEntity(context);

            entity.setMedicoId(c.getString(c.getColumnIndex(EstudiosQuery.C_COLUMNA_MEDICO_ID)));
            entity.setTitulo(c.getString(c.getColumnIndex(EstudiosQuery.C_COLUMNA_TITULO)));
            entity.setInstitucion(c.getString(c.getColumnIndex(EstudiosQuery.C_COLUMNA_INSTITUCION)));
            entity.setUsuarioRegistro(c.getString(c.getColumnIndex(EstudiosQuery.C_COLUMNA_USUARIO_REGISTRO)));
            entity.setFechaRegistro(c.getString(c.getColumnIndex(EstudiosQuery.C_COLUMNA_FECHA_REGISTRO)));
            entity.setEstado(c.getString(c.getColumnIndex(EstudiosQuery.C_COLUMNA_ESTADO)));

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

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getInstitucion() {
        return Institucion;
    }

    public void setInstitucion(String institucion) {
        Institucion = institucion;
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
        return "EstudiosEntity{" +
                "MedicoId='" + MedicoId + '\'' +
                ", Titulo='" + Titulo + '\'' +
                ", Institucion='" + Institucion + '\'' +
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
        dest.writeStringArray(new String[]{this.MedicoId, this.Titulo, this.Institucion,
        this.UsuarioRegistro, this.FechaRegistro, this.Estado});
    }

    public static final Parcelable.Creator<EstudiosEntity> CREATOR= new Parcelable.Creator<EstudiosEntity>() {

        @Override
        public EstudiosEntity createFromParcel(Parcel source) {
            return new EstudiosEntity(source);
        }

        @Override
        public EstudiosEntity[] newArray(int size) {
            return new EstudiosEntity[0];
        }
    };
}

