package zisac.com.pe.salutem24.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import zisac.com.pe.salutem24.entity.ExperienciaEntity;
import zisac.com.pe.salutem24.utils.StringUtils;

public class ExperienciaQuery {
    public static final String C_TABLA = "EXPERIENCIAENTITY";
    public static final String C_COLUMNA_MEDICO_ID = "MedicoId";
    public static final String C_COLUMNA_DESCRIPCION = "Descripcion";
    public static final String C_COLUMNA_LUGAR = "Lugar";
    public static final String C_COLUMNA_USUARIO_REGISTRO = "UsuarioRegistro";
    public static final String C_COLUMNA_FECHA_REGISTRO = "FechaRegistro";
    public static final String C_COLUMNA_ESTADO = "Estado";

    private String[] columnas = new String[]{ C_COLUMNA_MEDICO_ID, C_COLUMNA_DESCRIPCION, C_COLUMNA_LUGAR, C_COLUMNA_USUARIO_REGISTRO, C_COLUMNA_FECHA_REGISTRO, C_COLUMNA_ESTADO};
    private Context contexto;
    private SalutemDB dbSalutem;
    private SQLiteDatabase db;

    public ExperienciaQuery(Context contexto) {
        this.contexto = contexto;
    }

    public ExperienciaQuery abrir() throws SQLException {
        dbSalutem = new SalutemDB(contexto);
        db = dbSalutem.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        dbSalutem.close();
    }


    public ArrayList<ExperienciaEntity> insertarExperiencia(ArrayList experiencias){
        ArrayList<ExperienciaEntity> retornarNuevasExperiencias = new ArrayList<>();
        if (db == null)
            abrir();
        //Eliminamos todos los registros
        //db.delete(C_TABLA, null, null);

        int total = experiencias.size();
        for(int i=0; i<total; i++) {
            ExperienciaEntity experiencia = (ExperienciaEntity) experiencias.get(i);
            ContentValues contentValues = new ContentValues();

            String whereClause = "MedicoId =? ";
            String[] whereArgs = new String[]{experiencia.getMedicoId()};

            //Cursor c = db.query(true, C_TABLA, columnas, whereClause, whereArgs, null, null, null, null);

            //if(c.getCount() != 1){
                contentValues.put( C_COLUMNA_MEDICO_ID, StringUtils.devuelveVacioString(experiencia.getMedicoId()));
                contentValues.put( C_COLUMNA_DESCRIPCION, StringUtils.devuelveVacioString(experiencia.getDescripcion()));
                contentValues.put( C_COLUMNA_LUGAR, StringUtils.devuelveVacioString(experiencia.getLugar()));

                db.insert(C_TABLA, null, contentValues);
                retornarNuevasExperiencias.add(experiencia);
            //}
            //c.close();
        }
        return retornarNuevasExperiencias;
    }
}
