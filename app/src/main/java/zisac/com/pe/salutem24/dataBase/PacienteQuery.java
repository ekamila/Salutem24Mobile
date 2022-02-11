package zisac.com.pe.salutem24.dataBase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PacienteQuery {
    public static final String C_TABLA = "PACIENTEENTITY";
    public static final String C_COLUMNA_PACIENTE_ID = "PacienteId";
    public static final String C_COLUMNA_USUARIOID = "UsuarioId";
    public static final String C_COLUMNA_USUARIO = "Usuario";
    public static final String C_COLUMNA_NOMBRES = "Nombres";
    public static final String C_COLUMNA_APELLIDO_PATERNO = "ApellidoPaterno";
    public static final String C_COLUMNA_APELLIDO_MATERNO = "ApellidoMaterno";
    public static final String C_COLUMNA_DOCUMENTO_ID = "DocumentoId";
    public static final String C_COLUMNA_DOCUMENTO = "Documento";
    public static final String C_COLUMNA_NUMERO_DOCUMENTO = "NumeroDocumento";
    public static final String C_COLUMNA_CELULAR = "Celular";
    public static final String C_COLUMNA_SEXO = "Sexo";
    public static final String C_COLUMNA_FECHA_NACIMIENTO = "FechaNacimiento";
    public static final String C_COLUMNA_TOTAL = "Total";
    public static final String C_COLUMNA_USUARIO_REGISTRO = "UsuarioRegistro";
    public static final String C_COLUMNA_FECHA_REGISTRO = "FechaRegistro";
    public static final String C_COLUMNA_ESTADO = "Estado";
    public static final String C_COLUMNA_IS_SUCCESS = "IsSuccess";
    public static final String C_COLUMNA_MESSAGE = "Message";
    public static final String C_COLUMNA_EXPIRATION = "Expiration";
    public static final String C_COLUMNA_TOKEN= "Token";

    private String[] columnas = new String[]{ C_COLUMNA_PACIENTE_ID, C_COLUMNA_USUARIOID, C_COLUMNA_USUARIO, C_COLUMNA_NOMBRES, C_COLUMNA_APELLIDO_PATERNO,
            C_COLUMNA_APELLIDO_MATERNO, C_COLUMNA_DOCUMENTO_ID, C_COLUMNA_DOCUMENTO, C_COLUMNA_NUMERO_DOCUMENTO, C_COLUMNA_CELULAR, C_COLUMNA_SEXO,
            C_COLUMNA_FECHA_NACIMIENTO, C_COLUMNA_TOTAL, C_COLUMNA_USUARIO_REGISTRO, C_COLUMNA_FECHA_REGISTRO, C_COLUMNA_ESTADO,
            C_COLUMNA_IS_SUCCESS, C_COLUMNA_MESSAGE, C_COLUMNA_EXPIRATION, C_COLUMNA_TOKEN};
    private Context contexto;
    private SalutemDB dbSalutem;
    private SQLiteDatabase db;

    public PacienteQuery(Context contexto) {
        this.contexto = contexto;
    }

    public PacienteQuery abrir() throws SQLException {
        dbSalutem = new SalutemDB(contexto);
        db = dbSalutem.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        dbSalutem.close();
    }
}
