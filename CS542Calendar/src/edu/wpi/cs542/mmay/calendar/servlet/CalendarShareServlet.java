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
import edu.wpi.cs542.mmay.calendar.kinds.Ownership;

/**
 * working on this one 
 * @author Andrew Yee
 *
 */
public class CalendarShareServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Check that a user is logged in...
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
    	}
		
		String calendarName = req.getParameter("calendar");
		String shareTo = req.getParameter("user");
		User shareToUser = new User(shareTo, "gmail.com");
		Ownership shareToOwner = DatabaseAccess.getOwnershipByUser(shareToUser);
		Calendar calendarToShare = DatabaseAccess.getCalendarByUserByName(user, calendarName);
		DatabaseAccess.addPendingCalendar(shareToOwner, calendarToShare);
		//Add to pending Calendar
		
		
	}
}
