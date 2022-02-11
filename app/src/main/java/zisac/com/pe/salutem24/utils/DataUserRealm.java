package zisac.com.pe.salutem24.utils;

import io.realm.RealmObject;

public class DataUserRealm  extends RealmObject {
    private String usuarioId;
    private String correo;
    private String password;

    public DataUserRealm() {
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
