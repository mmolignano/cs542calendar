package edu.wpi.cs542.mmay.calendar.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import edu.wpi.cs542.mmay.calendar.DatabaseAccess;
import edu.wpi.cs542.mmay.calendar.WeekCalendar;
import edu.wpi.cs542.mmay.calendar.kinds.Event;
import edu.wpi.cs542.mmay.calendar.kinds.Ownership;

@SuppressWarnings("serial")
public class CalendarShowServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException {
		// Check that a user is logged in...
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }
		
		// force creation if not there already
		Ownership owner = DatabaseAccess.getOwnershipByUser(user);
		
		// Get a list of days of the current week
		ArrayList<GregorianCalendar> curWeek;
		if (req.getParameter("forward") != null) {
			curWeek = WeekCalendar.getNextWeek(stringToCal("" + req.getParameter("forward")));
		} else if (req.getParameter("backward") != null) {
			curWeek = WeekCalendar.getPreviousWeek(stringToCal("" + req.getParameter("backward")));
		} else {
			curWeek = WeekCalendar.getCurrentWeek();
		}
			
		// Setup today
		GregorianCalendar today = new GregorianCalendar();
		int tDay = today.get(GregorianCalendar.DATE);
		int tMonth = today.get(GregorianCalendar.MONTH);
		int tYear = today.get(GregorianCalendar.YEAR);
			
		
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		
		pw.println("<p>" + user.getNickname()+ " (You can <a href=\"" +
				userService.createLogoutURL(req.getRequestURI()) + "\">sign out</a>.)</p>");
		pw.println("<p><a href=\"index.jsp\">Home</a>");
		
		pw.print("<h1>My Calendar</h1>");
		
		// Add buttons to move forward and backward and show today
		// Show Today
		pw.println("<form action=\"showcalendar\" method=\"get\" />");
		pw.println("<input type=\"submit\" value=\"Today\"></form>");
		// Move Backward a week
		pw.println("<form style=\"display: inline\" action=\"showcalendar\" method=\"get\" />");
		pw.println("<input type=\"hidden\" name=\"backward\" value=\"" + this.getPreviousString(curWeek) + "\" />");
		pw.println("<input type=\"submit\" value=\"<--\"></form>");
		// Move Forward a week
		pw.println("<form style=\"display: inline\" action=\"showcalendar\" method=\"get\" />");
		pw.println("<input type=\"hidden\" name=\"forward\" value=\"" + this.getNextString(curWeek) + "\" />");
		pw.println("<input type=\"submit\" value=\"-->\"></form>");
		
		
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
		
		
		
		
		
		pw.println();
		
		
		// Create the top (days of the week)
		pw.println("<TABLE width=\"100%\" border=1 bordercolordark=\"#000000\" bordercolorlight=\"#FFFFFF\" cellpadding=\"5\"><TBODY><TR>");
		
		// Print out the 7 days of the week (SUN-SAT, day month year)
		for (GregorianCalendar c3 : curWeek) {
			int day = c3.get(java.util.Calendar.DATE);
			int month = c3.get(java.util.Calendar.MONTH);
			int year = c3.get(java.util.Calendar.YEAR);
			
			// Print this day
			if (day == tDay && month == tMonth && year == tYear) {
				pw.println("<TH valign=center align=middle width=\"14%\" bgcolor=\"#00A0F0\"><font size=\"1\" color=\"#FFFFFF\" face=\"Verdana\">" +
						getDayName(c3.get(GregorianCalendar.DAY_OF_WEEK)) + " " + (month+1) + "/" + day + "</font></TH>");
			} else {
				pw.println("<TH valign=center align=middle width=\"14%\" bgcolor=\"#000000\"><font size=\"1\" color=\"#FFFFFF\" face=\"Verdana\">" +
						getDayName(c3.get(GregorianCalendar.DAY_OF_WEEK)) + " " + (month+1) + "/" + day + "</font></TH>");
			}
		}
		
		pw.println("<TR>");
		// Loop through days and print events for that day
		for (GregorianCalendar c3 : curWeek) {
			pw.println("<TD vAlign=top align=left width=\"14%\" >");
			// Loop through and print events for this day
			Collection<Event> events = DatabaseAccess.getEventsByUserAndDate(user.getNickname(), c3);
			for (Event e : events) {
				// Add button to edit event
				// Button to Edit Calendar
				pw.println(e.getEventName() + "   ");
				pw.println("<form style=\"display: inline\" action=\"editEvent.jsp\" method=\"post\">");
				pw.println("<input type=\"hidden\" name=\"key\" value=\"" + e.getId() + "\" />");
				pw.println("<input type=\"submit\" value=\"+\" /></form>");
				
				//pw.println(e.getEventName() + "<br />");
				pw.println("<br />");
			}
			pw.println("&nbsp;<br /><br /><br /><br /></TD>");
		}
		pw.println("</tr></TABLE>");
		
		
//		pw.println("<TD vAlign=top align=left width=\"14%\" ><font size=\"3\" face=\"Verdana\">3</font><br><br><br><br></TD></tr></TABLE>");
		
		
//		c3 = new GregorianCalendar(begYear, begMonth, begDay);
//		
//		// Print out the events of the 7 days of the week (SUN-SAT)
//		for (int i = 1; i <=7; i++) {
//			day = c3.get(java.util.Calendar.DATE);
//			month = c3.get(java.util.Calendar.MONTH);
//			year = c3.get(java.util.Calendar.YEAR);
//			maxDay = c3.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
//			// First print this day
////			pw.println("Day: " + day + "\tMonth: " + (month+1) + "\tYear: " + year + "<br />");
//			// PRINT OUT ALL THE EVENTS OF THIS DAY
//			
//			// Move to next day
//			// First check to see if last day in month
//			if (day == maxDay) {
//				// Check to see if last month of year
//				if (month == 11) {
//					// Need to move month to 1 and year forward 1
//					c3  = new GregorianCalendar(year+1, 0, 1);
//				} else {
//					// Need to move month forward and set day 1
//					c3 = new GregorianCalendar(year, month+1, 1);
//				}
//			} else {
//				// Just move day back
//				c3 = new GregorianCalendar(year, month, day+1);
//			}
//		}
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
	
	private String getPreviousString(ArrayList<GregorianCalendar> cals) {
		GregorianCalendar c = cals.get(0);
		String sunday = c.get(GregorianCalendar.YEAR) + "/" + (c.get(GregorianCalendar.MONTH) + 1) + "/" + c.get(GregorianCalendar.DATE); 
		return sunday;
	}
	
	private String getNextString(ArrayList<GregorianCalendar> cals) {
		GregorianCalendar c = cals.get(6);
		String sunday = c.get(GregorianCalendar.YEAR) + "/" + (c.get(GregorianCalendar.MONTH) + 1) + "/" + c.get(GregorianCalendar.DATE); 
		return sunday;
	}
	
	private GregorianCalendar stringToCal(String day) {
		String [] s = day.split("/");
		return new GregorianCalendar(new Integer(s[0]), new Integer(s[1]) - 1, new Integer(s[2]));
	}
	
}
