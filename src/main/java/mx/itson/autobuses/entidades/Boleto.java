package mx.itson.autobuses.entidades;

public class Boleto {
    private int numeroAsiento;
    private String pasajero;

    public Boleto(int numeroAsiento, String pasajero) {
        this.numeroAsiento = numeroAsiento;
        this.pasajero = pasajero;
    }

    public int getNumeroAsiento() {
        return numeroAsiento;
    }

    public void setNumeroAsiento(int numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    public String getPasajero() {
        return pasajero;
    }

    public void setPasajero(String pasajero) {
        this.pasajero = pasajero;
    }

    @Override
    public String toString() {
        return "Boleto [Asiento: " + numeroAsiento + ", Pasajero: " + pasajero + "]";
    }
}
