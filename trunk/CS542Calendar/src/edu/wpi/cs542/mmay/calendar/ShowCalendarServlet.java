package edu.wpi.cs542.mmay.calendar;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ShowCalendarServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		//Event ev = new Event("event 1", new Date(2012, 1, 1, 0, 0), "world", "end of the world");
		
		// TODO Auto-generated method stub
		
		//DatabaseAccess access = new DatabaseAccess();
		
		ArrayList<Event> events = DatabaseAccess.getEvents();
		resp.setContentType("text/plain");
		
		// Print Headers
		resp.getWriter().println("NAME\n (" + (events.size()) + " events)");
		
		for (Event e1 : events) {
			resp.getWriter().println(e1.getEventName());
		}
	}
}
