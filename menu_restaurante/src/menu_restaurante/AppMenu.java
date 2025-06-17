package menu_restaurante;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class AppMenu {

	private JFrame frame;
	private JTable table;
	private JTextField textFieldIdComida;
	private JTextField textNombrePlato;
	private JTextField textFieldPrecioPlato;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppMenu window = new AppMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 449, 644);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("id");
		model.addColumn("nombre del plato");
		model.addColumn("precio del plato");

		try {

			Connection con = ConnectionSingleton.getConnection();

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM comidas");
			while (rs.next()) {

				Object[] row = new Object[3];
				row[0] = rs.getInt("id_comida");
				row[0] = rs.getInt("nombre");
				row[0] = rs.getInt("precio");
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException ex) {

			ex.printStackTrace();
			System.err.println(ex.getMessage());

		}
		table = new JTable();
		table.setBounds(30, 25, 365, 186);
		frame.getContentPane().add(table);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 25, 365, 186);
		frame.getContentPane().add(scrollPane);
		

		JButton btnInsertar = new JButton("Insertar");
		btnInsertar.setBounds(30, 273, 89, 23);
		frame.getContentPane().add(btnInsertar);

		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.setBounds(306, 273, 89, 23);
		frame.getContentPane().add(btnActualizar);

		JButton btnBorrar = new JButton("Borrar");
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
		
	
	}
}
