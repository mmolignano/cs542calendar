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

public class EventPendingServlet extends HttpServlet {
	
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
		String evId = req.getParameter("evKey");
		Key evKey = KeyFactory.createKey(evId.substring(0, evId.indexOf('(')), new Long(evId.substring(evId.indexOf('(') + 1, evId.indexOf(')'))));
		
		String calId = req.getParameter("calKey");
		Key calKey = KeyFactory.createKey(calId.substring(0, calId.indexOf('(')), new Long(calId.substring(calId.indexOf('(') + 1, calId.indexOf(')'))));
		
		resp.setContentType("text/html");
		PrintWriter wr = resp.getWriter();
		wr.println("<p>" + user.getNickname()+ " (You can <a href=\"" +
				userService.createLogoutURL(req.getRequestURI()) + "\">sign out</a>.)</p>");
		
		String evName = req.getParameter("evName");
		String calName = DatabaseAccess.getCalendar(calKey).getName();
		
		boolean remove = true;
		
		if (add) {
			remove = DatabaseAccess.addEventToCalendar(calKey, evKey);
			//DatabaseAccess.addToOwnedCalendars(owner, calendar);
			if (remove) {
				wr.println("<p>Event <b>" + evName + "</b> has been added Calendar <b>" + calName + "</b>.</p>");
			} else {
				wr.println("<p>Event <b>" + evName + "</b> has not been added Calendar <b>" + calName + "</b>.</p>");
			}
		}
		
		if (remove) {
			DatabaseAccess.removePendingEvent(user.getNickname(), evKey);
			wr.println("<p>Event <b>" + evName + "</b> has been removed from your list of pending calendars.</p>");
		} else {
			wr.println("<p>Event <b>" + evName + "</b> has not been removed from your list of pending calendars.</p>");
		}
		
		wr.println("<p><a href=\"pendingEvent.jsp\">Back</a>   <a href=\"index.jsp\">Home</a></p>");
		
	}
	
}
