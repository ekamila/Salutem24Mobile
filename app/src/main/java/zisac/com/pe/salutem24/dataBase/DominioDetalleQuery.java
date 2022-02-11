package zisac.com.pe.salutem24.dataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import zisac.com.pe.salutem24.entity.DominioDetalleEntity;

public class DominioDetalleQuery {
    public static final String C_TABLA = "DOMINIODETALLEENTITY";
    public static final String C_COLUMNA_DOMINIOID = "DominioId";
    public static final String C_COLUMNA_VALORDETALLE = "ValorDetalle";
    public static final String C_COLUMNA_DESCRIPCIONDETALLE = "DescripcionDetalle";
    public static final String C_COLUMNA_FECHAREGISTRO = "FechaRegistro";
    public static final String C_COLUMNA_ESTADO = "Estado";
    public static final String C_COLUMNA_USUARIOREGISTRO = "UsuarioRegistro";

    private String[] columnas = new String[]{C_COLUMNA_DOMINIOID, C_COLUMNA_VALORDETALLE,
            C_COLUMNA_DESCRIPCIONDETALLE,C_COLUMNA_FECHAREGISTRO, C_COLUMNA_ESTADO, C_COLUMNA_USUARIOREGISTRO};
    private Context contexto;
    private SalutemDB dbSalutem24;
    private SQLiteDatabase db;

    public DominioDetalleQuery() {
    }

    public DominioDetalleQuery(Context contexto) {
        this.contexto = contexto;
    }

    public DominioDetalleQuery abrir() throws SQLException {
        dbSalutem24 = new SalutemDB(contexto);
        db = dbSalutem24.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        dbSalutem24.close();
    }

    public ArrayList<DominioDetalleEntity> listDDTipo(String dominioId){
        ArrayList<DominioDetalleEntity> listDominio = new ArrayList<DominioDetalleEntity>();

        if (db == null)
            abrir();

        String whereClause = " DominioId =? ";
        String[] whereArgs = new String[]{(dominioId)};

        Cursor c;
        try {
            c = db.query(true, C_TABLA, columnas, whereClause, whereArgs, null, null, null, null);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                listDominio.add(DominioDetalleEntity.cursorToEntity(contexto, c));
            }
        } catch (Exception e){
            c = null;
            Log.e("excsp","" + e.getMessage());
        }

        c.close();

        return listDominio;
    }
}
