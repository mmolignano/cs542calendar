package edu.wpi.cs542.mmay.calendar.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.wpi.cs542.mmay.calendar.DatabaseAccess;
import edu.wpi.cs542.mmay.calendar.kinds.Event;

@SuppressWarnings("serial")
public class CalendarShowServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException {
		
		resp.setContentType("text/html");
		
		PrintWriter pw = resp.getWriter();
		
		
//		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
//		//Event ev = new Event("event 1", new Date(2012, 1, 1, 0, 0), "world", "end of the world");
//		
//		// TODO Auto-generated method stub
//		
//		//DatabaseAccess access = new DatabaseAccess();
//		
//		List<Event> events = DatabaseAccess.getEventsByDate();
//		//events.s
//		resp.setContentType("text/html");
//		
//		// Print Headers
//		//resp.getWriter().println("NAME\tDATE\tLocation\tDescription");
//		resp.getWriter().println("<html><head><title>This calendar</title></head>");
//		resp.getWriter().println("<body>");
//		resp.getWriter().println("<table border=\"0\" cellpadding=\"5\"");
//		resp.getWriter().println("<tr>");
//		resp.getWriter().println("<th>Event Name</th>");
//		resp.getWriter().println("<th>Event Date</th>");
//		resp.getWriter().println("<th>Location</th>");
//		resp.getWriter().println("<th>Description</th>");
//		resp.getWriter().println("</tr>");
//		
//		for (Event e1 : events) {
//			resp.getWriter().println("<tr>");
//			resp.getWriter().println("<td>" + e1.getEventName() + "</td>");
//			resp.getWriter().println("<td>" + formatter.format(e1.getStartDate()) + "</td>");
//			resp.getWriter().println("<td>" + e1.getLocation() + "</td>");
//			resp.getWriter().println("<td>" + e1.getDescription() + "</td>");
//			resp.getWriter().println("</tr>");
//		}
//		
//		resp.getWriter().println("</table></body></html>");
		
//		java.util.Calendar c3 = new GregorianCalendar(2011, 0, 1);
		GregorianCalendar today = new GregorianCalendar();
		int tDay = today.get(GregorianCalendar.DATE);
		int tMonth = today.get(GregorianCalendar.MONTH);
		int tYear = today.get(GregorianCalendar.YEAR);
		GregorianCalendar c = new GregorianCalendar();
		int day = c.get(GregorianCalendar.DATE);
		int month = c.get(GregorianCalendar.MONTH);
		int year = c.get(GregorianCalendar.YEAR);
		int dow = c.get(GregorianCalendar.DAY_OF_WEEK);
		int maxDay = c.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		
//		pw.println("day: " + day + "<br />");
//		pw.println("month: " + month + "<br />");
//		pw.println("year: " + year + "<br />");
//		pw.println("dow: " + dow + "<br />");
//		pw.println("maxDay: " + maxDay + "<br />");
		
		int checkdow = dow;
		// Get a calendar at first day of week
		for (int i = checkdow; i > 1; i--) {
			day = c.get(java.util.Calendar.DATE);
			month = c.get(java.util.Calendar.MONTH);
			year = c.get(java.util.Calendar.YEAR);
			dow = c.get(java.util.Calendar.DAY_OF_WEEK);
			// First check if first day of month
			if (day == 1) {
				// Check to see if first month of year
				if (month == 0) {
					// Need to move month to 12 and year back 1
					c  = new GregorianCalendar(year-1, 11, 31);
				} else {
					// Need to move month back and check max day
					java.util.Calendar temp = new GregorianCalendar(year, month-1, 1);
					maxDay = temp.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
					c = new GregorianCalendar(year, month-1, maxDay);
				}
			} else {
				// Just move day back
				c = new GregorianCalendar(year, month, day-1);
			}
		}
		
		pw.println();
		
		int begYear = c.get(GregorianCalendar.YEAR);
		int begMonth = c.get(GregorianCalendar.MONTH);
		int begDay = c.get(GregorianCalendar.DATE);
		GregorianCalendar c3 = new GregorianCalendar(begYear, begMonth, begDay);
		
		// Create the top (days of the week)
		pw.println("<TABLE width=\"100%\" border=1 bordercolordark=\"#000000\" bordercolorlight=\"#FFFFFF\" cellpadding=\"5\"><TBODY><TR>");
		
		// Print out the 7 days of the week (SUN-SAT, day month year)
		for (int i = 1; i <=7; i++) {
			day = c3.get(java.util.Calendar.DATE);
			month = c3.get(java.util.Calendar.MONTH);
			year = c3.get(java.util.Calendar.YEAR);
			maxDay = c3.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			
			// Print this day
			if (day == tDay && month == tMonth && year == tYear) {
				pw.println("<TH valign=center align=middle width=\"14%\" bgcolor=\"#00A0F0\"><font size=\"1\" color=\"#FFFFFF\" face=\"Verdana\">" +
						getDayName(i) + " " + (month+1) + "/" + day + "</font></TH>");
			} else {
				pw.println("<TH valign=center align=middle width=\"14%\" bgcolor=\"#000000\"><font size=\"1\" color=\"#FFFFFF\" face=\"Verdana\">" +
						getDayName(i) + " " + (month+1) + "/" + day + "</font></TH>");
			}
			
			// Move to next day
			// First check to see if last day in month
			if (day == maxDay) {
				// Check to see if last month of year
				if (month == 11) {
					// Need to move month to 1 and year forward 1
					c3  = new GregorianCalendar(year+1, 0, 1);
				} else {
					// Need to move month forward and set day 1
					c3 = new GregorianCalendar(year, month+1, 1);
				}
			} else {
				// Just move day back
				c3 = new GregorianCalendar(year, month, day+1);
			}
		}
		
		pw.println("<TR><TD vAlign=top align=left width=\"14%\" >&nbsp;<br><br><br><br></TD>");
		pw.println("<TD vAlign=top align=left width=\"14%\" >&nbsp;<br><br><br><br></TD>");
		pw.println("<TD vAlign=top align=left width=\"14%\" >&nbsp;<br><br><br><br></TD>");
		pw.println("<TD vAlign=top align=left width=\"14%\" >&nbsp;<br><br><br><br></TD>");
		pw.println("<TD vAlign=top align=left width=\"14%\" ><font size=\"3\" face=\"Verdana\">1</font><br><br><br><br></TD>");
		pw.println("<TD vAlign=top align=left width=\"14%\" ><font size=\"3\" face=\"Verdana\">2</font><br><br><br><br></TD>");
		pw.println("<TD vAlign=top align=left width=\"14%\" ><font size=\"3\" face=\"Verdana\">3</font><br><br><br><br></TD></tr></TABLE>");
		
		
		c3 = new GregorianCalendar(begYear, begMonth, begDay);
		
		// Print out the events of the 7 days of the week (SUN-SAT)
		for (int i = 1; i <=7; i++) {
			day = c3.get(java.util.Calendar.DATE);
			month = c3.get(java.util.Calendar.MONTH);
			year = c3.get(java.util.Calendar.YEAR);
			maxDay = c3.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			// First print this day
			pw.println("Day: " + day + "\tMonth: " + (month+1) + "\tYear: " + year + "<br />");
			
			// Move to next day
			// First check to see if last day in month
			if (day == maxDay) {
				// Check to see if last month of year
				if (month == 11) {
					// Need to move month to 1 and year forward 1
					c3  = new GregorianCalendar(year+1, 0, 1);
				} else {
					// Need to move month forward and set day 1
					c3 = new GregorianCalendar(year, month+1, 1);
				}
			} else {
				// Just move day back
				c3 = new GregorianCalendar(year, month, day+1);
			}
		}
		
//		pw.println("<TH valign=center align=middle width=\"14%\" bgcolor=\"#000000\"><font size=\"1\" color=\"#FFFFFF\" face=\"Verdana\">Sunday</font></TH>");
//		pw.println("<TH valign=center align=middle width=\"14%\" bgcolor=\"#000000\"><font size=\"1\" color=\"#FFFFFF\" face=\"Verdana\">Monday</font></TH>");
//		pw.println("<TH valign=center align=middle width=\"14%\" bgcolor=\"#000000\"><font size=\"1\" color=\"#FFFFFF\" face=\"Verdana\">Tuesday</font></TH>");
//		pw.println("<TH valign=center align=middle width=\"14%\" bgcolor=\"#000000\"><font size=\"1\" color=\"#FFFFFF\" face=\"Verdana\">Wednesday</font></TH>");
//		pw.println("<TH valign=center align=middle width=\"14%\" bgcolor=\"#000000\"><font size=\"1\" color=\"#FFFFFF\" face=\"Verdana\">Thursday</font></TH>");
//		pw.println("<TH valign=center align=middle width=\"14%\" bgcolor=\"#000000\"><font size=\"1\" color=\"#FFFFFF\" face=\"Verdana\">Friday</font></TH>");
//		pw.println("<TH valign=center align=middle width=\"14%\" bgcolor=\"#000000\"><font size=\"1\" color=\"#FFFFFF\" face=\"Verdana\">Saturday</font></TH>");
		
		
		
		
		
		
	}
	
	private String getDayName(int mon) {
		switch(mon) {
			case 1:
				return "Sunday";
			case 2:
				return "Monday";
			case 3:
				return "Tuesday";
			case 4:
				return "Wednesday";
			case 5:
				return "Thursday";
			case 6:
				return "Friday";
			case 7:
				return "Saturday";
			default:
				return "";
		}
	}
	
}
