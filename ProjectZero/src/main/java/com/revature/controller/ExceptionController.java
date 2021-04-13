package com.revature.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.ClientListNullException;
import com.revature.exceptions.ClientNotFoundException;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;

public class ExceptionController implements Controller {
	private Logger logger = LoggerFactory.getLogger(ExceptionController.class); 
	
	private ExceptionHandler<BadParameterException> badParameterExceptionHandler = (e, ctx)->{
		logger.warn("A user provided a bad parameter. Exception Message: " + e.getMessage());
		ctx.status(400); 
	};
	
	private ExceptionHandler<ClientNotFoundException> clientNotFoundExceptionHandler = (e, ctx)->{
		logger.warn("A user provided a bad parameter. Exception Message: " + e.getMessage()); 
		ctx.status(404); 
	};
	
	private ExceptionHandler<ClientListNullException> clientListNullExceptionHandler = (e, ctx)->{
		logger.warn("A user has requested access to Client table which is currently empty."); 
		ctx.status(400); 
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.exception(BadParameterException.class, badParameterExceptionHandler);
		app.exception(ClientNotFoundException.class, clientNotFoundExceptionHandler);
		app.exception(ClientListNullException.class, clientListNullExceptionHandler); 
	}

}
