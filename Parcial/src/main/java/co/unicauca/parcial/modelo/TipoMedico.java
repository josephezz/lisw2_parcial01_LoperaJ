package co.unicauca.parcial.modelo;

/**
 * Enum que representa los tipos de médico disponibles.
 * 
 * Principio OCP: para agregar un nuevo tipo de médico, basta con agregar
 * un nuevo valor al enum sin modificar la lógica existente.
 */
public enum TipoMedico {
    TIPO_1("Tipo 1"),
    TIPO_2("Tipo 2"),
    TIPO_3("Tipo 3");

    private final String etiqueta;

    TipoMedico(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Busca un TipoMedico por su etiqueta.
     *
     * @param etiqueta la etiqueta a buscar (ej. "Tipo 1")
     * @return el TipoMedico correspondiente
     * @throws IllegalArgumentException si no se encuentra la etiqueta
     */
    public static TipoMedico desdeEtiqueta(String etiqueta) {
        for (TipoMedico tipo : values()) {
            if (tipo.etiqueta.equalsIgnoreCase(etiqueta)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de médico no válido: " + etiqueta);
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
