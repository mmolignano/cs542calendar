package edu.wpi.cs542.mmay.calendar.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.wpi.cs542.mmay.calendar.DatabaseAccess;
import edu.wpi.cs542.mmay.calendar.kinds.Event;

@SuppressWarnings("serial")
public class CalendarShowServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		//Event ev = new Event("event 1", new Date(2012, 1, 1, 0, 0), "world", "end of the world");
		
		// TODO Auto-generated method stub
		
		//DatabaseAccess access = new DatabaseAccess();
		
		List<Event> events = DatabaseAccess.getEventsByDate();
		//events.s
		resp.setContentType("text/html");
		
		// Print Headers
		//resp.getWriter().println("NAME\tDATE\tLocation\tDescription");
		resp.getWriter().println("<html><head><title>This calendar</title></head>");
		resp.getWriter().println("<body>");
		resp.getWriter().println("<table border=\"0\" cellpadding=\"5\"");
		resp.getWriter().println("<tr>");
		resp.getWriter().println("<th>Event Name</th>");
		resp.getWriter().println("<th>Event Date</th>");
		resp.getWriter().println("<th>Location</th>");
		resp.getWriter().println("<th>Description</th>");
		resp.getWriter().println("</tr>");
		
		for (Event e1 : events) {
			resp.getWriter().println("<tr>");
			resp.getWriter().println("<td>" + e1.getEventName() + "</td>");
			resp.getWriter().println("<td>" + formatter.format(e1.getStartDate()) + "</td>");
			resp.getWriter().println("<td>" + e1.getLocation() + "</td>");
			resp.getWriter().println("<td>" + e1.getDescription() + "</td>");
			resp.getWriter().println("</tr>");
		}
		
		resp.getWriter().println("</table></body></html>");
	}
}
