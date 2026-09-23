package co.unicauca.parcial.modelo;

/**
 * Entidad de dominio que representa un Médico.
 * 
 * Principio SRP: esta clase solo tiene la responsabilidad de
 * almacenar los datos del médico (POJO).
 */
public class Medico {

    private int id;
    private String nombre;
    private String apellido;
    private TipoMedico tipo;
    private boolean atiendeSiempre;

    public Medico() {
    }

    public Medico(int id, String nombre, String apellido, TipoMedico tipo, boolean atiendeSiempre) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipo = tipo;
        this.atiendeSiempre = atiendeSiempre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public TipoMedico getTipo() {
        return tipo;
    }

    public void setTipo(TipoMedico tipo) {
        this.tipo = tipo;
    }

    public boolean isAtiendeSiempre() {
        return atiendeSiempre;
    }

    public void setAtiendeSiempre(boolean atiendeSiempre) {
        this.atiendeSiempre = atiendeSiempre;
    }

    @Override
    public String toString() {
        return "Medico{id=" + id
                + ", nombre='" + nombre + '\''
                + ", apellido='" + apellido + '\''
                + ", tipo=" + tipo
                + ", atiendeSiempre=" + (atiendeSiempre ? "Sí" : "No")
                + '}';
    }
}
