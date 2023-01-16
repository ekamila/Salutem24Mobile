package zisac.com.pe.salutem24.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.react.modules.core.PermissionListener;
import com.opentok.android.BaseVideoRenderer;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.opentok.android.SubscriberKit;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetActivityDelegate;
import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetFragment;
import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.dataBase.AnfitrionQuery;
import zisac.com.pe.salutem24.dataBase.CurriculumQuery;
import zisac.com.pe.salutem24.dataBase.EstudiosQuery;
import zisac.com.pe.salutem24.dataBase.ExperienciaQuery;
import zisac.com.pe.salutem24.dataBase.MedicoQuery;
import zisac.com.pe.salutem24.dataBase.URLDaoImplement;
import zisac.com.pe.salutem24.dataBase.URLDaoInterface;
import zisac.com.pe.salutem24.dataBase.UsuarioQuery;
import zisac.com.pe.salutem24.entity.ConsultaEntity;
import zisac.com.pe.salutem24.entity.ConsultaSesionEntity;
import zisac.com.pe.salutem24.entity.CurriculumEntity;
import zisac.com.pe.salutem24.entity.EspecialidadEntity;
import zisac.com.pe.salutem24.entity.UsuarioEntity;
import zisac.com.pe.salutem24.salutem.Registro;
import zisac.com.pe.salutem24.utils.Constantes;
import zisac.com.pe.salutem24.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultaOnlineFragment extends Fragment{
    private ProgressDialog popup;
    MenuFragment.OnClickOpcionFragmento optionSelected;
    private View rootView;
    private UsuarioEntity usuario;
    private ArrayList<EspecialidadEntity> especialidades;
    private ArrayList<ConsultaEntity> consultaEntity;
    TextView tv_mostrar, tv_ocultar, tv_salir;
    LinearLayout ly_informacion, content_videoLlamada;
    private TextView tvDoctor, tvEspecialidad, tvPaciente, tvHorario;
    private JitsiMeetView view;
    Dialog dialog;
    //private RelativeLayout content_videoLlamada;
    ConsultaSesionEntity consultaSesionEntity;
    private static final int PERMISSIONS_REQUEST_CODE = 124;
    private Session session;
    private FrameLayout publisherViewContainer;
    private FrameLayout subscriberViewContainer;
    private static final String TAG = ConsultaOnlineFragment.class.getSimpleName();
    private Publisher publisher;
    private Subscriber subscriber;

    public ConsultaOnlineFragment() {
    }

    public interface OnClickOpcionFragmento{
        void OnClickOpcionFragmento(int opcion, ArrayList datos);
    }

    private PublisherKit.PublisherListener publisherListener = new PublisherKit.PublisherListener() {
        @Override
        public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
            Log.d(TAG, "onStreamCreated: Publisher Stream Created. Own stream " + stream.getStreamId());
        }

        @Override
        public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
            Log.d(TAG, "onStreamDestroyed: Publisher Stream Destroyed. Own stream " + stream.getStreamId());
        }

        @Override
        public void onError(PublisherKit publisherKit, OpentokError opentokError) {
            Log.e(TAG, "PublisherKit onError: " + opentokError.getMessage());
        }
    };

    private Session.SessionListener sessionListener = new Session.SessionListener() {
        @Override
        public void onConnected(Session session) {
            Log.d(TAG, "onConnected: Connected to session: " + session.getSessionId());

            publisher = new Publisher.Builder(getActivity()).build();
            publisher.setPublisherListener(publisherListener);
            publisher.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);

            publisherViewContainer.addView(publisher.getView());

            if (publisher.getView() instanceof GLSurfaceView) {
                ((GLSurfaceView) publisher.getView()).setZOrderOnTop(true);
            }

            session.publish(publisher);
        }


        @Override
        public void onDisconnected(Session session) {
            Log.d(TAG, "onDisconnected: Disconnected from session: " + session.getSessionId());
        }

        @Override
        public void onStreamReceived(Session session, Stream stream) {
            Log.d(TAG, "onStreamReceived: New Stream Received " + stream.getStreamId() + " in session: " + session.getSessionId());

            if (subscriber == null) {
                subscriber = new Subscriber.Builder(getActivity(), stream).build();
                subscriber.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
                subscriber.setSubscriberListener(subscriberListener);
                session.subscribe(subscriber);
                subscriberViewContainer.addView(subscriber.getView());
            }
        }


        @Override
        public void onStreamDropped(Session session, Stream stream) {
            Log.i(TAG, "Stream Dropped");

            if (subscriber != null) {
                subscriber = null;
                subscriberViewContainer.removeAllViews();
            }
        }


        @Override
        public void onError(Session session, OpentokError opentokError) {
            Log.e(TAG, "Session error: " + opentokError.getMessage());
        }
    };

    SubscriberKit.SubscriberListener subscriberListener = new SubscriberKit.SubscriberListener() {
        @Override
        public void onConnected(SubscriberKit subscriberKit) {
            Log.d(TAG, "onConnected: Subscriber connected. Stream: " + subscriberKit.getStream().getStreamId());
        }

        @Override
        public void onDisconnected(SubscriberKit subscriberKit) {
            Log.d(TAG, "onDisconnected: Subscriber disconnected. Stream: " + subscriberKit.getStream().getStreamId());
        }

        @Override
        public void onError(SubscriberKit subscriberKit, OpentokError opentokError) {
            Log.e(TAG, "SubscriberKit onError: " + opentokError.getMessage());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        consultaEntity = getArguments().getParcelableArrayList(Constantes.KEY_CONSULTA_ONLINE);
        Log.i("Valor" , consultaEntity.get(0).getConsultaId());//=ID NUMERO DE CONSULTA
        //SE DEBE OBTENER LOS VALORES NECESARIOS PARA ESTABLECER LA VIDEOCONFERENCIA CON OPENTOK
        //GET https://localhost:44339/ConsultaMedica/TraerSesion?consulta_id=numero_consulta
        usuario = getArguments().getParcelable(Constantes.USUARIO);
        if(savedInstanceState!=null){
            consultaEntity = savedInstanceState.getParcelableArrayList(Constantes.KEY_CONSULTA_ONLINE);
        }
        //view = (JitsiMeetView) consultaEntity.get(0).getJitsiMeetView();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_consulta_online, container, false);
        //content_videoLlamada = rootView.findViewById(R.id.content_videoLlamada);
        publisherViewContainer = rootView.findViewById(R.id.publisher_container);
        subscriberViewContainer = rootView.findViewById(R.id.subscriber_container);
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

        tv_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarDialog();
            }
        });

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
        ConsultaSesion consultaSesion = new ConsultaSesion();
        consultaSesion.execute(consultaEntity.get(0).getConsultaId());
    }

    public void EjecutarVideo(ConsultaSesionEntity consultaSesionEntity){
        requestPermissions(consultaSesionEntity.sesion_sala, consultaSesionEntity.token_sala);
    }

    private class ConsultaSesion extends AsyncTask<String,Void, ConsultaSesionEntity> {
        //ConsultaSesionEntity consultaSesionEntity;
        @Override
        protected ConsultaSesionEntity doInBackground(String... params) {
            String consulta_id = params[0];
            URLDaoInterface dao = new URLDaoImplement();
            consultaSesionEntity = dao.getConsultaSesion(consulta_id);
            if (consultaSesionEntity != null) {
                //requestPermissions(consultaSesionEntity.sesion_sala, consultaSesionEntity.token_sala);
            }
            return consultaSesionEntity;
        }

        @Override
        protected void onPostExecute(ConsultaSesionEntity cs) {
            super.onPostExecute(cs);
            //Comparte los parámetros desde el AsynTask
            EjecutarVideo(cs);
        }
    }

    /*private void mostrarVideoLLamada(){
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
    }*/

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(PERMISSIONS_REQUEST_CODE)
    private void requestPermissions(String sessionId, String token) {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };

        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            // initialize and connect to the session
            initializeSession(sessionId, token);
        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", PERMISSIONS_REQUEST_CODE, perms);
        }
    }

    private void initializeSession(String sessionId, String token) {
        session = new Session.Builder(getActivity(), Constantes.API_KEY, sessionId).build();
        session.setSessionListener(sessionListener);
        session.connect(token);
    }

    private void MostrarDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_exit_dialog, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        Button btcancelar = view.findViewById(R.id.btcancelar);
        Button btterminar = view.findViewById(R.id.btterminar);

        btcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btterminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.disconnect();
                dialog.dismiss();
                getActivity().onBackPressed();
            }
        });

    }


}
