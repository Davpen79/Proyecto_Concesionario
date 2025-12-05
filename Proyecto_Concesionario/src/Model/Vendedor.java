package Model;

import java.util.ArrayList;
import java.util.List;

public class Vendedor {

    //Atributos de un Vendedor
    private String nombreVendedor;
    private int idVendedor;
    //private List<Venta> ventaEfectuada;

    //getters de un Vendedor

   //public List<Venta> getVentaEfectuada() {
   //    return ventaEfectuada;
   //}

    public int getIdVendedor() {
        return idVendedor;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    //setters de un vendedor

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    //public void setVentaEfectuada(ArrayList<Venta> ventaEfectuada) {
    //    this.ventaEfectuada = ventaEfectuada;
    //}

    //constructor de un Vendedor

    public Vendedor(String nombreVendedor, int idVendedor) {
        this.nombreVendedor = nombreVendedor;
        this.idVendedor = idVendedor;
        //this.ventaEfectuada = ventaEfectuada;

    }
}
