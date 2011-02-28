package edu.wpi.cs542.mmay.calendar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

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
	
	public static List<Event> getEvents(){
		ArrayList<Event> returner = new ArrayList<Event>();
		
		Collection<Event> events = fetchAllEvents();
	
		Iterator<Event> iterator = events.iterator();
		while(iterator.hasNext()){
			Event event = iterator.next();
			returner.add(event);
		}
		
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
		Collection<Event> returner = new LinkedList<Event>();
		Query query = pm.newQuery(pm.getExtent(Event.class));
		query.setOrdering("startDate asc");
		returner = (Collection<Event>) query.execute();
		return returner;
	}

}
