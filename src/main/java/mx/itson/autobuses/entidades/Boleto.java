package mx.itson.autobuses.entidades;

/**
 * Clase Boleto que representa un boleto de autobús.
 * Un boleto está asociado a un número de asiento y a un pasajero.
 */
public class Boleto {
    private int numeroAsiento; // Número del asiento asignado
    private String pasajero;    // Nombre del pasajero

    /**
     * Constructor de la clase Boleto.
     * 
     * @param numeroAsiento El número del asiento asignado al boleto.
     * @param pasajero El nombre del pasajero que ocupa el asiento.
     */
    public Boleto(int numeroAsiento, String pasajero) {
        this.numeroAsiento = numeroAsiento;
        this.pasajero = pasajero;
    }

    /**
     * Obtiene el número de asiento del boleto.
     * 
     * @return El número de asiento asignado al boleto.
     */
    public int getNumeroAsiento() {
        return numeroAsiento;
    }
    
    /**
     * Establece un nuevo número de asiento para el boleto.
     * 
     * @param numeroAsiento El nuevo número de asiento a asignar.
     */
    public void setNumeroAsiento(int numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    /**
     * Obtiene el nombre del pasajero asociado al boleto.
     * 
     * @return El nombre del pasajero.
     */
    public String getPasajero() {
        return pasajero;
    }

    /**
     * Establece un nuevo nombre de pasajero para el boleto.
     * 
     * @param pasajero El nuevo nombre del pasajero a asignar.
     */
    public void setPasajero(String pasajero) {
        this.pasajero = pasajero; // Asigna el nuevo nombre del pasajero
    }

    /**
     * Devuelve una representación en forma de cadena del boleto.
     * 
     * @return Una cadena que representa el boleto, incluyendo el número de asiento y el nombre del pasajero.
     */
    @Override
    public String toString() {
        return "Boleto [Asiento: " + numeroAsiento + ", Pasajero: " + pasajero + "]";
    }
}
