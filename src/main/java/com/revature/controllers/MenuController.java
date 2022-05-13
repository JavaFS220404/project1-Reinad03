package com.revature.controllers;

import java.util.Scanner;

public class MenuController {
	
	public static Scanner scan = new Scanner(System.in);
	
	public void welcomeMenu() {

		printWelcomeMenu();
		
	}
	
	private void printWelcomeMenu() {
		System.out.println("Welcome to Employee Reimbursement System. \n"
				+"Chose an option from the list \n"
				+"1) Login into application \n"
				+"0) Exit the aplication");
	}
	
	private void subMenu() {
		System.out.println("Select your Role \n"
				+"1) EMPLOYEE \n"
				+"2) FINANCE MANAGER \n;"
				+"3) QUIT");
		String response = scan.nextLine();
		
		switch (response) {
		case"1":
				employeeMenu();
				break;
		case "2":
				financeManagerMenu();
				break;
		default:
				System.out.println("Wrong user.");
				response = scan.nextLine();
				break;
		}
	}
	
	private void employeeMenu() {
		System.out.println("Employee Menu");
	}
	
	private void financeManagerMenu() {
		System.out.println("Finance Manager Menu");
	}
	
}
