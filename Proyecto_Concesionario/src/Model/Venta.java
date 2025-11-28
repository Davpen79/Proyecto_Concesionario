package Model;

import java.util.Date;

public class Venta {

    //atributos de una Venta
    private int idVenta;
    private Cliente clienteVenta;
    private Coche cocheVendido;
    private Date fechaVenta;
    private double precioVenta;
    private Vendedor vendedorVenta;

    //getters de Venta

    public Vendedor getVendedorVenta() {
        return vendedorVenta;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public Cliente getClienteVenta() {
        return clienteVenta;
    }

    public Coche getCocheVendido() {
        return cocheVendido;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    //setters de Venta

    public void setVendedorVenta(Vendedor vendedorVenta) {
        this.vendedorVenta = vendedorVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public void setClienteVenta(Cliente clienteVenta) {
        this.clienteVenta = clienteVenta;
    }

    public void setCocheVendido(Coche cocheVendido) {
        this.cocheVendido = cocheVendido;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public void setPrecioVenta(int precioVenta) {
        this.precioVenta = precioVenta;
    }

    //constructor de una Venta


    public Venta(int idVenta, Cliente clienteVenta, Coche cocheVendido, Date fechaVenta, int precioVenta, Vendedor vendedorVenta) {
        this.idVenta = idVenta;
        this.clienteVenta = clienteVenta;
        this.cocheVendido = cocheVendido;
        this.fechaVenta = fechaVenta;
        this.precioVenta = precioVenta;
        this.vendedorVenta = vendedorVenta;
    }
}
