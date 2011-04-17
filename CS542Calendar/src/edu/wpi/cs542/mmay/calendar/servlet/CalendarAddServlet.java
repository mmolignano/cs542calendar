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

@SuppressWarnings("serial")
public class CalendarAddServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// Check that a user is logged in...
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }
		
		//Event ev = new Event();
		Calendar calendar = new Calendar();
		
		calendar.setName(req.getParameter("name"));
		calendar.addOwner(user);
		calendar.setDescription(req.getParameter("desc"));
		
		boolean success = DatabaseAccess.addCalendar(calendar);
		
		// TODO : Redirects to index so need a way to print success or failure
		
		resp.setContentType("text/plain");
		
		if (success) {
			resp.getWriter().println("Added Calendar:\n");
			resp.getWriter().println("Calendar Name: " + calendar.getName());
			resp.getWriter().println("Calendar Owner: " + calendar.getOwners());
			resp.getWriter().println("Calendar Description: " + calendar.getDescription());
			
		} else {
			resp.getWriter().println("Add Unsuccessful");
		}
		
		resp.sendRedirect("/index.jsp");
	}
}
