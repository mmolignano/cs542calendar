package edu.wpi.cs542.mmay.calendar.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.wpi.cs542.mmay.calendar.DatabaseAccess;
import edu.wpi.cs542.mmay.calendar.kinds.Calendar;

public class CalendarAddServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		//Event ev = new Event();
		Calendar calendar = new Calendar();
		
		calendar.setName(req.getParameter("name"));
		calendar.setOwner(req.getParameter("owner"));
		
		
		
		//boolean success = DatabaseAccess.addEvent(ev); 
		boolean success = DatabaseAccess.addCalendar(calendar);
		
		resp.setContentType("text/plain");
		
		if (success) {
			resp.getWriter().println("Added Calendar:\n");
			resp.getWriter().println("Calendar Name: " + calendar.getName());
			resp.getWriter().println("Calendar Owner: " + calendar.getOwner());
			
		} else {
			resp.getWriter().println("Add Unsuccessful");
		}
	}
}
