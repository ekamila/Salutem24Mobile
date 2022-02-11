package zisac.com.pe.salutem24.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import zisac.com.pe.salutem24.entity.TurnoDetalleEntity;
import zisac.com.pe.salutem24.entity.TurnoEntity;

public class TurnoDetalleQuery {
    public static final String C_TABLA = "TURNODETALLEENTITY";
    public static final String C_COLUMNA_TURNO_DETALLE_ID = "TurnoDetalleId";
    public static final String C_COLUMNA_TURNO_ID = "TurnoId";
    public static final String C_COLUMNA_HORARIO = "Horario";
    public static final String C_COLUMNA_ESTADO_TURNO = "EstadoTurno";
    public static final String C_COLUMNA_ESTADO_TURNO_ID = "EstadoTurnoId";
    public static final String C_COLUMNA_USUARIO_REGISTRO = "UsuarioRegistro";
    public static final String C_COLUMNA_FECHA_REGISTRO = "FechaRegistro";
    public static final String C_COLUMNA_ESTADO = "Estado";

    private String[] columnas = new String[]{ C_COLUMNA_TURNO_DETALLE_ID, C_COLUMNA_TURNO_ID, C_COLUMNA_HORARIO, C_COLUMNA_ESTADO_TURNO,
            C_COLUMNA_ESTADO_TURNO_ID, C_COLUMNA_USUARIO_REGISTRO, C_COLUMNA_FECHA_REGISTRO, C_COLUMNA_ESTADO};
    private Context contexto;
    private SalutemDB dbSalutem;
    private SQLiteDatabase db;

    public TurnoDetalleQuery(Context contexto) {
        this.contexto = contexto;
    }

    public TurnoDetalleQuery abrir() throws SQLException {
        dbSalutem = new SalutemDB(contexto);
        db = dbSalutem.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        dbSalutem.close();
    }


    public ArrayList<TurnoDetalleEntity> insertarTurnosDetalle(ArrayList turnos){
        ArrayList<TurnoDetalleEntity> retornaNuevoTurnoDetalle = new ArrayList<>();
        if (db == null)
            abrir();
        //Eliminamos todos los registros
        //db.delete(C_TABLA, null, null);

        int totalTurnos = turnos.size();
        for(int i=0; i<totalTurnos; i++) {
            TurnoEntity turno = (TurnoEntity) turnos.get(i);

            ArrayList<TurnoDetalleEntity> turnosDetalle = turno.getTurnosDetalle();

            int totalTurnoDetalles = turnosDetalle.size();
            for(int j = 0; j<totalTurnoDetalles; j++){

                TurnoDetalleEntity turnoDetalles = turnosDetalle.get(i);

                ContentValues contentValues = new ContentValues();
                String whereClause = "TurnoId =? ";
                String[] whereArgs = new String[]{turnoDetalles.getTurnoId()};

                //Cursor c = db.query(true, C_TABLA, columnas, whereClause, whereArgs, null, null, null, null);
                //if(c.getCount() != 1){
                    contentValues.put( C_COLUMNA_TURNO_DETALLE_ID,turnoDetalles.getTurnoDetalleId());
                    contentValues.put( C_COLUMNA_TURNO_ID, turnoDetalles.getTurnoDetalleId());
                    contentValues.put( C_COLUMNA_HORARIO, turnoDetalles.getHorario());
                    contentValues.put( C_COLUMNA_ESTADO_TURNO, turnoDetalles.getEstadoTurno());
                    contentValues.put( C_COLUMNA_ESTADO_TURNO_ID, turnoDetalles.getEstadoTurnoId());
                    db.insert(C_TABLA, null, contentValues);
                    //retornaNuevoTurnoDetalle.add(turno);
                //}
                //c.close();
            }
        }
        return retornaNuevoTurnoDetalle;
    }
}
