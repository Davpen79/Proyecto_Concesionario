package Model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    //Atributos de un Cliente
    private String nombreCliente;
    private String dniCliente;
    private String telefonoCliente;
    //private List<Coche> cocheCliente; // complejidad innecesaria??

    //getters de un Cliente

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    //public List<Coche> getCocheCliente() {
    //    return cocheCliente;
    //}

    //setters de un Cliente

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    //public void setCocheCliente(ArrayList<Coche> cocheCliente) {
    //    this.cocheCliente = cocheCliente;
    //}

    //constructor de un Cliente

    public Cliente(String nombreCliente, String dniCliente, String telefonoCliente) {
        this.nombreCliente = nombreCliente;
        this.dniCliente = dniCliente;
        this.telefonoCliente = telefonoCliente;
        //this.cocheCliente = cocheCliente;
    }
}
