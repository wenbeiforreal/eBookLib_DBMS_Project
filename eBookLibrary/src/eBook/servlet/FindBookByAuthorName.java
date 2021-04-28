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

import eBook.dal.*;
import eBook.model.*;

@WebServlet("/findbookbyauthorname")
public class FindBookByAuthorName extends HttpServlet {
	protected BookDao bookDao;
	
	@Override
	public void init() throws ServletException {
		bookDao = BookDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		List<Book> books = new ArrayList<Book>();
		
		String authorName = req.getParameter("authorName");
		if (authorName == null || authorName.trim().isEmpty()) {
			messages.put("success", "Please enter a valid author name.");
		} else {
			try {
				books = bookDao.getBookFromAuthorName(authorName);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Displaying results for " + authorName);
			messages.put("previousName", authorName);
		}
		req.setAttribute("books", books);
		
		req.getRequestDispatcher("/FindBookByAuthorName.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		List<Book> books = new ArrayList<Book>();

		String authorName = req.getParameter("authorName");
		if (authorName == null || authorName.trim().isEmpty()) {
			messages.put("success", "Please enter a valid author name.");
		} else {
			try {
				books = bookDao.getBookFromAuthorName(authorName);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Displaying results for " + authorName);
		}
		req.setAttribute("books", books);
		
		req.getRequestDispatcher("/FindBookByAuthorName.jsp").forward(req, resp);
	}
}
