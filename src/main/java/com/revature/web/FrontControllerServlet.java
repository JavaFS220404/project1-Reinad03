package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.LogoutController;
import com.revature.controllers.ReimbursementController;
import com.revature.controllers.UserController;
import com.revature.models.Reimbursement;
import com.revature.models.User;

public class FrontControllerServlet extends HttpServlet{
	
	private ObjectMapper mapper = new ObjectMapper();
	private ReimbursementController reimbursementController = new ReimbursementController();
	private UserController userController = new UserController();
	private LogoutController logoutController = new LogoutController();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException{
		
		resp.setContentType("application/json");
		
		resp.setStatus(404);
		
		final String URL = req.getRequestURI().replace("/ERSApp/core/", "");
		
		String[] UrlSections = URL.split("/");
		
		switch(UrlSections[0]) {
		case "login":
			if(req.getMethod().equals("POST")) {
				
				userController.login(req,resp);
			}
			break;
		case "register":
			if(req.getMethod().equals("POST")) {
				userController.addUser(req, resp);
			}
			break;
		case "logout":
			if(req.getMethod().equals("GET")) {
				logoutController.logout(req,resp);
			}
			break;
		case "reimbursements":
			HttpSession session = req.getSession(false);
			if(session!=null) {
				if(req.getMethod().equals("GET")) {
				reimbursementController.getReimbursementList(session, resp);
				} else if(req.getMethod().equals("POST")) {
					BufferedReader reader = req.getReader();
					StringBuilder stBuilder = new StringBuilder();
					String line = reader.readLine();
					
					while(line!=null) {
						stBuilder.append(line);
						line = reader.readLine();
					}
					String body = new String(stBuilder);
					System.out.println(body);
					
					Reimbursement reimbursement = mapper.readValue(body, Reimbursement.class);
					reimbursement.setAuthor((User)session.getAttribute("user"));
					reimbursementController.addReimbursement(reimbursement, resp);
				}
			}else {
				resp.setStatus(401);
			}
			break;
		}
		
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		doGet(req, resp);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		doGet(req, resp);
	}
}
