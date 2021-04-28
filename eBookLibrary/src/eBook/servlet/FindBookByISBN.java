package eBook.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eBook.dal.BookDao;
import eBook.model.Book;

@WebServlet("/findbookbyisbn")
public class FindBookByISBN extends HttpServlet{
	protected BookDao bookDao;
	
	@Override
	public void init() throws ServletException {
		bookDao = BookDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        Book book = new Book();
        
        // Retrieve and validate.
        String isbn = req.getParameter("isbn");
        if (isbn == null || isbn.trim().isEmpty()) {
            messages.put("success", "Please enter a valid ISBN.");
        } else {
        	// Retrieve BlogUsers, and store as a message.
        	try {
            	book = bookDao.getBookFromISBN(isbn);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + isbn);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
        	messages.put("previousISBN", isbn);
        }
        req.setAttribute("books", book);
        
        req.getRequestDispatcher("/FindBookByISBN.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		List<Book> books = new ArrayList<Book>();

		String isbn = req.getParameter("isbn");
		if (isbn == null || isbn.trim().isEmpty()) {
			messages.put("success", "Please enter a valid ISBN.");
		} else {
			try {
				books.add(bookDao.getBookFromISBN(isbn));
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Displaying results for " + isbn);
		}
		req.setAttribute("books_isbn", books);
		
		req.getRequestDispatcher("/FindBookByISBN.jsp").forward(req, resp);
	}
}
