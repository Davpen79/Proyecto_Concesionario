
import Controller.GestionConcesionario;
import View.MenuView;

public class Programa {


    public static void main(String[] args) {

        MenuView view = new MenuView();
        GestionConcesionario gestionControlador = new GestionConcesionario(view);
        gestionControlador.run();

    }
}
