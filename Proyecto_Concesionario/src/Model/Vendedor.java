package Model;

import java.util.ArrayList;
import java.util.List;

public class Vendedor {

    //Atributos de un Vendedor
    private String nombreVendedor;
    private List<Venta> ventaEfectuada;

    //getters de un Vendedor

    public List<Venta> getVentaEfectuada() {
        return ventaEfectuada;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    //setters de un vendedor

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public void setVentaEfectuada(ArrayList<Venta> ventaEfectuada) {
        this.ventaEfectuada = ventaEfectuada;
    }

    //constructor de un Vendedor

    public Vendedor(String nombreVendedor, List<Venta> ventaEfectuada) {
        this.nombreVendedor = nombreVendedor;
        this.ventaEfectuada = ventaEfectuada;
    }
}
