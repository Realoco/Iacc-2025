package com.semana7RMR;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.sql.*;

public class VideojAppRMR {
    private Connection connection;
    private JFrame frame;
    private JTextField idField, tituloField, generoField, clasificacionField, plataformaField;
    private JTextArea resultTextArea;

    public VideojAppRMR() {
        initialize();
        connDatabase();
        generaGUI();
    }

    private void connDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3406/iacc_rmr_semana_7", "root", "xxxx");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error al conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void initialize() {
        frame = new JFrame("Biblioteca de Videojuegos");
        frame.setBounds(100, 100, 800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

	private void generaGUI() {
	    frame.setLayout(new BorderLayout());
	
	    JPanel mainPanel = new JPanel(new BorderLayout());
	    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
	    frame.add(mainPanel);
	
	    // Panel de botones con BoxLayout horizontal
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
	
	    JButton consultarBtn = new JButton("Consultar");
	    JButton insertarBtn = new JButton("Insertar");
	    JButton actualizarBtn = new JButton("Actualizar");
	    JButton borrarBtn = new JButton("Borrar");
	
	    buttonPanel.add(consultarBtn);
	    buttonPanel.add(Box.createRigidArea(new Dimension(10, 3)));
	    buttonPanel.add(insertarBtn);
	    buttonPanel.add(Box.createRigidArea(new Dimension(10, 3)));
	    buttonPanel.add(actualizarBtn);
	    buttonPanel.add(Box.createRigidArea(new Dimension(10, 3)));
	    buttonPanel.add(borrarBtn);
	
	    mainPanel.add(buttonPanel, BorderLayout.NORTH);
	
	    // Panel de campos
	    JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
	    idField = new JTextField(); tituloField = new JTextField();
	    generoField = new JTextField(); clasificacionField = new JTextField(); plataformaField = new JTextField();
	
	    inputPanel.add(new JLabel("ID:")); inputPanel.add(idField);
	    inputPanel.add(new JLabel("Título:")); inputPanel.add(tituloField);
	    inputPanel.add(new JLabel("Género:")); inputPanel.add(generoField);
	    inputPanel.add(new JLabel("Clasificación:")); inputPanel.add(clasificacionField);
	    inputPanel.add(new JLabel("Plataforma:")); inputPanel.add(plataformaField);
	
	    mainPanel.add(inputPanel, BorderLayout.CENTER);
	
	    // Área de resultados
	    resultTextArea = new JTextArea(10, 50);
	    resultTextArea.setEditable(false);
	    resultTextArea.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	            int offset = resultTextArea.viewToModel(e.getPoint());
	            try {
	                int rowStart = Utilities.getRowStart(resultTextArea, offset);
	                int rowEnd = Utilities.getRowEnd(resultTextArea, offset);
	                String selectedText = resultTextArea.getText(rowStart, rowEnd - rowStart);
	                cargarDatosSeleccionados(selectedText);
	            } catch (BadLocationException ex) {
	                ex.printStackTrace();
	            }
	        }
	    });
	    mainPanel.add(new JScrollPane(resultTextArea), BorderLayout.SOUTH);
	
	    // Acciones de los botones
	    consultarBtn.addActionListener(e -> consultarVideojuegos());
	    insertarBtn.addActionListener(e -> insertarVideojuego());
	    actualizarBtn.addActionListener(e -> actualizarVideojuego());
	    borrarBtn.addActionListener(e -> borrarVideojuego());
	
	    frame.pack();
	    frame.setVisible(true);
	}

    private void consultarVideojuegos() {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Videojuegos")) {
            resultTextArea.setText("");
            while (rs.next()) {
                resultTextArea.append(rs.getInt("ID") + " | " + rs.getString("Titulo") + " | " +
                        rs.getString("Genero") + " | " + rs.getString("Clasificacion") + " | " +
                        rs.getString("Plataforma") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertarVideojuego() {
        try (Statement stmt = connection.createStatement()) {
            int id = Integer.parseInt(idField.getText());
            String sql = "INSERT INTO Videojuegos (ID, Titulo, Genero, Clasificacion, Plataforma) VALUES (" +
                    id + ", '" + tituloField.getText() + "', '" + generoField.getText() + "', '" +
                    clasificacionField.getText() + "', '" + plataformaField.getText() + "')";
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(frame, "Videojuego insertado con éxito");
            limpiarCampos();
            consultarVideojuegos();
        } catch (SQLException e) { JOptionPane.showMessageDialog(frame, "Error al insertar", "Error", JOptionPane.ERROR_MESSAGE); }
    }

    private void actualizarVideojuego() {
        try (Statement stmt = connection.createStatement()) {
            int id = Integer.parseInt(idField.getText());
            String sql = "UPDATE Videojuegos SET Titulo='" + tituloField.getText() + "', Genero='" + generoField.getText() +
                    "', Clasificacion='" + clasificacionField.getText() + "', Plataforma='" + plataformaField.getText() +
                    "' WHERE ID=" + id;
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(frame, "Videojuego actualizado con éxito");
            limpiarCampos();
            consultarVideojuegos();
        } catch (SQLException e) { JOptionPane.showMessageDialog(frame, "Error al actualizar", "Error", JOptionPane.ERROR_MESSAGE); }
    }

    private void borrarVideojuego() {
        try (Statement stmt = connection.createStatement()) {
            int id = Integer.parseInt(idField.getText());
            stmt.executeUpdate("DELETE FROM Videojuegos WHERE ID=" + id);
            JOptionPane.showMessageDialog(frame, "Videojuego eliminado con éxito");
            limpiarCampos();
            consultarVideojuegos();
        } catch (SQLException e) { JOptionPane.showMessageDialog(frame, "Error al borrar", "Error", JOptionPane.ERROR_MESSAGE); }
    }

    private void cargarDatosSeleccionados(String selectedText) {
        String[] parts = selectedText.split(" \\| ");
        if (parts.length == 5) {
            idField.setText(parts[0].trim());
            tituloField.setText(parts[1].trim());
            generoField.setText(parts[2].trim());
            clasificacionField.setText(parts[3].trim());
            plataformaField.setText(parts[4].trim());
        }
    }


    private void limpiarCampos() {
        idField.setText(""); tituloField.setText(""); generoField.setText(""); clasificacionField.setText(""); plataformaField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VideojAppRMR::new);
    }
}
