package edu.wpi.cs542.mmay.calendar.servlet;

import java.io.IOException;


import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import edu.wpi.cs542.mmay.calendar.DatabaseAccess;
import edu.wpi.cs542.mmay.calendar.kinds.Event;

@SuppressWarnings("serial")
public class DummyServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		if (user != null) {
            resp.setContentType("text/plain");
            resp.getWriter().println("Hello, " + user.getNickname());
        } else {
            resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }
		
		
		
//		boolean success = DatabaseAccess.addEvent(new Event());
//		resp.setContentType("text/plain");
		resp.getWriter().println("Add was successful");
		
//		if (success) {
//			resp.getWriter().println("Add was successful");
//		} else {
//			resp.getWriter().println("Add failed");
//		}
	}
}
