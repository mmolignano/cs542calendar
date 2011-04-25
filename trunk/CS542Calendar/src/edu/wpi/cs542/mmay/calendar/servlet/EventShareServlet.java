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
import edu.wpi.cs542.mmay.calendar.kinds.Ownership;

// Working on this one now...
// Mike

@SuppressWarnings("serial")
public class EventShareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Check that a user is logged in...
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
    	}
		
		// force creation if not there already
		Ownership owner = DatabaseAccess.getOwnershipByUser(user);
		
		String id = req.getParameter("key");
		Key key = KeyFactory.createKey(id.substring(0, id.indexOf('(')), new Long(id.substring(id.indexOf('(') + 1, id.indexOf(')'))));
		
		String shareTo = req.getParameter("user");
		User shareToUser = new User(shareTo, "gmail.com");
		Ownership shareToOwner = DatabaseAccess.getOwnershipByUser(shareToUser);

		boolean success = DatabaseAccess.addPendingEvent(shareToUser.getNickname(), key);
		
		
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		
		if (success) {
			pw.println("<p>Calendar <b>" + req.getParameter("name") + "</b> shared with " + shareToUser.getNickname() + "!</p>");
		} else {
			pw.println("<p>Calendar <b>" + req.getParameter("name") + "</b> was not shared with " + shareToUser.getNickname() + "!</p>");
		}
		
		pw.println("<p><a href=\"listCalendar\">Back</a>   <a href=\"index.jsp\">Home</a></p>");
		
	}
}
