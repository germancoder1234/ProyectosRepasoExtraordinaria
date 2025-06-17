package menu_restaurante;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {

	private static Connection con;
	
	public static Connection getConnection() throws SQLException{
		String url = "jdbc:mysql://127.0.0.1:3307/comidas";
		String usuario= "alumno";
		String passwd= "Gerosol2006!";
		
		if (con == null || con.isClosed()) {
			con=DriverManager.getConnection(url, usuario, passwd);
		}
		return con;
	}
	
	
}
