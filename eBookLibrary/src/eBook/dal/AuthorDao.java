package eBook.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eBook.model.Author;

public class AuthorDao {

	protected ConnectionManager connectionManager;
	
	private static AuthorDao instance = null;
	protected AuthorDao() {
		connectionManager = new ConnectionManager();
	}
	public static AuthorDao getInstance() {
		if(instance == null) {
			instance = new AuthorDao();
		}
		return instance;
	}
	
	public Author create(Author author) throws SQLException {
		String insertAuthor = "INSERT INTO Author(Description,AuthorName) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertAuthor);

			insertStmt.setString(1, author.getDescription());
			insertStmt.setString(2, author.getAuthorName());
	
			insertStmt.executeUpdate();
			
			return author;
		} catch (SQLException e) {
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
	
	public Author getAuthorFromAuthorName(String authorName) throws SQLException {
		String selectAuthor = "SELECT Description,AuthorName FROM Author WHERE AuthorName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAuthor);
			selectStmt.setString(1, authorName);

			results = selectStmt.executeQuery();

			if(results.next()) {
				String resultAuthorName = results.getString("AuthorName");
				String description = results.getString("Description");
				Author author = new Author(description, resultAuthorName);
				return author;
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
		return null;
	}
	
	public Author updateDescription(Author author, String newDescription) throws SQLException {
		String updateAuthor = "UPDATE Author SET Description=? WHERE AuthorName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateAuthor);
			updateStmt.setString(1, newDescription);
			updateStmt.setString(2, author.getAuthorName());
			updateStmt.executeUpdate();
			
			author.setDescription(newDescription);
			return author;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
	
	public Author delete(Author author) throws SQLException {
		String deleteAuthor = "DELETE FROM Author WHERE AuthorName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteAuthor);
			deleteStmt.setString(1, author.getAuthorName());
			deleteStmt.executeUpdate();

			return null;
		} catch (SQLException e) {
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
