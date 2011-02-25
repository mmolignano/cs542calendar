package edu.wpi.cs542.mmay.calendar;

import javax.jdo.PersistenceManager;
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
			tx.begin();
	
			pm.makePersistent(ev);
	
			tx.commit();
		}finally	{
			if (tx.isActive()){
				tx.rollback();
				returner = false;
			}
		}
		
		pm.close();

		return returner;
	}

}
