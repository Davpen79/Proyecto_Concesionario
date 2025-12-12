package View;

import Model.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class MenuView {

    Scanner sc = new Scanner(System.in);

    public int menuPrincipal() {

        pintarLogo();

        int opcion = -1;

        System.out.println("Bienvenido a AutoGanga. ¿Que deseas hacer?");
        System.out.println("1. Añadir coches al concesionario");
        System.out.println("2. Mostrar los coches a la venta");
        System.out.println("3. Buscar coches");
        System.out.println("4. Registrar un nuevo cliente");
        System.out.println("5. Registrar una venta");
        System.out.println("6. Mostrar ventas");
        System.out.println("7. Mostrar coches ordenados");
        System.out.println("8. Mostrar Estadísticas de los vendedores");
        System.out.println("9. Salir");

        while (true) {

            try {
                opcion = sc.nextInt();
            } catch (InputMismatchException e) {
                sc.next();
            }
            sc.nextLine();
            if (opcion >= 1 && opcion <= 9) {
                break;
            }
            System.err.println("Introduce una opción valida");
        }

        return opcion;
    }

    /**
     * Pregunta al usuario los datos necesarios para añadir un coche nuevo
     *
     * @return Un nuevo objeto de la clase Coche
     */
    public Coche menuAnhadirCoche() {

        System.out.println("============ AÑADIR COCHE ============");
        System.out.println("¿Cual es la MARCA del Coche?");
        String marca = sc.nextLine();

        System.out.println("¿Cual es el MODELO del Coche?");
        String modelo = sc.nextLine();

        System.out.println("¿De que AÑO es el Coche?");
        int anho = sc.nextInt();

        System.out.println("¿Cuantos KILÓMETROS ha hecho el Coche?");
        float kilometros = sc.nextFloat();

        System.out.println("¿Cual es el PRECIO del Coche");
        float precio = sc.nextFloat();
        sc.nextLine();

        String matricula = "";
        while (matricula.isBlank()) {
            System.out.println("¿Cual es la MATRICULA del Coche?");
            matricula = sc.nextLine();
            if (matricula.isBlank()) {
                System.err.println("La matricula no puede estar en blanco");
            }
        }
        boolean cocheVendido = false;
        boolean registroValido = false;
        while (!registroValido) {
            System.out.println("¿El Coche está a la venta o ya ha sido vendido?");
            System.out.println("1. Está a la VENTA");
            System.out.println("2. Se ha VENDIDO");

            int estadoVenta = sc.nextInt();

            if (estadoVenta == 2) {
                cocheVendido = true;
                registroValido = true;
            } else if (estadoVenta == 1) {
                cocheVendido = false;
                registroValido = true;
            } else {
                System.err.println("Debes introducir 1 o 2. Registro del Coche Fallido.");
                registroValido = false;
            }
        }
        Coche coche = new Coche(marca, modelo, anho, kilometros, precio, matricula, cocheVendido);
        System.out.println("Nuevo coche añadido con éxito");
        pulsarParaContinuar();
        return coche;
    }

    /**
     * Solicita al usuario que seleccione el modo en el que quiere buscar un coche que forma parte de una lista
     *
     * @param listaCoches Lista que contiene objetos de la clase Coche
     */
    public void buscarCoches(List<Coche> listaCoches) {
        System.out.println("============ BUSCAR COCHE ============");
        System.out.println(" 1. Por MARCA\n 2. Por Año\n 3. Por Precio");
        int opcion = sc.nextInt();
        if (opcion == 1) {
            buscarPorMarca(listaCoches);
        }
        if (opcion == 2) {
            buscarPorAnho(listaCoches);
        }
        if (opcion == 3) {
            buscarPorPrecio(listaCoches);
        }
    }

    /**
     * Pregunta al usuario por un rango de valores enteros (minimo y maximo) para hacer una busqueda.
     * Luego imprime en pantalla los resultados. En caso de no encontrar ningun resultado imprime un mensaje de no disponibilidad.
     * @param listaCoches Lista que contiene objetos de la clase Coche
     */
    private void buscarPorPrecio(List<Coche> listaCoches) {

        System.out.println("Cual es el RANGO de PRECIOS del Coche que buscas?");
        boolean busquedaValida = false;
        float precioMinimo = 0;
        float precioMaximo = 0;
        while (!busquedaValida) {
            System.out.println("Indica el precio Mínimo");
            precioMinimo = sc.nextFloat();
            System.out.println("Indica el precio Máximo");
            precioMaximo = sc.nextFloat();
            if (precioMaximo < precioMinimo) {

                System.err.println("El precio Maximo no puede ser menor que el Minimo");
            } else {
                busquedaValida = true;
            }
        }
        List<Coche> listaPorPrecio = new ArrayList<>();
        for (Coche coche : listaCoches) {
            float precioCoche = coche.getPrecioCoche();
            if (precioCoche >= precioMinimo && precioCoche <= precioMaximo) {
                listaPorPrecio.add(coche);
            }
        }
        if (listaPorPrecio.isEmpty()) {
            System.err.println("No hay coches disponibles por ese rango de precios");
            pulsarParaContinuar();
        } else {
            pintarTablaCoches(listaPorPrecio);
        }


    }

    /**
     * Pregunta al usuario por un valor entero (Año) para hacer una busqueda.
     * Luego imprime en pantalla los resultados. En caso de no encontrar un resultado imprime un mensaje de no disponibilidad.
     * @param listaCoches Lista que contiene objetos de la clase Coche
     */
    private void buscarPorAnho(List<Coche> listaCoches) {
        List<Integer> anhosDisponibles = new ArrayList<>();
        for (Coche coche : listaCoches) {
            int anhoCoche = coche.getAnhoCoche();
            if (!anhosDisponibles.contains(anhoCoche)) {
                anhosDisponibles.add(anhoCoche);
            }
        }

        boolean anhoDisponible = false;
        int anhoBuscado = 0;
        while (!anhoDisponible) {
            System.out.println("¿De que AÑO es el Coche que estás buscando?");
            anhoBuscado = sc.nextInt();
            sc.nextLine();
            if (!anhosDisponibles.contains(anhoBuscado)) {
                System.err.println("No hay coches disponibles de ese año");
            } else {
                anhoDisponible = true;
            }
        }

        List<Coche> listaPorAnho = new ArrayList<>();
        for (Coche coche : listaCoches) {
            int anhoCoche = coche.getAnhoCoche();
            if (anhoBuscado == anhoCoche) {
                listaPorAnho.add(coche);
            }
        }

        pintarTablaCoches(listaPorAnho);

    }

    /**
     * Pregunta al usuario por una cadena de texto (Marca) para hacer una busqueda.
     * Luego imprime en pantalla los resultados. En caso de no encontrar un resultado imprime un mensaje de no disponibilidad.
     * @param listaCoches Lista que contiene objetos de la clase Coche
     */
    private void buscarPorMarca(List<Coche> listaCoches) {
        List<String> marcasDisponibles = new ArrayList<>();
        for (Coche coche : listaCoches) {
            String marcaCoche = coche.getMarcaCoche();
            if (!marcasDisponibles.contains(marcaCoche)) {
                marcasDisponibles.add(marcaCoche);
            }
        }

        boolean marcaDisponible = false;
        String marcaBuscada = null;
        while (!marcaDisponible) {
            System.out.println("¿Que MARCA de coches estás buscando?");
            System.out.println("Marcas disponibles:");
            for (String marca : marcasDisponibles) {
                System.out.println(marca);
            }
            marcaBuscada = sc.next();
            if (!marcasDisponibles.contains(marcaBuscada)) {
                System.err.println("Esa marca no está disponible");
            } else {
                marcaDisponible = true;
            }
        }

        List<Coche> listaPorMarca = new ArrayList<>();
        for (Coche coche : listaCoches) {
            String marcaCoche = coche.getMarcaCoche();
            if (marcaBuscada.equals(marcaCoche)) {
                listaPorMarca.add(coche);
            }
        }

        pintarTablaCoches(listaPorMarca);

    }

    /**
     * Pregunta al usuario los datos necesarios para registrar un cliente nuevo
     * @return Un nuevo objeto de la clase Cliente
     */
    public Cliente menuRegistrarCliente() {
        System.out.println("============ REGISTRAR CLIENTE ============");

        String nombre = "";
        while (nombre.isBlank()) {
            System.out.println("¿Cual es el NOMBRE del Cliente?");
            nombre = sc.nextLine();
            if (nombre.isBlank()) {
                System.err.println("El nombre del cliente no puede estar vacio");
            }

        }

        String dni = "";
        while (dni.isBlank()) {
            System.out.println("¿Cual es el DNI del Cliente?");
            dni = sc.nextLine();
            if (dni.isBlank()) {
                System.err.println("El DNI del cliente no puede estar en blanco");
            }
        }

        String telefono = "";
        while (telefono.isBlank()) {
            System.out.println("¿Cual es el TELEFONO del Cliente?");
            telefono = sc.nextLine();

            if (telefono.isBlank()) {
                System.err.println("El telefono no puede estar vacio");
            }
        }

        Cliente nuevocliente = new Cliente(nombre, dni, telefono);
        System.out.println("Cliente registrado correctamente");
        pulsarParaContinuar();
        return nuevocliente;
    }

    /**
     * Pregunta al usuario los datos necesarios para registrar una nueva venta
     * @return Un nuevo objeto de la clase Venta
     */
    public Venta menuRegistrarVenta() {
        System.out.println("============ REGISTRAR VENTA ============");

        int nuevaIdVenta = 0;

        System.out.println("¿Que DNI tiene el Cliente que ha comprado un Coche?");
        String dniComprador = sc.next();

        System.out.println("¿Que MATRICULA tiene el coche que ha comprado?");
        String matriculaVenta = sc.next();

        boolean fechaValida = false;
        LocalDate newlocalDate = null;
        while (!fechaValida) {
            System.out.println("¿En que FECHA se ha realizado la Venta? (yyyy-MM-dd)");
            Scanner sc = new Scanner(System.in);
            try {
                String fechaVentaText = sc.nextLine();
                newlocalDate = LocalDate.parse(fechaVentaText);
                fechaValida = true;
            } catch (DateTimeParseException e) {
                System.err.println("Formato de fecha incorrecto. Debe ser yyyy-MM-dd");
            }
        }
        ZonedDateTime fechaVenta = newlocalDate.atStartOfDay(ZoneId.systemDefault());

        System.out.println("¿Cual es el PRECIO de Venta?");
        float precioVenta = sc.nextFloat();

        System.out.println("¿Quien ha hecho la Venta?. Ingrese el ID del vendedor");
        int idVendedor = sc.nextInt();
        sc.nextLine();

        Venta nuevaVenta = new Venta(nuevaIdVenta, dniComprador, matriculaVenta, fechaVenta, precioVenta, idVendedor);
        System.out.println("Venta registrada correctamente");
        pulsarParaContinuar();
        return nuevaVenta;
    }

    /**
     * Solicita al usuario que seleccione el modo en el que quiere listar de forma ordenada una lista de Coches
     * @param listaCoches Lista que contiene objetos de la clase Coche
     */
    public void mostrarOrdenarCoches(List<Coche> listaCoches) {
        System.out.println("============ ORDENAR COCHES ============");
        System.out.println(" 1. Por MARCA\n 2. Por Año\n 3. Por Precio");
        int opcion = sc.nextInt();
        if (opcion == 1) {
            ordenarPorMarca(listaCoches);
        }
        if (opcion == 2) {
            ordenarPorAnho(listaCoches);
        }
        if (opcion == 3) {
            ordenarPorPrecio(listaCoches);
        }

    }

    /**
     * Imprime en pantalla los resultados de ordenar una lista de Coches por Marca
     * @param listaCoches Lista que contiene objetos de la clase Coche
     */
    public void ordenarPorMarca(List<Coche> listaCoches) {
        listaCoches.sort(Comparator.comparing(Coche::getMarcaCoche));
        pintarTablaCoches(listaCoches);

    }

    /**
     * Imprime en pantalla los resultados de ordenar una lista de Coches por Año
     * @param listaCoches Lista que contiene objetos de la clase Coche
     */
    private void ordenarPorAnho(List<Coche> listaCoches) {
        listaCoches.sort(Comparator.comparingInt(Coche::getAnhoCoche));
        pintarTablaCoches(listaCoches);

    }

    /**
     * Imprime en pantalla los resultados de ordenar una lista de Coches por Precio
     * @param listaCoches Lista que contiene objetos de la clase Coche
     */
    private void ordenarPorPrecio(List<Coche> listaCoches) {
        listaCoches.sort(Comparator.comparingDouble(Coche::getPrecioCoche));
        pintarTablaCoches(listaCoches);

    }

    /**
     * Muestra en pantalla un error indicando que el Coche ya existe
     */
    public void mostrarErrorCoche() {
        System.err.println("Este coche ya existe");
        pulsarParaContinuar();
    }

    /**
     * Muestra en pantalla un error indicando que el Cliente ya existe
     */
    public void mostrarErrorCliente() {
        System.err.println("Este cliente ya existe");
        pulsarParaContinuar();
    }

    /**
     * Menu para solicitar al usuario la identidad del vendedor del cual se quieren conocer sus estadisticas
     * @param listaVendedores Lista que contiene objetos de la clase Vendedor
     * @return La opcion de vendedor escogida
     */
    public int menuElegirVendedor(List<Vendedor> listaVendedores) {
        int vendedorElegido = 0;
        while (true) {
            System.out.println("============ ESTADISTICAS VENDEDORES ============");
            System.out.println("1. Estadísticas de " + listaVendedores.getFirst().getNombreVendedor());
            System.out.println("2. Estadísticas de " + listaVendedores.get(1).getNombreVendedor());
            System.out.println("3. Estadísticas de " + listaVendedores.get(2).getNombreVendedor());
            try {
                vendedorElegido = sc.nextInt();
            } catch (InputMismatchException e) {
                sc.next();
            }
            sc.nextLine();
            if (vendedorElegido == 1 || vendedorElegido == 2 || vendedorElegido == 3) {
                break;
            }
            System.err.println("No existe ese vendedor");
        }

        return vendedorElegido;
    }

    /**
     * Muestra en pantalla la información correspondiente a las ventas realizadas por un determinado Vendedor
     * @param infoVendedor Objeto de la clase InfoVendedor
     */
    public void mostrarEstadisticasCompletasVendedor(InfoVendedor infoVendedor) {
        DecimalFormat formato = new DecimalFormat("#.00");
        System.out.println("============ ESTADISTICAS VENDEDOR ============");
        System.out.println("El numero de coches vendidos por " + infoVendedor.getNombreVendedor() + " es: " + infoVendedor.getNumeroCochesVendidos());
        System.out.println("La suma total de los coches vendidos por " + infoVendedor.getNombreVendedor() + " es: " + formato.format(infoVendedor.getTotalVentas()) + " Euros");
        System.out.println("El valor medio de los coches vendidos por " + infoVendedor.getNombreVendedor() + " es: " + formato.format(infoVendedor.getPrecioMedioCoche()) + " Euros");
        System.out.println("El coche mas caro vendido por " + infoVendedor.getNombreVendedor() + " es: " + infoVendedor.getMarcaCocheMasCaro() + " " + infoVendedor.getModeloCocheMasCaro()
                + " con matricula " + infoVendedor.getMatriculaCocheMasCaro() + " vendido por " + formato.format(infoVendedor.getPrecioCocheMasCaro()) + " Euros");
        System.out.println("===============================================");
        pulsarParaContinuar();
    }

    /**
     * Muestra en pantalla una lista con los coches que están disponibles para vender
     * @param listaCoches Lista que contiene objetos de la clase Coche
     */
    public void pintarTablaCochesEnVenta(List<Coche> listaCoches) {
        System.out.println("============================= COCHES A LA VENTA =============================");
        System.out.format("+---------------+------------+-----------+-------+-------------+------------+%n");
        System.out.format("|     MARCA     |   MODELO   | MATRICULA |  AÑO  | KILOMETRAJE |   PRECIO   |%n");
        System.out.format("+---------------+------------+-----------+-------+-------------+------------+%n");
        String leftAlignFormat = "| %-13s | %-10s | %9s | %5d | %11s | %10s |%n";

        DecimalFormat formato = new DecimalFormat("#.00");

        List<Coche> listaCochesEnVenta = new ArrayList<>();
        for (Coche coche : listaCoches) {
            if (!coche.isCocheVendido()) {
                listaCochesEnVenta.add(coche);
            }
        }
        for (Coche coche : listaCochesEnVenta) {
            System.out.format(leftAlignFormat, coche.getMarcaCoche(), coche.getModeloCoche(), coche.getMatriculaCoche(),
                    coche.getAnhoCoche(), coche.getKilometrosCoche() + " Km", formato.format(coche.getPrecioCoche()) + " €");
        }
        System.out.format("+---------------+------------+-----------+-------+-------------+------------+%n");
        pulsarParaContinuar();
    }

    /**
     * Muestra en pantalla una lista con todos los coches que figuran en los registros del concesionario
     * @param listaCoches Lista que contiene objetos de la clase Coche
     */
    public void pintarTablaCoches(List<Coche> listaCoches) {
        System.out.println("===================================== CATALOGO DE COCHES =====================================");
        System.out.format("+---------------+------------+-----------+-------+-------------+------------+----------------+%n");
        System.out.format("|     MARCA     |   MODELO   | MATRICULA |  AÑO  | KILOMETRAJE |   PRECIO   | DISPONIBILIDAD |%n");
        System.out.format("+---------------+------------+-----------+-------+-------------+------------+----------------+%n");
        String leftAlignFormat = "| %-13s | %-10s | %9s | %5d | %11s | %10s | %14s |%n";

        DecimalFormat formato = new DecimalFormat("#.00");

        for (Coche coche : listaCoches) {
            boolean cocheVendido = coche.isCocheVendido();
            String cocheEnVenta;
            if (!cocheVendido) {
                cocheEnVenta = "En Venta";
            } else {
                cocheEnVenta = "Vendido";
            }
            System.out.format(leftAlignFormat, coche.getMarcaCoche(), coche.getModeloCoche(), coche.getMatriculaCoche(),
                    coche.getAnhoCoche(), coche.getKilometrosCoche() + " Km", formato.format(coche.getPrecioCoche()) + " €", cocheEnVenta);
        }
        System.out.format("+---------------+------------+-----------+-------+-------------+------------+----------------+%n");
        pulsarParaContinuar();
    }

    /**
     * Muestra en pantalla una lista con los datos de todas las ventas efectuadas en el concesionario
     * @param datosVenta Lista que contiene datos relativos a las ventas del concesionario
     */
    public void mostrarListaVentas(List<String> datosVenta) {
        DecimalFormat formato = new DecimalFormat("#.00");
        String leftAlignFormat = "| %7s | %-25s | %-25s | %9s | %10s | %10s |%n";
        System.out.format(leftAlignFormat, datosVenta.getFirst(), datosVenta.get(1), datosVenta.get(2),
                datosVenta.get(3), datosVenta.get(4), formato.format(Float.parseFloat(datosVenta.get(5))) + " €");
        System.out.format("+---------+---------------------------+---------------------------+-----------+------------+------------+%n");
    }

    /**
     * Muestra en pantalla una cabecera para las tablas de las Ventas
     */
    public void pintarCabeceraTablaVentas() {
        System.out.println("==========================================  LISTA DE VENTAS  ============================================");
        System.out.format("+---------+---------------------------+---------------------------+-----------+------------+------------+%n");
        System.out.format("| idVenta |          CLIENTE          |           COCHE           | MATRICULA |   FECHA    |   PRECIO   |%n");
        System.out.format("+---------+---------------------------+---------------------------+-----------+------------+------------+%n");

    }

    /**
     * Muestra un mensaje de error cuando el Coche no es valido
     */
    public void mostrarErrorCocheInvalido() {

        System.err.println("Ese coche no es valido");
        pulsarParaContinuar();
    }

    /**
     * Muestra un mensaje de error cuando el Cliente no es valido
     */
    public void mostrarErrorClienteInvalido() {

        System.err.println("Ese cliente no es valido");
        pulsarParaContinuar();
    }

    /**
     * Muestra un error cuando el vendedor no es valido
     */
    public void mostrarErrorVendedorInvalido() {
        System.err.println("Ese vendedor no es valido");
        pulsarParaContinuar();
    }

    /**
     * Muestra un mensaje para indicar al usuario que debe pulsar un boton (Entrar) para continuar
     */
    public void pulsarParaContinuar() {
        System.out.println("Presiona ENTER para continuar");
        Scanner se = new Scanner(System.in);
        se.nextLine();
    }

    /**
     * Imprime en pantalla un logo en ascii
     */
    public void pintarLogo() {
        System.out.println("=================================================================================");
        System.out.println("|     #     #     # ####### ####### #######     #     ##    # #######     #     |");
        System.out.println("|    # #    #     #    #    #     # #          # #    # #   # #          # #    |");
        System.out.println("|   #   #   #     #    #    #     # #   ###   #   #   #  #  # #   ###   #   #   |");
        System.out.println("|  #######  #     #    #    #     # #     #  #######  #   # # #     #  #######  |");
        System.out.println("| #       # #######    #    ####### ####### #       # #    ## ####### #       # |");
        System.out.println("=================================================================================");
    }


}
