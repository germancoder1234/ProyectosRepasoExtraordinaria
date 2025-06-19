package menu_restaurante;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

public class AppMenu {

    private JFrame frame;
    private JTable table;
    private JTextField textFieldIdComida;
    private JTextField textNombrePlato;
    private JTextField textFieldPrecioPlato;
    private JScrollPane scrollPane;
    private JTextField textFieldNumeroUnidades;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AppMenu window = new AppMenu();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AppMenu() {
        initialize();
    }

    private void cargarTabla(DefaultTableModel model, String query) {
        try {
            Connection con = ConnectionSingleton.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Object[] row = new Object[3];
                row[0] = rs.getInt("id_comida");
                row[1] = rs.getString("nombre");
                row[2] = rs.getDouble("precio");
                model.addRow(row);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 449, 644);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("codigo comida");
        model.addColumn("nombre del plato");
        model.addColumn("precio del plato");

        cargarTabla(model, "SELECT * FROM carta");

        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 25, 365, 186);
        frame.getContentPane().add(scrollPane);

        JButton btnMostrar = new JButton("Mostrar");
        btnMostrar.addActionListener(e -> {
            int indice = table.getSelectedRow();
            if (indice >= 0) {
                TableModel modelo = table.getModel();
                textFieldIdComida.setText(modelo.getValueAt(indice, 0).toString());
                textNombrePlato.setText(modelo.getValueAt(indice, 1).toString());
                textFieldPrecioPlato.setText(modelo.getValueAt(indice, 2).toString());
            }
        });
        btnMostrar.setBounds(171, 0, 89, 23);
        frame.getContentPane().add(btnMostrar);

        JButton btnInsertar = new JButton("Insertar");
        btnInsertar.addActionListener(e -> {
        	
        

            try {
                Connection con = ConnectionSingleton.getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO carta (nombre, precio) VALUES (?, ?)");

                String nombre = textNombrePlato.getText();
                double precio = Double.parseDouble(textFieldPrecioPlato.getText());
                ps.setString(1, nombre);
                ps.setDouble(2, precio);
                ps.executeUpdate();
                ps.close();
                con.close();

                model.setRowCount(0);
                cargarTabla(model, "SELECT * FROM carta");
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Error al insertar");
            }
        });
        btnInsertar.setBounds(30, 273, 89, 23);
        frame.getContentPane().add(btnInsertar);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> {
    
        
            try {
                Connection con = ConnectionSingleton.getConnection();
                PreparedStatement ps = con.prepareStatement("UPDATE carta SET nombre = ?, precio = ? WHERE id_comida = ?");
                ps.setString(1, textNombrePlato.getText());
                ps.setDouble(2, Double.parseDouble(textFieldPrecioPlato.getText()));
                ps.setInt(3, Integer.parseInt(textFieldIdComida.getText()));
                ps.executeUpdate();
                ps.close();
                con.close();

                model.setRowCount(0);
                cargarTabla(model, "SELECT * FROM carta");
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Datos no válidos para actualizar.");
            }
        });
        btnActualizar.setBounds(306, 273, 89, 23);
        frame.getContentPane().add(btnActualizar);

        JButton btnBorrar = new JButton("Borrar");
        btnBorrar.addActionListener(e -> {
       
        
            try {
                Connection con = ConnectionSingleton.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM carta WHERE id_comida = ?");
                ps.setInt(1, Integer.parseInt(textFieldIdComida.getText()));
                ps.executeUpdate();
                ps.close();
                con.close();

                model.setRowCount(0);
                cargarTabla(model, "SELECT * FROM carta");

                textFieldIdComida.setText("");
                textNombrePlato.setText("");
                textFieldPrecioPlato.setText("");
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "ID no válido para borrar.");
            }
        });
        btnBorrar.setBounds(171, 273, 89, 23);
        frame.getContentPane().add(btnBorrar);

        textFieldIdComida = new JTextField();
        textFieldIdComida.setEditable(false);
        textFieldIdComida.setBounds(30, 333, 86, 20);
        frame.getContentPane().add(textFieldIdComida);
        textFieldIdComida.setColumns(10);

        textNombrePlato = new JTextField();
        textNombrePlato.setBounds(171, 333, 86, 20);
        frame.getContentPane().add(textNombrePlato);
        textNombrePlato.setColumns(10);

        textFieldPrecioPlato = new JTextField();
        textFieldPrecioPlato.setBounds(306, 333, 86, 20);
        frame.getContentPane().add(textFieldPrecioPlato);
        textFieldPrecioPlato.setColumns(10);

        JLabel lblIdComida = new JLabel("codigo comida");
        lblIdComida.setBounds(40, 307, 68, 14);
        frame.getContentPane().add(lblIdComida);

        JLabel lblNombre = new JLabel("nombre comida");
        lblNombre.setBounds(188, 308, 72, 14);
        frame.getContentPane().add(lblNombre);

        JLabel lblPrecio = new JLabel("precio comida");
        lblPrecio.setBounds(316, 308, 79, 14);
        frame.getContentPane().add(lblPrecio);

        JComboBox comboBoxComidas = new JComboBox();
        comboBoxComidas.setBounds(87, 376, 100, 66);
        frame.getContentPane().add(comboBoxComidas);

        // Poblar comboBoxComidas con los IDs de comida
        try {
            Connection con = ConnectionSingleton.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT id_comida FROM carta");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                comboBoxComidas.addItem(rs.getInt("id_comida"));
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        textFieldNumeroUnidades = new JTextField();
        textFieldNumeroUnidades.setBounds(230, 399, 86, 20);
        frame.getContentPane().add(textFieldNumeroUnidades);
        textFieldNumeroUnidades.setColumns(10);

        JButton btnCobrar = new JButton("Cobrar");
        btnCobrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String unidadesTxt = textFieldNumeroUnidades.getText().trim();
                    if (unidadesTxt.isEmpty()) {
                        throw new NumberFormatException();
                    }
                    int unidades = Integer.parseInt(unidadesTxt);
                    Object sel = comboBoxComidas.getSelectedItem();
                    if (sel == null) {
                        throw new IllegalArgumentException();
                    }
                    int idplato = (int) sel;
                    if (unidades <= 0) {
                        throw new IllegalArgumentException();
                    }
                    Connection con = ConnectionSingleton.getConnection();
                    String sql = "select precio as precioplato from carta where id_comida = ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setInt(1, idplato);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        double precio = rs.getDouble("precioplato");
                        double total = precio * unidades;
                        JOptionPane.showMessageDialog(frame,
                            "El precio total de " + unidades + " unidades (id " + idplato + ") es " + total + " €.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "No se encontró el plato con id " + idplato + ".");
                    }
                    rs.close();
                    pst.close();
                    con.close();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Introduce un número de unidades válido (>0).");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, "Selecciona un plato e introduce unidades >0.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error al calcular el precio.");
                }
            }
        });
        btnCobrar.setBounds(149, 475, 89, 23);
        frame.getContentPane().add(btnCobrar);
    
}
}
