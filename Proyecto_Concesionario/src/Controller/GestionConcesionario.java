package Controller;

import Model.Cliente;
import Model.Coche;
import Model.Vendedor;
import Model.Venta;
import View.MenuView;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.time.ZoneOffset.UTC;

public class GestionConcesionario {

    public List<Coche> listaCoches = new ArrayList<>();
    public List<Cliente> listaClientes = new ArrayList<>();
    public List<Vendedor> listaVendedores = new ArrayList<>();
    public List<Venta> listaVentas = new ArrayList<>();
    public List<Object> listaVentaModificada = new ArrayList<>();

    private MenuView view;

    Scanner sc = new Scanner(System.in);

    public GestionConcesionario(MenuView view) {
        this.view = view;
        loadListaCoches();
        loadListaClientes();
        loadListaVentas();
        loadListaVendedores();
    }

    public boolean anhadirCoche(Coche nuevoCoche) {
        //comprobar si el coche es valido
        boolean nuevaMatricula = false;
        String matricula = nuevoCoche.getMatriculaCoche();

        for (Coche coche : listaCoches) {
            nuevaMatricula = true;
            if (coche.getMatriculaCoche().equals(matricula)) {
                nuevaMatricula = false;
                break;
            }
        }
        //si valido add a la lista
        if (nuevaMatricula) {
            listaCoches.add(nuevoCoche);
        }
        //si no valido return false;
        return nuevaMatricula;
    }

    private boolean registrarCliente(Cliente nuevoCliente) {
        //comprobar si el cliente ya existe
        boolean nuevoDni = false;
        String dni = nuevoCliente.getDniCliente();

        for (Cliente cliente : listaClientes) {
            nuevoDni = true;
            if (cliente.getDniCliente().equals(dni)) {
                nuevoDni = false;
                break;
            }
        }
        //si el cliente es nuevo lo añadimos
        if (nuevoDni) {
            listaClientes.add(nuevoCliente);
        }
        //si no es cliente nuevo return false;
        return nuevoDni;
    }

    public void run() {

        int opcion;
        MenuView newMenu = new MenuView();

        while (true) {

            opcion = newMenu.menuPrincipal();

            if (opcion == 1) {
                Coche nuevoCoche = view.menuAnhadirCoche(listaCoches);
                boolean nuevaMatricula = anhadirCoche(nuevoCoche);
                if (!nuevaMatricula) view.mostrarErrorCoche();
            }
            if (opcion == 2) {
                view.pintarTablaCochesEnVenta(listaCoches);

            }
            if (opcion == 3) {
                view.buscarCoches(listaCoches);
            }
            if (opcion == 4) {
                Cliente nuevoCliente = view.menuRegistrarCliente(listaClientes);
                boolean nuevoDni = registrarCliente(nuevoCliente);
                if (!nuevoDni) view.mostrarErrorCliente();

            }
            if (opcion == 5) {
                Venta nuevaVenta = view.menuRegistrarVenta(listaVentas);
                boolean ventaValida = true;
                //comprobar si existe cliente
                boolean dniValido = comprobarCliente(nuevaVenta);
                if (!dniValido){
                    view.mostrarErrorClienteInvalido();
                    ventaValida = false;
                }
                //comprobar si existe coche
                boolean matriculaValida = comprobarCoche(nuevaVenta);
                if (!matriculaValida) {
                    view.mostrarErrorCocheInvalido();
                    ventaValida = false;
                }
                //comprobar si existe vendedor
                boolean vendedorValido = comprobarVendedor(nuevaVenta);
                if (!vendedorValido){
                    view.mostrarErrorVendedorInvalido();
                    ventaValida = false;
                }

                if (ventaValida) {
                    int nuevoIndex = listaVentas.size() + 1;
                    nuevaVenta.setIdVenta(nuevoIndex);
                    listaVentas.add(nuevaVenta);
                }
            }
            if (opcion == 6) {

                List<Object> listaVentasCompleta = List.of();
                //List<Object> listaVentaUnica = null;
                for (Venta venta : listaVentas) {

                    ArrayList<Object> listaVentasUnica = new ArrayList<>();
                    listaVentasUnica.add(venta.getIdVenta());
                    listaVentasUnica.add(obtenerNombreCliente(venta, listaClientes));
                    listaVentasUnica.add(obtenerCocheVenta(venta, listaCoches));
                    listaVentasUnica.add(venta.getMatriculaCoche());
                    String fechaTexto = convertirFecha(venta.getFechaVenta());
                    listaVentasUnica.add(fechaTexto);
                    listaVentasUnica.add(venta.getPrecioVenta());
                    listaVentasCompleta.add(listaVentasUnica);
                    //view.pintarTablaVentas(listaVentasCompleta);
                    view.mostrarListaVentas(listaVentasCompleta);
                }
                view.pintarTablaVentas(listaVentasCompleta);

            }
            if (opcion == 7) {
                view.mostrarOrdenarCoches(listaCoches);
            }
            if (opcion == 8) {
                List<Object> datosEstadisticos = new ArrayList<>();
                int idVendedorBuscado = view.menuElegirVendedor(listaVendedores);
                String nombreVendedorBuscado = buscarNombreVendedor(idVendedorBuscado);
                datosEstadisticos.add(nombreVendedorBuscado);
                datosEstadisticos.add(calcularTotalCochesVendidos(idVendedorBuscado));
                datosEstadisticos.add(calcularTotalVentasVendedor(idVendedorBuscado));
                datosEstadisticos.add(calcularPrecioMedioCoche(idVendedorBuscado));
                Coche cocheMasCaro = calcularCocheMasCaro(idVendedorBuscado);
                datosEstadisticos.add(cocheMasCaro.getMarcaCoche());
                datosEstadisticos.add(cocheMasCaro.getModeloCoche());
                datosEstadisticos.add(cocheMasCaro.getMatriculaCoche());
                datosEstadisticos.add(cocheMasCaro.getPrecioCoche());

                view.mostrarEstadisticasVendedor(datosEstadisticos);
            }
            if (opcion == 9) {
                break;
            }
            pulsarParaContinuar();

        }
    }

    private boolean comprobarVendedor(Venta nuevaVenta) {
        //comprobar si el vendedor existe
        boolean vendedorValido = false;
        int idVendedor = nuevaVenta.getIdVendedor();
        for (Vendedor vendedor : listaVendedores){
            if (vendedor.getIdVendedor() == idVendedor){
                vendedorValido = true;
                break;
            }
        }
        return vendedorValido;
    }

    private boolean comprobarCliente(Venta venta) {
        //comprobar si el cliente existe
        boolean dniValido = false;
        String dniCliente = venta.getDniCliente();
        for (Cliente cliente : listaClientes){
            if (cliente.getDniCliente().equals(dniCliente)){
                dniValido = true;
                break;
            }
        }
        return dniValido;
    }

    private boolean comprobarCoche(Venta venta) {
        //comprobar si el coche existe y está a la venta
        //boolean matriculaExiste = false;
        //boolean cocheEnVenta = false;
        boolean matriculaValida = false;
        String matricula = venta.getMatriculaCoche();
        for (Coche coche : listaCoches) {
            if (coche.getMatriculaCoche().equals(matricula) && !coche.isCocheVendido()) {
                matriculaValida = true;
                break;
            }
        }
        return matriculaValida;
    }

    private String convertirFecha(ZonedDateTime fechaVenta) {

        LocalDate zonedToLocalDate = fechaVenta.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        String fechaTexto = zonedToLocalDate.format(formatter);

        return fechaTexto;
    }

    private String obtenerCocheVenta(Venta venta, List<Coche> listaCoches) {
        String cocheVenta = "";
        String matriculaCoche = venta.getMatriculaCoche();
        for (Coche coche : listaCoches) {
            if (coche.getMatriculaCoche().equals(matriculaCoche)) {
                cocheVenta = coche.getMarcaCoche() + " " + coche.getModeloCoche();
                break;
            }
        }
        return cocheVenta;
    }

    private String obtenerNombreCliente(Venta venta, List<Cliente> listaClientes) {
        String nombreCliente = "";
        String dniCliente = venta.getDniCliente();
        for (Cliente cliente : listaClientes) {
            if (cliente.getDniCliente().equals(dniCliente)) {
                nombreCliente = cliente.getNombreCliente();
                break;
            }
        }
        return nombreCliente;
    }

    public void pulsarParaContinuar() {
        System.out.println("Presiona ENTER para continuar");
        Scanner se = new Scanner(System.in);
        se.nextLine();
    }

    private String buscarNombreVendedor(int idVendedorBuscado) {
        String nombreVendedorBuscado = null;
        for (Vendedor vendedor : listaVendedores) {
            if (vendedor.getIdVendedor() == idVendedorBuscado) {
                nombreVendedorBuscado = vendedor.getNombreVendedor();
            }
        }
        return nombreVendedorBuscado;
    }

    private Coche calcularCocheMasCaro(int idVendedor) {
        float mayorVenta = 0;
        String matriculaCocheVendido = null;
        Coche cocheMasCaro = null;
        for (Venta venta : listaVentas) {
            if (venta.getIdVendedor() == idVendedor && venta.getPrecioVenta() >= mayorVenta) {
                mayorVenta = venta.getPrecioVenta();
                matriculaCocheVendido = venta.getMatriculaCoche();
            }
        }
        for (Coche coche : listaCoches) {
            if (coche.getMatriculaCoche().equals(matriculaCocheVendido)) {
                cocheMasCaro = coche;
            }
        }

        return cocheMasCaro;
    }

    private int calcularTotalCochesVendidos(int idVendedor) {
        int numeroCochesVendidos = 0;
        for (Venta venta : listaVentas) {
            if (venta.getIdVendedor() == idVendedor) {
                numeroCochesVendidos++;
            }
        }
        return numeroCochesVendidos;
    }

    private float calcularTotalVentasVendedor(int idVendedor) {
        float ventasTotales = 0;
        for (Venta venta : listaVentas) {
            if (venta.getIdVendedor() == idVendedor) {
                ventasTotales = ventasTotales + venta.getPrecioVenta();
            }
        }
        return ventasTotales;
    }

    public float calcularPrecioMedioCoche(int idVendedor) {
        float precioTotal = 0;
        int numeroVentas = 0;
        for (Venta venta : listaVentas) {
            if (venta.getIdVendedor() == idVendedor) {
                precioTotal = precioTotal + venta.getPrecioVenta();
                numeroVentas++;
            }
        }
        float precioMedio = Math.round(precioTotal / numeroVentas);
        return precioMedio;
    }

    private void loadListaCoches() {
        this.listaCoches = new ArrayList<Coche>();
        listaCoches.add(new Coche("Seat", "Ibiza", 2018, 580000, 12000, "1234ABC", true));
        listaCoches.add(new Coche("Volkswagen", "Golf", 2020, 360000, 18000, "5678DEF", false));
        listaCoches.add(new Coche("Renault", "Clio", 2019, 403000, 13000, "9101GHI", false));
        listaCoches.add(new Coche("Fiat", "Punto", 2017, 607000, 9000, "2345JKL", true));
        listaCoches.add(new Coche("Seat", "León", 2021, 290000, 20000, "6789MNO", false));
        listaCoches.add(new Coche("Volkswagen", "Polo", 2018, 415000, 14000, "3456PQR", true));
        listaCoches.add(new Coche("Renault", "Mégane", 2020, 265000, 16000, "7890STU", false));
        listaCoches.add(new Coche("Fiat", "Panda", 2016, 704000, 8000, "4567VWX", true));
        listaCoches.add(new Coche("Volkswagen", "Passat", 2021, 156000, 22000, "1234BCD", false));
        listaCoches.add(new Coche("Renault", "Captur", 2018, 472000, 13500, "5678EFG", true));
        listaCoches.add(new Coche("Fiat", "Panda", 2017, 553000, 9500, "9101HIJ", true));
        listaCoches.add(new Coche("Seat", "Arona", 2020, 282000, 17000, "2345KLM", false));
        listaCoches.add(new Coche("Volkswagen", "Tiguan", 2019, 321000, 19500, "6789NOP", true));
        listaCoches.add(new Coche("Renault", "Kadjar", 2021, 198000, 21000, "3456QRS", false));
        listaCoches.add(new Coche("Seat", "Ateca", 2019, 345000, 19000, "8901YZA", false));

    }

    private void loadListaClientes() {
        this.listaClientes = new ArrayList<Cliente>();
        listaClientes.add(new Cliente("Luis Martinez Montes", "11111111A", "+346784152"));
        listaClientes.add(new Cliente("Juan Pérez García", "22222222B", "+34612345678"));
        listaClientes.add(new Cliente("María López Rodríguez", "33333333C", "+34687654321"));
        listaClientes.add(new Cliente("Carlos Gómez Fernández", "44444444D", "+34623456789"));
        listaClientes.add(new Cliente("Ana Sánchez Martínez", "55555555E", "+34698765432"));
        listaClientes.add(new Cliente("Javier Fernández Martínez", "66666666F", "+34634567890"));
        listaClientes.add(new Cliente("Laura García López", "77777777G", "+34645678901"));
    }

    private void loadListaVentas() {
        this.listaVentas = new ArrayList<Venta>();
        listaVentas.add(new Venta(1, "11111111A", "1234ABC", ZonedDateTime.of(2023, 12, 5, 0, 0, 0, 0, UTC), 12000, 1));
        listaVentas.add(new Venta(2, "22222222B", "2345JKL", ZonedDateTime.of(2024, 2, 10, 0, 0, 0, 0, UTC), 9000, 2));
        listaVentas.add(new Venta(3, "33333333C", "3456PQR", ZonedDateTime.of(2024, 2, 18, 0, 0, 0, 0, UTC), 14000, 3));
        listaVentas.add(new Venta(4, "44444444D", "4567VWX", ZonedDateTime.of(2022, 8, 3, 0, 0, 0, 0, UTC), 8000, 1));
        listaVentas.add(new Venta(5, "55555555E", "5678EFG", ZonedDateTime.of(2024, 7, 21, 0, 0, 0, 0, UTC), 13500, 2));
        listaVentas.add(new Venta(6, "66666666F", "9101HIJ", ZonedDateTime.of(2023, 10, 18, 0, 0, 0, 0, UTC), 9500, 3));
        listaVentas.add(new Venta(7, "77777777G", "6789NOP", ZonedDateTime.of(2022, 3, 28, 0, 0, 0, 0, UTC), 19500, 1));

    }

    private void loadListaVendedores() {
        this.listaVendedores = new ArrayList<Vendedor>();
        listaVendedores.add(new Vendedor("Mateo Torres", 1));
        listaVendedores.add(new Vendedor("Teresa Gutierrez", 2));
        listaVendedores.add(new Vendedor("Carlos Cazorla", 3));

    }

    /*
        LocalDate localDate = LocalDate.parse("2017-07-22");
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());

        //to LocalDate
        LocalDate zonedToLocalDate = zonedDateTime.toLocalDate();
        System.out.println(zonedToLocalDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        String text = zonedToLocalDate.format(formatter);
        //LocalDate parsedDate = LocalDate.parse(text, formatter);
        System.out.println(text);


        // create a ZonedDateTime object
        ZonedDateTime zonedDT
                = ZonedDateTime.parse("2018-10-25T23:12:31.123+02:00[Europe/Paris]");

        // apply get() method
        int year = zonedDT.get(ChronoField.YEAR);

        // print result
        System.out.println("Value: " + year);

        Month month = zonedDT.getMonth();
        System.out.println(month);
        */
        /*
        import java.util.ArrayList;
        import java.util.List;

        public class EjemploListaCompuesta {
        public static void main(String[] args) {
            List<List<String>> listaCompuesta = new ArrayList<>();

            List<String> lista1 = new ArrayList<>();
            lista1.add("Elemento 1A");
            lista1.add("Elemento 1B");

            List<String> lista2 = new ArrayList<>();
            lista2.add("Elemento 2A");
            lista2.add("Elemento 2B");

            listaCompuesta.add(lista1);
            listaCompuesta.add(lista2);

            for (List<String> lista : listaCompuesta) {
                for (String elemento : lista) {
                    System.out.println(elemento);
                }
            }
        }*/
}
