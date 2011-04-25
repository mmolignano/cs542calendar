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
public class EventRemoveServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// Check that a user is logged in...
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }

		String evId = req.getParameter("key");
		Key evKey = KeyFactory.createKey(evId.substring(0, evId.indexOf('(')), new Long(evId.substring(evId.indexOf('(') + 1, evId.indexOf(')'))));
		
		/*String calId = req.getParameter("calKey");
		Key calKey = KeyFactory.createKey(evId.substring(0, calId.indexOf('(')), new Long(calId.substring(calId.indexOf('(') + 1, calId.indexOf(')'))));*/
		
		//DatabaseAccess.removeEventFromCalendar(calKey, evKey);
		DatabaseAccess.removeEvent(evKey);
		
		
		resp.sendRedirect("/showcalendar");
	}
}
