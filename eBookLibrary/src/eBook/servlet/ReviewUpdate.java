package eBook.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eBook.dal.BookDao;
import eBook.dal.ReviewDao;
import eBook.dal.UsersDao;
import eBook.model.Review;

/**
 * Servlet implementation class ReviewUpdate
 */
@WebServlet("/reviewupdate")
public class ReviewUpdate extends HttpServlet {
	protected ReviewDao reviewDao;
	protected UsersDao usersDao;
	protected BookDao bookDao;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		reviewDao = ReviewDao.getInstance();
		usersDao = UsersDao.getInstance();
		bookDao = BookDao.getInstance();
		
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve review and validate.
        String stringReviewId = req.getParameter("reviewid");
        Integer reviewId = (stringReviewId == null) ? 0 : Integer.parseInt(stringReviewId);
        String loginName = req.getParameter("loginname");
        if (loginName == null || loginName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid LoginName.");
        } else {
        	try {
        		Review review = reviewDao.getReviewById(reviewId);
        		if(review == null) {
        			messages.put("success", "Review does not exist.");
        		}
        		req.setAttribute("review", review);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/ReviewUpdate.jsp").forward(req, resp);
	}


	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String loginName = req.getParameter("loginname");
        if (loginName == null || loginName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid LoginName.");
        } else {
        	try {

        		String ISBN = req.getParameter("isbn");
        		Review review = reviewDao.getReviewByLoginNameAndIsbn(loginName, ISBN);
        		if(review == null) {
        			messages.put("success", "Review does not exist. No update to perform.");
        		} else {
        			String newComments = req.getParameter("comments");
        			String stringNewRating = req.getParameter("rating");
        			Double newRating = Double.parseDouble(stringNewRating);
        			if (newComments == null || newComments.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid comment.");
        	        } else if (stringNewRating == null || stringNewRating.trim().isEmpty()){
        	        	messages.put("success", "Please enter a valid rating.");
        	        } else {
        	
        	        	review = reviewDao.UpdateReview(review, newComments, newRating);
        	        	messages.put("success", "Successfully updated review for " + loginName);
        	        }
        		}
        		req.setAttribute("review", review);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/ReviewUpdate.jsp").forward(req, resp);
    }

}
