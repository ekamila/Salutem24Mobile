package zisac.com.pe.salutem24.dataBase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ConsultaQuery {
    public static final String C_TABLA = "CONSULTAENTITY";
    public static final String C_COLUMNA_CONSULTA_ID = "ConsultaId";
    public static final String C_COLUMNA_PACIENTE_ID = "PacienteId";
    public static final String C_COLUMNA_PACIENTE = "Paciente";
    public static final String C_COLUMNA_HISTORIACLINICA_ID = "HistoriaClinicaId";
    public static final String C_COLUMNA_ESPECIALIDAD_ID = "EspecialidadId";
    public static final String C_COLUMNA_ESPECIALIDAD = "Especialidad";
    public static final String C_COLUMNA_MEDICO = "Medico";
    public static final String C_COLUMNA_CODIGO_SALA = "CodigoSala";
    public static final String C_COLUMNA_ESTADO_CONSULTA = "EstadoConsulta";
    public static final String C_COLUMNA_FECHA_CONSULTA = "FechaConsulta";
    public static final String C_COLUMNA_HORARIO_CONSULTA = "HorarioConsulta";
    public static final String C_COLUMNA_ESTADO_CONSULTA_ID = "EstadoConsultaId";
    public static final String C_COLUMNA_ESTADO_CONSULTA_DESC = "EstadoConsultaDesc";
    public static final String C_COLUMNA_TOTAL = "Total";
    public static final String C_COLUMNA_USUARIO_REGISTRO = "UsuarioRegistro";
    public static final String C_COLUMNA_FECHA_REGISTRO = "FechaRegistro";
    public static final String C_COLUMNA_ESTADO = "Estado";
    public static final String C_COLUMNA_IS_SUCCESS = "IsSuccess";
    public static final String C_COLUMNA_MESSAGE = "Message";
    public static final String C_COLUMNA_EXPIRATION = "Expiration";
    public static final String C_COLUMNA_TOKEN= "Token";
    public static final String C_COLUMNA_NRO_CITA= "NroCita";
    public static final String C_COLUMNA_CONSULTA_INMEDIATA= "ConsultaInmediata";


    private String[] columnas = new String[]{ C_COLUMNA_CONSULTA_ID, C_COLUMNA_PACIENTE_ID, C_COLUMNA_PACIENTE, C_COLUMNA_HISTORIACLINICA_ID, C_COLUMNA_ESPECIALIDAD_ID,
            C_COLUMNA_ESPECIALIDAD, C_COLUMNA_MEDICO, C_COLUMNA_CODIGO_SALA, C_COLUMNA_ESTADO_CONSULTA, C_COLUMNA_FECHA_CONSULTA, C_COLUMNA_HORARIO_CONSULTA,
            C_COLUMNA_ESTADO_CONSULTA_ID, C_COLUMNA_ESTADO_CONSULTA_DESC, C_COLUMNA_TOTAL, C_COLUMNA_USUARIO_REGISTRO, C_COLUMNA_FECHA_REGISTRO, C_COLUMNA_ESTADO,
            C_COLUMNA_IS_SUCCESS, C_COLUMNA_MESSAGE, C_COLUMNA_EXPIRATION, C_COLUMNA_TOKEN, C_COLUMNA_NRO_CITA, C_COLUMNA_CONSULTA_INMEDIATA};
    private Context contexto;
    private SalutemDB dbSalutem;
    private SQLiteDatabase db;

    public ConsultaQuery(Context contexto) {
        this.contexto = contexto;
    }

    public ConsultaQuery abrir() throws SQLException {
        dbSalutem = new SalutemDB(contexto);
        db = dbSalutem.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        dbSalutem.close();
    }
}
