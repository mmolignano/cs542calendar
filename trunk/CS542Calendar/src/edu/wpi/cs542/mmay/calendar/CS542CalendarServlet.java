package edu.wpi.cs542.mmay.calendar;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CS542CalendarServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		//Event ev = new Event("event 1", new Date(2012, 1, 1, 0, 0), "world", "end of the world");
		
		// TODO Auto-generated method stub
		
		//DatabaseAccess access = new DatabaseAccess();
		
		boolean success = DatabaseAccess.addEvent(new Event());
		resp.setContentType("text/plain");
		
		if (success) {
			resp.getWriter().println("Add was successful");
		} else {
			resp.getWriter().println("Add failed");
		}
	}
}
