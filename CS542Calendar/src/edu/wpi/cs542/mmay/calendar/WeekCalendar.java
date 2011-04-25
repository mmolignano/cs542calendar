package edu.wpi.cs542.mmay.calendar;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class WeekCalendar {

	/**
	 * Get a list of days for the current week
	 * 
	 * @return current week
	 */
	public static ArrayList<GregorianCalendar> getCurrentWeek() {
		ArrayList<GregorianCalendar> curWeek = new ArrayList<GregorianCalendar>();
		
		// Get to Sunday of current week first
		GregorianCalendar c = new GregorianCalendar();
		int day = c.get(GregorianCalendar.DATE);
		int month = c.get(GregorianCalendar.MONTH);
		int year = c.get(GregorianCalendar.YEAR);
		int dow = c.get(GregorianCalendar.DAY_OF_WEEK);
		int maxDay = c.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		
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
		
		// Once Sunday is known, set variables for that day
		int begYear = c.get(GregorianCalendar.YEAR);
		int begMonth = c.get(GregorianCalendar.MONTH);
		int begDay = c.get(GregorianCalendar.DATE);
		GregorianCalendar c3 = new GregorianCalendar(begYear, begMonth, begDay);
		
		// Loop through from Sunday to Saturday and add each new day to the list
		for (int i = 1; i <=7; i++) {
			day = c3.get(java.util.Calendar.DATE);
			month = c3.get(java.util.Calendar.MONTH);
			year = c3.get(java.util.Calendar.YEAR);
			maxDay = c3.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			
			// Add this calendar to list then move to next day
			curWeek.add(c3);
			
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
		
		return curWeek;
	}
	
	/**
	 * Given a Saturday, get a list of days for the next week
	 * 
	 * @param saturday	Saturday of week behind
	 * @return	next week
	 */
	public static ArrayList<GregorianCalendar> getNextWeek(GregorianCalendar sunday) {
		ArrayList<GregorianCalendar> curWeek = new ArrayList<GregorianCalendar>();
		
		// Once Sunday is known, set variables for that day
		int year = sunday.get(GregorianCalendar.YEAR);
		int month = sunday.get(GregorianCalendar.MONTH);
		int day = sunday.get(GregorianCalendar.DATE);
		int maxDay = sunday.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		GregorianCalendar c3 = new GregorianCalendar(year, month, day);
		
		// Loop through from Sunday to Saturday and add each new day to the list
		for (int i = 1; i <=7; i++) {
			day = c3.get(java.util.Calendar.DATE);
			month = c3.get(java.util.Calendar.MONTH);
			year = c3.get(java.util.Calendar.YEAR);
			maxDay = c3.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			
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
			
			// Add this calendar to list then move to next day
			curWeek.add(c3);
		}
		
		return curWeek;
	}
	
	/**
	 * Given a Sunday, get a list of days for the previous week
	 * 
	 * @param sunday	Sunday of week ahead
	 * @return previous week
	 */
	public static ArrayList<GregorianCalendar> getPreviousWeek(GregorianCalendar sunday) {
		ArrayList<GregorianCalendar> curWeek = new ArrayList<GregorianCalendar>();
		
		// Get a calendar at first day of week
		int day = sunday.get(GregorianCalendar.DATE);
		int month = sunday.get(GregorianCalendar.MONTH);
		int year = sunday.get(GregorianCalendar.YEAR);
		int maxDay = sunday.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		GregorianCalendar c = new GregorianCalendar(year, month, day);
		
		// Go backwards 7 days
		for (int i = 0; i < 7; i++) {
			day = c.get(java.util.Calendar.DATE);
			month = c.get(java.util.Calendar.MONTH);
			year = c.get(java.util.Calendar.YEAR);
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
			curWeek.add(0, c);
		}
		
		return curWeek;
	}
}
