package View;

import Model.Cliente;
import Model.Coche;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        System.out.println("7. Salir");

        while (true) {
            //System.out.println("Selecciona una opción");
            opcion = sc.nextInt();
            //POrqué????
            sc.nextLine();
            if (opcion >= 1 && opcion <= 7) {
                break;
            }

            System.err.println("Introduce una opción valida");
        }

        return opcion;
    }

    public Coche anhadirCoche(List<Coche> listaCoches) {

        System.out.println("============ AÑADIR COCHE ============");
        System.out.println("¿Cual es la MARCA del Coche?");
        String marca = sc.next();

        System.out.println("¿Cual es el MODELO del Coche?");
        String modelo = sc.next();

        System.out.println("¿De que AÑO es el Coche?");
        int anho = sc.nextInt();

        System.out.println("¿Cuantos KILÓMETROS ha hecho el Coche?");
        double kilometros = sc.nextDouble();

        System.out.println("¿Cual es el PRECIO del Coche");
        double precio = sc.nextDouble();

        boolean nuevaMatricula = false;
        String matricula = null;
        while (!nuevaMatricula){
            System.out.println("¿Cual es la MATRICULA del Coche?");
            matricula = sc.next();
            for (Coche coche : listaCoches){
                nuevaMatricula = true;
                if (coche.getMatriculaCoche().equals(matricula)){
                    nuevaMatricula = false;
                    System.err.println("---> El Coche ya existe <---");
                    break;
                }
            }
        }

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

    private void mostrarCoche(Coche coche) {
        System.out.println(coche.getMarcaCoche() + " " + coche.getModeloCoche() + " " + coche.getMatriculaCoche() +
                " - " + coche.getAnhoCoche() + " - " + coche.getKilometrosCoche() + " Kms" + " - " + coche.getPrecioCoche() +
                " €" + " - " + coche.isCocheVendido());
    }

    public void mostrarListaCoches(List<Coche> listaCoches) {
        for (Coche coche : listaCoches) {
            mostrarCoche(coche);
        }
    }

    public void mostrarListaCochesEnVenta(List<Coche> listaCoches) {
        List<Coche> listaCochesEnVenta = new ArrayList<>();
        for (Coche coche : listaCoches) {
            if (!coche.isCocheVendido()) {
                listaCochesEnVenta.add(coche);
            }
        }
        System.out.println("============ COCHES A LA VENTA ============");
        for (Coche coche : listaCochesEnVenta) {
            mostrarCoche(coche);
        }
    }

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

    private void buscarPorPrecio(List<Coche> listaCoches) {
        System.out.println("Cual es el RANGO de PRECIOS del Coche que buscas?");
        double precioMinimo = sc.nextDouble();
        double precioMaximo = sc.nextDouble();
        for (Coche coche : listaCoches) {
            double precioCoche = coche.getPrecioCoche();
            if (precioCoche >= precioMinimo && precioCoche <= precioMaximo) {
                mostrarCoche(coche);
            }
        }
    }

    private void buscarPorAnho(List<Coche> listaCoches) {
        System.out.println("¿De que AÑO es el Coche que estás buscando?");
        int anhoBuscado = sc.nextInt();
        for (Coche coche : listaCoches) {
            int anhoCoche = coche.getAnhoCoche();
            if (anhoBuscado == anhoCoche) {
                mostrarCoche(coche);
            }
        }
    }

    private void buscarPorMarca(List<Coche> listaCoches) {
        System.out.println("¿Que MARCA de coches estás buscando?");
        String marcaBuscada = sc.next();
        for (Coche coche : listaCoches) {
            String marcaCoche = coche.getMarcaCoche();
            if (marcaBuscada.equals(marcaCoche)) {
                mostrarCoche(coche);
            }
        }
    }

    public Cliente registrarCliente(List<Cliente> listaClientes) {
        System.out.println("============ REGISTRAR CLIENTE ============");
        System.out.println("¿Cual es el NOMBRE del Cliente?");
        String nombre = sc.nextLine();

        boolean nuevoDni = false;
        String dni = null;
        while (!nuevoDni) {
            System.out.println("¿Cual es el DNI del Cliente?");
            dni = sc.nextLine();
            for (Cliente cliente : listaClientes) {
                nuevoDni = true;
                if (cliente.getDniCliente().equals(dni)) {
                    nuevoDni = false;
                    System.err.println("---> El Cliente ya existe <---");
                    break;
                }
            }
        }

        System.out.println("¿Cual es el TELEFONO del Cliente?");
        String telefono = sc.nextLine();

        Cliente nuevocliente = new Cliente(nombre, dni, telefono);

        return nuevocliente;
    }

    public void mostrarListaClientes(List<Cliente> listaClientes) {
        for (Cliente cliente : listaClientes) {
            System.out.println(cliente.getNombreCliente() + " " + cliente.getDniCliente() + " " + cliente.getTelefonoCliente());
        }
    }

    public void registrarVenta() {
        System.out.println("============ REGISTRAR VENTA ============");
        System.out.println("¿Cual es el Identificador de la Venta?");
        int nuevaVenta = sc.nextInt();

        System.out.println("¿Que DNI tiene el Cliente que ha comprado un Coche?");
        String dniComprador = sc.nextLine();

        System.out.println("¿Que MATRICULA tiene el coche que ha comprado?");
        String matriculaVenta = sc.nextLine();

        System.out.println("¿En que FECHA se ha realizado la Venta? (dd/mm/yyyy)");
        //Ejemplo: Date fecha = new Date(125, 1, 5);
        String fechaVenta = sc.nextLine();

        System.out.println("¿Cual es el PRECIO de Venta?");
        double precioVenta = sc.nextDouble();

        System.out.println("¿Quien ha hecho la Venta?");
        String nombreVendedor = sc.nextLine();

        return;
    }


}
