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


@WebServlet("/recommendationdelete")
public class RecommendationDelete extends HttpServlet {
	
	protected RecommendationDao recommendationDao;
	
	@Override
	public void init() throws ServletException {
		recommendationDao = recommendationDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Recommendation");        
        req.getRequestDispatcher("/RecommendationDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String recommendationIdSt = req.getParameter("recommendationId");
        if (recommendationIdSt == null || recommendationIdSt.trim().isEmpty()) {
            messages.put("title", "Invalid recommendationId");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the Recommendation.
        	if(recommendationIdSt != null) {
        		int recommendationId = Integer.parseInt(recommendationIdSt);
        		Recommendation recommendation = new Recommendation(recommendationId);
        		try {
        			recommendation = recommendationDao.delete(recommendation);
        			// Update the message.
        			if (recommendation == null) {
        				messages.put("title", "Successfully deleted " + recommendationId);
        				messages.put("disableSubmit", "true");
        			} else {
        				messages.put("title", "Failed to delete " + recommendationId);
        				messages.put("disableSubmit", "false");
        			}
        		} catch (SQLException e) {
        			e.printStackTrace();
        			throw new IOException(e);
        		}
        	}
        }
        
        req.getRequestDispatcher("/RecommendationDelete.jsp").forward(req, resp);
    }
}
