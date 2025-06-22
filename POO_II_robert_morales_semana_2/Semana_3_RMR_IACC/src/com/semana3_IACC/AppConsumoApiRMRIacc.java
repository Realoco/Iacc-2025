package com.semana3_IACC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AppConsumoApiRMRIacc extends JFrame {
    private JButton btnConsultar;
    private JTextField txtFecha, txtMoneda, txtValor;

    public AppConsumoApiRMRIacc() {
        setTitle("Consumo API NodeJS");
        setSize(380, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lbl1 = new JLabel("Fecha:");
        lbl1.setBounds(30, 30, 80, 20);
        add(lbl1);

        txtFecha = new JTextField();
        txtFecha.setBounds(100, 30, 220, 20);
        txtFecha.setEditable(false);
        add(txtFecha);

        JLabel lbl2 = new JLabel("Moneda:");
        lbl2.setBounds(30, 60, 80, 20);
        add(lbl2);

        txtMoneda = new JTextField();
        txtMoneda.setBounds(100, 60, 220, 20);
        txtMoneda.setEditable(false);
        add(txtMoneda);

        JLabel lbl3 = new JLabel("Valor:");
        lbl3.setBounds(30, 90, 80, 20);
        add(lbl3);

        txtValor = new JTextField();
        txtValor.setBounds(100, 90, 220, 20);
        txtValor.setEditable(false);
        add(txtValor);

        // Botón al final
        btnConsultar = new JButton("Consultar API");
        btnConsultar.setBounds(120, 140, 140, 30);
        add(btnConsultar);

        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consultarApi();
            }
        });
    }

    private void consultarApi() {
        try {
            URL url = new URL("http://localhost:3000");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONParser parser = new JSONParser();
                JSONObject root = (JSONObject) parser.parse(response.toString());

                JSONArray tipoCambioArray = (JSONArray) root.get("tipo_cambio");
                if (tipoCambioArray != null && !tipoCambioArray.isEmpty()) {
                    JSONObject tipoCambio = (JSONObject) tipoCambioArray.get(0);

                    String fecha = (String) tipoCambio.get("fecha");
                    String moneda = (String) tipoCambio.get("moneda");
                    double valor = Double.parseDouble(tipoCambio.get("valor").toString());

                    txtFecha.setText(fecha);
                    txtMoneda.setText(moneda);
                    txtValor.setText(String.valueOf(valor));
                } else {
                    JOptionPane.showMessageDialog(this, "No hay datos en tipo_cambio");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Error al conectar con la API");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppConsumoApiRMRIacc app = new AppConsumoApiRMRIacc();
            app.setVisible(true);
        });
    }
}
