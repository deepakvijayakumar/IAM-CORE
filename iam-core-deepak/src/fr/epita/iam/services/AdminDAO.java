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
 *Class for database services of admin login
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
		
		PreparedStatement pstmt_authenticate = connection.prepareStatement("SELECT ADMIN_USERNAME, ADMIN_PASSWORD FROM ADMINS where ADMIN_USERNAME=?");
		
		pstmt_authenticate.setString(1, username);
			
		
		ResultSet output= pstmt_authenticate.executeQuery();
		
		while (output.next()) {
			
			return username.equals(output.getString("ADMIN_USERNAME")) && password.equals(output.getString("ADMIN_PASSWORD")); 
		}
		
		return false;


	}

}
