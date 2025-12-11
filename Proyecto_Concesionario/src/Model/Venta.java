package Model;

import java.time.ZonedDateTime;

public class Venta {

    //atributos de una Venta
    private int idVenta;
    private String dniCliente;
    private String matriculaCoche;
    private ZonedDateTime fechaVenta;
    private float precioVenta;
    private int idVendedor;

    //getters de Venta


    public int getIdVendedor() {
        return idVendedor;
    }

    public ZonedDateTime getFechaVenta() {
        return fechaVenta;
    }

    public float getPrecioVenta() {
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


    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public void setFechaVenta(ZonedDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public void setPrecioVenta(float precioVenta) {
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


    public Venta(int idVenta, String dniCliente, String matriculaCoche, ZonedDateTime fechaVenta, float precioVenta, int idVendedor) {
        this.idVenta = idVenta;
        this.dniCliente = dniCliente;
        this.matriculaCoche = matriculaCoche;
        this.fechaVenta = fechaVenta;
        this.precioVenta = precioVenta;
        this.idVendedor = idVendedor;
    }
}
