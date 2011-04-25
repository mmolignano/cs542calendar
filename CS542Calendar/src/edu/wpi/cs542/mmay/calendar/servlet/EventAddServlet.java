package edu.wpi.cs542.mmay.calendar.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import edu.wpi.cs542.mmay.calendar.DatabaseAccess;
import edu.wpi.cs542.mmay.calendar.kinds.Event;

@SuppressWarnings("serial")
public class EventAddServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// Check that a user is logged in...
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }
		
		String id = req.getParameter("calendar");
		Key key = KeyFactory.createKey(id.substring(0, id.indexOf('(')), new Long(id.substring(id.indexOf('(') + 1, id.indexOf(')'))));
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); 
		Event ev = new Event();
		
		ev.setEventName(req.getParameter("name"));
		//ev.setStartDate(new Date(req.getParameter("startD")));
		try {
			ev.setStartDate(formatter.parse(req.getParameter("startD")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ev.setLocation(req.getParameter("loc"));
		ev.setDescription(req.getParameter("desc"));
		
		resp.setContentType("text/plain");

		boolean success = DatabaseAccess.addNewEventToCalendar(ev, key);

		if (success) {
			resp.getWriter().println("Added Event:\n");
			resp.getWriter().println(ev.getEventName());
			resp.getWriter().println(formatter.format(ev.getStartDate()));
			resp.getWriter().println(ev.getLocation());
			resp.getWriter().println(ev.getDescription());
		} else {
			resp.getWriter().println("Add Unsuccessful");
		}
		
		resp.sendRedirect("/listEvent");
	}
}
