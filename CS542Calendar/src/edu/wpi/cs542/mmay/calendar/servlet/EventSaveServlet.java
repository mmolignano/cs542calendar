package edu.wpi.cs542.mmay.calendar.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import edu.wpi.cs542.mmay.calendar.DatabaseAccess;
import edu.wpi.cs542.mmay.calendar.kinds.Calendar;
import edu.wpi.cs542.mmay.calendar.kinds.Event;
import edu.wpi.cs542.mmay.calendar.kinds.Ownership;

@SuppressWarnings("serial")
public class EventSaveServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// Check that a user is logged in...
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }
				
		String id = req.getParameter("key");
		Key key = KeyFactory.createKey(id.substring(0, id.indexOf('(')), new Long(id.substring(id.indexOf('(') + 1, id.indexOf(')'))));
		Event ev = DatabaseAccess.getEvent(key);
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		
		try {
			ev.setEventName(req.getParameter("name"));
			ev.setDescription(req.getParameter("desc"));
			ev.setStartDate(formatter.parse(req.getParameter("startD")));
			ev.setLocation(req.getParameter("loc"));
			
			DatabaseAccess.saveEvent(ev);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		resp.sendRedirect("/showcalendar");
	}

}
