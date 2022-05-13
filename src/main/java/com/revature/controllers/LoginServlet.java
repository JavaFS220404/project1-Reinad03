package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginServlet extends HttpServlet{
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException{
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		//check against the database for username and password
		
		RequestDispatcher reqDispatcher = null;
		PrintWriter printWriter = resp.getWriter();
		
		if(username.equalsIgnoreCase("userId")&password.equals("password")){
			HttpSession session = req.getSession();
			session.setAttribute("username", username);
			
			reqDispatcher = req.getRequestDispatcher("employee-home-page.html");
			reqDispatcher.forward(req, resp);
		}else {
			reqDispatcher = req.getRequestDispatcher("login.html");
			reqDispatcher.include(req,resp);
	
		}
	}

}
