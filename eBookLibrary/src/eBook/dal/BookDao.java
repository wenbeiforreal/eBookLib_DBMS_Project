package eBook.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eBook.model.Author;
import eBook.model.Book;
import eBook.model.Publisher;


public class BookDao {
	protected ConnectionManager connectionManager;
	
	private static BookDao instance = null;
	protected BookDao() {
		connectionManager = new ConnectionManager();
	}
	public static BookDao getInstance() {
		if(instance == null) {
			instance = new BookDao();
		}
		return instance;
	}

//	  BookName VARCHAR(255),
//	  ISBN VARCHAR(13),
//	  Pages INT,
//	  PublisherName VARCHAR(255),
//	  AuthorName VARCHAR(255),
//	  PublishedYear YEAR,
//	  WrittenLanguage VARCHAR(10),
//	  Price DOUBLE,
//	  Category ENUM ('Fiction', 'Non-Fiction')
	
	public Book create(Book book) throws SQLException {
		String insertBook = "INSERT INTO Book(BookName,ISBN,Pages,PublisherName,AuthorName,"
				+ "PublishedYear,WrittenLanguage,Price,Category) VALUES(?,?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertBook);

			insertStmt.setString(1, book.getBookName());
			insertStmt.setString(2, book.getIsbn());
			insertStmt.setInt(3, book.getPages());
			insertStmt.setString(4, book.getPublisher().getPublisherName());
			insertStmt.setString(5, book.getAuthor().getAuthorName());
			insertStmt.setInt(6, book.getPublishedYear());
			insertStmt.setString(7, book.getWrittenLanguage());
			insertStmt.setDouble(8, book.getPrice());
			insertStmt.setString(9, book.getCategory().name());
			insertStmt.executeUpdate();
			
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
		}
	}
	
	public List<Book> getBookFromAuthorName(String authorName) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		String selectBook =
			"SELECT BookName,ISBN,Pages,PublisherName,AuthorName,"
			+ "PublishedYear,WrittenLanguage,Price,Category FROM Book WHERE AuthorName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBook);
			selectStmt.setString(1, authorName);
			results = selectStmt.executeQuery();
			
			PublisherDao publisherDao = PublisherDao.getInstance();
			AuthorDao authorDao = AuthorDao.getInstance();
			while(results.next()) {
				String bookName = results.getString("BookName");
				String isbn = results.getString("ISBN");
				int pages = results.getInt("Pages");
				
				String publisherName = results.getString("PublisherName");
				String resultAuthorName = results.getString("AuthorName");
				int publishedYear = results.getInt("PublishedYear");
				System.out.println(publishedYear);
				String writtenLanguage = results.getString("WrittenLanguage");
				//TODO: change back
				/*
				double price = results.getDouble("Price");
				Book.Category category = Book.Category.valueOf(
						results.getString("Category"));
				*/
				
				Publisher publisher = publisherDao.getPublisherFromPublisherName(publisherName);
				Author author = authorDao.getAuthorFromAuthorName(resultAuthorName);
				
//				String bookName, String isbn, Integer pages, Publisher publisher, Author author, Integer publishedYear,
//				String writtenLanguage, Double price, Category category
				
				Book book = new Book(bookName, isbn, pages, publisher, author, publishedYear, writtenLanguage);
				books.add(book);
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
		return books;
	}
	
	public List<Book> getBookByPublisherAndAuthorName(String publisherName, String authorName) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		String selectBook =
			"SELECT BookName,ISBN,Pages,PublisherName,AuthorName,"
			+ "PublishedYear,WrittenLanguage,Price,Category FROM Book WHERE AuthorName=? AND PublisherName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBook);
			selectStmt.setString(2, publisherName);
			selectStmt.setString(1,  authorName);
			results = selectStmt.executeQuery();
			
			PublisherDao publisherDao = PublisherDao.getInstance();
			AuthorDao authorDao = AuthorDao.getInstance();
			while(results.next()) {
				String bookName = results.getString("BookName");
				String isbn = results.getString("ISBN");
				int pages = results.getInt("Pages");
				
				String resultPublisherName = results.getString("PublisherName");
				String resultAuthorName = results.getString("AuthorName");
				int publishedYear = results.getInt("PublishedYear");
				System.out.println(publishedYear);
				String writtenLanguage = results.getString("WrittenLanguage");
				//TODO: change back
				/*
				double price = results.getDouble("Price");
				Book.Category category = Book.Category.valueOf(
						results.getString("Category"));
				*/
				
				Publisher publisher = publisherDao.getPublisherFromPublisherName(resultPublisherName);
				Author author = authorDao.getAuthorFromAuthorName(resultAuthorName);
				
//				String bookName, String isbn, Integer pages, Publisher publisher, Author author, Integer publishedYear,
//				String writtenLanguage, Double price, Category category
				
				Book book = new Book(bookName, isbn, pages, publisher, author, publishedYear, writtenLanguage);
				books.add(book);
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
		return books;
	}
	
	public Book getBookFromISBN(String ISBN) throws SQLException {
		String selectBook = "SELECT BookName,ISBN,Pages,PublisherName,AuthorName,"
				+ "PublishedYear,WrittenLanguage,Price,Category FROM Book WHERE ISBN=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBook);
			selectStmt.setString(1, ISBN);

			results = selectStmt.executeQuery();
			PublisherDao publisherDao = PublisherDao.getInstance();
			AuthorDao authorDao = AuthorDao.getInstance();

			if(results.next()) {
				String bookName = results.getString("BookName");
				String resultsIsbn = results.getString("ISBN");
				int pages = results.getInt("Pages");
				
				String publisherName = results.getString("PublisherName");
				String authorName = results.getString("AuthorName");
				int publishedYear = results.getInt("PublishedYear");
				String writtenLanguage = results.getString("WrittenLanguage");
	
				/*
				Book.Category category = Book.Category.valueOf(
						results.getString("Category"));
				double price = results.getDouble("Price");
				*/
				
				Publisher publisher = publisherDao.getPublisherFromPublisherName(publisherName);
				Author author = authorDao.getAuthorFromAuthorName(authorName);
				
//				String bookName, String isbn, Integer pages, Publisher publisher, Author author, Integer publishedYear,
//				String writtenLanguage, Double price, Category category
				
				Book book = new Book(bookName, resultsIsbn, pages, publisher, author, publishedYear, writtenLanguage);
				// Book book = new Book(bookName, resultsIsbn, pages, publisher, author, publishedYear, writtenLanguage, price, category);
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
	
	public Book delete(Book book) throws SQLException {
		String deleteBook = "DELETE FROM Book WHERE ISBN=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteBook);
			deleteStmt.setString(1, book.getIsbn());
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
