package com.semana2;

/**
 * Clase que contiene el método para calcular el área del triángulo. aqui utilizamos "throws" para propagar la excepción al método que lo llame.
 */
public class CalculadoraArea {

    public static double calcularArea(double base, double altura) throws IllegalArgumentException {
        if (base <= 0 || altura <= 0) // Validacion de valores mayores a 0 
        {	
            throw new IllegalArgumentException("La base y la altura deben ser mayores que cero.");  // Mensaje de salida al usuario
        }

        return (base * altura) / 2;	// Realiza el calculo y devuelve el resultado
    }
}
