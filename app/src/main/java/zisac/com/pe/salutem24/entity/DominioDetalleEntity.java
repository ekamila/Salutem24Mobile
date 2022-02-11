package zisac.com.pe.salutem24.entity;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import zisac.com.pe.salutem24.dataBase.DominioDetalleQuery;

public class DominioDetalleEntity implements Parcelable {
    private String DominioId;
    private String ValorDetalle;
    private String DescripcionDetalle;
    private String FechaRegistro;
    private String Estado;
    private String UsuarioRegistro;
    private boolean selected;
    private Context context;

    public DominioDetalleEntity() {
    }

    public DominioDetalleEntity(String valorDetalle, String descripcionDetalle) {
        ValorDetalle = valorDetalle;
        DescripcionDetalle = descripcionDetalle;
    }

    public DominioDetalleEntity(Context context) {
        this.context = context;
    }

    public DominioDetalleEntity(Parcel in) {
        String[] data= new String[6];
        in.readStringArray(data);

        this.DominioId=data[0];
        this.ValorDetalle=data[1];
        this.DescripcionDetalle=data[2];
        this.FechaRegistro=data[3];
        this.Estado=data[4];
        this.UsuarioRegistro=data[5];
    }

    public static DominioDetalleEntity cursorToEntity(Context context, Cursor c)
    {
        DominioDetalleEntity entity = null;

        if (c != null)
        {
            entity = new DominioDetalleEntity(context);

            entity.setDominioId(c.getString(c.getColumnIndex(DominioDetalleQuery.C_COLUMNA_DOMINIOID)));
            entity.setValorDetalle(c.getString(c.getColumnIndex(DominioDetalleQuery.C_COLUMNA_VALORDETALLE)));
            entity.setDescripcionDetalle(c.getString(c.getColumnIndex(DominioDetalleQuery.C_COLUMNA_DESCRIPCIONDETALLE)));
            entity.setFechaRegistro(c.getString(c.getColumnIndex(DominioDetalleQuery.C_COLUMNA_FECHAREGISTRO)));
            entity.setEstado(c.getString(c.getColumnIndex(DominioDetalleQuery.C_COLUMNA_ESTADO)));
            entity.setUsuarioRegistro(c.getString(c.getColumnIndex(DominioDetalleQuery.C_COLUMNA_USUARIOREGISTRO)));
            //Log.e("LISTA",entity.toString());
        }

        return entity ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.DominioId,this.ValorDetalle,this.DescripcionDetalle,this.FechaRegistro, this.Estado, this.UsuarioRegistro});
    }

    public static final Parcelable.Creator<DominioDetalleEntity> CREATOR= new Creator<DominioDetalleEntity>() {

        @Override
        public DominioDetalleEntity createFromParcel(Parcel source) {
            return new DominioDetalleEntity(source);
        }

        @Override
        public DominioDetalleEntity[] newArray(int size) {
            return new DominioDetalleEntity[0];
        }
    };

    public String getDominioId() {
        return DominioId;
    }

    public void setDominioId(String dominioId) {
        DominioId = dominioId;
    }

    public String getValorDetalle() {
        return ValorDetalle;
    }

    public void setValorDetalle(String valorDetalle) {
        ValorDetalle = valorDetalle;
    }

    public String getDescripcionDetalle() {
        return DescripcionDetalle;
    }

    public void setDescripcionDetalle(String descripcionDetalle) {
        DescripcionDetalle = descripcionDetalle;
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

    public String getUsuarioRegistro() {
        return UsuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        UsuarioRegistro = usuarioRegistro;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "DominioDetalleEntity{" +
                "DominioId='" + DominioId + '\'' +
                ", ValorDetalle='" + ValorDetalle + '\'' +
                ", DescripcionDetalle='" + DescripcionDetalle + '\'' +
                ", FechaRegistro='" + FechaRegistro + '\'' +
                ", Estado='" + Estado + '\'' +
                ", UsuarioRegistro='" + UsuarioRegistro + '\'' +
                '}';
    }
}
