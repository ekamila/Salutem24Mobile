package zisac.com.pe.salutem24.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import zisac.com.pe.salutem24.entity.TurnoEntity;

public class TurnoQuery {
    public static final String C_TABLA = "TURNOENTITY";
    public static final String C_COLUMNA_TURNO_ID = "TurnoId";
    public static final String C_COLUMNA_ESPECIALIDAD_ID = "EspecialidadId";
    public static final String C_COLUMNA_ESPECIALIDAD = "Especialidad";
    public static final String C_COLUMNA_MEDICOID = "MedicoId";
    public static final String C_COLUMNA_MEDICO = "Medico";
    public static final String C_COLUMNA_HORARIO = "Horario";
    public static final String C_COLUMNA_FECHATURNO = "FechaTurno";
    public static final String C_COLUMNA_IMPORTE = "Importe";
    public static final String C_COLUMNA_FOTO = "Foto";
    public static final String C_COLUMNA_USUARIO_REGISTRO = "UsuarioRegistro";
    public static final String C_COLUMNA_FECHA_REGISTRO = "FechaRegistro";
    public static final String C_COLUMNA_ESTADO = "Estado";
    public static final String C_COLUMNA_IS_SUCCESS = "IsSuccess";
    public static final String C_COLUMNA_MESSAGE = "Message";
    public static final String C_COLUMNA_EXPIRATION = "Expiration";
    public static final String C_COLUMNA_TOKEN= "Token";
    public static final String C_COLUMNA_INMEDIATA= "Inmediata";

    private String[] columnas = new String[]{ C_COLUMNA_TURNO_ID, C_COLUMNA_ESPECIALIDAD_ID, C_COLUMNA_ESPECIALIDAD, C_COLUMNA_MEDICOID, C_COLUMNA_MEDICO,
            C_COLUMNA_HORARIO, C_COLUMNA_FECHATURNO, C_COLUMNA_IMPORTE, C_COLUMNA_FOTO, C_COLUMNA_USUARIO_REGISTRO, C_COLUMNA_FECHA_REGISTRO, C_COLUMNA_ESTADO,
            C_COLUMNA_IS_SUCCESS, C_COLUMNA_MESSAGE, C_COLUMNA_EXPIRATION, C_COLUMNA_TOKEN, C_COLUMNA_INMEDIATA};
    private Context contexto;
    private SalutemDB dbSalutem;
    private SQLiteDatabase db;

    public TurnoQuery(Context contexto) {
        this.contexto = contexto;
    }

    public TurnoQuery abrir() throws SQLException {
        dbSalutem = new SalutemDB(contexto);
        db = dbSalutem.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        dbSalutem.close();
    }


    public ArrayList<TurnoEntity> insertarTurnos(ArrayList turnos){
        ArrayList<TurnoEntity> retornaNuevoTurno = new ArrayList<>();
        if (db == null)
            abrir();
        //Eliminamos todos los registros
        //db.delete(C_TABLA, null, null);

        int total = turnos.size();
        for(int i=0; i<total; i++) {
            TurnoEntity turno = (TurnoEntity) turnos.get(i);
            ContentValues contentValues = new ContentValues();

            String whereClause = "TurnoId =? ";
            String[] whereArgs = new String[]{turno.getTurnoId()};

            //Cursor c = db.query(true, C_TABLA, columnas, whereClause, whereArgs, null, null, null, null);

            //if(c.getCount() != 1){
                contentValues.put( C_COLUMNA_TURNO_ID,turno.getTurnoId());
                contentValues.put( C_COLUMNA_ESPECIALIDAD_ID, turno.getEspecialidadId());
                contentValues.put( C_COLUMNA_ESPECIALIDAD, turno.getEspecialidad());
                contentValues.put( C_COLUMNA_MEDICOID, turno.getMedicoId());
                contentValues.put( C_COLUMNA_MEDICO, turno.getMedico());
                contentValues.put( C_COLUMNA_HORARIO, turno.getHorario());
                contentValues.put( C_COLUMNA_FECHATURNO, turno.getFechaTurno());
                contentValues.put( C_COLUMNA_IMPORTE, turno.getImporte());
                contentValues.put( C_COLUMNA_FECHATURNO, turno.getFechaTurno());
                contentValues.put( C_COLUMNA_FOTO, turno.getFoto());

                db.insert(C_TABLA, null, contentValues);
                retornaNuevoTurno.add(turno);
            //}
            //c.close();
        }
        return retornaNuevoTurno;
    }
}
