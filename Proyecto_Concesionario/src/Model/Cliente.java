package Model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    //Atributos de un Cliente
    private String nombreCliente;
    private String dniCliente;
    private String telefonoCliente;

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


    //constructor de un Cliente

    public Cliente(String nombreCliente, String dniCliente, String telefonoCliente) {
        this.nombreCliente = nombreCliente;
        this.dniCliente = dniCliente;
        this.telefonoCliente = telefonoCliente;

    }
}
