package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exceptions.RegistrationUnsuccessfulException;
import com.revature.models.User;
import com.revature.services.AuthService;

public class UserController {

	private AuthService authService = new AuthService();
	private ObjectMapper mapper = new ObjectMapper();

	public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException{

		BufferedReader reader = req.getReader();
		StringBuilder stBuilder = new StringBuilder();
		String line = reader.readLine();
		
		while (line != null) {
			stBuilder.append(line);
			line = reader.readLine();
		}
		
		String body = new String(stBuilder);
		
		User user = mapper.readValue(body, User.class);
		System.out.println("reached authservice");
		//System.out.println(body);
		

		User authUser = authService.login(user.getUsername(), user.getPassword());
		System.out.println("passed authservice");

		if (authUser!=null) {
			HttpSession session = req.getSession();
			session.setAttribute("role", authUser.getRole().toString());
			session.setAttribute("user", authUser);
			resp.setStatus(200);
		} else {
			resp.setStatus(401);
		}

	}
	
	public void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader reader = req.getReader();
		StringBuilder stBuilder = new StringBuilder();
		String line = reader.readLine();
		
		while (line != null) {
			stBuilder.append(line);
			line = reader.readLine();
		}
		
		String body = new String(stBuilder);
		System.out.println(body);
		User user = mapper.readValue(body, User.class);
		try {
			User newUser = authService.register(user); 
			HttpSession session = req.getSession();
			session.setAttribute("user", newUser);
			resp.setStatus(201);	
		} catch(RegistrationUnsuccessfulException e) {
			
			resp.setStatus(406);
		}
	}
}
