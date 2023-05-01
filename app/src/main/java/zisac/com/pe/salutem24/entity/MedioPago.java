package zisac.com.pe.salutem24.entity;

public class MedioPago {
    String descripcion;
    int imagen;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public MedioPago(String descripcion, int imagen) {
        this.descripcion = descripcion;
        this.imagen = imagen;
    }
}
