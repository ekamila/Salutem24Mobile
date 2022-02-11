package zisac.com.pe.salutem24.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import zisac.com.pe.salutem24.entity.AnfitrionEntity;
import zisac.com.pe.salutem24.entity.MedicoEntity;
import zisac.com.pe.salutem24.utils.StringUtils;

public class AnfitrionQuery {
    public static final String C_TABLA = "ANFITRIONENTITY";
    public static final String C_COLUMNA_ANFITRION_ID = "AnfitrionId";
    public static final String C_COLUMNA_USUARIO_ID = "UsuarioId";
    public static final String C_COLUMNA_NOMBRE = "Nombre";
    public static final String C_COLUMNA_CONTRASENA = "Contrasena";
    public static final String C_COLUMNA_USUARIO_REGISTRO = "UsuarioRegistro";
    public static final String C_COLUMNA_FECHA_REGISTRO = "FechaRegistro";
    public static final String C_COLUMNA_ESTADO = "Estado";

    private String[] columnas = new String[]{ C_COLUMNA_ANFITRION_ID, C_COLUMNA_USUARIO_ID, C_COLUMNA_NOMBRE,
            C_COLUMNA_CONTRASENA, C_COLUMNA_USUARIO_REGISTRO, C_COLUMNA_FECHA_REGISTRO, C_COLUMNA_ESTADO};
    private Context contexto;
    private SalutemDB dbSalutem;
    private SQLiteDatabase db;

    public AnfitrionQuery(Context contexto) {
        this.contexto = contexto;
    }

    public AnfitrionQuery abrir() throws SQLException {
        dbSalutem = new SalutemDB(contexto);
        db = dbSalutem.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        dbSalutem.close();
    }

    public int insertarAnfitrion(AnfitrionEntity anfitrionEntity){
        if (db == null)
            abrir();

        Integer total=0;
        try{
            ContentValues contentValues = new ContentValues();

            contentValues.put( C_COLUMNA_ANFITRION_ID, StringUtils.devuelveVacioString(anfitrionEntity.getAnfitrionId()));
            contentValues.put( C_COLUMNA_USUARIO_ID, StringUtils.devuelveVacioString(anfitrionEntity.getUsuarioId()));
            contentValues.put( C_COLUMNA_NOMBRE, StringUtils.devuelveVacioString(anfitrionEntity.getNombre()));
            contentValues.put( C_COLUMNA_CONTRASENA, StringUtils.devuelveVacioString(anfitrionEntity.getContrasena()));

            db.insert(C_TABLA, null, contentValues);
        }catch (Exception e){
            Log.e("ErrorRegisNucleo", e.getLocalizedMessage());
        }
        db.close();

        return total;
    }
}
