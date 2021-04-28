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

@WebServlet("/findbookbypublisher")
public class FindBookByPublisher extends HttpServlet {
	protected BookDao bookDao;
	
	@Override
	public void init() throws ServletException {
		bookDao = BookDao.getInstance();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		request.setAttribute("messages", messages);
		
		List<Book> books = new ArrayList<Book>();
		
		String publisherName = request.getParameter("publisherName");
		String authorName = request.getParameter("authorName");
		if (publisherName == null || publisherName.trim().isEmpty() || authorName == null || authorName.trim().isEmpty()) {
			messages.put("success", "Please enter a valid publisher or author name.");
		} else {
			try {
				books = bookDao.getBookByPublisherAndAuthorName(publisherName, authorName);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Displaying results for " + publisherName + " " + authorName);
		}
		request.setAttribute("books", books);
		
		request.getRequestDispatcher("/FindBookByAuthorAndPublisher.jsp").forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		request.setAttribute("messages", messages);
		
		List<Book> books = new ArrayList<Book>();
		
		String publisherName = request.getParameter("publisherName");
		String authorName = request.getParameter("authorName");
		if (publisherName == null || publisherName.trim().isEmpty() || authorName == null || authorName.trim().isEmpty()) {
			messages.put("success", "Please enter a valid publisher or author name.");
		} else {
			try {
				books = bookDao.getBookByPublisherAndAuthorName(publisherName, authorName);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Displaying results for " + publisherName + " " + authorName);
		}
		request.setAttribute("books", books);
		
		request.getRequestDispatcher("/FindBookByAuthorAndPublisher.jsp").forward(request, response);
	}
}
