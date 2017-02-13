/**
 * 
 */
package fr.epita.iam.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.iam.datamodel.Identity;

/**
 * @author Deepz
 *
 *Class to perform database services
 */
public class JDBCIdentityDAO {

	
	
	private Connection connection;
	
	/**
	 * @throws SQLException if there is an error in the sql query
	 * 
	 */
	public JDBCIdentityDAO() throws SQLException {
		this.connection = DriverManager.getConnection("jdbc:derby://localhost:1527/IAMCORE;create=true","deepak","deepak");
		connection.commit();
	}
	
	
	/**
	 * @param identity       Identity data 
	 * @throws SQLException  if there is an error in running the sql query
	 */
	public void writeIdentity(Identity identity) throws SQLException {
		
		String insertStatement = "insert into IDENTITIES (IDENTITIES_DISPLAYNAME, IDENTITIES_EMAIL, IDENTITIES_PHONENUMBER) "
				+ "values(?, ?, ?)";
		
		PreparedStatement pstmt_insert = connection.prepareStatement(insertStatement);
		
		
		pstmt_insert.setString(1, identity.getDisplayName());
		pstmt_insert.setString(2, identity.getEmail());
		pstmt_insert.setString(3, identity.getPhoneNumber());

		pstmt_insert.execute();
		
	

	}

	/**
	 * Function to read all the identities
	 * @return               returns all the identities
	 * @throws SQLException  if there is an error in running the sql query
	 */
	public List<Identity> readAll() throws SQLException {
		List<Identity> identities = new ArrayList<Identity>();

		PreparedStatement pstmt_select = connection.prepareStatement("select * from IDENTITIES");
		ResultSet rs = pstmt_select.executeQuery();
		while (rs.next()) {
			String displayName = rs.getString("IDENTITIES_DISPLAYNAME");
			String uid = String.valueOf(rs.getString("IDENTITIES_UID"));
			String email = rs.getString("IDENTITIES_EMAIL");
			String phoneNumber = rs.getString("IDENTITIES_PHONENUMBER");
			Identity identity = new Identity(uid, displayName, email, phoneNumber);
			identities.add(identity);
		}
		return identities;

	}


	/**
	 * Function to update the identity table
	 * 
	 * @param newIdentity    Identity to be updated
	 * @return               returns the number of rows
	 * @throws SQLException  if there is an error in running the sql query
	 */
	public boolean updateIdentity(Identity newIdentity)throws SQLException {
		
		String updateStatement = "UPDATE IDENTITIES SET IDENTITIES_DISPLAYNAME=?,IDENTITIES_EMAIL=?,IDENTITIES_PHONENUMBER=? WHERE IDENTITIES_UID=?";
		int no_of_rows;
		PreparedStatement pstmt_update = connection.prepareStatement(updateStatement);
		pstmt_update.setString(1, newIdentity.getDisplayName());
		pstmt_update.setString(2, newIdentity.getEmail());
		pstmt_update.setString(3, newIdentity.getPhoneNumber());
		pstmt_update.setString(4, newIdentity.getUid());
		no_of_rows=pstmt_update.executeUpdate();
		return(no_of_rows>0);
		
	}

    /**
     * Function to delete from the identity table
     * 
     * @param newIdentity    Identity to be deleted
     * @return               returns the number of deleted rows
     * @throws SQLException  if there is an error in running the sql query
     */
    public boolean deleteIdentity(Identity newIdentity)throws SQLException {
		
		String deleteStatement = "DELETE from IDENTITIES where IDENTITIES_DISPLAYNAME=?";
		int no_of_rows;
		PreparedStatement pstmt_delete = connection.prepareStatement(deleteStatement);
	
		pstmt_delete.setString(1, newIdentity.getDisplayName());
		no_of_rows=pstmt_delete.executeUpdate();
		return (no_of_rows>0);
		
		
		
	}

    /**
     * Function to find the matching identity
     * 
     * @param oldDisplayName  Display name to be matched
     * @return                returns the matching identity
     * @throws SQLException   if there is an error in running the sql query
     */
    public List<Identity> findIdentity (String oldDisplayName) throws SQLException {
		List<Identity> matchingIdentity = new ArrayList<Identity>();

		PreparedStatement pstmt_select = connection.prepareStatement("select * from IDENTITIES WHERE IDENTITIES_DISPLAYNAME=?");
		pstmt_select.setString(1, oldDisplayName);
		ResultSet rs = pstmt_select.executeQuery();

		while (rs.next()) {
		
	
		String displayName = rs.getString("IDENTITIES_DISPLAYNAME");
		String uid = String.valueOf(rs.getString("IDENTITIES_UID"));
		String email = rs.getString("IDENTITIES_EMAIL");
		String phoneNumber=rs.getString("IDENTITIES_PHONENUMBER");
		
		Identity identity = new Identity(uid, displayName, email,phoneNumber);
		matchingIdentity.add(identity);
	}
	
	pstmt_select.close();
	return matchingIdentity;


}
}
