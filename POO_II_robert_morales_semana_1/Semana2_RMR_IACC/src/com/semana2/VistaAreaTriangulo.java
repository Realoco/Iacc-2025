package com.semana2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class VistaAreaTriangulo extends JFrame {

    private JTextField txtBase;
    private JTextField txtAltura;
    private JButton btnCalcular;
    private JLabel lblResultado;

    public VistaAreaTriangulo() {
        setTitle("Calculadora de Área de Triángulo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal
        JPanel contenido = new JPanel(new GridLayout(3, 2, 10, 10));
        contenido.setBorder(new EmptyBorder(20, 20, 10, 20));

        // Campos base y altura
        contenido.add(new JLabel("Base:"));
        txtBase = new JTextField();
        contenido.add(txtBase);

        contenido.add(new JLabel("Altura:"));
        txtAltura = new JTextField();
        contenido.add(txtAltura);

        // Panel para botón con 2 columnas
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnCalcular = new JButton("Calcular Área");
        panelBoton.add(btnCalcular);

        // Este panel se usará para mostrar el resultado centrado
        JPanel panelResultado = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lblResultado = new JLabel("");
        panelResultado.add(lblResultado);

        // Panel contenedor general (usa BorderLayout)
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.add(contenido, BorderLayout.NORTH);
        panelPrincipal.add(panelBoton, BorderLayout.CENTER);
        panelPrincipal.add(panelResultado, BorderLayout.SOUTH);

        // Agregar al frame
        add(panelPrincipal);

        // Acción del botón
        btnCalcular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calcularArea();
            }
        });

        setSize(600, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void calcularArea() {
        try {
            String textoBase = txtBase.getText().trim();
            String textoAltura = txtAltura.getText().trim();

            if (textoBase.isEmpty() || textoAltura.isEmpty()) {
                throw new NullPointerException("Debe completar ambos campos.");
            }

            double base = Double.parseDouble(textoBase);
            double altura = Double.parseDouble(textoAltura);

            double area = CalculadoraArea.calcularArea(base, altura);
            lblResultado.setText("Área del triángulo: " + area);
        } catch (NumberFormatException nfe) {
            lblResultado.setText("Error NumberFormatException: ingrese solo números válidos.");
        } catch (IllegalArgumentException iae) {
            lblResultado.setText("Error IllegalArgumentException(Propagada desde el metodo calcularArea) : " + iae.getMessage());
        } catch (NullPointerException npe) {
            lblResultado.setText("Error NullPointerException: " + npe.getMessage());
        } catch (Throwable ex) {
            lblResultado.setText("Error inesperado: " + ex.getMessage());
        }
    }
}
