package eBook.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import eBook.model.Recommendation;

public class RecommendationDao {
	protected ConnectionManager connectionManager;

	private static RecommendationDao instance = null;
	protected RecommendationDao() {
		connectionManager = new ConnectionManager();
	}
	public static RecommendationDao getInstance() {
		if(instance == null) {
			instance = new RecommendationDao();
		}
		return instance;
	}
	
	public Recommendation create(Recommendation rec) throws SQLException {
		String insertRec =
			"INSERT INTO Recommendation(Created,LoginName,ISBN,Recommends) " +
			"VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRec,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setTimestamp(1, rec.getCreated());
			insertStmt.setString(2, rec.getLoginName());
			insertStmt.setString(3, rec.getiSBN());
			insertStmt.setBoolean(4, rec.getRecommends());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int recId = -1;
			if(resultKey.next()) {
				recId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			rec.setRecommendationId(recId);;
			return rec;
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
	
	public Recommendation getRecById(int id) throws SQLException {
		String selectRec = "SELECT * FROM Recommendation WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRec);
			selectStmt.setInt(1, id);
			results = selectStmt.executeQuery();
			if(results.next()) {
				Integer resultId = results.getInt("CheckOutBookId");
				Timestamp resultCreated = results.getTimestamp("Created");
				String resultLogin = results.getString("LoginName");
				String resultISBN = results.getString("ISBN");
				Boolean resultRec = results.getBoolean("Recommends");
				Recommendation rec = new Recommendation(resultId, resultCreated, resultLogin, resultISBN, resultRec);
				return rec;
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
	
	public Recommendation updateRecommends(Recommendation rec, Boolean recommends) throws SQLException {
		String updateBook = "UPDATE Recommendation SET Recommends=? WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateBook);
			updateStmt.setBoolean(1, recommends);
			updateStmt.setInt(2, rec.getRecommendationId());
			updateStmt.executeUpdate();
			
			rec.setRecommends(recommends);;
			return rec;
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
	
	public Recommendation delete(Recommendation rec) throws SQLException {
		String deleteRec = "DELETE FROM Recommendation WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRec);
			deleteStmt.setInt(1, rec.getRecommendationId());
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
