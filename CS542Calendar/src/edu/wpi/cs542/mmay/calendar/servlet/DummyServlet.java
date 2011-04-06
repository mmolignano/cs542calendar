package edu.wpi.cs542.mmay.calendar.servlet;

import java.io.IOException;

import javax.servlet.http.*;

import edu.wpi.cs542.mmay.calendar.DatabaseAccess;
import edu.wpi.cs542.mmay.calendar.kinds.Event;

@SuppressWarnings("serial")
public class DummyServlet extends HttpServlet {
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