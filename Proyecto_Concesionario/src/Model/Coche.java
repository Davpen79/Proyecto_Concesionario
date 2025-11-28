package Model;

import java.util.Date;

public class Coche {

    //Atributos de la clase Coche
    private String marcaCoche;
    private String modeloCoche;
    private int anhoCoche;
    private double kilometrosCoche;
    private double precioCoche;
    private String matriculaCoche;
    private boolean cocheVendido;

    //getters de Coche

    public boolean isCocheVendido() {
        return cocheVendido;
    }

    public String getMarcaCoche() {
        return marcaCoche;
    }

    public String getModeloCoche() {
        return modeloCoche;
    }

    public int getAnhoCoche() {
        return anhoCoche;
    }

    public double getKilometrosCoche() {
        return kilometrosCoche;
    }

    public double getPrecioCoche() {
        return precioCoche;
    }

    public String getMatriculaCoche() {
        return matriculaCoche;
    }

    //setters de Coche

    public void setPrecioCoche(double precioCoche) {
        this.precioCoche = precioCoche;
    }

    public void setCocheVendido(boolean cocheVendido) {
        this.cocheVendido = cocheVendido;
    }

    public void setMarcaCoche(String marcaCoche) {
        this.marcaCoche = marcaCoche;
    }

    public void setModeloCoche(String modeloCoche) {
        this.modeloCoche = modeloCoche;
    }

    public void setAnhoCoche(int anhoCoche) {
        this.anhoCoche = anhoCoche;
    }

    public void setKilometrosCoche(double kilometrosCoche) {
        this.kilometrosCoche = kilometrosCoche;
    }

    public void setMatriculaCoche(String matriculaCoche) {
        this.matriculaCoche = matriculaCoche;
    }

    //Constructor de Coche

    public Coche(String marcaCoche, String modeloCoche, int anhoCoche, double kilometrosCoche, double precioCoche, String matriculaCoche, boolean cocheVendido) {
        this.marcaCoche = marcaCoche;
        this.modeloCoche = modeloCoche;
        this.anhoCoche = anhoCoche;
        this.kilometrosCoche = kilometrosCoche;
        this.precioCoche = precioCoche;
        this.matriculaCoche = matriculaCoche;
        this.cocheVendido = cocheVendido;
    }

}
