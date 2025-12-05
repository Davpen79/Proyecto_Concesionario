package Model;

import java.time.LocalDate;
import java.util.Date;

public class Venta {

    //atributos de una Venta
    private int idVenta;
    private String dniCliente;
    private String matriculaCoche;
    private LocalDate fechaVenta;
    private double precioVenta;
    //private Cliente clienteVenta;
    //private Coche cocheVendido;
    //private Vendedor vendedorVenta;

    //getters de Venta


    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public String getMatriculaCoche() {
        return matriculaCoche;
    }

    public int getIdVenta() {

        return idVenta;
    }


    //setters de Venta


    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public void setMatriculaCoche(String matriculaCoche) {
        this.matriculaCoche = matriculaCoche;
    }

    public void setIdVenta(int idVenta) {

        this.idVenta = idVenta;
    }

    //constructor de una Venta

    public Venta(int idVenta, String dniCliente, String matriculaCoche, LocalDate fechaVenta, double precioVenta) {
        this.idVenta = idVenta;
        this.dniCliente = dniCliente;
        this.matriculaCoche = matriculaCoche;
        this.fechaVenta = fechaVenta;
        this.precioVenta = precioVenta;
    }
}
