package zisac.com.pe.salutem24.utils;

public class Constantes {
    public static final String HTTP_CABECERA = "https://190.117.64.238/SalutemApi/";
    public static final String URL_LOGIN = "cuenta/login";
    public static final String URL_VALIDAR_CUENTA = "Cuenta/";
    public static final String URL_INSERTAR = "cuenta/insertar";
    public static final String URL_ESPECIALIDADES = "Especialidad/Listar";
    public static final String URL_MEDICOS_ESPECIALIDAD = "Medico/Listar?especialidad=";
    public static final String URL_TURNO_FECHA_MEDICO = "Turno/Listar?especialidad=";
    public static final String URL_CURRICULUM_MEDICO = "curriculum/listar/";
    public static final String URL_LISTAR_PACIENTE = "Paciente/Listar?usuario=";
    public static final String URL_INSERTAR_PACIENTE = "Paciente/Insertar";
    public static final String URL_REGISTRAR_CONSULTA = "ConsultaMedica/Insertar";
    public static final String URL_CONSULTA_LISTAR = "ConsultaMedica/Listar?usuario=";
    public static final String URL_CONSULTA_INMEDIATA = "Turno/ListarConsultaInmediata";
    public static final String URL_CONSULTA = "ConsultaMedica/Traer/";
    public static final int OPCIONES_MENU=1;
    public static final int OPCIONES_ESPECIALIDADES=2;
    public static final int OPCION_CONSULTA_PROGRAMADA=3;
    public static final int OPCIONES_HORARIOS=4;
    public static final int OPCION_DATOS_PACIENTES=5;
    public static final int OPCION_PAGOS=6;
    public static final int OPCION_CONSULTA_ONLINE=7;

    public static final int TIEMPO_REFRESH_ALERT = 300000;
    public static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    public static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
    public static final String BDSALUTEM = "BDSALUTEM";
    public static final String PACKAGE_NAME="zisac.com.pe.salutem24";
    public static final String USUARIO = "usuario";
    public static final String INMEDIATA = "inmediata";

    public static final String KEY_DETALLE_MENU = "detalleMenu";
    public static final String KEY_DETALLE_ESPECIALIDADES = "detalleEspecialidad";
    public static final String KEY_DETALLE_MEDICOS = "detalleMedicos";
    public static final String KEY_DATOS_PACIENTE = "detallePaciente";
    public static final String KEY_PAGO_CONSULTA = "detallePago";
    public static final String KEY_CONSULTA_PROGRAMADA = "detalleConsultaProgramada";
    public static final String KEY_CONSULTA_ONLINE = "detalleConsultaOnline";

    public static final String ID_OPCIONES_MENU = "MenuFragment";
    public static final String ID_OPCIONES_ESPECIALIDAD_FRAGMENT = "OpcionesEspecialidadFragment";
    public static final String ID_HORARIOS_FRAGMENT = "HorariosFragment";
    public static final String ID_DATOS_PACIENTE_FRAGMENT = "DatosPacienteFragment";
    public static final String ID_PAGAR_CONSULTA_FRAGMENT = "PagarConsultaFragment";
    public static final String ID_CONSULTA_PROGRAMADA_FRAGMENT = "ConsultaProgramadaFragment";
    public static final String ID_CONSULTA_ONLINE_FRAGMENT = "ConsultaOnlineFragment";

    public static final String DOMINIO_DETALLE_SEXO = "1";
    public static final String DOMINIO_DETALLE_PACIENTES = "2"; //NUMERO CUALQUIERA
    public static final String DOMINIO_DETALLE_ESTADOS = "3";
    public static final String DOMINIO_DETALLE_ESPECIALIDADES = "4"; //NUMERO CUALQUIERA
    public static final String DOMINIO_DETALLE_ORDENAR = "5";

    public static final String USUARIO_RECORDADO = "1";
    public static final String USUARIO_NO_RECORDADO = "0";

    public static final String FECHA_FILTRO_RESERVAR_CONSULTA = "0";
    public static final String FECHA_NACIMIENTO_AGREGAR_PACIENTE = "1";
    public static final String FECHA_PROGRAMADA = "2";

    public static final String RESERVA_NORMAL = "0";
    public static final String RESERVA_INMEDIATA = "1";
}
