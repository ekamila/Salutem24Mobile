package zisac.com.pe.salutem24.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.react.modules.core.PermissionListener;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetActivityDelegate;
import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetFragment;
import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.entity.ConsultaEntity;
import zisac.com.pe.salutem24.entity.UsuarioEntity;
import zisac.com.pe.salutem24.utils.Constantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultaOnlineFragment extends Fragment{
    private ProgressDialog popup;
    MenuFragment.OnClickOpcionFragmento optionSelected;
    private View rootView;
    private UsuarioEntity usuario;
    private ArrayList<ConsultaEntity> consultaEntity;
    TextView tv_mostrar, tv_ocultar, tv_salir;
    LinearLayout ly_informacion, content_videoLlamada;
    private TextView tvDoctor, tvEspecialidad, tvPaciente, tvHorario;
    private JitsiMeetView view;
    //private RelativeLayout content_videoLlamada;

    public ConsultaOnlineFragment() {
    }

    public interface OnClickOpcionFragmento{
        void OnClickOpcionFragmento(int opcion, ArrayList datos);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        consultaEntity = getArguments().getParcelableArrayList(Constantes.KEY_CONSULTA_ONLINE);
        usuario = getArguments().getParcelable(Constantes.USUARIO);
        if(savedInstanceState!=null){
            consultaEntity = savedInstanceState.getParcelableArrayList(Constantes.KEY_CONSULTA_ONLINE);
        }
        view = (JitsiMeetView) consultaEntity.get(0).getJitsiMeetView();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_consulta_online, container, false);
        content_videoLlamada = rootView.findViewById(R.id.content_videoLlamada);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_mostrar = rootView.findViewById( R.id.tv_mostrar );
        tv_ocultar = rootView.findViewById( R.id.tv_ocultar );
        tv_salir = rootView.findViewById( R.id.tv_salir );
        ly_informacion = rootView.findViewById( R.id.ly_informacion );

        tv_ocultar.setPaintFlags(tv_ocultar.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_salir.setPaintFlags(tv_salir.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        setearValores();
        addOnclickListener();
        mostrarVideoLLamada();
    }

    private void setearValores(){
        tvDoctor = rootView.findViewById( R.id.tvDoctor );
        tvEspecialidad = rootView.findViewById( R.id.tvEspecialidad );
        tvPaciente = rootView.findViewById( R.id.tvPaciente );
        tvHorario = rootView.findViewById( R.id.tvHorario );

        tvDoctor.setText(consultaEntity.get(0).getMedico());
        tvEspecialidad.setText(consultaEntity.get(0).getEspecialidad());
        tvPaciente.setText(consultaEntity.get(0).getPaciente());
        tvHorario.setText(consultaEntity.get(0).getHorarioConsulta() + " hrs.");
    }

    private void addOnclickListener(){
        tv_mostrar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_ocultar.setVisibility( View.VISIBLE );
                ly_informacion.setVisibility( View.VISIBLE );
                tv_mostrar.setVisibility( View.GONE );
            }
        } );

        tv_ocultar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_ocultar.setVisibility( View.GONE );
                ly_informacion.setVisibility( View.GONE );
                tv_mostrar.setVisibility( View.VISIBLE );
            }
        } );

    }

    private void mostrarVideoLLamada(){
        //view = new JitsiMeetView(getContext());
        JitsiMeetConferenceOptions options = null;
        //Log.e("codigoSala", "" + consultaEntity.get(0).getCodigoSala());
        try {
            //url prueba: https://meet.jit.si
            options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL("https://servidorjitsi.brazilsouth.cloudapp.azure.com/"))
                    .setRoom(consultaEntity.get(0).getCodigoSala())
                    .setWelcomePageEnabled(false)
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        view.join(options);
        view.setListener(new JitsiMeetViewListener() {
            @Override
            public void onConferenceJoined(Map<String, Object> map) {
                Log.e("Listener_CJ","onConferenceJoined");
            }

            @Override
            public void onConferenceTerminated(Map<String, Object> map) {
                Log.e("Listener_CT","onConferenceTerminated");
            }

            @Override
            public void onConferenceWillJoin(Map<String, Object> map) {
                Log.e("Listener_CWJ","onConferenceWillJoin");
            }
        });
        content_videoLlamada.addView(view);
    }

    private void crearPopUpEspera(){
        if(popup==null) {
            popup = new ProgressDialog(getActivity());
            popup.setIndeterminate(true);
            popup.setCancelable(false);
            popup.show();
            popup.setContentView(R.layout.custom_progress_dialog);
        }
    }
    private void cerrarPopUpEspera(){
        if(popup!=null && popup.isShowing()){
            popup.dismiss();
        }
        popup = null;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if(activity instanceof MenuFragment.OnClickOpcionFragmento)
        {
            optionSelected = (MenuFragment.OnClickOpcionFragmento)activity;
        }
        else
            throw new ClassCastException();
    }

    private void notifyOptionSelected(int opcion, ArrayList datos){
        optionSelected.OnClickOpcionFragmento(opcion, datos);
    }


}
