package eBook.servlet;

import eBook.dal.*;
import eBook.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/reviewdelete")
public class ReviewDelete extends HttpServlet {
	
	protected ReviewDao reviewDao;
	
	@Override
	public void init() throws ServletException {
		reviewDao = reviewDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Review");        
        req.getRequestDispatcher("/ReviewDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
//        String reviewIdSt = req.getParameter("reviewId");
        String loginName = req.getParameter("loginname");
        String isbn = req.getParameter("isbn");
        if (loginName == null || loginName.trim().isEmpty() || isbn == null || isbn.trim().isEmpty()) {
            messages.put("success", "Invalid login name or ISBN");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the Review.
        		
        		Users user = null;
				try {
					user = UsersDao.getInstance().getUserFromLoginName(loginName);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		Book book = null;
				try {
					book = BookDao.getInstance().getBookFromISBN(isbn);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (user == null) {
					messages.put("success", "This user does not exist. Please enter another login name.");
					messages.put("disableSubmit", "false");
				} else if (book == null) {
					messages.put("success", "This ISBN does not exist. Please enter another ISBN.");
					messages.put("disableSubmit", "false");
				} else {
	        		Review review = new Review(user, book);
	        		try {
	        			review = reviewDao.deleteByLoginNameAndIsbn(review);
	        			// Update the message.
	        			if (review == null) {
	        				messages.put("title", "Successfully deleted review written by " + loginName + " for ISBN: " + isbn);
	        				messages.put("disableSubmit", "true");
	        			} else {
	        				messages.put("title", "Failed to delete review written by " + loginName + " for ISBN: " + isbn);
	        				messages.put("disableSubmit", "false");
	        			}
	        		} catch (SQLException e) {
	        			e.printStackTrace();
	        			throw new IOException(e);
	        		}
				}
        }
        
        req.getRequestDispatcher("/ReviewDelete.jsp").forward(req, resp);
    }
}
