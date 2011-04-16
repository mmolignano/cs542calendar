package edu.wpi.cs542.mmay.calendar.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import edu.wpi.cs542.mmay.calendar.DatabaseAccess;
import edu.wpi.cs542.mmay.calendar.kinds.Calendar;
import edu.wpi.cs542.mmay.calendar.PMF;

public class CalendarAddServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// Check that a user is logged in...
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }
		
		//Event ev = new Event();
		Calendar calendar = new Calendar();
		
//		calendar.setName(req.getParameter("name"));
//		calendar.setOwner(user.getNickname());
		
		
		
//		//boolean success = DatabaseAccess.addEvent(ev); 
//		boolean success = DatabaseAccess.addCalendar(calendar);
//		
//		resp.setContentType("text/plain");
//		
//		if (success) {
//			resp.getWriter().println("Added Calendar:\n");
//			resp.getWriter().println("Calendar Name: " + calendar.getName());
//			resp.getWriter().println("Calendar Owner: " + calendar.getOwner());
//			
//		} else {
//			resp.getWriter().println("Add Unsuccessful");
//		}
	}
}
