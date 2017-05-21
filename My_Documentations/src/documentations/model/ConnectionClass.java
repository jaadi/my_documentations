package documentations.model;

import java.sql.*;

import javax.swing.JOptionPane;

public class ConnectionClass {
	
	public static Connection Connect() {

		Connection con = null;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager
					.getConnection("jdbc:sqlite:resources\\documentations.sqlite");			
			con.setAutoCommit(false);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			System.exit(0);
			return null;
		}
		
		return con;		

	}

}
