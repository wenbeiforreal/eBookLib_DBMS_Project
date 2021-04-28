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


@WebServlet("/userdelete")
public class UserDelete extends HttpServlet {
	
	protected UsersDao usersDao;
	
	@Override
	public void init() throws ServletException {
		usersDao = usersDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete User");        
        req.getRequestDispatcher("/UserDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String loginName = req.getParameter("loginname");
        if (loginName == null || loginName.trim().isEmpty()) {
            messages.put("title", "Invalid loginName");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the BlogUser.
        	Users user = null;
			try {
				user = UsersDao.getInstance().getUserFromLoginName(loginName);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	if (user == null) {
	        	messages.put("title", loginName + " Does Not Exist");
	        	messages.put("disableSubmit", "false");
        	} else {
    	        Users user1 = new Users(loginName);
    	        try {
    	        	user1 = usersDao.delete(user1);
    	        	// Update the message.
    		        if (user1 == null) {
    		            messages.put("title", "Successfully deleted " + loginName);
    		            messages.put("disableSubmit", "true");
    		        } else {
    		        	messages.put("title", "Failed to delete " + loginName);
    		        	messages.put("disableSubmit", "false");
    		        }
    	        } catch (SQLException e) {
    				e.printStackTrace();
    				throw new IOException(e);
    	        }
        	}
        }
        
        req.getRequestDispatcher("/UserDelete.jsp").forward(req, resp);
    }
}
