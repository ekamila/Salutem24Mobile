package zisac.com.pe.salutem24.entity;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import zisac.com.pe.salutem24.dataBase.TurnoDetalleQuery;

public class TurnoDetalleEntity implements Parcelable {
    private String TurnoDetalleId;
    private String TurnoId;
    private String Horario;
    private String EstadoTurno;
    private String EstadoTurnoId;
    private String UsuarioRegistro;
    private String FechaRegistro;
    private String Estado;

    private Context context;

    public TurnoDetalleEntity() {
    }

    public TurnoDetalleEntity(Context context) {
        this.context = context;
    }

    public TurnoDetalleEntity(Parcel in) {
        String[] data= new String[7];
        in.readStringArray(data);
        this.TurnoDetalleId=data[0];
        this.TurnoId=data[1];
        this.Horario=data[2];
        this.EstadoTurno=data[3];
        this.EstadoTurnoId=data[3];
        this.UsuarioRegistro=data[4];
        this.FechaRegistro=data[5];
        this.Estado=data[6];
    }


    public static TurnoDetalleEntity cursorToEntity(Context context, Cursor c)
    {
        TurnoDetalleEntity entity = null;

        if (c != null)
        {
            entity = new TurnoDetalleEntity(context);

            entity.setTurnoDetalleId(c.getString(c.getColumnIndex(TurnoDetalleQuery.C_COLUMNA_TURNO_DETALLE_ID)));
            entity.setTurnoId(c.getString(c.getColumnIndex(TurnoDetalleQuery.C_COLUMNA_TURNO_ID)));
            entity.setHorario(c.getString(c.getColumnIndex(TurnoDetalleQuery.C_COLUMNA_HORARIO)));
            entity.setEstadoTurno(c.getString(c.getColumnIndex(TurnoDetalleQuery.C_COLUMNA_ESTADO_TURNO)));
            entity.setEstadoTurnoId(c.getString(c.getColumnIndex(TurnoDetalleQuery.C_COLUMNA_ESTADO_TURNO_ID)));
            entity.setUsuarioRegistro(c.getString(c.getColumnIndex(TurnoDetalleQuery.C_COLUMNA_USUARIO_REGISTRO)));
            entity.setFechaRegistro(c.getString(c.getColumnIndex(TurnoDetalleQuery.C_COLUMNA_FECHA_REGISTRO)));
            entity.setEstado(c.getString(c.getColumnIndex(TurnoDetalleQuery.C_COLUMNA_ESTADO)));

            //Log.e("USUARIO ENTITY",entity.toString());
        }

        return entity ;
    }

    public String getTurnoDetalleId() {
        return TurnoDetalleId;
    }

    public void setTurnoDetalleId(String turnoDetalleId) {
        TurnoDetalleId = turnoDetalleId;
    }

    public String getTurnoId() {
        return TurnoId;
    }

    public void setTurnoId(String turnoId) {
        TurnoId = turnoId;
    }

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String horario) {
        Horario = horario;
    }

    public String getEstadoTurno() {
        return EstadoTurno;
    }

    public void setEstadoTurno(String estadoTurno) {
        EstadoTurno = estadoTurno;
    }

    public String getEstadoTurnoId() {
        return EstadoTurnoId;
    }

    public void setEstadoTurnoId(String estadoTurnoId) {
        EstadoTurnoId = estadoTurnoId;
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
        return "TurnoDetalleEntity{" +
                "TurnoDetalleId='" + TurnoDetalleId + '\'' +
                ", TurnoId='" + TurnoId + '\'' +
                ", Horario='" + Horario + '\'' +
                ", EstadoTurno='" + EstadoTurno + '\'' +
                ", EstadoTurnoId='" + EstadoTurnoId + '\'' +
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
        dest.writeStringArray(new String[]{this.TurnoDetalleId, this.TurnoId, this.Horario, this.EstadoTurno,
                this.EstadoTurnoId,this.UsuarioRegistro, this.FechaRegistro, this.Estado});
    }

    public static final Parcelable.Creator<TurnoDetalleEntity> CREATOR= new Parcelable.Creator<TurnoDetalleEntity>() {

        @Override
        public TurnoDetalleEntity createFromParcel(Parcel source) {
            return new TurnoDetalleEntity(source);
        }

        @Override
        public TurnoDetalleEntity[] newArray(int size) {
            return new TurnoDetalleEntity[0];
        }
    };
}

