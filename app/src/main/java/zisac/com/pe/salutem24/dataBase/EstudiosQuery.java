package zisac.com.pe.salutem24.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import zisac.com.pe.salutem24.entity.EstudiosEntity;
import zisac.com.pe.salutem24.utils.StringUtils;

public class EstudiosQuery {
    public static final String C_TABLA = "ESTUDIOSENTITY";
    public static final String C_COLUMNA_MEDICO_ID = "MedicoId";
    public static final String C_COLUMNA_TITULO = "Titulo";
    public static final String C_COLUMNA_INSTITUCION = "Institucion";
    public static final String C_COLUMNA_USUARIO_REGISTRO = "UsuarioRegistro";
    public static final String C_COLUMNA_FECHA_REGISTRO = "FechaRegistro";
    public static final String C_COLUMNA_ESTADO = "Estado";

    private String[] columnas = new String[]{ C_COLUMNA_MEDICO_ID, C_COLUMNA_TITULO, C_COLUMNA_INSTITUCION, C_COLUMNA_USUARIO_REGISTRO, C_COLUMNA_FECHA_REGISTRO, C_COLUMNA_ESTADO};
    private Context contexto;
    private SalutemDB dbSalutem;
    private SQLiteDatabase db;

    public EstudiosQuery(Context contexto) {
        this.contexto = contexto;
    }

    public EstudiosQuery abrir() throws SQLException {
        dbSalutem = new SalutemDB(contexto);
        db = dbSalutem.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        dbSalutem.close();
    }

    public ArrayList<EstudiosEntity> insertarEstudios(ArrayList estudios){
        ArrayList<EstudiosEntity> retornarNuevosEstudios = new ArrayList<>();
        if (db == null)
            abrir();
        //Eliminamos todos los registros
        //db.delete(C_TABLA, null, null);

        int total = estudios.size();
        for(int i=0; i<total; i++) {
            EstudiosEntity estudio = (EstudiosEntity) estudios.get(i);
            ContentValues contentValues = new ContentValues();

            String whereClause = "MedicoId =? ";
            String[] whereArgs = new String[]{estudio.getMedicoId()};

            //Cursor c = db.query(true, C_TABLA, columnas, whereClause, whereArgs, null, null, null, null);

            //if(c.getCount() != 1){
                contentValues.put( C_COLUMNA_MEDICO_ID, StringUtils.devuelveVacioString(estudio.getMedicoId()));
                contentValues.put( C_COLUMNA_TITULO, StringUtils.devuelveVacioString(estudio.getTitulo()));
                contentValues.put( C_COLUMNA_INSTITUCION, StringUtils.devuelveVacioString(estudio.getInstitucion()));

                db.insert(C_TABLA, null, contentValues);
                retornarNuevosEstudios.add(estudio);
            //}
            //c.close();
        }
        return retornarNuevosEstudios;
    }
}