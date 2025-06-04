package juego;

import java.awt.Image;
import java.util.Random;

import entorno.Entorno;

public class Murcielago {
    private double x;
    private double y;
    private double radio;
    private boolean colision;
    private boolean atacando;
    private final Random random = new Random();
    private final double probabilidadPocion = 0.5; // 
    private Image imagen = entorno.Herramientas.cargarImagen("images/bat4.gif");

    public Murcielago(double x, double y) {
        this.x = x;
        this.y = y;
        this.radio = 10;
        this.colision = false;
        this.atacando = false;
    }

    // Método para generar una poción al morir (si hay suerte)
    public Pocion generarPocion() {
        if (random.nextDouble() < probabilidadPocion) {
            return new Pocion(x, y);
        }
        return null;
    }


    public void dibujarse(Entorno entorno) {
        entorno.dibujarImagen(imagen, x, y, 0);
    }

    public void seguirMago(Personaje personaje) {
        if (x < personaje.getX()) x++;
        if (x > personaje.getX()) x--;
        if (y < personaje.getY()) y++;
        if (y > personaje.getY()) y--;
    }

    public boolean colision(Personaje personaje) {
        double distancia = Math.hypot(x - personaje.getX(), y - personaje.getY());
        if (distancia <= (radio + personaje.getRadio())) {
            atacando = true;
            personaje.setEsAtacado(true);
            return true;
        }
        return false;
    }



    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }

    public double getRadio() {
        return radio;
    }
    public void setRadio(double radio) {
        this.radio = radio;
    }
    public boolean isColision() {
        return colision;
    }
    public void setColision(boolean colision) {
        this.colision = colision;
    }
    public boolean isAtacando() {
        return atacando;
    }
    public void setAtacando(boolean atacando) {
        this.atacando = atacando;
    }





}

