/**
 * 
 */
package fr.epita.iam.datamodel;


/**
 * @author Deepz
 *
 *Class used for the storing the identities data
 */
public class Identity {
	
	private String uid;
	private String displayName;
	private String email;
	private String phoneNumber;
	
	/**
	 * Parametrised constructor
	 * @param uid            unique user id
	 * @param displayName    display name of the user
	 * @param email          email id of the user
	 * @param phoneNumber    phone number of the user
	 */
	public Identity(String uid, String displayName, String email, String phoneNumber) {
		
		this.uid = uid;
		this.displayName = displayName;
		this.email = email;
		this.setPhoneNumber(phoneNumber);
	}
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Identity [uid=" + uid + ", displayName=" + displayName + ", email=" + email + ", phoneNumber="
				+ phoneNumber + "]";
	}


	
	
	

}
