package zisac.com.pe.salutem24.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import zisac.com.pe.salutem24.utils.Constantes;

public class SalutemDB extends SQLiteOpenHelper {
    private static int version = 1;
    private static String name = Constantes.BDSALUTEM;
    private static SQLiteDatabase.CursorFactory factory = null;

    public SalutemDB(Context context) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE USUARIOENTITY (" +
                " IsSuccess varchar(10), " +
                " Message varchar(200), " +
                " Expiration varchar(200), " +
                " Token varchar(500), " +
                " UsuarioId integer NOT NULL, " + //PK
                " Usuario varchar(20), " +
                " Clave varchar(64), " +
                " TipoDocumento varchar(50), " +
                " NumeroDocumento varchar(11), " +
                " Nombres varchar(50), " +
                " ApellidoPaterno varchar(50), " +
                " ApellidoMaterno varchar(50), " +
                " Sexo varchar(1), " +
                " Correo varchar(50), " +
                " Perfil varchar(50), " +
                " PerfilId integer, " +
                " Medico varchar(50), " +
                " MedicoId integer, " +
                " Anfitrion varchar(50), " +
                " Foto varchar(50), " +
                " UsuarioRegistro varchar(50), " +
                " FechaRegistro varchar(50), " +
                " Estado varchar(50) ) " );

        db.execSQL( "CREATE TABLE MEDICOENTITY(" +
                " IsSuccess varchar(10), " +
                " Message varchar(200), " +
                " Expiration varchar(200), " +
                " Token varchar(500), " +
                " MedicoId integer NOT NULL, " +
                " EspecialidadId integer, " +
                " Especialidad varchar(200), " +
                " UsuarioId integer, " +
                " Usuario varchar(150), " +
                " Nombres varchar(50), " +
                " ApellidoPaterno varchar(50), " +
                " ApellidoMaterno varchar(3), " +
                " Documento varchar(200)," +
                " DocumentoId integer," +
                " NumeroDocumento varchar(50), " +
                " Cmp varchar(50), " +
                " Celular varchar(50), " +
                " Correo varchar(50), " +
                " Sexo varchar(50), " +
                " Foto varchar(500), " +
                " Total varchar(10), " +
                " UsuarioRegistro varchar(50), " +
                " FechaRegistro varchar(50), " +
                " Estado varchar(50))" );

        db.execSQL("CREATE TABLE ANFITRIONENTITY (  " +
                " IsSuccess varchar(10), " +
                " Message varchar(200), " +
                " Expiration varchar(200), " +
                " Token varchar(500), " +
                " AnfitrionId integer NOT NULL, " +
                " UsuarioId integer, " +
                " Nombre varchar(100), " +
                " Contrasena varchar(50), " +
                " UsuarioRegistro varchar(50), " +
                " FechaRegistro varchar(200), " + //FK
                " Estado varchar(200)) ");

        db.execSQL( "CREATE TABLE ESPECIALIDADENTITY (" +
                " IsSuccess varchar(10), " +
                " Message varchar(200), " +
                " Expiration varchar(200), " +
                " Token varchar(500), " +
                " EspecialidadId integer, " +
                " Codigo varchar(50), " +
                " Nombre varchar(50) ," +
                " Descripcion varchar(200), " +
                " Importe varchar(100), " +
                " Foto varchar(10), " +
                " Total varchar(10), " +
                " UsuarioRegistro varchar(50), " +
                " FechaRegistro varchar(200), " +
                " Estado varchar(200) )");

        db.execSQL( "CREATE TABLE TURNOENTITY (" +
                " IsSuccess varchar(10), " +
                " Message varchar(200), " +
                " Expiration varchar(200), " +
                " Token varchar(500), " +
                " Inmediata varchar(2), " +
                " TurnoId  integer, " +
                " EspecialidadId integer, " +
                " Especialidad varchar(50), " +
                " MedicoId integer, " +
                " Medico varchar(50), " +
                " Horario varchar(50)," +
                " FechaTurno varchar(50), " +
                " Importe varchar(50), " +
                " Foto varchar(50), " +
                " UsuarioRegistro varchar(50), " +
                " FechaRegistro varchar(200), " +
                " Estado varchar(200))");

        db.execSQL( "CREATE TABLE TURNODETALLEENTITY (" +
                " IsSuccess varchar(10), " +
                " Message varchar(200), " +
                " Expiration varchar(200), " +
                " Token varchar(500), " +
                " TurnoDetalleId integer, " +
                " TurnoId integer, " +
                " Horario varchar(50), " +
                " EstadoTurno varchar(50)   , " +
                " EstadoTurnoId varchar(50)   , " +
                " UsuarioRegistro varchar(50), " +
                " FechaRegistro varchar(200), " +
                " Estado varchar(200)) ");

        db.execSQL( "CREATE TABLE CURRICULUMENTITY (" +
                " IsSuccess varchar(10), " +
                " Message varchar(200), " +
                " Expiration varchar(200), " +
                " Token varchar(500), " +
                " MedicoId integer, " +
                " Foto varchar(50), " +
                " Nombres varchar(150), " +
                " ApellidoPaterno varchar(3), " +
                " ApellidoMaterno varchar(50), " +
                " Cmp varchar(150), " +
                " Rne varchar(3), " +
                " NombreEspecialidad varchar(50), " +
                " EstadoTurno varchar(50), " +
                " UsuarioRegistro varchar(50), " +
                " FechaRegistro varchar(200), " +
                " Estado varchar(200))");

        db.execSQL( "CREATE TABLE ESTUDIOSENTITY (" +
                " IsSuccess varchar(10), " +
                " Message varchar(200), " +
                " Expiration varchar(200), " +
                " Token varchar(500), " +
                " MedicoId  integer, " +
                " Titulo varchar(50), " +
                " Institucion varchar(200), " +
                " UsuarioRegistro varchar(50), " +
                " FechaRegistro varchar(200), " +
                " Estado varchar(200)) ");

        db.execSQL( "CREATE TABLE EXPERIENCIAENTITY (" +
                " IsSuccess varchar(10), " +
                " Message varchar(200), " +
                " Expiration varchar(200), " +
                " Token varchar(500), " +
                " MedicoId  integer, " +
                " Descripcion varchar(50), " +
                " Lugar varchar(50), " +
                " UsuarioRegistro varchar(50), " +
                " FechaRegistro varchar(200), " +
                " Estado varchar(200))");

        db.execSQL( "CREATE TABLE PACIENTEENTITY (" +
                " IsSuccess varchar(10), " +
                " Message varchar(200), " +
                " Expiration varchar(200), " +
                " Token varchar(500), " +
                " PacienteId integer, " +
                " UsuarioId integer, " +
                " Usuario varchar(50), " +
                " Nombres varchar(500), " +
                " ApellidoPaterno varchar(300), " +
                " ApellidoMaterno varchar(50) , " +
                " DocumentoId integer, " +
                " Documento varchar(500), " +
                " NumeroDocumento varchar(300), " +
                " Celular varchar(50), " +
                " Sexo varchar(50), " +
                " FechaNacimiento varchar(50), " +
                " Total varchar(50), " +
                " UsuarioRegistro varchar(50), " +
                " FechaRegistro varchar(200), " +
                " Estado varchar(200)) ");

        db.execSQL("CREATE TABLE CONSULTAENTITY ( " +
                " IsSuccess varchar(10), " +
                " Message varchar(200), " +
                " Expiration varchar(200), " +
                " Token varchar(500), " +
                " ConsultaId integer, " +
                " PacienteId integer, " +
                " Paciente varchar(50), " +
                " HistoriaClinicaId integer, " +
                " EspecialidadId integer, " +
                " Especialidad varchar(500), " +
                " Medico varchar(50), " +
                " CodigoSala varchar(50), " +
                " EstadoConsulta varchar(50), " +
                " FechaConsulta varchar(500), " +
                " HorarioConsulta varchar(50), " +
                " EstadoConsultaId varchar(500), " +
                " EstadoConsultaDesc varchar(50), " +
                " Total varchar(50), " +
                " UsuarioRegistro varchar(50), " +
                " FechaRegistro varchar(200), " +
                " Estado varchar(200), " +
                " NroCita varchar(200), " +
                " ConsultaInmediata varchar(200)) ");

        db.execSQL("CREATE TABLE DOMINIODETALLEENTITY(  " +
                " DominioId varchar(500),  " +
                " ValorDetalle varchar(10),  " +
                " DescripcionDetalle varchar(1000),  " +
                " FechaRegistro varchar(20),  " +
                " Estado varchar(2),  " +
                " UsuarioRegistro varchar(3))");

        //ESTADO CIVIL
        db.execSQL("INSERT INTO DOMINIODETALLEENTITY VALUES ('1','1','Masculino','2020-11-06','1','1');");
        db.execSQL("INSERT INTO DOMINIODETALLEENTITY VALUES ('1','2','Femenino','2020-11-06','1','1');");

        db.execSQL("INSERT INTO DOMINIODETALLEENTITY VALUES ('3','1','Pendiente','2020-11-06','1','1');");
        db.execSQL("INSERT INTO DOMINIODETALLEENTITY VALUES ('3','2','Atendida','2020-11-06','1','1');");
        db.execSQL("INSERT INTO DOMINIODETALLEENTITY VALUES ('3','3','Canceladas','2020-11-06','1','1');");

        //ORDENAR POR
        db.execSQL("INSERT INTO DOMINIODETALLEENTITY VALUES ('5','1','Más Recientes','2020-11-06','1','1');");
        db.execSQL("INSERT INTO DOMINIODETALLEENTITY VALUES ('5','2','Más Antiguas','2020-11-06','1','1');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

