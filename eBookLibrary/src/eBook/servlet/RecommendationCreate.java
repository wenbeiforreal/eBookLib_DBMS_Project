package eBook.servlet;

import eBook.dal.BookDao;
import eBook.dal.RecommendationDao;
import eBook.dal.UsersDao;
import eBook.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/recommendationcreate")
public class RecommendationCreate extends HttpServlet {
	protected BookDao bookDao;
	protected UsersDao usersDao;
	protected RecommendationDao recommendationDao; 
	
	@Override 
	public void init() throws ServletException{
        bookDao = BookDao.getInstance();
        usersDao = UsersDao.getInstance();
		recommendationDao = RecommendationDao.getInstance(); 
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/RecommendationCreate.jsp").forward(req, resp);
	}
	
	@Override 
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        String loginName = req.getParameter("loginname");
        Users user = null;
		try {
			user = usersDao.getUserFromLoginName(loginName);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        if (loginName == null || loginName.trim().isEmpty()) {
            messages.put("success", "Invalid LoginName");
        } else {
        	// Create the recommendation.
        	String ISBN = req.getParameter("isbn");
        	Book book = null;
			try {
				book = bookDao.getBookFromISBN(ISBN);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        if (ISBN == null || ISBN.trim().isEmpty()) {
	            messages.put("success", "Invalid ISBN");
	        } else {
	        	String stringRecommends = req.getParameter("recommends");
	        	Boolean recommends = Boolean.parseBoolean(stringRecommends);
	        	Date created = new Date();

	        try {
	        	Timestamp timestamp = new Timestamp(created.getTime());
	        	Recommendation recommendation = new Recommendation(timestamp, loginName, ISBN, recommends);
	        	recommendationDao.create(recommendation);
	        	messages.put("success", "Successfully created recommendation for User " + loginName + " and Book " + ISBN);
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
	        
	        req.getRequestDispatcher("/RecommendationCreate.jsp").forward(req, resp);
        }
	
	}
	}
}

