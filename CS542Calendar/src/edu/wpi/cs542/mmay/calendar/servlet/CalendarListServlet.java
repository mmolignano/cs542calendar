package edu.wpi.cs542.mmay.calendar.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import edu.wpi.cs542.mmay.calendar.DatabaseAccess;
import edu.wpi.cs542.mmay.calendar.kinds.Calendar;
import edu.wpi.cs542.mmay.calendar.kinds.Ownership;

@SuppressWarnings("serial")
public class CalendarListServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Check that a user is logged in...
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }

		// force creation if not there already
		Ownership owner = DatabaseAccess.getOwnershipByUser(user);
		
		resp.setContentType("text/html");
		PrintWriter wr = resp.getWriter();
		wr.println("<p>" + user.getNickname()+ " (You can <a href=\"" +
				userService.createLogoutURL(req.getRequestURI()) + "\">sign out</a>.)</p>");
		wr.println("<p><a href=\"index.jsp\">Home</a>");
		
		
		wr.println("<h1>My Calendars</h1>");
		
		/*Collection<Calendar> calendars = DatabaseAccess.fetchAllCalendars();
		Collection<Calendar> myCals = new ArrayList<Calendar>();
		for (Calendar c : calendars) {
			Collection<User> users = c.getOwners();
			if (users.contains(user)) {
				myCals.add(c);
			}
		}*/
		
		//Collection<Calendar> myCals = DatabaseAccess.getCalendarsByUser(user.getNickname());
		Collection<Calendar> myCals = owner.getOwnedCalendars();
		
		wr.println("<table cellpadding=\"5\">");
		for(Calendar c : myCals) {
			// Button to Edit Calendar
			wr.println("<tr>");
			wr.println("<td>");
			wr.println("<form style=\"display: inline\" action=\"editcalendar\" method=\"post\" />");
			wr.println("<input type=\"hidden\" name=\"key\" value=\"" + c.getId() + "\" />");
			wr.println("<input type=\"submit\" value=\"Edit\"></form>");
			// Button to Remove Calendar
			wr.println("<form style=\"display: inline\" action=\"removecalendar\" method=\"post\" />");
			wr.println("<input type=\"hidden\" name=\"key\" value=\"" + c.getId() + "\" />");
			wr.println("<input type=\"submit\" value=\"Remove\"></form>");
			wr.println("</td>");
			wr.println("<td>");
			// Print Calendar and Description
			wr.println("<b>" + c.getName() + "</b>: " + c.getDescription());
			wr.println("</td>");
			// Button to Share Calendar with someone
			wr.println("<td>");
			wr.println("<form style=\"display: inline\" action=\"sharecalendar\" method=\"post\" />");
			wr.println("<input type=\"hidden\" name=\"key\" value=\"" + c.getId() + "\" />");
			wr.println("<input type=\"hidden\" name=\"name\" value=\"" + c.getName() + "\" />");
			wr.println("  Share with: <input type=\"text\" name=\"user\" />");
			wr.println("<input type=\"submit\" value=\"Share\"></form>");
			wr.println("</td>");
			
			//wr.println("<br />");
			wr.println("</tr>");
		}
		wr.println("</table>");
	}

}
