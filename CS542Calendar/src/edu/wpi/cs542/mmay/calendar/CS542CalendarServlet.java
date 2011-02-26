package edu.wpi.cs542.mmay.calendar;

import java.io.IOException;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CS542CalendarServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		boolean success = DatabaseAccess.addEvent(new Event());
		resp.setContentType("text/plain");
		
		if (success) {
			resp.getWriter().println("Add was successful");
		} else {
			resp.getWriter().println("Add failed");
		}
	}
}
