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
import edu.wpi.cs542.mmay.calendar.kinds.*;

public class CalendarPendingServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }
		//boolean success = false;
		
		// force creation if not there already
		Ownership owner = DatabaseAccess.getOwnershipByUser(user);
		
		boolean add = Boolean.parseBoolean(req.getParameter("add"));
		String id = req.getParameter("key");
		Key key = KeyFactory.createKey(id.substring(0, id.indexOf('(')), new Long(id.substring(id.indexOf('(') + 1, id.indexOf(')'))));
		
		resp.setContentType("text/html");
		PrintWriter wr = resp.getWriter();
		wr.println("<p>" + user.getNickname()+ " (You can <a href=\"" +
				userService.createLogoutURL(req.getRequestURI()) + "\">sign out</a>.)</p>");
		
		String calName = req.getParameter("name");
		
		boolean remove = true;
		
		if (add) {
			remove = DatabaseAccess.addToOwnedCalendars(user.getNickname(), key);
			//DatabaseAccess.addToOwnedCalendars(owner, calendar);
			if (remove) {
				wr.println("<p>Calendar <b>" + calName + "</b> has been added to your list of calendars.</p>");
			} else {
				wr.println("<p>Calendar <b>" + calName + "</b> has not been added to your list of calendars.</p>");
			}
		}
		
		if (remove) {
			DatabaseAccess.removePendingCalendar(user.getNickname(), key);
			wr.println("<p>Calendar <b>" + calName + "</b> has been removed from your list of pending calendars.</p>");
		} else {
			wr.println("<p>Calendar <b>" + calName + "</b> has not been removed from your list of pending calendars.</p>");
		}
		
		wr.println("<p><a href=\"pendingCalendar.jsp\">Back</a>   <a href=\"index.jsp\">Home</a></p>");
		
	}
}
