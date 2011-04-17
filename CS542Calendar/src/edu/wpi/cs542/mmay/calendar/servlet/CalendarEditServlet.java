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

@SuppressWarnings("serial")
public class CalendarEditServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Check that a user is logged in...
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }
		
		resp.setContentType("text/html");
		PrintWriter wr = resp.getWriter();
		wr.println("<p>" + user.getNickname()+ " (You can <a href=\"" +
				userService.createLogoutURL(req.getRequestURI()) + "\">sign out</a>.)</p>");
		wr.println("<p><a href=\"index.jsp\">Home</a>");
		
		
		wr.print("<h1>My Calendars</h1>");
		
		String id = req.getParameter("key");
		Key key = KeyFactory.createKey(id.substring(0, id.indexOf('(')), new Long(id.substring(id.indexOf('(') + 1, id.indexOf(')'))));
		Calendar c = DatabaseAccess.getCalendar(key);
		
		// Create the form to edit this calendar
		wr.println("<form style=\"display: inline\" name=\"input\" action=\"/savecalendar\" method=\"post\">");
		wr.println("<input type=\"hidden\" name=\"key\" value=\"" + id + "\" />");
		wr.println("<table>");
		wr.println("<tr><td><b>Name:</b></td><td> <input type=\"text\" name=\"name\" value=\"" + c.getName()+ "\"/></td></tr>");
		wr.println("<tr><td><b>Description:</b></td><td> <textarea name=\"desc\" rows=\"3\" cols=\"60\" value=\"" +
				c.getDescription() + "\">" + c.getDescription() + "</textarea></td></tr>");
		wr.println("</table>");
		wr.println("<input type=\"submit\" value=\"Save\" />");
		wr.println("</form>");
		
		// Add a button to cancel
		wr.println("<form style=\"display: inline\" name=\"cancel\" action=\"/listCalendar\" method=\"get\">");
		wr.println("<input type=\"submit\" value=\"Cancel\" />");
		wr.println("</form>");
	}
}
