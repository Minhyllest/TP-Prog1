package juego;

import entorno.Entorno;
import java.awt.Color;

public class Menu {
    private boolean mostrarSeleccionPersonaje = false;

    public void dibujarMenuPrincipal(Entorno entorno) {
        entorno.cambiarFont("Arial", 40, Color.WHITE);
        entorno.escribirTexto("FEAR AND HUNGER ARCADE", 360, 340);
        entorno.cambiarFont("Arial", 30, Color.WHITE);
        entorno.escribirTexto("Presione ENTER para comenzar", 420, 680);
    }

    public void dibujarSeleccionPersonaje(Entorno entorno, int seleccion) {
        entorno.cambiarFont("Arial", 30, Color.WHITE);
        entorno.escribirTexto("Seleccione su personaje:", 450, 300);
        entorno.escribirTexto((seleccion == 0 ? "→ " : "") + "ENKI", 500, 370);
        entorno.escribirTexto((seleccion == 1 ? "→ " : "") + "O'SAA", 500, 420);
    }

    public void setMostrarSeleccionPersonaje(boolean mostrar) {
        this.mostrarSeleccionPersonaje = mostrar;
    }

    public boolean estaSeleccionandoPersonaje() {
        return mostrarSeleccionPersonaje;
    }
}


