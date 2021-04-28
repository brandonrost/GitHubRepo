package com.revature.controllers;

import io.javalin.Javalin;

public interface Controller {

	void mapEndpoints(Javalin app);

}
