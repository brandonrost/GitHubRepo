package com.revature.dbsetup;


import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.ReimbursementType;
import com.revature.models.StatusCode;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.util.SessionUtility;

public class HibernateDBSetup {
	
	public void setUp() {
		
		Session session = SessionUtility.getSessionFactory().openSession();
		
		Transaction tx = session.beginTransaction(); 
		
		UserType employeeType = new UserType("Employee"); 
		UserType financeManagerType = new UserType ("Finance Manager"); 
		
		StatusCode pendingCode = new StatusCode("Pending"); 
		StatusCode acceptedCode = new StatusCode("Accepted");
		StatusCode rejectedCode = new StatusCode("Rejected"); 
		
		ReimbursementType lodgingType = new ReimbursementType("Lodging");
		ReimbursementType travelType = new ReimbursementType("Travel");
		ReimbursementType foodType = new ReimbursementType("Food");
		ReimbursementType otherType = new ReimbursementType("Other");
		
		
		User newEmployee = new User("username", "password","Bob","Burger","email@email.com", new UserType("Employee")); 
		User newFinanceManager = new User("username1", "password","Jane","Doe", "email1@email.com", new UserType("Finance Manager"));
		
		session.save(financeManagerType);
		session.save(employeeType); 
		
		session.save(pendingCode); 
		session.save(acceptedCode);
		session.save(rejectedCode);
		
		session.save(lodgingType);
		session.save(travelType);
		session.save(foodType);
		session.save(otherType);
		
		session.save(newEmployee);
		session.save(newFinanceManager);

		tx.commit();
		
	}
}
