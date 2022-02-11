package zisac.com.pe.salutem24.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import zisac.com.pe.salutem24.entity.EspecialidadEntity;

public class EspecialidadQuery {
    public static final String C_TABLA = "ESPECIALIDADENTITY";
    public static final String C_COLUMNA_ESPECIALIDAD_ID = "EspecialidadId";
    public static final String C_COLUMNA_CODIGO = "Codigo";
    public static final String C_COLUMNA_NOMBRE = "Nombre";
    public static final String C_COLUMNA_DESCRIPCION = "Descripcion";
    public static final String C_COLUMNA_IMPORTE = "Importe";
    public static final String C_COLUMNA_FOTO = "Foto";
    public static final String C_COLUMNA_TOTAL = "Total";
    public static final String C_COLUMNA_USUARIO_REGISTRO = "UsuarioRegistro";
    public static final String C_COLUMNA_FECHA_REGISTRO = "FechaRegistro";
    public static final String C_COLUMNA_ESTADO = "Estado";
    public static final String C_COLUMNA_IS_SUCCESS = "IsSuccess";
    public static final String C_COLUMNA_MESSAGE = "Message";
    public static final String C_COLUMNA_EXPIRATION = "Expiration";
    public static final String C_COLUMNA_TOKEN= "Token";

    private String[] columnas = new String[]{ C_COLUMNA_ESPECIALIDAD_ID, C_COLUMNA_CODIGO, C_COLUMNA_NOMBRE, C_COLUMNA_DESCRIPCION,
            C_COLUMNA_IMPORTE, C_COLUMNA_FOTO, C_COLUMNA_TOTAL, C_COLUMNA_USUARIO_REGISTRO, C_COLUMNA_FECHA_REGISTRO, C_COLUMNA_ESTADO,
            C_COLUMNA_IS_SUCCESS, C_COLUMNA_MESSAGE, C_COLUMNA_EXPIRATION, C_COLUMNA_TOKEN};
    private Context contexto;
    private SalutemDB dbSalutem;
    private SQLiteDatabase db;

    public EspecialidadQuery(Context contexto) {
        this.contexto = contexto;
    }

    public EspecialidadQuery abrir() throws SQLException {
        dbSalutem = new SalutemDB(contexto);
        db = dbSalutem.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        dbSalutem.close();
    }

    public ArrayList<EspecialidadEntity> insertarEpecialidades(ArrayList especialidades){
        ArrayList<EspecialidadEntity> retornaNuevaEspecialidad = new ArrayList<>();
        if (db == null)
            abrir();
        //Eliminamos todos los registros
        //db.delete(C_TABLA, null, null);

        int total = especialidades.size();
        for(int i=0; i<total; i++) {
            EspecialidadEntity especialidad = (EspecialidadEntity) especialidades.get(i);
            ContentValues contentValues = new ContentValues();

            String whereClause = "EspecialidadId =? ";
            String[] whereArgs = new String[]{especialidad.getEspecialidadId()};

            //Cursor c = db.query(true, C_TABLA, columnas, whereClause, whereArgs, null, null, null, null);

            //if(c.getCount() != 1){
                contentValues.put( C_COLUMNA_ESPECIALIDAD_ID,especialidad.getEspecialidadId());
                contentValues.put( C_COLUMNA_CODIGO, especialidad.getCodigo());
                contentValues.put( C_COLUMNA_NOMBRE, especialidad.getNombre());
                contentValues.put( C_COLUMNA_DESCRIPCION, especialidad.getDescripcion());
                contentValues.put( C_COLUMNA_IMPORTE, especialidad.getImporte());
                contentValues.put( C_COLUMNA_FOTO, especialidad.getFoto());
                contentValues.put( C_COLUMNA_TOTAL, especialidad.getTotal());

                db.insert(C_TABLA, null, contentValues);
                retornaNuevaEspecialidad.add(especialidad);
            //}
            //c.close();
        }
        return retornaNuevaEspecialidad;
    }
}
