package Controller;

import Model.*;
import View.MenuView;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import static java.time.ZoneOffset.UTC;

public class GestionConcesionario {

    enum opcionesMenuPrincipal {ANHADIR_COCHE, VER_COCHES_EN_VENTA, BUSCAR_COCHE, REGISTRAR_CLIENTE,
                                REGISTRAR_VENTA, VER_VENTAS, VER_COCHES_ORDEN, VER_STATS_VENDEDORES, SALIR}
    public List<Coche> listaCoches = new ArrayList<>();
    public List<Cliente> listaClientes = new ArrayList<>();
    public List<Vendedor> listaVendedores = new ArrayList<>();
    public List<Venta> listaVentas = new ArrayList<>();

    private MenuView view;

    /**
     * Crea una vista y carga en memoria listas de Coches, Clientes, Ventas y Vendedores
     *
     * @param view
     */
    public GestionConcesionario(MenuView view) {
        this.view = view;
        loadListaCoches();
        loadListaClientes();
        loadListaVentas();
        loadListaVendedores();
    }

    /**
     * Funcion que controla todos los procesos del programa
     */
    public void run() {

        int opcion;
        MenuView newMenu = new MenuView();

        while (true) {

            opcion = newMenu.menuPrincipal();
            opcionesMenuPrincipal opcionMenu = opcionesMenuPrincipal.values()[opcion];

            if (opcionMenu == opcionesMenuPrincipal.ANHADIR_COCHE) {
                Coche nuevoCoche = view.menuAnhadirCoche();
                boolean nuevaMatricula = anhadirCoche(nuevoCoche, listaCoches);
                if (!nuevaMatricula) view.mostrarErrorCoche();
            }
            if (opcionMenu == opcionesMenuPrincipal.VER_COCHES_EN_VENTA) {
                view.pintarTablaCochesEnVenta(listaCoches);

            }
            if (opcionMenu == opcionesMenuPrincipal.BUSCAR_COCHE) {
                view.buscarCoches(listaCoches);
            }
            if (opcionMenu == opcionesMenuPrincipal.REGISTRAR_CLIENTE) {
                Cliente nuevoCliente = view.menuRegistrarCliente();
                boolean clienteValido = comprobarClienteValido(nuevoCliente);
                if (!clienteValido) view.mostrarErrorDatoEnBlanco();
                boolean nuevoDni = registrarCliente(nuevoCliente, listaClientes);
                if (!nuevoDni) view.mostrarErrorCliente();

            }
            if (opcionMenu == opcionesMenuPrincipal.REGISTRAR_VENTA) {
                Venta nuevaVenta = view.menuRegistrarVenta();
                boolean ventaValida = true;
                //comprobar si existe cliente
                boolean dniValido = comprobarCliente(nuevaVenta, listaClientes);
                if (!dniValido) {
                    view.mostrarErrorClienteInvalido();
                    ventaValida = false;
                }
                //comprobar si existe coche
                boolean matriculaValida = comprobarCoche(nuevaVenta, listaCoches);
                if (!matriculaValida) {
                    view.mostrarErrorCocheInvalido();
                    ventaValida = false;
                }
                //comprobar si existe vendedor
                boolean vendedorValido = comprobarVendedor(nuevaVenta, listaVendedores);
                if (!vendedorValido) {
                    view.mostrarErrorVendedorInvalido();
                    ventaValida = false;
                }

                if (ventaValida) {
                    //int nuevoIndex = listaVentas.size() + 1; Código Antiguo. Id dependía del tamaño de la lista
                    int nuevoIndex;
                    if (listaVentas.isEmpty()) {
                        nuevoIndex = 1; //Comprobamos si la lista está vacía. Si lo está el Id de la nueva venta es 1.
                    }
                    else {
                        nuevoIndex = listaVentas.getLast().getIdVenta() + 1;
                    } // Ahora el Id no depende del tamaño. Siempre se incrementa y no se puede repetir
                    nuevaVenta.setIdVenta(nuevoIndex);
                    listaVentas.add(nuevaVenta);
                }
            }
            if (opcionMenu == opcionesMenuPrincipal.VER_VENTAS) {

                view.pintarCabeceraTablaVentas();
                for (Venta venta : listaVentas) {

                    List<String> datosVenta = new ArrayList<>();

                    datosVenta.add(String.valueOf(venta.getIdVenta()));
                    datosVenta.add(obtenerNombreCliente(venta, listaClientes));
                    datosVenta.add(obtenerCocheVenta(venta, listaCoches));
                    datosVenta.add(venta.getMatriculaCoche());
                    String fechaTexto = convertirFecha(venta.getFechaVenta());
                    datosVenta.add(fechaTexto);
                    datosVenta.add(String.valueOf(venta.getPrecioVenta()));
                    view.mostrarListaVentas(datosVenta);

                }
                view.pulsarParaContinuar();

            }
            if (opcionMenu == opcionesMenuPrincipal.VER_COCHES_ORDEN) {
                view.mostrarOrdenarCoches(listaCoches);
            }
            if (opcionMenu == opcionesMenuPrincipal.VER_STATS_VENDEDORES) {

                int idVendedorBuscado = view.menuElegirVendedor(listaVendedores);
                String nombreVendedorBuscado = buscarNombreVendedor(idVendedorBuscado);
                Venta cocheMasCaro = calcularCocheMasCaro(idVendedorBuscado, listaVentas);

                int numeroCochesVendidos = calcularTotalCochesVendidos(idVendedorBuscado, listaVentas);
                float totalVentas = calcularTotalVentasVendedor(idVendedorBuscado, listaVentas);
                float precioMedioCoche = calcularPrecioMedioCoche(idVendedorBuscado, listaVentas);
                String matriculaCocheMasCaro = cocheMasCaro.getMatriculaCoche();
                float precioCocheMasCaro = cocheMasCaro.getPrecioVenta();
                String marcaCocheMasCaro = obtenerMarcaCocheMasCaro(matriculaCocheMasCaro, listaCoches);
                String modeloCocheMasCaro = obtenerModeloCocheMasCaro(matriculaCocheMasCaro, listaCoches);

                InfoVendedor infoVendedor = new InfoVendedor(idVendedorBuscado, nombreVendedorBuscado, numeroCochesVendidos, totalVentas,
                        precioMedioCoche, marcaCocheMasCaro, modeloCocheMasCaro, matriculaCocheMasCaro, precioCocheMasCaro);

                view.mostrarEstadisticasCompletasVendedor(infoVendedor);

            }
            if (opcionMenu == opcionesMenuPrincipal.SALIR) {
                break;
            }
        }
    }


    /**
     * Funcion que dada una lista de coches y una matricula de Coche, devuelve el Modelo de ese Coche
     *
     * @param matriculaCocheMasCaro Valor de la matricula del Coche cuyo Modelo se busca
     * @param listaCoches           Lista de objetos de la clase Coche
     * @return Modelo del Coche buscado
     */
    private String obtenerModeloCocheMasCaro(String matriculaCocheMasCaro, List<Coche> listaCoches) {
        String modeloCocheMasCaro = "";
        for (Coche coche : listaCoches) {
            if (coche.getMatriculaCoche().equals(matriculaCocheMasCaro)) {
                modeloCocheMasCaro = coche.getModeloCoche();
            }
        }
        return modeloCocheMasCaro;
    }

    /**
     * Funcion que dada una lista de coches y una matricula de Coche, devuelve la Marca de ese Coche
     *
     * @param matriculaCocheMasCaro Valor de la matricula del Coche cuya Marca se busca
     * @param listaCoches           Lista de objetos de la clase Coche
     * @return
     */
    private String obtenerMarcaCocheMasCaro(String matriculaCocheMasCaro, List<Coche> listaCoches) {
        String marcaCocheMasCaro = "";
        for (Coche coche : listaCoches) {
            if (coche.getMatriculaCoche().equals(matriculaCocheMasCaro)) {
                marcaCocheMasCaro = coche.getMarcaCoche();
            }
        }
        return marcaCocheMasCaro;
    }

    /**
     * Comprueba si el nuevo coche ya existe en la lista de coches. Si no existe lo añade a la lista
     * de lo contrario devuelve un valor False
     *
     * @param nuevoCoche  Objeto de la clase Coche
     * @param listaCoches Lista de objetos de la clase Coche
     * @return Devuelve False si la matricula del nuevo coche ya existe, si no añade el nuevo coche a la lista de coches
     */
    public boolean anhadirCoche(Coche nuevoCoche, List<Coche> listaCoches) {
        //comprobar si el coche es valido
        boolean nuevaMatricula = false;
        String matricula = nuevoCoche.getMatriculaCoche();

        for (Coche coche : listaCoches) {
            nuevaMatricula = true;
            if (coche.getMatriculaCoche().equals(matricula) || matricula.isBlank()) {
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

    /**
     * Comprueba si el nuevo Cliente ya existe en la lista de Clientes. Si no existe lo añade a la lista
     * de lo contrario devuelve un valor False
     *
     * @param nuevoCliente  Objeto de la clase Cliente
     * @param listaClientes Lista de objetos de la clase Cliente
     * @return Devuelve False si el DNI del nuevo cliente ya existe, si no añade el nuevo cliente a la lista de Clientes
     */
    private boolean registrarCliente(Cliente nuevoCliente, List<Cliente> listaClientes) {
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

    /**
     * Comprueba si algún dato introducido al añadir un nuevo cliente está en blanco, si lo está el nuevo cliente es inválido
     * @param nuevoCliente  Objeto de la clase Cliente
     * @return Devuelve True si todos los datos introducidos no estan en blanco, False si algun dato está en blanco
     */
    private boolean comprobarClienteValido(Cliente nuevoCliente) {
        boolean nuevoClienteValido = false;
        boolean nombreEnBlanco = comprobarSiEnBlanco(nuevoCliente.getNombreCliente());
        boolean dniEnBlanco = comprobarSiEnBlanco(nuevoCliente.getDniCliente());
        boolean telefonoEnBlanco = comprobarSiEnBlanco(nuevoCliente.getTelefonoCliente());
        if (!nombreEnBlanco & !dniEnBlanco & !telefonoEnBlanco){
            nuevoClienteValido = true;
        }
        return nuevoClienteValido;
    }

    /**
     * Comprueba si un texto está en blanco
     * @param stringVerificado  Variable de tipo String
     * @return Devuelve True si el texto está en blanco, False en caso contrario
     */
    public boolean comprobarSiEnBlanco(String stringVerificado){

        return stringVerificado.isBlank();
    }

    /**
     * Comprueba si el vendedor de la nueva venta existe en la lista de vendedores
     *
     * @param nuevaVenta      Objeto de la clase Venta
     * @param listaVendedores Lista de objetos de la clase Vendedor
     * @return Devuelve False si el vendedor no existe en la lista de vendedores, True si existe
     */
    private boolean comprobarVendedor(Venta nuevaVenta, List<Vendedor> listaVendedores) {
        //comprobar si el vendedor existe
        boolean vendedorValido = false;
        int idVendedor = nuevaVenta.getIdVendedor();
        for (Vendedor vendedor : listaVendedores) {
            if (vendedor.getIdVendedor() == idVendedor) {
                vendedorValido = true;
                break;
            }
        }
        return vendedorValido;
    }

    /**
     * Comprueba si el cliente de la nueva venta existe en la lista de Clientes
     *
     * @param venta         Objeto de la clase Venta
     * @param listaClientes Lista de objetos de la clase Cliente
     * @return Devuelve False si el Cliente no existe en la lista de Clientes, True en caso contrario.
     */
    private boolean comprobarCliente(Venta venta, List<Cliente> listaClientes) {
        //comprobar si el cliente existe
        boolean dniValido = false;
        String dniCliente = venta.getDniCliente();
        for (Cliente cliente : listaClientes) {
            if (cliente.getDniCliente().equals(dniCliente)) {
                dniValido = true;
                break;
            }
        }
        return dniValido;
    }

    /**
     * Comprueba si el coche de la nueva venta existe en la lista de Coches
     *
     * @param venta       Objeto de la clase Venta
     * @param listaCoches Lista de objetos de la clase Coche
     * @return
     */
    private boolean comprobarCoche(Venta venta, List<Coche> listaCoches) {

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

    /**
     * Convierte un valor de tipo ZonedDateTime a un String con formato de fecha
     *
     * @param fechaVenta Fecha en formato ZonedDateTime
     * @return Devuelve una fecha en formato de texto
     */
    private String convertirFecha(ZonedDateTime fechaVenta) {

        LocalDate zonedALocalDate = fechaVenta.toLocalDate();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        String fechaTexto = zonedALocalDate.format(formateador);

        return fechaTexto;
    }

    /**
     * Obtener el coche que se ha vendido en una venta
     *
     * @param venta       Objeto de la clase Venta
     * @param listaCoches Lista de objetos de la clase Coche
     * @return El coche que se ha vendido
     */
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

    /**
     * Obtener el nombre del cliente que ha comprado un coche en una venta
     *
     * @param venta         Objeto de la clase Venta
     * @param listaClientes Lista de objetos de la clase Cliente
     * @return El nombre del cliente que ha comprado un coche
     */
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

    /**
     * Obtener el nombre del vendedor de una venta
     *
     * @param idVendedorBuscado Valor del id del vendedor buscado
     * @return El nombre del vendedor que ha participado en una venta
     */
    private String buscarNombreVendedor(int idVendedorBuscado) {
        String nombreVendedorBuscado = null;
        for (Vendedor vendedor : listaVendedores) {
            if (vendedor.getIdVendedor() == idVendedorBuscado) {
                nombreVendedorBuscado = vendedor.getNombreVendedor();
            }
        }
        return nombreVendedorBuscado;
    }

    /**
     * Hallar la venta más cara que ha efectuado un vendedor
     *
     * @param idVendedor  Valor del id del vendedor buscado
     * @param listaVentas Lista de objetos de la clase Venta
     * @return La venta mas cara realizada
     */
    private Venta calcularCocheMasCaro(int idVendedor, List<Venta> listaVentas) {
        float mayorVenta = 0;
        String matriculaCocheVendido = null;
        Venta ventaMasCara = null;
        for (Venta venta : listaVentas) {
            if (venta.getIdVendedor() == idVendedor && venta.getPrecioVenta() >= mayorVenta) {
                mayorVenta = venta.getPrecioVenta();
                matriculaCocheVendido = venta.getMatriculaCoche();
            }
        }
        for (Venta venta : listaVentas) {
            if (venta.getMatriculaCoche().equals(matriculaCocheVendido)) {
                ventaMasCara = venta;
            }
        }

        return ventaMasCara;
    }

    /**
     * Calcular el numero total de coches vendidos por un vendedor
     *
     * @param idVendedor  Valor del id del vendedor buscado
     * @param listaVentas Lista de objetos de la clase Venta
     * @return El numero de coches vendidos
     */
    private int calcularTotalCochesVendidos(int idVendedor, List<Venta> listaVentas) {
        int numeroCochesVendidos = 0;
        for (Venta venta : listaVentas) {
            if (venta.getIdVendedor() == idVendedor) {
                numeroCochesVendidos++;
            }
        }
        return numeroCochesVendidos;
    }

    /**
     * Calcular el valor total de las ventas efectuadas por un vendedor
     *
     * @param idVendedor  Valor del id del vendedor buscado
     * @param listaVentas Lista de objetos de la clase Venta
     * @return Total de ventas de un vendedor
     */
    private float calcularTotalVentasVendedor(int idVendedor, List<Venta> listaVentas) {
        float ventasTotales = 0;
        for (Venta venta : listaVentas) {
            if (venta.getIdVendedor() == idVendedor) {
                ventasTotales = ventasTotales + venta.getPrecioVenta();
            }
        }
        return ventasTotales;
    }

    /**
     * Calcular el precio medio de las ventas de un vendedor
     *
     * @param idVendedor  Valor del id del vendedor buscado
     * @param listaVentas Lista de objetos de la clase Venta
     * @return Precio medio de las ventas
     */
    public float calcularPrecioMedioCoche(int idVendedor, List<Venta> listaVentas) {
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

    /**
     * Lista de Coches del Concesionario
     */
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

    /**
     * Lista de Clientes del Concesionario
     */
    private void loadListaClientes() {
        this.listaClientes = new ArrayList<Cliente>();
        listaClientes.add(new Cliente("Luis Martinez Montes", "11111111A", "+34678415251"));
        listaClientes.add(new Cliente("Juan Pérez García", "22222222B", "+34612345678"));
        listaClientes.add(new Cliente("María López Rodríguez", "33333333C", "+34687654321"));
        listaClientes.add(new Cliente("Carlos Gómez Fernández", "44444444D", "+34623456789"));
        listaClientes.add(new Cliente("Ana Sánchez Martínez", "55555555E", "+34698765432"));
        listaClientes.add(new Cliente("Javier Fernández Martínez", "66666666F", "+34634567890"));
        listaClientes.add(new Cliente("Laura García López", "77777777G", "+34645678901"));
    }

    /**
     * Lista de Ventas del Concesionario
     */
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

    /**
     * Lista de Vendedores del Concesionario
     */
    private void loadListaVendedores() {
        this.listaVendedores = new ArrayList<Vendedor>();
        listaVendedores.add(new Vendedor("Mateo Torres", 1));
        listaVendedores.add(new Vendedor("Teresa Gutierrez", 2));
        listaVendedores.add(new Vendedor("Carlos Cazorla", 3));

    }
}

