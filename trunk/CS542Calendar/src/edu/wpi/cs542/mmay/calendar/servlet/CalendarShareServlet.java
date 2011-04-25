package edu.wpi.cs542.mmay.calendar.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
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
	
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Check that a user is logged in...
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
    	}
		
		//String calendarName = req.getParameter("calendar");
		String id = req.getParameter("key");
		Key key = KeyFactory.createKey(id.substring(0, id.indexOf('(')), new Long(id.substring(id.indexOf('(') + 1, id.indexOf(')'))));
		//Calendar calendarToShare = DatabaseAccess.getCalendar(key);
		
		String shareTo = req.getParameter("user");
		User shareToUser = new User(shareTo, "gmail.com");
		//User shareToUser = new User(shareTo, shareTo.substring(shareTo.indexOf("@")+1));
		//User shareToUser = new User(shareTo) 
		Ownership shareToOwner = DatabaseAccess.getOwnershipByUser(shareToUser);
		//Calendar calendarToShare = DatabaseAccess.getCalendarByUserByName(user, calendarName);
		//DatabaseAccess.addPendingCalendar(shareToOwner, calendarToShare);
		boolean success = DatabaseAccess.addPendingCalendar(shareToUser.getNickname(), key);
		//Add to pending Calendar
		
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		//pw.println("<p>Calendar " + calendarToShare.getName() + " shared with " + shareTo + "!</p>");
		if (success) {
			pw.println("<p>Calendar <b>" + req.getParameter("name") + "</b> shared with " + shareToUser.getNickname() + "!</p>");
		} else {
			pw.println("<p>Calendar <b>" + req.getParameter("name") + "</b> was not shared with " + shareToUser.getNickname() + "!</p>");
		}
		
		pw.println("<p><a href=\"listCalendar\">Back</a>   <a href=\"index.jsp\">Home</a></p>");
		
	}
}
