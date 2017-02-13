/**
 * 
 */
package fr.epita.iam.launcher;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.AdminDAO;
import fr.epita.iam.services.JDBCIdentityDAO;

/**
 * @author Deepz
 *Class for launching the IAM application
 */
public class ConsoleLauncher {
	
	private static JDBCIdentityDAO dao;
	private static AdminDAO adminDao;

	/**
	 * Main function
	 * @param args         Default string array for taking command line arguments
	 * @throws Exception   Any exception thrown by the function
	 */
	public static void main(String[] args) throws Exception {
		
		System.out.println("Hello, Welcome to the IAM application");
		Scanner scanner = new Scanner(System.in);
		dao = new JDBCIdentityDAO();
		adminDao=new AdminDAO();
		
		//authentication for admin access
		System.out.println("Please enter your username");
		String username = scanner.nextLine();
		System.out.println("Please enter your password");
		String password = scanner.nextLine();
		
		if(!authenticate(username, password)){
			System.out.println("Invalid username or password");
			scanner.close();
			return;
		}
		String answer;
		// menu displaying the various actions
		do{
			
		answer = menu(scanner);
		
		switch (answer) {
		case "a":
			// creating the identity
			createIdentity(scanner);
			break;
		case "b":
			//updating the identity
			updateIdentity(scanner);
			break;
		case "c":
			// the identity
			deleteIdentity(scanner);
			break;
		case "d":
			//listing the identities
			listIdentities();
			break;
		case "e":
			//exiting the application
			System.out.println("You have exited the IAM application");
		default:
			System.out.println("This option is not recognized ("+ answer + ")");
			break;
		}
		
		}while(!answer.equals("e"));
		scanner.close();

	}
	
	/**
	 * Function for creating the identity
	 * 
	 * @param scanner  Used to read data from the user
	 * @throws SQLException If there is an error in the SQL query
	 */
	private static void createIdentity(Scanner scanner) throws SQLException {
		System.out.println("IDENTITY CREATION");
		System.out.println("------------------");
		System.out.println("Please enter the Identity display name");
		String displayName = scanner.nextLine();
		System.out.println("Please enter the Identity email");
		String email = scanner.nextLine();
		System.out.println("Please enter the Identity phone number");
		String phoneNumber = scanner.nextLine();
		Identity newIdentity = new Identity(null, displayName, email,phoneNumber);
		dao.writeIdentity(newIdentity);
		System.out.println("Succesfully created the identity");
		
	}

	/**
	 * Function for updating the identity
	 * 
	 * @param scanner       used to read data from the user
	 * @throws SQLException if there is an error in running the sql query 
	 */
	private static void updateIdentity(Scanner scanner) throws SQLException {
		System.out.println("IDENTITY UPDATE ");
		System.out.println("----------------");
		System.out.println("Please enter the display name of the person whose details have to be updated");
		String oldDisplayName = scanner.nextLine();
		System.out.println("Please enter the new Identity display name");
		String displayName = scanner.nextLine();
		System.out.println("Please enter the new identity email");
		String email = scanner.nextLine();
		System.out.println("Please enter the new identity phone number");
		String phoneNumber = scanner.nextLine();
		
		List <Identity> list = dao.findIdentity (oldDisplayName);
		Identity newIdentity = new Identity(list.get(0).getUid(), displayName, email,phoneNumber);
		if (dao.updateIdentity(newIdentity))
			System.out.println("Successfully updated the identity");
		else
			System.out.println("Identity do not exist");
	}
	
	/**
	 * @param scanner       used to read data from the user
	 * @throws SQLException if there is an error in running the sql query
	 */
	private static void deleteIdentity(Scanner scanner) throws SQLException {
		System.out.println("IDENTITY DELETE");
		System.out.println("-----------------");
		System.out.println("Please enter the Display name");
		String displayName = scanner.nextLine();
		Identity newIdentity = new Identity(null,displayName,null,null);
		if (dao.deleteIdentity(newIdentity))
			System.out.println("Successfully deleted the identity");
		else
			System.out.println("Identity do not exist");

	}
	/**
	 * @throws SQLException if there is an error in the SQL query
	 * 
	 */
	private static void listIdentities() throws SQLException {
		System.out.println("This is the list of all identities in the system");
		List<Identity> list = dao.readAll();
		int size = list.size();
		for(int i = 0; i < size; i++){
			System.out.println( (i+1)+ "." + list.get(i));
		}
		
	}

	

	/**
	 * @param scanner  used to read the data from the user
	 * @return         returns the choice of the user
	 */
	private static String menu(Scanner scanner) {
	
		System.out.println("a. Create an Identity");
		System.out.println("b. Modify an Identity");
		System.out.println("c. Delete an Identity");
		System.out.println("d. List Identities");
		System.out.println("e. quit");
		System.out.println("your choice (a|b|c|d|e) ? : ");
		String answer = scanner.nextLine();
		return answer;
	}

	/**
	 * @param username      username for admin login
	 * @param password      password for admin login
	 * @throws SQLException if there is an error in the sql query
	 * @return              return whether authentication is successful or not
	 */
	private static boolean authenticate(String username, String password) throws SQLException {

		
		return adminDao.authenticate(username,password);
	}

}
