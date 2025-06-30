package com.RmrIacc;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Avion implements Runnable {
    private String nombreAvion;
    private String operacion;

    public Avion(String nombreAvion, String operacion) {
        this.nombreAvion = nombreAvion;
        this.operacion = operacion;
    }

    @Override
    public void run() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaInicio = LocalTime.now().format(formatter);
        System.out.println(nombreAvion + " está " + operacion + " a las " + horaInicio + "...");

        try {
            if (operacion.equalsIgnoreCase("despegando")) {
            	System.out.println(nombreAvion + " :Esperando Autorizacion para despegue " + horaInicio + "...");
                Thread.sleep(3000);
            } else if (operacion.equalsIgnoreCase("aterrizando")) {
            	System.out.println(nombreAvion + " :Esperando Autorizacion para Aterrizar " + horaInicio + "...");
                Thread.sleep(5000); 
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String horaFin = LocalTime.now().format(formatter);
        System.out.println(nombreAvion + " ha " + operacion + " a las " + horaFin + ".");
    }
}
