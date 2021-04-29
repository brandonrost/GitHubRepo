package com.revature.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controllers.Controller;
import com.revature.dbsetup.HibernateDBSetup;

import io.javalin.Javalin;

public class Application {
	
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	private static HibernateDBSetup setup = new HibernateDBSetup();

	public static void main(String[] args) {
		
		//setup.setUp(); //This can be used to reset the database and set up a new one
		
		Javalin app = Javalin.create((config) -> {
			config.addStaticFiles("static");
			config.enableCorsForAllOrigins(); 
		});
		
		
		mapControllers(app);
		
		app.start(7000);
		

	}
	
	private static void mapControllers(Javalin app, Controller... controllers) {
		for (Controller c : controllers) {
			c.mapEndpoints(app);
		}
	}
}
