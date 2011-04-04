package edu.wpi.cs542.mmay.calendar;

import java.util.Date;

import edu.wpi.cs542.mmay.calendar.kinds.Event;

public class TestEvent {

	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		Event ev = new Event("event 1", new Date(2012, 1, 1, 0, 0), "world", "end of the world");
		// TODO Auto-generated method stub
		
		//DatabaseAccess access = new DatabaseAccess();
		
		DatabaseAccess.addEvent(ev);

	}

}
