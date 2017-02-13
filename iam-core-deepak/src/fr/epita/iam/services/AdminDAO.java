/**
 * 
 */
package fr.epita.iam.services;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

/**
 * @author Deepz
 *
 *Class for database services of admin logins
 */



public class AdminDAO {
	
	private Connection connection;
	
	/**
	 * @throws SQLException  if there is an error in running the sql query
	 */
	public AdminDAO() throws SQLException {
		this.connection = DriverManager.getConnection("jdbc:derby://localhost:1527/IAMCORE;create=true","deepak","deepak");
		connection.commit();
	}
	
	/**
	 * Function to check authentication of admin login
	 * 
	 * @param username      username of the admin
	 * @param password      password of the admin
	 * @return              returns whether authentication is success or not
	 * @throws SQLException if there is an error in running the sql query
	 */
	public boolean authenticate(String username, String password) throws SQLException {
		
		PreparedStatement pstmtAuthenticate = connection.prepareStatement("SELECT ADMIN_USERNAME, ADMIN_PASSWORD FROM ADMINS where ADMIN_USERNAME=?");
		
		pstmtAuthenticate.setString(1, username);
			
		
		ResultSet rs= pstmtAuthenticate.executeQuery();
		
		while (rs.next()) {
			
			return username.equals(rs.getString("ADMIN_USERNAME")) && password.equals(rs.getString("ADMIN_PASSWORD")); 
		}
		pstmtAuthenticate.close();
		return false;


	}

}
