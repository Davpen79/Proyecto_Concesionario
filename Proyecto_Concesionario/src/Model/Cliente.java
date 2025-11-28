package Model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    //Atributos de un Cliente
    private String nombreCliente;
    private String dniCliente;
    private List<String> telefonoCliente;
    private List<Coche> cocheCliente;

    //getters de un Cliente

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public List<String> getTelefonoCliente() {
        return telefonoCliente;
    }

    public List<Coche> getCocheCliente() {
        return cocheCliente;
    }

    //setters de un Cliente

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public void setTelefonoCliente(ArrayList<String> telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public void setCocheCliente(ArrayList<Coche> cocheCliente) {
        this.cocheCliente = cocheCliente;
    }

    //constructor de un Cliente

    public Cliente(String nombreCliente, String dniCliente, ArrayList<String> telefonoCliente, ArrayList<Coche> cocheCliente) {
        this.nombreCliente = nombreCliente;
        this.dniCliente = dniCliente;
        this.telefonoCliente = telefonoCliente;
        this.cocheCliente = cocheCliente;
    }
}
