package zisac.com.pe.salutem24.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import zisac.com.pe.salutem24.entity.CurriculumEntity;
import zisac.com.pe.salutem24.utils.StringUtils;

public class CurriculumQuery {
    public static final String C_TABLA = "CURRICULUMENTITY";
    public static final String C_COLUMNA_MEDICO_ID = "MedicoId";
    public static final String C_COLUMNA_FOTO = "Foto";
    public static final String C_COLUMNA_NOMBRES = "Nombres";
    public static final String C_COLUMNA_APELLIDO_PATERNO = "ApellidoPaterno";;
    public static final String C_COLUMNA_APELLIDO_MATERNO = "ApellidoMaterno";
    public static final String C_COLUMNA_CMP = "Cmp";
    public static final String C_COLUMNA_RNE = "Rne";
    public static final String C_COLUMNA_NOMBRE_ESPECIALIDAD = "NombreEspecialidad";
    public static final String C_COLUMNA_ESTADO_TURNO = "EstadoTurno";
    public static final String C_COLUMNA_USUARIO_REGISTRO = "UsuarioRegistro";
    public static final String C_COLUMNA_FECHA_REGISTRO = "FechaRegistro";
    public static final String C_COLUMNA_ESTADO = "Estado";
    public static final String C_COLUMNA_IS_SUCCESS = "IsSuccess";
    public static final String C_COLUMNA_MESSAGE = "Message";
    public static final String C_COLUMNA_EXPIRATION = "Expiration";
    public static final String C_COLUMNA_TOKEN= "Token";

    private String[] columnas = new String[]{ C_COLUMNA_MEDICO_ID, C_COLUMNA_FOTO, C_COLUMNA_NOMBRES, C_COLUMNA_APELLIDO_PATERNO, C_COLUMNA_APELLIDO_MATERNO,
            C_COLUMNA_CMP, C_COLUMNA_RNE, C_COLUMNA_NOMBRE_ESPECIALIDAD, C_COLUMNA_ESTADO_TURNO, C_COLUMNA_USUARIO_REGISTRO, C_COLUMNA_FECHA_REGISTRO, C_COLUMNA_ESTADO,
            C_COLUMNA_IS_SUCCESS, C_COLUMNA_MESSAGE, C_COLUMNA_EXPIRATION, C_COLUMNA_TOKEN};
    private Context contexto;
    private SalutemDB dbSalutem;
    private SQLiteDatabase db;

    public CurriculumQuery(Context contexto) {
        this.contexto = contexto;
    }

    public CurriculumQuery abrir() throws SQLException {
        dbSalutem = new SalutemDB(contexto);
        db = dbSalutem.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        dbSalutem.close();
    }

    public int insertarCurriculum(CurriculumEntity curriculum){
        if (db == null)
            abrir();

        Integer total=0;
        try{
            ContentValues contentValues = new ContentValues();

            contentValues.put( C_COLUMNA_MEDICO_ID, StringUtils.devuelveVacioString(curriculum.getMedicoId()));
            contentValues.put( C_COLUMNA_FOTO, StringUtils.devuelveVacioString(curriculum.getFoto()));
            contentValues.put( C_COLUMNA_NOMBRES, StringUtils.devuelveVacioString(curriculum.getNombres()));
            contentValues.put( C_COLUMNA_APELLIDO_PATERNO, StringUtils.devuelveVacioString(curriculum.getApellidoPaterno()));
            contentValues.put( C_COLUMNA_APELLIDO_MATERNO, StringUtils.devuelveVacioString(curriculum.getApellidoMaterno()));
            contentValues.put( C_COLUMNA_NOMBRES, StringUtils.devuelveVacioString(curriculum.getNombres()));
            contentValues.put( C_COLUMNA_APELLIDO_PATERNO, StringUtils.devuelveVacioString(curriculum.getApellidoPaterno()));
            contentValues.put( C_COLUMNA_APELLIDO_MATERNO, StringUtils.devuelveVacioString(curriculum.getApellidoMaterno()));

            db.insert(C_TABLA, null, contentValues);
        }catch (Exception e){
            Log.e("ErrorRegisNucleo", e.getLocalizedMessage());
        }
        db.close();

        return total;
    }
}
