package eBook.dal;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import eBook.model.Book;
import eBook.model.Review;
import eBook.model.Users;

public class ReviewDao {
	protected ConnectionManager connectionManager;

	private static ReviewDao instance = null;
	protected ReviewDao() {
		connectionManager = new ConnectionManager();
	}
	public static ReviewDao getInstance() {
		if(instance == null) {
			instance = new ReviewDao();
		}
		return instance;
	}
	
	public Review create(Review review) throws SQLException {
		String insertReview =
			"INSERT INTO Review(LoginName,ISBN,Comments,Created,Rating) " +
			"VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReview,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, review.getUser().getLoginName());
			insertStmt.setString(2, review.getBook().getIsbn());
			insertStmt.setString(3, review.getComments());
			insertStmt.setTimestamp(4, review.getCreated());
			insertStmt.setDouble(5, review.getRating());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int revId = -1;
			if(resultKey.next()) {
				revId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			review.setReviewId(revId);;
			return review;
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
	
	public Review getReviewById(int id) throws SQLException {
		String selectBooks = "SELECT * FROM Review WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBooks);
			selectStmt.setInt(1, id);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			BookDao bookDao = BookDao.getInstance();
			if(results.next()) {
				Integer resultId = results.getInt("ReviewId");
				String resultLoginName = results.getString("LoginName");
				String resultISBN = results.getString("ISBN");
				String resultComments = results.getString("Comments");
				Timestamp resultCreated = results.getTimestamp("Created");
				Double resultRating = results.getDouble("Rating");
				Users user = usersDao.getUserFromLoginName(resultLoginName);
				Book book = bookDao.getBookFromISBN(resultISBN);
				Review review = new Review(resultId, user, book, resultComments, resultCreated, resultRating);
				return review;
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
	
	public Review getReviewByLoginNameAndIsbn(String loginName, String isbn) throws SQLException {
		
		String selectBooks = "SELECT * FROM Review WHERE LoginName=? AND ISBN=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBooks);
//			selectStmt.setInt(1, id);
			selectStmt.setString(1, loginName);
			selectStmt.setString(2, isbn);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			BookDao bookDao = BookDao.getInstance();
			if(results.next()) {
				Integer resultId = results.getInt("ReviewId");
				String resultLoginName = results.getString("LoginName");
				String resultISBN = results.getString("ISBN");
				String resultComments = results.getString("Comments");
				Timestamp resultCreated = results.getTimestamp("Created");
				Double resultRating = results.getDouble("Rating");
				Users user = usersDao.getUserFromLoginName(resultLoginName);
				Book book = bookDao.getBookFromISBN(resultISBN);
				Review review = new Review(resultId, user, book, resultComments, resultCreated, resultRating);
				return review;
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
	
	/**
	 * Get the matching Persons records by fetching from your MySQL instance.
	 * This runs a SELECT statement and returns a list of matching Persons.
	 */
	public List<Review> getReviewsFromLoginName(String loginName) throws SQLException {
		List<Review> reviews = new ArrayList<Review>();
//		String selectPersons =
//			"SELECT UserName,FirstName,LastName FROM Persons WHERE FirstName=?;";
		
		String selectReviews = 
				"SELECT LoginName, ISBN, Comments, Created, Rating FROM Review WHERE LoginName=?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setString(1, loginName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			BookDao bookDao = BookDao.getInstance();
			while(results.next()) {
				String resultLoginName = results.getString("LoginName");
				String isbn = results.getString("ISBN");
				String comments = results.getString("Comments");
				Timestamp created = results.getTimestamp("Created"); 
				Double rating = results.getDouble("Rating");
				Users user = usersDao.getUserFromLoginName(resultLoginName);
				Book book = bookDao.getBookFromISBN(isbn);
				Review review = new Review(user, book, comments, created, rating);
				reviews.add(review);
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
		return reviews;
	}
	
	
	public Review updateRating(Review review, Double rating) throws SQLException {
		String updateBook = "UPDATE Review SET Rating=? WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateBook);
			updateStmt.setDouble(1, rating);
			updateStmt.setInt(2, review.getReviewId());
			updateStmt.executeUpdate();
			
			review.setRating(rating);;
			return review;
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
	
	public Review UpdateReview(Review review, String comments, Double rating) throws SQLException {
		String updateBook = "UPDATE Review SET Rating=?, Comments=?, Created=? WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateBook);
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			updateStmt.setDouble(1, rating);
			updateStmt.setString(2, comments);
			updateStmt.setTimestamp(3, timestamp);
			updateStmt.setInt(4, review.getReviewId());
			updateStmt.executeUpdate();
			
			review.setComments(comments);
			return review;
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
	
//	public Review delete(Review review) throws SQLException {
//		String deleteReview = "DELETE FROM Review WHERE ReviewId=?;";
//		Connection connection = null;
//		PreparedStatement deleteStmt = null;
//		try {
//			connection = connectionManager.getConnection();
//			deleteStmt = connection.prepareStatement(deleteReview);
//			deleteStmt.setInt(1, review.getReviewId());
//			deleteStmt.executeUpdate();
//
//			// Return null so the caller can no longer operate on the Persons instance.
//			return null;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			if(connection != null) {
//				connection.close();
//			}
//			if(deleteStmt != null) {
//				deleteStmt.close();
//			}
//		}
//	}
	public Review deleteByLoginNameAndIsbn(Review review) throws SQLException {
		String deleteReview = "DELETE FROM Review WHERE LoginName=? AND ISBN=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReview);
			deleteStmt.setString(1, review.getUser().getLoginName());
			deleteStmt.setString(2, review.getBook().getIsbn());
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
