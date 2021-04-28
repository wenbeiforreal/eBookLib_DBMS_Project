package eBook.dal;


import eBook.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class UsersDao {
	
	protected ConnectionManager connectionManager; 
	
	private static UsersDao instance = null;
	protected UsersDao() {
		connectionManager = new ConnectionManager();
	}
	public static UsersDao getInstance() {
		if(instance == null) {
			instance = new UsersDao();
		}
		return instance;
	}

	public Users create(Users user) throws SQLException {
		String insertUser = 
				"INSERT INTO Users(LoginName, LoginPass, FirstName, LastName, Email) VALUES (?,?,?,?,?);";
		Connection connection = null; 
		PreparedStatement insertStmt = null; 
		
		try {
			connection = connectionManager.getConnection(); 
			insertStmt = connection.prepareStatement(insertUser); 
			
			insertStmt.setString(1,  user.getLoginName());
			insertStmt.setString(2, user.getLoginPass());
			insertStmt.setString(3,  user.getFirstName());
			insertStmt.setString(4,  user.getLastName());
			insertStmt.setString(5, user.getEmail());
			
			insertStmt.executeUpdate();
			return user;
		}catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
		}
	}
	
	public Users getUserFromLoginName(String loginName) throws SQLException{
		
		String selectUser = 
				"SELECT LoginName, LoginPass, FirstName, LastName, Email FROM Users WHERE LoginName=?;"; 
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection(); 
			selectStmt = connection.prepareStatement(selectUser); 
			selectStmt.setString(1, loginName);
			results = selectStmt.executeQuery();

			if(results.next()) {
				String resultLoginName = results.getString("LoginName");
				String password = results.getString("LoginPass"); 
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String email = results.getString("Email"); 
				Users user = new Users(resultLoginName, password, firstName, lastName, email); 
				
				return user; 
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;		
	}
	
	/**
	 * Get the matching Persons records by fetching from your MySQL instance.
	 * This runs a SELECT statement and returns a list of matching Persons.
	 */
	public List<Users> getUsersFromFirstName(String firstName) throws SQLException {
		List<Users> users = new ArrayList<Users>();
//		String selectPersons =
//			"SELECT UserName,FirstName,LastName FROM Persons WHERE FirstName=?;";
		String selectUsers = 
				"SELECT LoginName, LoginPass, FirstName, LastName, Email FROM Users WHERE FirstName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUsers);
			selectStmt.setString(1, firstName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String loginName = results.getString("LoginName");
				String resultFirstName = results.getString("FirstName");
				String password = results.getString("LoginPass"); 
				String lastName = results.getString("LastName");
				String email = results.getString("Email"); 
				Users user = new Users(loginName, password, resultFirstName, lastName, email);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return users;
	}
	
	public Users delete(Users user) throws SQLException{
		String deleteUser = "DELETE FROM Users WHERE LoginName=?;"; 
		Connection connection = null; 
		PreparedStatement deleteStmt = null; 
		
		try {
			connection = connectionManager.getConnection(); 
			deleteStmt = connection.prepareStatement(deleteUser); 
			deleteStmt.setString(1, user.getLoginName()); 
			deleteStmt.executeUpdate(); 
			
			return null;
		}catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	
}
