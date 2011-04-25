package edu.wpi.cs542.mmay.calendar;

import java.util.ArrayList;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
	
	public static boolean addNewCalendar(String nickname, Calendar cal) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Ownership owner = pm.getObjectById(Ownership.class, nickname);
		pm.close();
		
		returner &= addCalendar(cal);
		returner &= addOwnedCalendar(owner, cal.getId());
				
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

	public static boolean addToOwnedCalendars(String nickname, Key calKey) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		Ownership owner = pm.getObjectById(Ownership.class, nickname);
		Calendar cal = pm.getObjectById(Calendar.class, calKey);
		pm.close();
		
		returner = addOwnedCalendar(owner, calKey) && addCalendarOwner(owner, cal);
		
		return returner;
	}
	
	public static boolean addOwnedCalendar(Ownership owner, Key calKey) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		try {
			tx.begin();
			owner.addOwnedCalendar(calKey);
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
	
	public static boolean addCalendarOwner(Ownership owner, Calendar cal) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		try {
			tx.begin();
			cal.addOwner(owner.getAccount());
			pm.makePersistent(cal);
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
	
	public static boolean removeCalendar(String nickname, Key key) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Transaction tx = pm.currentTransaction();;
		
		try {
			tx.begin();
			Calendar c = pm.getObjectById(Calendar.class, key);
			pm.deletePersistent(c);
			tx.commit();	
		} finally {
			if (tx.isActive()){
				tx.rollback();
				returner = false;
			}
			pm.close();
		}
		 
		returner &= removeFromOwner(nickname, key);
		
		return returner;
	}
	
	public static boolean removeFromOwner(String nickname, Key calKey) {
		boolean returner = true;
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Ownership owner = pm.getObjectById(Ownership.class, nickname);
	    Transaction tx = pm.currentTransaction();
	    try {
	    	tx.begin();
	    	owner.removeOwnedCalendar(calKey);
	    	tx.commit();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	if (tx.isActive()){
				tx.rollback();
				returner = false;
			}
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
		return getCalendarsByUser (user.getNickname());
	}
	
	public static Collection<Calendar> getCalendarsByUser(String nickname) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Ownership owner = pm.getObjectById(Ownership.class, nickname);
		//Set<Key> calKeys = owner.getOwnedCalendars();
		//Collection<Calendar> myCalendars = (Collection<Calendar>) pm.getObjectsById(calKeys);
		pm.close();
		//return myCalendars;
		return getCalendars(owner.getOwnedCalendars());
	}
	
	public static Collection<Calendar> getCalendars(Set<Key> calKeys) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Collection<Calendar> myCalendars = new LinkedList<Calendar>();
		/*if (calKeys.isEmpty()) {
			myCalendars = new LinkedList<Calendar>(); 
		} else {*/
			//myCalendars = (Collection<Calendar>) pm.getObjectsById(calKeys);
		//}
		Iterator<Key> iterator = calKeys.iterator();
		//for (Key key : calKeys) {
		while (iterator.hasNext()) {
			Key key = iterator.next();
			myCalendars.add(pm.getObjectById(Calendar.class, key));
		}
		pm.close();
		return myCalendars;
	}
	
	public static Collection<Event> getEventsByCalendarKey(Key calKey) {
		return getEventsFromCalendar(calKey);
	}
	
	public static Collection<Event> getCalendarsByCalendar(Calendar cal) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Calendar calendar = pm.getObjectById(Calendar.class, cal.getId());
		pm.close();
		return getEventsFromCalendar(calendar.getEventSet());
	}
	
	public static Collection<Event> getEventsFromCalendar(Set<Key> eventKeys) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Collection<Event> myEvents = new LinkedList<Event>();
		Iterator<Key> iterator = eventKeys.iterator();
		while (iterator.hasNext()) {
			Key key = iterator.next();
			myEvents.add(pm.getObjectById(Event.class, key));
		}
		pm.close();
		return myEvents;
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
	
	
	public static Ownership getOwnershipByUser(User user) {
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		Ownership returner;
		try {
			Ownership owner = pm.getObjectById(Ownership.class, user.getNickname());
			returner = owner;
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
			Ownership owner = pm.getObjectById(Ownership.class, nickname);
			//PendingCalendar pending = pm.getObjectById(PendingCalendar.class, nickname);
			owner.addPendingCalendar(calKey);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx.isActive()){
				tx.rollback();
				returner = false;
			}
			pm.close();
		}

		return returner;
	}
	
	public static boolean addPendingCalendar(Ownership owner, Key calKey) {
		boolean returner = true;
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		try {	
			owner.addPendingCalendar(calKey);
			pm.makePersistent(owner);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
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
			Ownership owner = pm.getObjectById(Ownership.class, nickname);
			//PendingCalendar pending = pm.getObjectById(PendingCalendar.class, nickname);
			owner.removePendingCalendar(calKey);
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
