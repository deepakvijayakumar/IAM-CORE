/**
 * 
 */
package fr.epita.iam.tests;

import fr.epita.iam.datamodel.Identity;

/**
 * @author deepz
 *
 */
public class TestIdentity {
	
	
	public static void main(String[] args) {
		Identity identity = new Identity("0","deepak", "deepakr@gmail.com.com","1234");
		System.out.println(identity);
	}
}
