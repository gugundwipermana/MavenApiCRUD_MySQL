package com.gudperna.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil
{
	public static Connection getConnection()
	{
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false", "root", "permana");
			
		} catch(ClassNotFoundException cnfe) {
			System.err.println("Class error: "+cnfe.getMessage());
		} catch(SQLException se) {
			System.err.println("SQL error: "+se.getMessage());
		} catch(Exception e) {
			System.err.println("Error: "+e.getMessage());
		}

		return conn;
	}
}