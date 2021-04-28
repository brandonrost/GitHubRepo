package com.revature.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.exceptions.BadParameterException;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;

public class ExceptionController implements Controller {
	private Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	
	private ExceptionHandler<BadParameterException> badParameterException = (e, ctx) -> {
		logger.warn(e.getMessage());
		ctx.status(400);
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.exception(BadParameterException.class, badParameterException);
	}

}
