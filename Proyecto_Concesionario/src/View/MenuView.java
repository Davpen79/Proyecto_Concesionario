package View;

import java.util.Scanner;

public class MenuView {

    public static int menuPrincipal() {

        int opcion = 0;

        System.out.println("Bienvenido a AutoGanga. ¿Que deseas hacer?");
        System.out.println("1. Añadir coches al concesionario");
        System.out.println("2. Mostrar los coches a la venta");
        System.out.println("3. Buscar coches");
        System.out.println("4. Registrar un nuevo cliente");
        System.out.println("5. Registrar una venta");
        System.out.println("6. Mostrar ventas");
        System.out.println("7. Salir");

        while(true) {
            Scanner sc = new Scanner(System.in);
            opcion = sc.nextInt();
            if (opcion < 1 || opcion >7 ) {
                break;
            }
        }
        System.out.println(opcion);
        return opcion;
    }

}
