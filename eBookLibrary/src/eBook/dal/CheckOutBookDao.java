package eBook.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import eBook.model.CheckOutBook;

public class CheckOutBookDao {
	protected ConnectionManager connectionManager;

	private static CheckOutBookDao instance = null;
	protected CheckOutBookDao() {
		connectionManager = new ConnectionManager();
	}
	public static CheckOutBookDao getInstance() {
		if(instance == null) {
			instance = new CheckOutBookDao();
		}
		return instance;
	}
	
	public CheckOutBook create(CheckOutBook book) throws SQLException {
		String insertBook =
			"INSERT INTO CheckOutBook(LoginName,ISBN,TimeStart,TimeEnd,PermitCheckOutTime) " +
			"VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertBook,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, book.getLoginName());
			insertStmt.setString(2, book.getiSBN());
			insertStmt.setTimestamp(3, book.getTimeStart());
			insertStmt.setTimestamp(4, book.getTimeEnd());
			insertStmt.setInt(5, book.getPermitCheckOutTime());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int bookId = -1;
			if(resultKey.next()) {
				bookId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			book.setCheckOutBookId(bookId);
			return book;
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
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}
	
	public CheckOutBook getCheckOutById(int id) throws SQLException {
		String selectBooks = "SELECT * FROM CheckOutBook WHERE CheckOutBookId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBooks);
			selectStmt.setInt(1, id);
			results = selectStmt.executeQuery();
			if(results.next()) {
				Integer resultId = results.getInt("CheckOutBookId");
				String resultLoginName = results.getString("LoginName");
				String resultISBN = results.getString("ISBN");
				Timestamp resultStart = results.getTimestamp("TimeStart");
				Timestamp resultEnd = results.getTimestamp("TimeEnd");
				Integer resultTime = results.getInt("PermitCheckOutTime");
				CheckOutBook book = new CheckOutBook(resultId, resultLoginName, resultISBN, resultStart, resultEnd, resultTime);
				return book;
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
	
	public CheckOutBook updatePermittedTime(CheckOutBook book, int newTime) throws SQLException {
		String updateBook = "UPDATE CheckOutBook SET PermitCheckOutTime=? WHERE CheckOutBookId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateBook);
			updateStmt.setInt(1, newTime);
			updateStmt.setInt(2, book.getCheckOutBookId());
			updateStmt.executeUpdate();
			
			book.setPermitCheckOutTime(newTime);
			return book;
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
	
	public CheckOutBook delete(CheckOutBook book) throws SQLException {
		String deleteBook = "DELETE FROM CheckOutBook WHERE CheckOutBookId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteBook);
			deleteStmt.setInt(1, book.getCheckOutBookId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Persons instance.
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
