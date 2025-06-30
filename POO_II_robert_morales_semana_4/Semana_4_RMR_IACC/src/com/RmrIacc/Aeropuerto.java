package com.RmrIacc;

public class Aeropuerto {
    public static void main(String[] args) {
        Thread avion1 = new Thread(new Avion("Avion1", "despegando"));
        Thread avion2 = new Thread(new Avion("Avion2", "aterrizando"));

        avion1.start();
        avion2.start();
    }
}
