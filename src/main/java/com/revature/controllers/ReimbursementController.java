package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.ReimbursementService;

public class ReimbursementController {
	
	private ReimbursementService reimbursementService = new ReimbursementService();
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public void getReimbursementList(HttpSession session, HttpServletResponse resp) throws IOException{
		User user= (User) session.getAttribute("user");
		System.out.println(reimbursementService);
		System.out.println(user);
		List<Optional<Reimbursement>> reimbursements = reimbursementService.getReimbursementList(user.getId());
		
		if(reimbursements.isEmpty()) {
			resp.setStatus(204);
		}else {
			
			List<Reimbursement> reimbList = new ArrayList<>();
			for(Optional<Reimbursement> reimbursement : reimbursements) {
				if(reimbursement.isPresent()) {
					reimbList.add(reimbursement.get());
					
				}
			}
			resp.setStatus(200); 
			String json = objectMapper.writeValueAsString(reimbList);
			System.out.println(reimbursements);
			PrintWriter print = resp.getWriter();
			print.print(json);
		}
	}
	
	public void addReimbursement(Reimbursement reimbursement, HttpServletResponse resp) throws IOException{

		if(reimbursementService.newReimbursement(reimbursement)) {
			resp.setStatus(201);
		}else {
			resp.setStatus(400);
		}
	}
}
