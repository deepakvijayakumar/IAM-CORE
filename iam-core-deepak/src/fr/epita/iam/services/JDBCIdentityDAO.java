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
 *Class to perform database service
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
		
		PreparedStatement pstmtInsert = connection.prepareStatement(insertStatement);
		
		
		pstmtInsert.setString(1, identity.getDisplayName());
		pstmtInsert.setString(2, identity.getEmail());
		pstmtInsert.setString(3, identity.getPhoneNumber());

		pstmtInsert.execute();
		pstmtInsert.close();
		
	

	}

	/**
	 * Function to read all the identities
	 * @return               returns all the identities
	 * @throws SQLException  if there is an error in running the sql query
	 */
	public List<Identity> readAll() throws SQLException {
		List<Identity> identities = new ArrayList<>();

		PreparedStatement pstmtSelect = connection.prepareStatement("select * from IDENTITIES");
		ResultSet rs = pstmtSelect.executeQuery();
		while (rs.next()) {
			String displayName = rs.getString("IDENTITIES_DISPLAYNAME");
			String uid = String.valueOf(rs.getString("IDENTITIES_UID"));
			String email = rs.getString("IDENTITIES_EMAIL");
			String phoneNumber = rs.getString("IDENTITIES_PHONENUMBER");
			Identity identity = new Identity(uid, displayName, email, phoneNumber);
			identities.add(identity);
		}
		pstmtSelect.close();
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
		int numberOfRows;
		PreparedStatement pstmtUpdate = connection.prepareStatement(updateStatement);
		pstmtUpdate.setString(1, newIdentity.getDisplayName());
		pstmtUpdate.setString(2, newIdentity.getEmail());
		pstmtUpdate.setString(3, newIdentity.getPhoneNumber());
		pstmtUpdate.setString(4, newIdentity.getUid());
		numberOfRows=pstmtUpdate.executeUpdate();
		pstmtUpdate.close();
		return numberOfRows>0;
		
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
		int numberOfRows;
		PreparedStatement pstmtDelete = connection.prepareStatement(deleteStatement);
	
		pstmtDelete.setString(1, newIdentity.getDisplayName());
		numberOfRows=pstmtDelete.executeUpdate();
		pstmtDelete.close();
		return numberOfRows>0;
		
		
		
	}

    /**
     * Function to find the matching identity
     * 
     * @param oldDisplayName  Display name to be matched
     * @return                returns the matching identity
     * @throws SQLException   if there is an error in running the sql query
     */
    public List<Identity> findIdentity (String oldDisplayName) throws SQLException {
		List<Identity> matchingIdentity = new ArrayList<>();

		PreparedStatement pstmtSelect = connection.prepareStatement("select * from IDENTITIES WHERE IDENTITIES_DISPLAYNAME=?");
		pstmtSelect.setString(1, oldDisplayName);
		ResultSet rs = pstmtSelect.executeQuery();

		while (rs.next()) {
		
	
		String displayName = rs.getString("IDENTITIES_DISPLAYNAME");
		String uid = String.valueOf(rs.getString("IDENTITIES_UID"));
		String email = rs.getString("IDENTITIES_EMAIL");
		String phoneNumber=rs.getString("IDENTITIES_PHONENUMBER");
		
		Identity identity = new Identity(uid, displayName, email,phoneNumber);
		matchingIdentity.add(identity);
	}
	
	pstmtSelect.close();
	return matchingIdentity;


}
}
