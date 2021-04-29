package com.revature.dbsetup;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Reimbursement;
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
		
		session.save(financeManagerType);
		session.save(employeeType); 
		
		session.save(pendingCode); 
		session.save(acceptedCode);
		session.save(rejectedCode);
		
		session.save(lodgingType);
		session.save(travelType);
		session.save(foodType);
		session.save(otherType);
		
		tx.commit();
		
		Transaction tx2 = session.beginTransaction();
		
		User newEmployee = new User("username", "password","Bob","Burger","email@email.com", new UserType("Employee")); 
		User newFinanceManager = new User("username1", "password","Jane","Doe", "email1@email.com", new UserType("Finance Manager"));
		
		session.persist(newEmployee);
		session.persist(newFinanceManager);
		
		Reimbursement reimbursement = new Reimbursement(50, LocalTime.now().atDate(LocalDate.now()), session.get(User.class, 1), session.get(StatusCode.class, 1), session.get(ReimbursementType.class, 2));
		
		session.persist(reimbursement); 

		tx2.commit(); 
		
	}
}
