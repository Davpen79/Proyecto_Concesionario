package View;

import Model.Cliente;
import Model.Coche;
import Model.Vendedor;
import Model.Venta;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class MenuView {

    Scanner sc = new Scanner(System.in);

    public int menuPrincipal() {

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

            opcion = sc.nextInt();
            sc.nextLine();
            if (opcion >= 1 && opcion <= 9) {
                break;
            }

            System.err.println("Introduce una opción valida");
        }
        return opcion;
    }

    public Coche menuAnhadirCoche(List<Coche> listaCoches) {

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

        System.out.println("¿Cual es la MATRICULA del Coche?");
        String matricula = sc.nextLine();

        System.out.println("¿El Coche está a la venta o ya ha sido vendido?");
        System.out.println("1. Está a la VENTA");
        System.out.println("2. Se ha VENDIDO");

        int estadoVenta = sc.nextInt();
        boolean cocheVendido = false;
        if (estadoVenta == 2) {
            cocheVendido = true;
        }

        Coche coche = new Coche(marca, modelo, anho, kilometros, precio, matricula, cocheVendido);
        return coche;
    }

    //private void mostrarCoche(Coche coche) {
    //    //crear variable para convertir boolean
    //    boolean cocheVendido = coche.isCocheVendido();
    //    String cocheEnVenta;
    //    if (!cocheVendido) {
    //        cocheEnVenta = "En Venta";
    //    } else {
    //        cocheEnVenta = "Vendido";
    //    }
    //    //mostrar información del coche
    //    System.out.println(coche.getMarcaCoche() + " " + coche.getModeloCoche() + " " + coche.getMatriculaCoche() +
    //            " - " + coche.getAnhoCoche() + " - " + coche.getKilometrosCoche() + " Kms" + " - " + coche.getPrecioCoche() +
    //            " €" + " - " + cocheEnVenta);
    //}

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
        //if (opcion == 4) {
        //    busquedaMultiple(listaCoches);
        //}
    }

    private void buscarPorPrecio(List<Coche> listaCoches) {
        while (true) {
            System.out.println("Cual es el RANGO de PRECIOS del Coche que buscas?");
            System.out.println("Indica el precio Mínimo");
            float precioMinimo = sc.nextFloat();
            System.out.println("Indica el precio Máximo");
            float precioMaximo = sc.nextFloat();
            List<Coche> listaPorPrecio = new ArrayList<>();
            for (Coche coche : listaCoches) {
                float precioCoche = coche.getPrecioCoche();
                if (precioCoche >= precioMinimo && precioCoche <= precioMaximo) {
                    listaPorPrecio.add(coche);
                }
            }
            if (listaPorPrecio.isEmpty()) {
                System.err.println("No hay coches disponibles por ese rango de precios");
                break;
            }
            pintarTablaCoches(listaPorPrecio);
            break;
        }

    }

    private void buscarPorAnho(List<Coche> listaCoches) {
        List<Integer> anhosDisponibles = new ArrayList<>();
        for (Coche coche : listaCoches){
            int anhoCoche = coche.getAnhoCoche();
            if (!anhosDisponibles.contains(anhoCoche)){
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
            }
            else {
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

    private void buscarPorMarca(List<Coche> listaCoches) {
        List<String> marcasDisponibles = new ArrayList<>();
        for (Coche coche : listaCoches){
            String marcaCoche = coche.getMarcaCoche();
            if (!marcasDisponibles.contains(marcaCoche)){
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
            }
            else {
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

    public Cliente menuRegistrarCliente(List<Cliente> listaClientes) {
        System.out.println("============ REGISTRAR CLIENTE ============");
        System.out.println("¿Cual es el NOMBRE del Cliente?");
        String nombre = sc.nextLine();

        System.out.println("¿Cual es el DNI del Cliente?");
        String dni = sc.nextLine();

        System.out.println("¿Cual es el TELEFONO del Cliente?");
        String telefono = sc.nextLine();

        Cliente nuevocliente = new Cliente(nombre, dni, telefono);

        return nuevocliente;
    }

    //public void mostrarListaClientes(List<Cliente> listaClientes) {
    //    for (Cliente cliente : listaClientes) {
    //        System.out.println(cliente.getNombreCliente() + " " + cliente.getDniCliente() + " " + cliente.getTelefonoCliente());
    //    }
    //}

    public Venta menuRegistrarVenta(List<Venta> listaVentas) {
        System.out.println("============ REGISTRAR VENTA ============");

        int nuevaIdVenta = 0;

        System.out.println("¿Que DNI tiene el Cliente que ha comprado un Coche?");
        String dniComprador = sc.nextLine();

        System.out.println("¿Que MATRICULA tiene el coche que ha comprado?");
        String matriculaVenta = sc.nextLine();

        boolean fechaValida = false;
        LocalDate newlocalDate = null;
        while (!fechaValida) {
            System.out.println("¿En que FECHA se ha realizado la Venta? (yyyy-MM-dd)");
            Scanner sc = new Scanner(System.in);
            try {
                String fechaVentaText = sc.nextLine();
                newlocalDate  = LocalDate.parse(fechaVentaText);
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

        Venta nuevaVenta = new Venta(nuevaIdVenta,dniComprador,matriculaVenta,fechaVenta,precioVenta,idVendedor);

        return nuevaVenta;
    }

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

    public void ordenarPorMarca(List<Coche> listaCoches) {
        listaCoches.sort(Comparator.comparing(Coche::getMarcaCoche));
        pintarTablaCoches(listaCoches);
    }

    private void ordenarPorAnho(List<Coche> listaCoches) {
        listaCoches.sort(Comparator.comparingInt(Coche::getAnhoCoche));
        pintarTablaCoches(listaCoches);
    }

    private void ordenarPorPrecio(List<Coche> listaCoches) {
        listaCoches.sort(Comparator.comparingDouble(Coche::getPrecioCoche));
        pintarTablaCoches(listaCoches);
    }
    public void mostrarErrorCoche() {
        System.err.println("Este coche ya existe");

    }

    public void mostrarErrorCliente() {
        System.err.println("Este cliente ya existe");

    }

    public int menuElegirVendedor(List<Vendedor> listaVendedores) {
        System.out.println("============ ESTADISTICAS VENDEDORES ============");
        System.out.println("1. Estadísticas de " + listaVendedores.getFirst().getNombreVendedor());
        System.out.println("2. Estadísticas de " + listaVendedores.get(1).getNombreVendedor());
        System.out.println("3. Estadísticas de " + listaVendedores.get(2).getNombreVendedor());
        int vendedorElegido = sc.nextInt();
        sc.nextLine();

        return vendedorElegido;
    }

    public void mostrarEstadisticasVendedor(List<Object> datosEstadisticos){
        System.out.println("============ ESTADISTICAS VENDEDOR ============");
        System.out.println("El numero de coches vendidos por " + datosEstadisticos.getFirst() + " es: " + datosEstadisticos.get(1));
        System.out.println("La suma total de los coches vendidos por " + datosEstadisticos.getFirst() + " es: " + datosEstadisticos.get(2) + " Euros");
        System.out.println("El valor medio de los coches vendidos por " + datosEstadisticos.getFirst() + " es: " + datosEstadisticos.get(3) + " Euros");
        System.out.println("El coche mas caro vendido por " + datosEstadisticos.getFirst() + " es: " + datosEstadisticos.get(4) + " " + datosEstadisticos.get(5)
                                            + " con matricula " + datosEstadisticos.get(6) + " vendido por " + datosEstadisticos.get(7) + " Euros");

    }

    public void pintarTablaCochesEnVenta(List<Coche> listaCoches){
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
                                coche.getAnhoCoche(), coche.getKilometrosCoche()+" Km",formato.format(coche.getPrecioCoche()) + " €");
        }
        System.out.format("+---------------+------------+-----------+-------+-------------+------------+%n");
    }

    public void pintarTablaCoches(List<Coche> listaCoches){
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
                    coche.getAnhoCoche(), coche.getKilometrosCoche()+" Km", formato.format(coche.getPrecioCoche()) +" €", cocheEnVenta);
        }
        System.out.format("+---------------+------------+-----------+-------+-------------+------------+----------------+%n");
    }

    public void mostrarListaVentas(List<Object> listaVentasCompleta) {
        //for (Object object : listaVentasCompleta) {
        //System.out.println(venta.getIdVenta() + " " + venta.getDniCliente() + " " + venta.getMatriculaCoche() + " " + venta.getPrecioVenta());
        System.out.println(listaVentasCompleta.getFirst() + " " + listaVentasCompleta.get(1)+ " "+ listaVentasCompleta.get(2)+
                " " + listaVentasCompleta.get(3)+ " "+ listaVentasCompleta.get(4)+ " "+ listaVentasCompleta.get(5));
        // }

    }


    public void pintarTablaVentas(List<Object> listaVentasCompleta){
        System.out.println("===================================== CATALOGO DE COCHES =====================================");
        System.out.format("+---------------+------------+-----------+-------+-------------+------------+----------------+%n");
        System.out.format("|     idVenta     |   CLIENTE   | COCHE  | MATRICULA |   FECHA   | PRECIO |%n");
        System.out.format("+---------------+------------+-----------+-------+-------------+------------+----------------+%n");
        String leftAlignFormat = "| %2s | %-25s | %-25s | %9s | %10s | %10f |%n";

        DecimalFormat formato = new DecimalFormat("#.00");

        for (Object object : listaVentasCompleta) {

            System.out.format(leftAlignFormat, listaVentasCompleta.getFirst(), listaVentasCompleta.get(1), listaVentasCompleta.get(2),
                    listaVentasCompleta.get(3), listaVentasCompleta.get(4), listaVentasCompleta.get(5));
        }

        System.out.format("+---------------+------------+-----------+-------+-------------+------------+----------------+%n");
    }

    public void mostrarErrorCocheInvalido() {
        System.err.println("Ese coche no es valido");
    }

    public void mostrarErrorClienteInvalido() {
        System.err.println("Ese cliente no es valido");
    }

    public void mostrarErrorVendedorInvalido() {
        System.err.println("Ese vendedor no es valido");
    }
}
