package com.revature.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controller.AccountController;
import com.revature.controller.ClientController;
import com.revature.controller.Controller;
import com.revature.controller.ExceptionController;
import com.revature.controller.StaticFileController;

import io.javalin.Javalin;

public class Application {
		
	private static Javalin app;
	private static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		
		app = Javalin.create(); 
		
		app.before(ctx ->{
			String URI = ctx.req.getRequestURI(); 
			String httpMethod = ctx.req.getMethod();
			if(ctx.req.getQueryString()==null) {
				logger.info(httpMethod + " request to endpoint '" + URI + "' recieved."); 
			}else {
				logger.info(httpMethod + " request to endpoint '" + URI + "' recieved with following query param string:"
						+ ctx.req.getQueryString()); 
			}
			});
		
		mapControllers(new ClientController(), new ExceptionController(), new StaticFileController(), new AccountController());
		
		app.start(7000);
		
		
	}
	
	public static void mapControllers(Controller...controllers) {
		for(Controller controller: controllers) {
			controller.mapEndpoints(app);
		}
	}

}
