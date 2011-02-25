package edu.wpi.cs542.mmay.calendar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class AddEventServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		Event ev = new Event();
		
		ev.setEventName(req.getParameter("name"));
		//ev.setStartDate(new Date(req.getParameter("startD")));
		ev.setLocation(req.getParameter("loc"));
		ev.setDescription(req.getParameter("desc"));
		
		boolean success = DatabaseAccess.addEvent(ev); 
		
		resp.setContentType("text/plain");
		
		if (success) {
			resp.getWriter().println("Added Event:\n");
			resp.getWriter().println(ev.getEventName());
			resp.getWriter().println(ev.getStartDate());
			resp.getWriter().println(req.getParameter("endD"));
			resp.getWriter().println(req.getParameter("startT"));
			resp.getWriter().println(req.getParameter("endT"));
			resp.getWriter().println(ev.getLocation());
			resp.getWriter().println(ev.getDescription());
		} else {
			resp.getWriter().println("Add Unsuccessful");
		}
	}
}
