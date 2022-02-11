package zisac.com.pe.salutem24.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import zisac.com.pe.salutem24.entity.MedicoEntity;
import zisac.com.pe.salutem24.entity.UsuarioEntity;
import zisac.com.pe.salutem24.utils.StringUtils;

public class MedicoQuery {
    public static final String C_TABLA = "MEDICOENTITY";
    public static final String C_COLUMNA_MEDICO_ID = "MedicoId";
    public static final String C_COLUMNA_ESPECIALIDAD_ID = "EspecialidadId";
    public static final String C_COLUMNA_ESPECIALIDAD = "Especialidad";
    public static final String C_COLUMNA_USUARIO_ID = "UsuarioId";
    public static final String C_COLUMNA_USUARIO = "Usuario";
    public static final String C_COLUMNA_NOMBRES = "Nombres";
    public static final String C_COLUMNA_APELLIDO_PATERNO = "ApellidoPaterno";
    public static final String C_COLUMNA_APELLIDO_MATERNO = "ApellidoMaterno";
    public static final String C_COLUMNA_DOCUMENTO = "Documento";
    public static final String C_COLUMNA_DOCUMENTO_ID = "DocumentoId";
    public static final String C_COLUMNA_NUMERO_DOCUMENTO = "NumeroDocumento";
    public static final String C_COLUMNA_CMP = "Cmp";
    public static final String C_COLUMNA_CELULAR = "Celular";
    public static final String C_COLUMNA_CORREO = "Correo";
    public static final String C_COLUMNA_SEXO = "Sexo";
    public static final String C_COLUMNA_FOTO = "Foto";
    public static final String C_COLUMNA_TOTAL = "Total";
    public static final String C_COLUMNA_USUARIO_REGISTRO = "UsuarioRegistro";
    public static final String C_COLUMNA_FECHA_REGISTRO = "FechaRegistro";
    public static final String C_COLUMNA_ESTADO = "Estado";
    public static final String C_COLUMNA_IS_SUCCESS = "IsSuccess";
    public static final String C_COLUMNA_MESSAGE = "Message";
    public static final String C_COLUMNA_EXPIRATION = "Expiration";
    public static final String C_COLUMNA_TOKEN= "Token";

    private String[] columnas = new String[]{C_COLUMNA_MEDICO_ID, C_COLUMNA_ESPECIALIDAD_ID, C_COLUMNA_ESPECIALIDAD, C_COLUMNA_USUARIO_ID, C_COLUMNA_USUARIO, C_COLUMNA_NOMBRES, C_COLUMNA_APELLIDO_PATERNO,
            C_COLUMNA_APELLIDO_MATERNO, C_COLUMNA_DOCUMENTO, C_COLUMNA_DOCUMENTO_ID, C_COLUMNA_NUMERO_DOCUMENTO, C_COLUMNA_CMP, C_COLUMNA_CELULAR, C_COLUMNA_CORREO, C_COLUMNA_SEXO, C_COLUMNA_FOTO,
            C_COLUMNA_TOTAL, C_COLUMNA_USUARIO_REGISTRO, C_COLUMNA_FECHA_REGISTRO, C_COLUMNA_ESTADO, C_COLUMNA_IS_SUCCESS, C_COLUMNA_MESSAGE, C_COLUMNA_EXPIRATION, C_COLUMNA_TOKEN};
    private Context contexto;
    private SalutemDB dbSalutem;
    private SQLiteDatabase db;

    public MedicoQuery(Context contexto) {
        this.contexto = contexto;
    }

    public MedicoQuery abrir() throws SQLException {
        dbSalutem = new SalutemDB(contexto);
        db = dbSalutem.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        dbSalutem.close();
    }

    public int insertarMedico(MedicoEntity medicoEntity){
        if (db == null)
            abrir();

        Integer total=0;
        try{
            ContentValues contentValues = new ContentValues();

            contentValues.put( C_COLUMNA_MEDICO_ID, StringUtils.devuelveVacioString(medicoEntity.getMedicoId()));
            contentValues.put( C_COLUMNA_ESPECIALIDAD_ID, StringUtils.devuelveVacioString(medicoEntity.getEspecialidadId()));
            contentValues.put( C_COLUMNA_USUARIO_ID, StringUtils.devuelveVacioString(medicoEntity.getUsuarioId()));
            contentValues.put( C_COLUMNA_USUARIO, StringUtils.devuelveVacioString(medicoEntity.getUsuario()));
            contentValues.put( C_COLUMNA_NOMBRES, StringUtils.devuelveVacioString(medicoEntity.getNombres()));
            contentValues.put( C_COLUMNA_APELLIDO_PATERNO, StringUtils.devuelveVacioString(medicoEntity.getApellidoPaterno()));
            contentValues.put( C_COLUMNA_APELLIDO_MATERNO, StringUtils.devuelveVacioString(medicoEntity.getApellidoMaterno()));
            contentValues.put( C_COLUMNA_DOCUMENTO_ID, StringUtils.devuelveVacioString(medicoEntity.getDocumentoId()));
            contentValues.put( C_COLUMNA_NUMERO_DOCUMENTO, StringUtils.devuelveVacioString(medicoEntity.getNumeroDocumento()));
            contentValues.put( C_COLUMNA_CMP, StringUtils.devuelveVacioString(medicoEntity.getCmp()));
            contentValues.put( C_COLUMNA_CELULAR, StringUtils.devuelveVacioString(medicoEntity.getCelular()));
            contentValues.put( C_COLUMNA_CORREO, StringUtils.devuelveVacioString(medicoEntity.getCorreo()));
            contentValues.put( C_COLUMNA_SEXO, StringUtils.devuelveVacioString(medicoEntity.getSexo()));
            contentValues.put( C_COLUMNA_FOTO, StringUtils.devuelveVacioString(medicoEntity.getFoto()));

            db.insert(C_TABLA, null, contentValues);
        }catch (Exception e){
            Log.e("ErrorRegisNucleo", e.getLocalizedMessage());
        }
        db.close();

        return total;
    }


    public ArrayList<MedicoEntity> insertarMedicoxEpecialidade(ArrayList medicos){
        ArrayList<MedicoEntity> retornaNuevosMedicos = new ArrayList<>();
        if (db == null)
            abrir();
        //Eliminamos todos los registros
        //db.delete(C_TABLA, null, null);

        int total = medicos.size();
        for(int i=0; i<total; i++) {
            MedicoEntity medico = (MedicoEntity) medicos.get(i);
            ContentValues contentValues = new ContentValues();

            String whereClause = "MedicoId =? ";
            String[] whereArgs = new String[]{medico.getMedicoId()};

            try {
                //Cursor c = db.query( C_TABLA, columnas, whereClause, whereArgs, null, null, null, null);

                //if (c.getCount() != 1) {
                    contentValues.put(C_COLUMNA_MEDICO_ID, StringUtils.devuelveVacioString(medico.getMedicoId()));
                    contentValues.put(C_COLUMNA_ESPECIALIDAD_ID, StringUtils.devuelveVacioString(medico.getEspecialidadId()));
                    contentValues.put(C_COLUMNA_ESPECIALIDAD, StringUtils.devuelveVacioString(medico.getEspecialidad()));
                    contentValues.put(C_COLUMNA_USUARIO_ID, StringUtils.devuelveVacioString(medico.getUsuarioId()));
                    contentValues.put(C_COLUMNA_USUARIO, StringUtils.devuelveVacioString(medico.getUsuario()));
                    contentValues.put(C_COLUMNA_NOMBRES, StringUtils.devuelveVacioString(medico.getNombres()));
                    contentValues.put(C_COLUMNA_APELLIDO_PATERNO, StringUtils.devuelveVacioString(medico.getApellidoPaterno()));
                    contentValues.put(C_COLUMNA_APELLIDO_MATERNO, StringUtils.devuelveVacioString(medico.getApellidoMaterno()));
                    contentValues.put(C_COLUMNA_DOCUMENTO, StringUtils.devuelveVacioString(medico.getDocumento()));
                    contentValues.put(C_COLUMNA_DOCUMENTO_ID, StringUtils.devuelveVacioString(medico.getDocumentoId()));
                    contentValues.put(C_COLUMNA_NUMERO_DOCUMENTO, StringUtils.devuelveVacioString(medico.getNumeroDocumento()));
                    contentValues.put(C_COLUMNA_CMP, StringUtils.devuelveVacioString(medico.getCmp()));
                    contentValues.put(C_COLUMNA_CELULAR, StringUtils.devuelveVacioString(medico.getCelular()));
                    contentValues.put(C_COLUMNA_CORREO, StringUtils.devuelveVacioString(medico.getCorreo()));
                    contentValues.put(C_COLUMNA_SEXO, StringUtils.devuelveVacioString(medico.getSexo()));
                    contentValues.put(C_COLUMNA_FOTO, StringUtils.devuelveVacioString(medico.getFoto()));
                    contentValues.put(C_COLUMNA_TOTAL, StringUtils.devuelveVacioString(medico.getTotal()));

                    db.insert(C_TABLA, null, contentValues);
                    retornaNuevosMedicos.add(medico);
                //}
                //c.close();
            }catch (Exception e){
                Log.e("ErrorInsrMEd", "" + e.getMessage());
            }
        }
        return retornaNuevosMedicos;
    }
}
