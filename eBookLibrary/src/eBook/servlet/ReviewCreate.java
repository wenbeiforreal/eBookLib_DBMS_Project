package eBook.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eBook.dal.BookDao;
import eBook.dal.ReviewDao;
import eBook.dal.UsersDao;
import eBook.model.Book;
import eBook.model.Review;
import eBook.model.Users;

/**
 * Servlet implementation class ReviewCreate
 */
@WebServlet("/reviewcreate")
public class ReviewCreate extends HttpServlet {
	protected BookDao bookDao;
	protected UsersDao usersDao;
	protected ReviewDao reviewDao;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public void init() throws ServletException {
        bookDao = BookDao.getInstance();
        usersDao = UsersDao.getInstance();
        reviewDao = ReviewDao.getInstance();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/ReviewCreate.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        
        String loginName = req.getParameter("loginname");
        if (loginName == null || loginName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid login name.");
        } else {
        	// Create the review.
        	String ISBN = req.getParameter("isbn");
            Users user = null;
    		try {
    			user = usersDao.getUserFromLoginName(loginName);
    		} catch (SQLException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
			Review review = null;
			try {
				review = reviewDao.getReviewByLoginNameAndIsbn(loginName, ISBN);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
        	Book book = null;
			try {
				book = bookDao.getBookFromISBN(ISBN);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (user == null) {
				messages.put("success", "This user does not exist. Please enter another login name.");
			} else if (book == null) {
				messages.put("success", "This ISBN does not exist in the library. Please enter another ISBN.");
			} else if (review != null) {
				messages.put("success", "You already created a review for " + ISBN 
						+ ". Please update your review.");
			} else {
	        	String comments = req.getParameter("comments");

	        	String stringRating = req.getParameter("rating");
	        	
	        	if (comments.trim().isEmpty() || stringRating.trim().isEmpty()) {
	        		messages.put("success", "Please fill in all fields.");
	        	} else {
	        		Double rating = Double.parseDouble(stringRating);
		        	Date created = new Date();
	
			        try {
			        	
			        	Timestamp timestamp = new Timestamp(created.getTime());
			        	Review review1 = new Review(user, book, comments, timestamp, rating);
			        	reviewDao.create(review1);
			        	messages.put("success", "Successfully created review for " + loginName);
			        } catch (SQLException e) {
						e.printStackTrace();
						throw new IOException(e);
			        }
	        	}
			}
	        
        }
        req.getRequestDispatcher("/ReviewCreate.jsp").forward(req, resp);
	}
	
}

