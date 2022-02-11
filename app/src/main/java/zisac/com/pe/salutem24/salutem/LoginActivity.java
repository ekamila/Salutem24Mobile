package zisac.com.pe.salutem24.salutem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.dataBase.URLDaoImplement;
import zisac.com.pe.salutem24.dataBase.URLDaoInterface;
import zisac.com.pe.salutem24.dataBase.UsuarioQuery;
import zisac.com.pe.salutem24.entity.UsuarioEntity;
import zisac.com.pe.salutem24.utils.Constantes;
import zisac.com.pe.salutem24.utils.DataUserRealm;
import zisac.com.pe.salutem24.utils.StringUtils;
import zisac.com.pe.salutem24.utils.Utils;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {
    TextView tv_registro;
    Button btIngresar;
    EditText etCorreo, etContrasena;
    CheckBox cbRecordarme;
    private AlertDialog.Builder dialogo;
    private AlertDialog alert;
    private static final int PERMISSIONS_REQUEST_CALL = 16;
    private static final int PERMISSIONS_REQUEST_AUDIO  = 15;
    private static final int PERMISSIONS_REQUEST_CAMERA = 14;
    private boolean aceptaCall = false;
    private boolean aceptaAudio = false;
    private boolean aceptaCamera = false;

    String correo, contrasena, checkRecordame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        tv_registro = findViewById(R.id.tv_registro);
        btIngresar = findViewById(R.id.btIngresar);
        cbRecordarme = findViewById(R.id.cbRecordarme);

        tv_registro.setOnClickListener(this);
        btIngresar.setOnClickListener(this);

        checkRecordame = "false";

        cbRecordarme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkRecordame = ""+b; //
            }
        });

        Log.e("cRecord","" + checkRecordame);

        UsuarioQuery usuarioQuery = new UsuarioQuery(LoginActivity.this);
        UsuarioEntity userExiste = usuarioQuery.existeUsuarioRecordado();
        if(userExiste != null){
            Intent intent = new Intent().setClass(LoginActivity.this, PrincipalActivity.class);
            intent.putExtra(Constantes.USUARIO, userExiste);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.tv_registro){
            Intent intent = new Intent().setClass(LoginActivity.this, Registro.class);
            startActivity(intent);
        }

        if(view.getId()==R.id.btIngresar){
            correo = etCorreo.getText().toString().trim();
            contrasena = etContrasena.getText().toString().trim();

            if (!correo.equals("") && !contrasena.equals("")) {
                askForPermission(Manifest.permission.CAMERA, PERMISSIONS_REQUEST_CAMERA);
                if(aceptaCamera) {
                    askForPermission(Manifest.permission.RECORD_AUDIO, PERMISSIONS_REQUEST_AUDIO);
                    if (aceptaAudio) {
                        askForPermission(Manifest.permission.CALL_PHONE, PERMISSIONS_REQUEST_CALL);
                        if (aceptaCall) {
                            showLoading();
                            LoginUser login = new LoginUser();
                            login.execute(correo, contrasena);
                        }
                    }
                }
            } else {
                Toast.makeText(LoginActivity.this, "INGRESE CORREO Y/O CONTRASEÑA", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class LoginUser extends AsyncTask <String,Void,UsuarioEntity>{
        @Override
        protected UsuarioEntity doInBackground(String... params) {
            String correo=params[0], clave=params[1];
            UsuarioEntity user;
            URLDaoInterface dao = new URLDaoImplement();
            user = dao.getRespuestaLogin(correo, clave);
            return user;
        }

        @Override
        protected void onPostExecute(UsuarioEntity usuario) {
            hideLoading();
            if(usuario != null) {

                if (usuario.getIsSuccess().equals("true")) {
                    UsuarioQuery usuarioQuery = new UsuarioQuery(LoginActivity.this);
                    usuario.setEstado(checkRecordame.equals("true")? Constantes.USUARIO_RECORDADO: Constantes.USUARIO_NO_RECORDADO);
                    usuarioQuery.insertarUpdateUsuario(usuario);

                    Intent intent = new Intent().setClass(LoginActivity.this, PrincipalActivity.class);
                    intent.putExtra(Constantes.USUARIO, usuario);
                    startActivity(intent);
                    finish();
                    Toast.makeText(LoginActivity.this, usuario.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, usuario.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Usted no se encuentra regsitrado", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(LoginActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permission)) {
                //ActivityCompat.requestPermissions(LoginActivity.this, new String[]{permission}, requestCode);
                /***** SI RECHAZO, DEVOLVEMOS aceptaGps=false para que vuelva a preguntar ****/
                if (requestCode == PERMISSIONS_REQUEST_CAMERA) aceptaCamera = false;
                if (requestCode == PERMISSIONS_REQUEST_AUDIO) aceptaAudio = false;
                if (requestCode == PERMISSIONS_REQUEST_CALL) aceptaCall = false;
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{permission}, requestCode);
            } else {
                /***** REALIZACION DE LA PREGUNTA ****/
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{permission}, requestCode);
                /***** Respuesta en  onRequestPermissionsResult ****/
            }
        } else {
            if (requestCode == PERMISSIONS_REQUEST_CAMERA){
                aceptaCamera = true;
            }
            if (requestCode == PERMISSIONS_REQUEST_AUDIO){
                aceptaAudio = true;
            }
            if (requestCode == PERMISSIONS_REQUEST_CALL){
                aceptaCall = true;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            /***** RESPUESTA DE: ¿PERMITE QUE SALUTEM24 TOME FOTOS Y GRABE VIDEOS? *****/
            case PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    aceptaCamera = true; //Log.e("CALL", "PERMITIDO");
                    askForPermission(Manifest.permission.RECORD_AUDIO, PERMISSIONS_REQUEST_AUDIO);
                } else {
                    aceptaCamera = false; //Log.e("CALL", "RECHAZADO");
                }
                break;
            }
            /***** RESPUESTA DE: ¿PERMITE QUE SALUTEM24 GRABE ARCHIVOS DE AUDIO? *****/
            case PERMISSIONS_REQUEST_AUDIO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    aceptaAudio = true; //Log.e("AUDIO", "ACEPTADO");
                    askForPermission(Manifest.permission.CALL_PHONE, PERMISSIONS_REQUEST_CALL);
                } else {
                    aceptaAudio = false; //Log.e("AUDIO", "RECHAZADO");
                }
                break;
            }
            /***** RESPUESTA DE: ¿PERMITE QUE SALUTEM REALICE Y GESTIONE LLAMADAS? *****/
            //PERMISO PARA ENVIAR MENSAJE Y OBTENER NUMERO
            case PERMISSIONS_REQUEST_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showLoading();
                    LoginUser login = new LoginUser();
                    login.execute(correo, contrasena);
                    aceptaCall = true; //Log.e("CALL", "PERMITIDO");
                } else {
                    aceptaCall = false; //Log.e("CALL", "RECHAZADO");
                }
                break;
            }
        }
    }

    private void showLoading() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        dialogo = new AlertDialog.Builder(this);
        dialogo.setMessage("Espera ...");
        alert = dialogo.create();
        alert = dialogo.show();
        alert.setCanceledOnTouchOutside(false);
    }

    private void hideLoading() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        alert.hide();
        alert.dismiss();
    }
}
