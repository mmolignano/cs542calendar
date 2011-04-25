package edu.wpi.cs542.mmay.calendar.servlet;

import java.io.IOException;
import java.io.PrintWriter;

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
public class CalendarAddServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// Check that a user is logged in...
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }
		
		// force creation if not there already
		Ownership owner = DatabaseAccess.getOwnershipByUser(user);
		
		Calendar calendar = new Calendar();
		
		calendar.setName(req.getParameter("name"));
		calendar.addOwner(user);
		calendar.setDescription(req.getParameter("desc"));
		
		boolean success = DatabaseAccess.addNewCalendar(user.getNickname(), calendar);
		
		// TODO : Redirects to index so need a way to print success or failure
		
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		
		if (success) {
			pw.println("Added Calendar:<br />");
			pw.println("Calendar Name: " + calendar.getName() + "<br />");
			pw.println("Calendar Owner: " + calendar.getOwners() + "<br />");
			pw.println("Calendar Description: " + calendar.getDescription() + "<br />");
			
		} else {
			pw.println("Add Unsuccessful");
		}
		
		pw.println("<p><a href=\"index.jsp\">Back</a></p>");
	}
}
