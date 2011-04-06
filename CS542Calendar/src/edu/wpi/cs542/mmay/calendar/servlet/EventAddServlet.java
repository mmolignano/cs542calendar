package edu.wpi.cs542.mmay.calendar.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.wpi.cs542.mmay.calendar.DatabaseAccess;
import edu.wpi.cs542.mmay.calendar.kinds.Event;

@SuppressWarnings("serial")
public class EventAddServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); 
		Event ev = new Event();
		
		ev.setEventName(req.getParameter("name"));
		//ev.setStartDate(new Date(req.getParameter("startD")));
		try {
			ev.setStartDate(formatter.parse(req.getParameter("startD")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ev.setLocation(req.getParameter("loc"));
		ev.setDescription(req.getParameter("desc"));
		
		boolean success = DatabaseAccess.addEvent(ev); 
		
		resp.setContentType("text/plain");
		
		if (success) {
			resp.getWriter().println("Added Event:\n");
			resp.getWriter().println(ev.getEventName());
			resp.getWriter().println(formatter.format(ev.getStartDate()));
			//resp.getWriter().println(req.getParameter("endD"));
			//resp.getWriter().println(req.getParameter("startT"));
			//resp.getWriter().println(req.getParameter("endT"));
			resp.getWriter().println(ev.getLocation());
			resp.getWriter().println(ev.getDescription());
		} else {
			resp.getWriter().println("Add Unsuccessful");
		}
	}
}
