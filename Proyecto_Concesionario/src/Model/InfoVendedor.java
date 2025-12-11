package Model;

public class InfoVendedor {
    //atributos
    private int idVendedor;
    private String nombreVendedor;
    private int numeroCochesVendidos;
    private float totalVentas;
    private float precioMedioCoche;
    private String marcaCocheMasCaro;
    private String modeloCocheMasCaro;
    private String matriculaCocheMasCaro;
    private float precioCocheMasCaro;

    //getters y setters


    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public int getNumeroCochesVendidos() {
        return numeroCochesVendidos;
    }

    public void setNumeroCochesVendidos(int numeroCochesVendidos) {
        this.numeroCochesVendidos = numeroCochesVendidos;
    }

    public float getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(float totalVentas) {
        this.totalVentas = totalVentas;
    }

    public float getPrecioMedioCoche() {
        return precioMedioCoche;
    }

    public void setPrecioMedioCoche(float precioMedioCoche) {
        this.precioMedioCoche = precioMedioCoche;
    }

    public String getMarcaCocheMasCaro() {
        return marcaCocheMasCaro;
    }

    public void setMarcaCocheMasCaro(String marcaCocheMasCaro) {
        this.marcaCocheMasCaro = marcaCocheMasCaro;
    }

    public String getModeloCocheMasCaro() {
        return modeloCocheMasCaro;
    }

    public void setModeloCocheMasCaro(String modeloCocheMasCaro) {
        this.modeloCocheMasCaro = modeloCocheMasCaro;
    }

    public String getMatriculaCocheMasCaro() {
        return matriculaCocheMasCaro;
    }

    public void setMatriculaCocheMasCaro(String matriculaCocheMasCaro) {
        this.matriculaCocheMasCaro = matriculaCocheMasCaro;
    }

    public float getPrecioCocheMasCaro() {
        return precioCocheMasCaro;
    }

    public void setPrecioCocheMasCaro(float precioCocheMasCaro) {
        this.precioCocheMasCaro = precioCocheMasCaro;
    }

    //constructor

    public InfoVendedor(int idVendedor, String nombreVendedor, int numeroCochesVendidos, float totalVentas, float precioMedioCoche, String marcaCocheMasCaro, String modeloCocheMasCaro, String matriculaCocheMasCaro, float precioCocheMasCaro) {
        this.idVendedor = idVendedor;
        this.nombreVendedor = nombreVendedor;
        this.numeroCochesVendidos = numeroCochesVendidos;
        this.totalVentas = totalVentas;
        this.precioMedioCoche = precioMedioCoche;
        this.marcaCocheMasCaro = marcaCocheMasCaro;
        this.modeloCocheMasCaro = modeloCocheMasCaro;
        this.matriculaCocheMasCaro = matriculaCocheMasCaro;
        this.precioCocheMasCaro = precioCocheMasCaro;
    }
}
