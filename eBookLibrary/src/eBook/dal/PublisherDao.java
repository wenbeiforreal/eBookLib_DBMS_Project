package eBook.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eBook.model.Publisher;

public class PublisherDao {

	protected ConnectionManager connectionManager;
	
	private static PublisherDao instance = null;
	protected PublisherDao() {
		connectionManager = new ConnectionManager();
	}
	public static PublisherDao getInstance() {
		if(instance == null) {
			instance = new PublisherDao();
		}
		return instance;
	}
	
	public Publisher create(Publisher publisher) throws SQLException {
		String insertPublisher = "INSERT INTO Publisher(Description,PublisherName) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPublisher);

			insertStmt.setString(1, publisher.getDescription());
			insertStmt.setString(2, publisher.getPublisherName());
	
			insertStmt.executeUpdate();
			
			return publisher;
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
	
	public Publisher getPublisherFromPublisherName(String publisherName) throws SQLException {
		String selectPublisher = "SELECT Description,PublisherName FROM Publisher WHERE PublisherName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPublisher);
			selectStmt.setString(1, publisherName);

			results = selectStmt.executeQuery();

			if(results.next()) {
				String resultPublisherName = results.getString("PublisherName");
				String description = results.getString("Description");
				Publisher publisher = new Publisher(description, resultPublisherName);
				return publisher;
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
	
	public Publisher updateDescription(Publisher publisher, String newDescription) throws SQLException {
		String updatePublisher = "UPDATE Publisher SET Description=? WHERE PublisherName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePublisher);
			updateStmt.setString(1, newDescription);
			updateStmt.setString(2, publisher.getPublisherName());
			updateStmt.executeUpdate();
			
			publisher.setDescription(newDescription);
			return publisher;
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
	
	public Publisher delete(Publisher publisher) throws SQLException {
		String deletePublisher = "DELETE FROM Publisher WHERE PublisherName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePublisher);
			deleteStmt.setString(1, publisher.getPublisherName());
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

