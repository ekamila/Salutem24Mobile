package zisac.com.pe.salutem24.salutem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import zisac.com.pe.salutem24.R;
import zisac.com.pe.salutem24.dataBase.AnfitrionQuery;
import zisac.com.pe.salutem24.dataBase.MedicoQuery;
import zisac.com.pe.salutem24.dataBase.URLDaoImplement;
import zisac.com.pe.salutem24.dataBase.URLDaoInterface;
import zisac.com.pe.salutem24.dataBase.UsuarioQuery;
import zisac.com.pe.salutem24.entity.UsuarioEntity;
import zisac.com.pe.salutem24.utils.Constantes;
import zisac.com.pe.salutem24.utils.DataUserRealm;
import zisac.com.pe.salutem24.utils.Utils;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class Registro extends AppCompatActivity implements View.OnClickListener{
    EditText nombre, correo, contrasena, confirContrasena;
    Button btnRegistrar;
    private AlertDialog.Builder dialogo;
    private AlertDialog alert;
    private CheckBox cbTerminosCondiciones;
    private String terminoCondicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre = findViewById(R.id.etNombre);
        correo = findViewById(R.id.etCorreoRegistro);
        contrasena = findViewById(R.id.etContrasenaRegistro);
        confirContrasena = findViewById(R.id.etConfirmarContrasena);
        cbTerminosCondiciones = findViewById(R.id.cbTerminosCondiciones);

        btnRegistrar  = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);

        terminoCondicion="false";
        cbTerminosCondiciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                terminoCondicion = ""+b; //
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnRegistrar){
            String name = nombre.getText().toString().trim();
            String mail = correo.getText().toString().trim();
            String clave = contrasena.getText().toString().trim();
            String confirClave = confirContrasena.getText().toString().trim();

            if(!name.trim().equals("") && !mail.trim().equals("") && !clave.trim().equals("") && !confirClave.trim().equals("")) {
                if (!Utils.redOrWifiActivo(Registro.this)) {
                    Toast.makeText(Registro.this, "VERIFIQUE SU CONEXIÓN A LA RED", Toast.LENGTH_LONG).show();
                    return ;
                }
                if(clave.equals(confirClave)) {
                    if(terminoCondicion.equals("true")) {
                        showLoading();
                        ValidacionRegistro validacionRegistro = new ValidacionRegistro();
                        validacionRegistro.execute(name, mail, clave);
                    } else {
                        Toast.makeText(getBaseContext(), "Debe Aceptar Términos y Condiciones", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Confirmacion de contraseña incorrecto", Toast.LENGTH_LONG).show();
                }
            } else {
                if(!name.trim().equals("")){
                    if(!mail.trim().equals("")) {
                        if(!mail.trim().equals("")) {
                            if(confirClave.trim().equals("")) {
                                Toast.makeText(Registro.this, "INGRESE CONFIRMACIÓN CONTRASEÑA", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(Registro.this, "INGRESE CONTRASEÑA", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Registro.this, "INGRESE CORREO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Registro.this, "INGRESE NOMBRE", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public class ValidacionRegistro extends AsyncTask<String,Void, UsuarioEntity> {

        @Override
        protected UsuarioEntity doInBackground(String... params) {
            UsuarioEntity usuarioEntity = new UsuarioEntity();
            String nombre = params[0], mail = params[1], password = params[2];

            String url = Constantes.HTTP_CABECERA + Constantes.URL_VALIDAR_CUENTA + mail;
            Utils utils = new Utils();
            JSONObject jsonObject = utils.getJSONfromURL(url);

            String existe = utils.getValueStringOrNull(jsonObject, "isSuccess");

            if(!existe.equals("true")) {
                URLDaoInterface dao = new URLDaoImplement();
                usuarioEntity = dao.getRegistrarUsuario(nombre, mail, password);
                if(usuarioEntity!=null){
                    if(usuarioEntity.getIsSuccess().equals("true")){
                        UsuarioQuery usuarioQuery = new UsuarioQuery(Registro.this);
                        usuarioQuery.insertarUpdateUsuario(usuarioEntity);

                        MedicoQuery medicoQuery = new MedicoQuery(Registro.this);
                        medicoQuery.insertarMedico(usuarioEntity.getMedicoEntity());

                        AnfitrionQuery anfitrionQuery = new AnfitrionQuery(Registro.this);
                        anfitrionQuery.insertarAnfitrion(usuarioEntity.getAnfitrionEntity());
                    }
                } else {
                    usuarioEntity = new UsuarioEntity();
                    usuarioEntity.setMessage("Ocurrio un error vuelva a intentar");
                }
            } else {
                usuarioEntity.setMessage("Usted ya se encuentra registrado");
            }
            return usuarioEntity;
        }

        @Override
        protected void onPostExecute(UsuarioEntity usuario) {
            //super.onPostExecute(aVoid);
            hideLoading();
            if(usuario != null) {
                if (usuario.getIsSuccess().equals("true")) {
                    Intent intent = new Intent().setClass(Registro.this, PrincipalActivity.class);
                    intent.putExtra(Constantes.USUARIO, usuario);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getBaseContext(), usuario.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), usuario.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getBaseContext(), "Usted ya se encuentra registrado", Toast.LENGTH_LONG).show();
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
