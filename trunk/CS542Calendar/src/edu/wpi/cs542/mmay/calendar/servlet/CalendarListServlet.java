package edu.wpi.cs542.mmay.calendar.servlet;

import java.io.IOException;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.wpi.cs542.mmay.calendar.kinds.Calendar;

@SuppressWarnings("serial")
public class CalendarListServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		java.util.Calendar c3 = new GregorianCalendar(2011, 0, 1);
		int day = c3.get(java.util.Calendar.DATE);
		int month = c3.get(java.util.Calendar.MONTH) + 1;
		int year = c3.get(java.util.Calendar.YEAR);
		int dow = c3.get(java.util.Calendar.DAY_OF_WEEK);
		int maxDay = c3.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		
		resp.setContentType("text/plain");
		
		resp.getWriter().println("day: " + day);
		resp.getWriter().println("month: " + month);
		resp.getWriter().println("year: " + year);
		resp.getWriter().println("dow: " + dow);
		resp.getWriter().println("maxDay: " + maxDay);
		
		int checkdow = dow;
		// Get a calendar at first day of week
		for (int i = checkdow; i > 1; i--) {
			day = c3.get(java.util.Calendar.DATE);
			month = c3.get(java.util.Calendar.MONTH);
			year = c3.get(java.util.Calendar.YEAR);
			dow = c3.get(java.util.Calendar.DAY_OF_WEEK);
			// First check if first day of month
			if (day == 1) {
				// Check to see if first month of year
				if (month == 0) {
					// Need to move month to 12 and year back 1
					c3  = new GregorianCalendar(year-1, 11, 31);
				} else {
					// Need to move month back and check max day
					java.util.Calendar temp = new GregorianCalendar(year, month-1, 1);
					maxDay = temp.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
					c3 = new GregorianCalendar(year, month-1, maxDay);
				}
			} else {
				// Just move day back
				c3 = new GregorianCalendar(year, month, day-1);
			}
		}
		
		resp.getWriter().println();
		
		// Print out the 7 days of the week (SUN-SAT, day month year)
		for (int i = 1; i <=7; i++) {
			day = c3.get(java.util.Calendar.DATE);
			month = c3.get(java.util.Calendar.MONTH) + 1;
			year = c3.get(java.util.Calendar.YEAR);
			maxDay = c3.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			// First print this day
			resp.getWriter().println("Day: " + day + "\tMonth: " + month + "\tYear: " + year);
			
			// Move to next day
			// First check to see if last day in month
			if (day == maxDay) {
				// Check to see if last month of year
				if (month == 11) {
					// Need to move month to 1 and year forward 1
					c3  = new GregorianCalendar(year+1, 0, 1);
				} else {
					// Need to move month forward and set day 1
					c3 = new GregorianCalendar(year, month, 1);
				}
			} else {
				// Just move day back
				c3 = new GregorianCalendar(year, month-1, day+1);
			}
		}
	}

}
