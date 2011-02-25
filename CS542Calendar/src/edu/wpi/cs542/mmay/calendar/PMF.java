package edu.wpi.cs542.mmay.calendar;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * Persistence Manager Factory Singleton
 *
 * @author Mike Molignano
 * @author Andrew Yee
 *
 */
public class PMF {
	
	private static final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	private PMF() {}
	
	public static PersistenceManagerFactory getInstance() {
		return pmf;
	}

}
