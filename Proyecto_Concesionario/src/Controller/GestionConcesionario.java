package Controller;

import Model.Cliente;
import Model.Coche;
import Model.Venta;
import View.MenuView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionConcesionario {

    public List<Coche> listaCoches = new ArrayList<>();
    public List<Cliente> listaClientes = new ArrayList<>();
    public List<Venta> listaVentas = new ArrayList<>();

    private MenuView view;

    Scanner sc = new Scanner(System.in);

    public GestionConcesionario(MenuView view) {
        this.view = view;
        loadListaCoches();
        loadListaClientes();
    }

    public void registrarVenta(Venta venta) {

    }

    public void listarVentas() {

    }

    public boolean anhadirCoche(Coche nuevoCoche){
        //comprobar si el coche es valido
        boolean nuevaMatricula = false;
        String matricula = nuevoCoche.getMatriculaCoche();


            for (Coche coche : listaCoches){
                nuevaMatricula = true;
                if (coche.getMatriculaCoche().equals(matricula)){
                    nuevaMatricula = false;
                    System.err.println("---> El Coche ya existe <---");
                    break;
                }
            }

        //si valido add a la lista

        //si no valido return false;
        return;
    }

    public void run() {

        int opcion;
        MenuView newMenu = new MenuView();

        while (true) {

            opcion = newMenu.menuPrincipal();

            if (opcion == 1) {
                Coche nuevoCoche = view.menuAnhadirCoche(listaCoches);
                boolean added = anhadirCoche(nuevoCoche);
                if(!added) view.mostarError();
            }
            if (opcion == 2) {

                view.mostrarListaCochesEnVenta(listaCoches);

            }
            if (opcion == 3) {
                view.buscarCoches(listaCoches);
            }
            if (opcion == 4) {
                Cliente nuevoCliente = view.registrarCliente(listaClientes);
                listaClientes.add(nuevoCliente);
                //view.mostrarListaClientes(listaClientes);

            }
            if (opcion == 5){
                //Venta nuevaVenta = view.registrarVenta();
            }

        }
    }

    private void loadListaCoches() {
        this.listaCoches = new ArrayList<Coche>();
        listaCoches.add(new Coche("Seat", "Ibiza", 2018, 50000, 12000, "1234ABC", true));
        listaCoches.add(new Coche("Volkswagen", "Golf", 2020, 30000, 18000, "5678DEF", false));
        listaCoches.add(new Coche("Renault", "Clio", 2019, 40000, 13000, "9101GHI", false));
        listaCoches.add(new Coche("Fiat", "Punto", 2017, 60000, 9000, "2345JKL", true));
        listaCoches.add(new Coche("Seat", "León", 2021, 20000, 20000, "6789MNO", false));
        listaCoches.add(new Coche("Volkswagen", "Polo", 2018, 45000, 14000, "3456PQR", true));
        listaCoches.add(new Coche("Renault", "Mégane", 2020, 25000, 16000, "7890STU", false));
        listaCoches.add(new Coche("Fiat", "Panda", 2016, 70000, 8000, "4567VWX", true));
        listaCoches.add(new Coche("Volkswagen", "Passat", 2021, 15000, 22000, "1234BCD", false));
        listaCoches.add(new Coche("Renault", "Captur", 2018, 42000, 13500, "5678EFG", true));
        listaCoches.add(new Coche("Fiat", "Panda", 2017, 55000, 9500, "9101HIJ", true));
        listaCoches.add(new Coche("Seat", "Arona", 2020, 28000, 17000, "2345KLM", false));
        listaCoches.add(new Coche("Volkswagen", "Tiguan", 2019, 32000, 19500, "6789NOP", true));
        listaCoches.add(new Coche("Renault", "Kadjar", 2021, 18000, 21000, "3456QRS", false));
        listaCoches.add(new Coche("Seat", "Ateca", 2019, 35000, 19000, "8901YZA", false));

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


}
