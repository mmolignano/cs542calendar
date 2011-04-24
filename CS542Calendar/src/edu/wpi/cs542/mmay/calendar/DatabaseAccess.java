package edu.wpi.cs542.mmay.calendar;

import java.util.ArrayList;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.*;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import edu.wpi.cs542.mmay.calendar.kinds.*;

/**
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
			tx.begin();
			pm.makePersistent(ev);
			tx.commit();
		} catch (Exception e) {
		} finally {
			if (tx.isActive()){
				tx.rollback();
				returner = false;
			}
			pm.close();
		}
		
		return returner;
	}
	
	public static boolean addEventToCalendar(Event ev, Key calKey) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		try {	
			tx.begin();
			pm.makePersistent(ev);
			Calendar c = getCalendar(calKey);
			c.addEvent(ev);
			pm.makePersistent(c);
			tx.commit();
		} catch (Exception e) {
		} finally {
			if (tx.isActive()){
				tx.rollback();
				returner = false;
			}
			pm.close();
		}

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
			tx.begin();
			pm.makePersistent(cal);
			tx.commit();
		} catch (Exception e) {
		} finally {
			if (tx.isActive()) {
				// Error occurred so rollback the transaction
		        tx.rollback();
		        returner = false;
			}
			pm.close();
		}
		
		return returner;
	}
	
	public static boolean saveCalendar(Calendar cal) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		
		try {
			pm.makePersistent(cal);
		} finally {
			pm.close();
		}
		
		return returner;
	}
	
	public static boolean addToOwnedCalendars(Ownership owner, Calendar cal) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		try {
			tx.begin();
			pm.makePersistent(cal);
			owner.addPendingCalendar(cal);
			pm.makePersistent(owner);
			tx.commit();
		} finally {
			if (tx.isActive()){
				tx.rollback();
				returner = false;
			}
			pm.close();
		}
		
		return returner;
	}
	
	public static boolean addToOwnedCalendars(String nickname, Key calKey) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		try {
			tx.begin();
			Ownership owner = pm.getObjectById(Ownership.class, nickname);
			Calendar cal = pm.getObjectById(Calendar.class, calKey);
			owner.addPendingCalendar(cal);
			cal.addOwner(owner.getAccount());
			tx.commit();
		} finally {
			if (tx.isActive()){
				tx.rollback();
				returner = false;
			}
			pm.close();
		}
		
		return returner;
	}
	
	public static boolean removeCalendar(Key key) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Transaction tx = pm.currentTransaction();;
		
		try {
			tx.begin();
			/*
			// Get the correct calendar
			Extent<Calendar> e = pm.getExtent(Calendar.class, true);
		    Iterator<Calendar> iter = e.iterator();
		    while (iter.hasNext())
		    {
		        Calendar c = (Calendar)iter.next();
		        if (c.getId().equals(key)) {
		        	pm.deletePersistent(c);
		        	break;
		        }
		    }
		    */
			
			Calendar c = pm.getObjectById(Calendar.class, key);
			pm.deletePersistent(c);
			tx.commit();
		} finally {
			pm.close();
		}
		
		return returner;
	}
	
	public static Calendar getCalendar(Key key) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Calendar cal = pm.getObjectById(Calendar.class, key);
		pm.close();
		return cal;		
	}
	
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
	
	public static Collection<Calendar> getCalendarsByUser(User user) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Collection<Calendar> myCalendars = new ArrayList<Calendar>();
		Collection<Calendar> calendars = fetchAllCalendars();
		
		for (Calendar c : calendars) {
			if (c.getOwners().contains(user)) {
				myCalendars.add(c);
			}
		}
		
		return myCalendars;
	}
	
	public static Calendar getCalendarByUserByName(User user, String calName) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Collection<Calendar> myCalendars = getCalendarsByUser(user);
		
		
		for (Calendar c : myCalendars) {
			if (c.getName().equals(calName)) {
				return c;
			}
		}
		
		return new Calendar(calName, user);
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
	
	public static Collection<Event> getEventsFromCalendar(Key key) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Calendar cal = pm.getObjectById(Calendar.class, key);
		pm.close();
		return cal.getEvents();
		
	}
	
	public static List<Event> getPendingEventsForUser(Ownership account) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		PendingEvent pendEvent = pm.getObjectById(PendingEvent.class, account.getNickname());
		pm.close();
		return pendEvent.getPendingEvents();
	}

	/*public static Ownership getOwnershipByUser(User user) {
		Ownership returner = new Ownership();
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Extent<Ownership> owners = pm.getExtent(Ownership.class);
		Iterator<Ownership> iterator = owners.iterator();
		boolean found = false;
		while (iterator.hasNext()) {
			Ownership thisOwner = iterator.next();
			if (user.equals(thisOwner.getAccount())) {
				returner = thisOwner;
				found = true;
				break;
			}
		}
		pm.close();
		
		if (!found) {
			returner = new Ownership(user);
			addNewOwnership(returner);
		}
		
		return returner;
	}*/
	
	public static Ownership getOwnershipByUser(User user) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Ownership returner;
		try {
			returner = pm.getObjectById(Ownership.class, user.getNickname()); 
		} catch (Exception e) {
			returner = new Ownership(user);
			addNewOwnership(returner);
		} finally {
			pm.close();
		}
		
		return returner;
	}
	
	public static boolean addNewOwnership(Ownership owner) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		try {	
			tx.begin();
			pm.makePersistent(owner);
			tx.commit();
		} catch (Exception e) {
		} finally {
			if (tx.isActive()){
				tx.rollback();
				returner = false;
			}
			pm.close();
		}
		
		return returner;
	}

	public static boolean addPendingCalendar(Ownership owner, Calendar cal) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		try {	
			tx.begin();
			owner.addPendingCalendar(cal);
			pm.makePersistent(owner);
			tx.commit();
		} catch (Exception e) {
		} finally {
			if (tx.isActive()){
				tx.rollback();
				returner = false;
			}
			pm.close();
		}

		return returner;
	}
	
	public static boolean addPendingCalendar(String nickname, Key calKey) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		try {	
			tx.begin();
			//Ownership owner = pm.getObjectById(Ownership.class, nickname);
			PendingCalendar pending = pm.getObjectById(PendingCalendar.class, nickname);
			Calendar cal = pm.getObjectById(Calendar.class, calKey);
			pending.addPendingCalendar(cal);
			tx.commit();
		} catch (Exception e) {
		} finally {
			if (tx.isActive()){
				tx.rollback();
				returner = false;
			}
			pm.close();
		}

		return returner;
	}
	
	public static PendingEvent getPendingEventsForUser(User user) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		PendingEvent returner = pm.getObjectById(PendingEvent.class, user.getNickname()); 
		pm.close();
		
		return returner;
	}
	
	public static PendingCalendar getPendingCalendarsForUser(User user) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		PendingCalendar returner = pm.getObjectById(PendingCalendar.class, user.getNickname()); 
		pm.close();
		
		return returner;
	}
	
	public static boolean removePendingCalendar(String nickname, Key calKey) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		try {
			tx.begin();
			//Ownership owner = pm.getObjectById(Ownership.class, nickname);
			PendingCalendar pending = pm.getObjectById(PendingCalendar.class, nickname);
			pending.removePendingCalendar(calKey);
			tx.commit();
		} finally {
			if (tx.isActive()){
				tx.rollback();
				returner = false;
			}
			pm.close();
		}
		
		return returner;
	}
}
