package zisac.com.pe.salutem24.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import zisac.com.pe.salutem24.entity.UsuarioEntity;
import zisac.com.pe.salutem24.utils.Constantes;
import zisac.com.pe.salutem24.utils.StringUtils;

public class UsuarioQuery {
    public static final String C_TABLA = "USUARIOENTITY";
    public static final String C_COLUMNA_IS_SUCCESS = "IsSuccess";
    public static final String C_COLUMNA_MESSAGE = "Message";
    public static final String C_COLUMNA_EXPIRATION = "Expiration";
    public static final String C_COLUMNA_TOKEN= "Token";
    public static final String C_COLUMNA_USUARIO_ID = "UsuarioId";
    public static final String C_COLUMNA_USUARIO = "Usuario";
    public static final String C_COLUMNA_CLAVE = "Clave";
    public static final String C_COLUMNA_TIPO_DOCUMENTO = "TipoDocumento";
    public static final String C_COLUMNA_NUMERO_DOCUMENTO = "NumeroDocumento";
    public static final String C_COLUMNA_NOMBRES = "Nombres";
    public static final String C_COLUMNA_APELLIDO_PATERNO = "ApellidoPaterno";
    public static final String C_COLUMNA_APELLIDO_MATERNO = "ApellidoMaterno";
    public static final String C_COLUMNA_SEXO = "Sexo";
    public static final String C_COLUMNA_CORREO = "Correo";
    public static final String C_COLUMNA_PERFIL = "Perfil";
    public static final String C_COLUMNA_PERFIL_ID = "PerfilId";
    public static final String C_COLUMNA_MEDICO = "Medico";
    public static final String C_COLUMNA_MEDICO_ID = "MedicoId";
    public static final String C_COLUMNA_ANFITRION = "Anfitrion";
    public static final String C_COLUMNA_FOTO = "Foto";
    public static final String C_COLUMNA_USUARIO_REGISTRO = "UsuarioRegistro";
    public static final String C_COLUMNA_FECHA_REGISTRO = "FechaRegistro";
    public static final String C_COLUMNA_ESTADO = "Estado";

    private String[] columnas = new String[]{C_COLUMNA_IS_SUCCESS, C_COLUMNA_MESSAGE, C_COLUMNA_EXPIRATION, C_COLUMNA_TOKEN, C_COLUMNA_USUARIO_ID,
            C_COLUMNA_USUARIO, C_COLUMNA_CLAVE, C_COLUMNA_TIPO_DOCUMENTO, C_COLUMNA_NUMERO_DOCUMENTO, C_COLUMNA_NOMBRES, C_COLUMNA_APELLIDO_PATERNO,
            C_COLUMNA_APELLIDO_MATERNO, C_COLUMNA_SEXO, C_COLUMNA_CORREO, C_COLUMNA_PERFIL, C_COLUMNA_PERFIL_ID, C_COLUMNA_MEDICO, C_COLUMNA_MEDICO_ID,
            C_COLUMNA_ANFITRION, C_COLUMNA_FOTO, C_COLUMNA_USUARIO_REGISTRO, C_COLUMNA_FECHA_REGISTRO, C_COLUMNA_ESTADO};
    private Context contexto;
    private SalutemDB dbSalutem;
    private SQLiteDatabase db;

    public UsuarioQuery(Context contexto) {
        this.contexto = contexto;
    }

    public UsuarioQuery abrir() throws SQLException {
        dbSalutem = new SalutemDB(contexto);
        db = dbSalutem.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        dbSalutem.close();
    }

    public void insertarUpdateUsuario(UsuarioEntity usuarioEntity) {
        if (db == null)
            abrir();

        UsuarioEntity userTemp=null;

        String whereClause =  C_COLUMNA_USUARIO_ID + " =? ";
        String[] whereArgs = new String[]{(usuarioEntity.getUsuarioId())};
        Cursor c = db.query(true, C_TABLA, columnas, whereClause, whereArgs, null, null, null, null);

        try{
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
            {
                userTemp = UsuarioEntity.cursorToEntity(contexto, c);
            }
        }catch (Exception e){
            e.getLocalizedMessage();
        }

        try{
            ContentValues contentValues = new ContentValues();
            if(userTemp==null) {

                contentValues.put(C_COLUMNA_USUARIO_ID, StringUtils.devuelveVacioString(usuarioEntity.getUsuarioId()));
                contentValues.put(C_COLUMNA_USUARIO, StringUtils.devuelveVacioString(usuarioEntity.getUsuario()));
                contentValues.put(C_COLUMNA_CLAVE, StringUtils.devuelveVacioString(usuarioEntity.getClave()));
                contentValues.put(C_COLUMNA_TIPO_DOCUMENTO, StringUtils.devuelveVacioString(usuarioEntity.getTipoDocumento()));
                contentValues.put(C_COLUMNA_NUMERO_DOCUMENTO, StringUtils.devuelveVacioString(usuarioEntity.getNumeroDocumento()));
                contentValues.put(C_COLUMNA_NOMBRES, StringUtils.devuelveVacioString(usuarioEntity.getNombres()));
                contentValues.put(C_COLUMNA_APELLIDO_PATERNO, StringUtils.devuelveVacioString(usuarioEntity.getApellidoPaterno()));
                contentValues.put(C_COLUMNA_APELLIDO_MATERNO, StringUtils.devuelveVacioString(usuarioEntity.getApellidoMaterno()));
                contentValues.put(C_COLUMNA_SEXO, StringUtils.devuelveVacioString(usuarioEntity.getSexo()));
                contentValues.put(C_COLUMNA_CORREO, StringUtils.devuelveVacioString(usuarioEntity.getCorreo()));
                contentValues.put(C_COLUMNA_PERFIL, StringUtils.devuelveVacioString(usuarioEntity.getPerfil()));
                contentValues.put(C_COLUMNA_PERFIL_ID, StringUtils.devuelveVacioString(usuarioEntity.getPerfilId()));
                //contentValues.put( C_COLUMNA_MEDICO, StringUtils.devuelveVacioString(usuarioEntity.getMedico()));
                contentValues.put(C_COLUMNA_MEDICO_ID, StringUtils.devuelveVacioString(usuarioEntity.getMedicoId()));
                //contentValues.put( C_COLUMNA_ANFITRION, StringUtils.devuelveVacioString(usuarioEntity.getAnfitrion()));
                contentValues.put(C_COLUMNA_FOTO, StringUtils.devuelveVacioString(usuarioEntity.getFoto()));
                contentValues.put(C_COLUMNA_ESTADO, Constantes.USUARIO_RECORDADO);

                db.insert(C_TABLA, null, contentValues);
            } else {
                contentValues.put(C_COLUMNA_USUARIO, StringUtils.devuelveVacioString(usuarioEntity.getUsuario()));
                contentValues.put(C_COLUMNA_CLAVE, StringUtils.devuelveVacioString(usuarioEntity.getClave()));
                contentValues.put(C_COLUMNA_TIPO_DOCUMENTO, StringUtils.devuelveVacioString(usuarioEntity.getTipoDocumento()));
                contentValues.put(C_COLUMNA_NUMERO_DOCUMENTO, StringUtils.devuelveVacioString(usuarioEntity.getNumeroDocumento()));
                contentValues.put(C_COLUMNA_NOMBRES, StringUtils.devuelveVacioString(usuarioEntity.getNombres()));
                contentValues.put(C_COLUMNA_APELLIDO_PATERNO, StringUtils.devuelveVacioString(usuarioEntity.getApellidoPaterno()));
                contentValues.put(C_COLUMNA_APELLIDO_MATERNO, StringUtils.devuelveVacioString(usuarioEntity.getApellidoMaterno()));
                contentValues.put(C_COLUMNA_SEXO, StringUtils.devuelveVacioString(usuarioEntity.getSexo()));
                contentValues.put(C_COLUMNA_CORREO, StringUtils.devuelveVacioString(usuarioEntity.getCorreo()));
                contentValues.put(C_COLUMNA_PERFIL, StringUtils.devuelveVacioString(usuarioEntity.getPerfil()));
                contentValues.put(C_COLUMNA_PERFIL_ID, StringUtils.devuelveVacioString(usuarioEntity.getPerfilId()));
                //contentValues.put( C_COLUMNA_MEDICO, StringUtils.devuelveVacioString(usuarioEntity.getMedico()));
                contentValues.put(C_COLUMNA_MEDICO_ID, StringUtils.devuelveVacioString(usuarioEntity.getMedicoId()));
                //contentValues.put( C_COLUMNA_ANFITRION, StringUtils.devuelveVacioString(usuarioEntity.getAnfitrion()));
                contentValues.put(C_COLUMNA_FOTO, StringUtils.devuelveVacioString(usuarioEntity.getFoto()));
                contentValues.put(C_COLUMNA_ESTADO, usuarioEntity.getEstado());

                db.update(C_TABLA, contentValues, whereClause, whereArgs);
            }
        }catch (Exception e){
            Log.e("ErrorRegisNucleo", e.getLocalizedMessage());
        }
        db.close();
    }

    public UsuarioEntity existeUsuarioRecordado() {
        if (db == null)
            abrir();

        UsuarioEntity userTemp=null;

        String whereClause =  C_COLUMNA_ESTADO + " =? ";
        String[] whereArgs = new String[]{Constantes.USUARIO_RECORDADO};
        Cursor c = db.query(true, C_TABLA, columnas, whereClause, whereArgs, null, null, null, null);

        try{
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
            {
                userTemp = UsuarioEntity.cursorToEntity(contexto, c);
            }
        }catch (Exception e){
            userTemp = null;

            e.getLocalizedMessage();
        }

        db.close();
        return userTemp;
    }
}
