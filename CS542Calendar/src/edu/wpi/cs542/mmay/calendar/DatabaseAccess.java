package edu.wpi.cs542.mmay.calendar;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jdo.*;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import edu.wpi.cs542.mmay.calendar.kinds.*;


/**
 * 
 *
 * @author Mike Molignano
 * @author Andrew Yee
 *
 */
public class DatabaseAccess {
	
	public static boolean addEvent(Event ev) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		try {	
//			tx.begin();
			pm.makePersistent(ev);
//			tx.commit();
		}finally	{
//			if (tx.isActive()){
//				tx.rollback();
//				returner = false;
//			}
		}
		
		pm.close();

		return returner;
	}
	
	public static boolean removeEvent(Event ev) {
		return false;
	}
	
	public static boolean addCalendar(Calendar cal) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		try {	
//			tx.begin();
			pm.makePersistent(cal);
//			tx.commit();
		}finally	{
//			if (tx.isActive()){
//				tx.rollback();
//				returner = false;
//			}
		}
		
		pm.close();

		return returner;
	}
	
	public static boolean removeCalendar(Calendar cal) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		try {
			pm.deletePersistent(cal);
		} finally {
			
		}
		
		pm.close();
		return returner;
	}
	
//	public static Calendar getCalendar(Key key) {
//		
//	}
	
	public static Collection<Calendar> fetchAllCalendars() {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Collection<Calendar> returner = new LinkedList<Calendar>();
		Extent<Calendar> calendars = pm.getExtent(Calendar.class);

		Iterator<Calendar> iterator = calendars.iterator();
		while (iterator.hasNext()){
			returner.add(iterator.next());
		}
		pm.close();
		return returner;
	}
	
	public static List<Event> getEventsByDate(){
		ArrayList<Event> returner = new ArrayList<Event>();
		
		Collection<Event> events = fetchAllEventsByDate();
	
		Iterator<Event> iterator = events.iterator();
		while(iterator.hasNext()){
			Event event = iterator.next();
			returner.add(event);
		}
		
		return returner;
	}
	
	public static Collection<Event> fetchAllEvents() {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Collection<Event> returner = new LinkedList<Event>();
		Extent<Event> events = pm.getExtent(Event.class);

		Iterator<Event> iterator = events.iterator();
		while (iterator.hasNext()){
			returner.add(iterator.next());
		}
		pm.close();
		return returner;
	}
	
	public static Collection<Event> fetchAllEventsByDate() {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		//Collection<Event> returner = new LinkedList<Event>();
		Query query = pm.newQuery(pm.getExtent(Event.class));
		query.setOrdering("startDate asc");
		Collection<Event> returner = (Collection<Event>)(query.execute());
		//return returner;
		pm.close();
		return returner;
	}
	
	public static List<Event> getEventsFromCalendar(Calendar calendar) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		//Query query = pm.newQuery(pm.getExtent(Calendar.class));
		//query.setFilter("id == idParam");
		//query.declareParameters(" idParam)
		Calendar cal = pm.getObjectById(Calendar.class, calendar.getId());
		pm.close();
		return cal.getEvents();
		
	}
	
	public static List<Event> getPendingEventsForUser(Ownership account) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		PendingEvent pendEvent = pm.getObjectById(PendingEvent.class, account.getAccount());
		pm.close();
		return pendEvent.getPendingEvents();
	}

}
