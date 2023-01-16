package zisac.com.pe.salutem24.entity;

public class ConsultaSesionEntity {
    public int consulta_id;
    public String sesion_sala;
    public String token_sala;

    public int isConsulta_id() {
        return consulta_id;
    }

    public void setConsulta_id(int consulta_id) {
        this.consulta_id = consulta_id;
    }

    public String getSesion_sala() {
        return sesion_sala;
    }

    public void setSesion_sala(String sesion_sala) {
        this.sesion_sala = sesion_sala;
    }

    public String getToken_sala() {
        return token_sala;
    }

    public void setToken_sala(String token_sala) {
        this.token_sala = token_sala;
    }
}
