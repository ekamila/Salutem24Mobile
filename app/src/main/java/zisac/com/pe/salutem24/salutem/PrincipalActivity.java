package zisac.com.pe.salutem24.salutem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.dataBase.UsuarioQuery;
import zisac.com.pe.salutem24.entity.ConsultaEntity;
import zisac.com.pe.salutem24.entity.UsuarioEntity;
import zisac.com.pe.salutem24.fragments.ConsultaOnlineFragment;
import zisac.com.pe.salutem24.fragments.ConsultaPragramadaFragment;
import zisac.com.pe.salutem24.fragments.DatosPacienteFragment;
import zisac.com.pe.salutem24.fragments.HorariosFragment;
import zisac.com.pe.salutem24.fragments.IzipayFragment;
import zisac.com.pe.salutem24.fragments.MedioPagoFragment;
import zisac.com.pe.salutem24.fragments.MenuFragment;
import zisac.com.pe.salutem24.fragments.OpcionesEspecialidadFragment;
import zisac.com.pe.salutem24.fragments.PagarConsultaFragment;
import zisac.com.pe.salutem24.fragments.PagoefectivoFragment;
import zisac.com.pe.salutem24.utils.Constantes;
import zisac.com.pe.salutem24.utils.Utils;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.facebook.react.modules.core.PermissionListener;

import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetView;

import java.util.ArrayList;

public class PrincipalActivity extends AppCompatActivity
        implements MenuFragment.OnClickOpcionFragmento,
        OpcionesEspecialidadFragment.OnClickOpcionFragmento,
        HorariosFragment.OnClickOpcionFragmento,
        DatosPacienteFragment.OnClickOpcionFragmento,
        PagarConsultaFragment.OnClickOpcionFragmento,
        ConsultaPragramadaFragment.OnClickOpcionFragmento,
        ConsultaOnlineFragment.OnClickOpcionFragmento,
        MedioPagoFragment.OnClickOpcionFragmento,
        IzipayFragment.OnClickOpcionFragmento,
        JitsiMeetActivityInterface {

    private MenuFragment menuFragment;
    private OpcionesEspecialidadFragment opcionesEspecialidadFragment;
    private HorariosFragment horariosFragment;
    private DatosPacienteFragment datosPacienteFragment;
    private PagarConsultaFragment pagarConsultaFragment;
    private MedioPagoFragment medioPagoFragment;
    private IzipayFragment izipayFragment;
    private PagoefectivoFragment pagoefectivoFragment;
    private ConsultaPragramadaFragment consultaPragramadaFragment;
    private ConsultaOnlineFragment consultaOnlineFragment;
    private int caseOption=0;
    private boolean salirActivity=false;
    private UsuarioEntity usuario;
    private JitsiMeetView jitsiMeetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        /*Toolbar*/
        setSupportActionBar(findViewById(R.id.toolbar));

        Bundle bundle = getIntent().getExtras();

        try{
            usuario = bundle.getParcelable(Constantes.USUARIO);
        }catch(Exception ex){
            Intent mainIntent = new Intent().setClass(this, LoginActivity.class);
            startActivity(mainIntent);
            finish();
        }

        if(savedInstanceState==null){
            showOpcionesMenu();
        }
    }

    @Override
    public void OnClickOpcionFragmento(int opcion, ArrayList datos) {
        caseOption = opcion;
        switch(opcion) {
            case Constantes.OPCIONES_MENU: showOpcionesMenu(); break;
            case Constantes.OPCIONES_ESPECIALIDADES: showOpcionesEspecialidad(datos); break;
            case Constantes.OPCIONES_HORARIOS: mostrarHorariosConsulta(datos); break;
            case Constantes.OPCION_DATOS_PACIENTES: mostrarDatosPaciente(datos); break;
            case Constantes.OPCION_PAGOS: mostrarPago(datos); break;
            case Constantes.OPCION_CONSULTA_PROGRAMADA: mostrarConsultaProgramada(datos); break;
            case Constantes.OPCION_CONSULTA_ONLINE: mostrarConsultaOnline(datos); break;
            case Constantes.OPCION_MEDIO_PAGO: mostrarMedioPago(datos); break;
            case Constantes.OPCION_IZIPAY: mostrarIzipay(); break;
            case Constantes.OPCION_PAGOEFECTIVO: mostrarPagoEfectivo(); break;

        }
    }

    @Override
    public void onBackPressed() {
        //Log.e("coutn","baxkStarckconut_" + getSupportFragmentManager().getBackStackEntryCount());
        //Log.e("case","option_" + caseOption);
        if(getSupportFragmentManager().getBackStackEntryCount()>0){
            switch(caseOption){
                case Constantes.OPCIONES_MENU: caseOption = Constantes.OPCIONES_MENU; menuFragment = null; break;
                case Constantes.OPCIONES_ESPECIALIDADES: caseOption = Constantes.OPCIONES_MENU; opcionesEspecialidadFragment = null; break;
                case Constantes.OPCIONES_HORARIOS: caseOption = Constantes.OPCIONES_MENU; horariosFragment = null; break;
                case Constantes.OPCION_DATOS_PACIENTES: caseOption = Constantes.OPCIONES_MENU; datosPacienteFragment = null; break;
                case Constantes.OPCION_PAGOS: caseOption = Constantes.OPCIONES_MENU; pagarConsultaFragment = null; break;
                case Constantes.OPCION_CONSULTA_PROGRAMADA: caseOption = Constantes.OPCIONES_MENU; consultaPragramadaFragment = null; break;
                case Constantes.OPCION_CONSULTA_ONLINE:
                    jitsiMeetView.leave();
                    jitsiMeetView.dispose();
                    caseOption = Constantes.OPCIONES_MENU;
                    consultaOnlineFragment = null;
                    break;
            }
            getSupportFragmentManager().popBackStack();
        }else{
            //Log.e("onBack5","2");
            cerrarSesionSalutem();
            //moveTaskToBack(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!salirActivity){
            moveTaskToBack(true);
        }
        salirActivity=false;
    }

    private void showOpcionesMenu(){
        Bundle arguments =new Bundle();
        arguments.putParcelable(Constantes.USUARIO, usuario);

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        caseOption= Constantes.OPCIONES_MENU;
        menuFragment = null;
        menuFragment = new MenuFragment();
        menuFragment.setArguments(arguments);
        FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
        tran.replace(R.id.frame_fragment, menuFragment);
        tran.commit();
    }

    private void showOpcionesEspecialidad(ArrayList datos){
        Bundle arguments =new Bundle();
        arguments.putParcelableArrayList(Constantes.KEY_DETALLE_ESPECIALIDADES, datos);
        arguments.putParcelable(Constantes.USUARIO, usuario);
        opcionesEspecialidadFragment = new OpcionesEspecialidadFragment();
        opcionesEspecialidadFragment.setArguments(arguments);
        insertarFragmentoOpcion(opcionesEspecialidadFragment, Constantes.ID_OPCIONES_ESPECIALIDAD_FRAGMENT);
    }

    private void mostrarHorariosConsulta(ArrayList datos){
        Bundle arguments =new Bundle();
        arguments.putParcelableArrayList(Constantes.KEY_DETALLE_MEDICOS, datos);
        arguments.putParcelable(Constantes.USUARIO, usuario);
        horariosFragment = new HorariosFragment();
        horariosFragment.setArguments(arguments);
        insertarFragmentoOpcion(horariosFragment, Constantes.ID_HORARIOS_FRAGMENT);
    }

    private void mostrarDatosPaciente(ArrayList datos){
        Bundle arguments =new Bundle();
        arguments.putParcelableArrayList(Constantes.KEY_DATOS_PACIENTE, datos);
        arguments.putParcelable(Constantes.USUARIO, usuario);
        datosPacienteFragment = new DatosPacienteFragment();
        datosPacienteFragment.setArguments(arguments);
        insertarFragmentoOpcion(datosPacienteFragment, Constantes.ID_DATOS_PACIENTE_FRAGMENT);
    }

    private void mostrarPago(ArrayList datos){
        Bundle arguments =new Bundle();
        arguments.putParcelableArrayList(Constantes.KEY_PAGO_CONSULTA, datos);
        arguments.putParcelable(Constantes.USUARIO, usuario);
        pagarConsultaFragment = new PagarConsultaFragment();
        pagarConsultaFragment.setArguments(arguments);
        insertarFragmentoOpcion(pagarConsultaFragment, Constantes.ID_PAGAR_CONSULTA_FRAGMENT);
    }

    private void mostrarMedioPago(ArrayList datos){
        Bundle arguments =new Bundle();
        arguments.putParcelableArrayList(Constantes.KEY_OPCION_PAGO, datos);
        arguments.putParcelable(Constantes.USUARIO, usuario);
        medioPagoFragment = new MedioPagoFragment();
        medioPagoFragment.setArguments(arguments);
        insertarFragmentoOpcion(medioPagoFragment, Constantes.ID_MEDIO_PAGO_FRAGMENT);
    }

    private void mostrarIzipay(){
        Bundle arguments =new Bundle();
        //arguments.putParcelableArrayList(Constantes.KEY_OPCION_PAGO, datos);
        arguments.putParcelable(Constantes.USUARIO, usuario);
        izipayFragment = new IzipayFragment();
        izipayFragment.setArguments(arguments);
        insertarFragmentoOpcion(izipayFragment, Constantes.ID_IZIPAY_FRAGMENT);
    }

    private void mostrarPagoEfectivo(){
        Bundle arguments =new Bundle();
        //arguments.putParcelableArrayList(Constantes.KEY_OPCION_PAGO, datos);
        arguments.putParcelable(Constantes.USUARIO, usuario);
        pagoefectivoFragment = new PagoefectivoFragment();
        pagoefectivoFragment.setArguments(arguments);
        insertarFragmentoOpcion(pagoefectivoFragment, Constantes.ID_PAGOEEFECTIVO_FRAGMENT);
    }

    private void mostrarConsultaProgramada(ArrayList datos){
        Bundle arguments =new Bundle();
        arguments.putParcelableArrayList(Constantes.KEY_CONSULTA_PROGRAMADA, datos);
        arguments.putParcelable(Constantes.USUARIO, usuario);
        consultaPragramadaFragment = new ConsultaPragramadaFragment();
        consultaPragramadaFragment.setArguments(arguments);
        insertarFragmentoOpcion(consultaPragramadaFragment, Constantes.ID_CONSULTA_PROGRAMADA_FRAGMENT);
    }

    private void mostrarConsultaOnline(ArrayList datos){
        ArrayList<ConsultaEntity> consulta = new ArrayList<>();
        consulta = datos;
        jitsiMeetView = new JitsiMeetView(this);
        consulta.get(0).setJitsiMeetView(jitsiMeetView);

        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(Constantes.KEY_CONSULTA_ONLINE, datos);
        arguments.putParcelable(Constantes.USUARIO, usuario);
        consultaOnlineFragment = new ConsultaOnlineFragment();
        consultaOnlineFragment.setArguments(arguments);

        if(consulta.get(0).getConsultaInmediata().equals(Constantes.RESERVA_NORMAL)) {
            insertarFragmentoOpcion(consultaOnlineFragment, Constantes.ID_CONSULTA_ONLINE_FRAGMENT);
        }

        if(consulta.get(0).getConsultaInmediata().equals(Constantes.RESERVA_INMEDIATA)) {
            getSupportFragmentManager().popBackStack(null, 2);

            FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
            tran.replace(R.id.frame_fragment, consultaOnlineFragment);
            tran.commit();
        }
    }

    public void insertarFragmentoOpcion(Fragment fragment, String idFragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.enter_animation_fragment, R.anim.exit_animation_fragment, R.anim.pop_enter_animation_fragment, R.anim.pop_exit_animation_fragment);
        ft.replace(R.id.frame_fragment, fragment);
        ft.addToBackStack(idFragment);
        ft.commit();
    }

    private void cerrarSesionSalutem(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("¡Confirmar!");
        dialog.setMessage("¿Está seguro de cerrar sesión?");
        dialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                salirActivity = true;

                UsuarioQuery usuarioQuery = new UsuarioQuery(PrincipalActivity.this);
                usuario.setEstado(Constantes.USUARIO_NO_RECORDADO);
                usuarioQuery.insertarUpdateUsuario(usuario);
                Intent loginIntent = new Intent().setClass(PrincipalActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
        dialog.setNegativeButton("NO", null);
        dialog.show();
    }

    @Override
    public void requestPermissions(String[] strings, int i, PermissionListener permissionListener) {
        Log.e("1", "");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items_toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.atras:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
