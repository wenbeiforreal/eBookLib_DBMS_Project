//Change package name here
package eBook.servlet;

import eBook.dal.UsersDao;
import eBook.model.*;

import java.io.IOException;
import java.sql.SQLException;
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

@WebServlet("/usercreate")
public class UserCreate extends HttpServlet {
	protected UsersDao usersDao; 
	
	@Override 
	public void init() throws ServletException{
		usersDao = UsersDao.getInstance(); 
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/UserCreate.jsp").forward(req, resp);
	}
	
	@Override 
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException{
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        //Retrieve and validate username 
        String userName = req.getParameter("username"); 
        if(userName == null || userName.trim().isEmpty()) {
        	messages.put("success", "Invalid UserName"); 
        }else {
        	//Create the user 
        	
        	String firstName = req.getParameter("firstname"); 
        	String lastName = req.getParameter("lastname"); 
        	String password = req.getParameter("password"); 
        	String email = req.getParameter("email"); 
        	
        	if (password.trim().isEmpty() || lastName.trim().isEmpty() || firstName.trim().isEmpty() || email.trim().isEmpty()) {
        		messages.put("success", "Please fill in all fields. One or more fields are empty.");
        	} else {
	        	try {
	        		Users user = new Users(userName,password,firstName, lastName, email); 
	        		user = usersDao.create(user); 
	        		messages.put("success", "successfully created" +userName); 
	        		
	        	}catch(SQLException e ) {
	        		e.printStackTrace();
	        		throw new IOException(e); 
	        	}
        	}
        }
        req.getRequestDispatcher("/UserCreate.jsp").forward(req, resp); 
      }
}
