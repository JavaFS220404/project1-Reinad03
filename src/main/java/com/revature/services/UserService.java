package com.revature.services;

import java.util.List;
import java.util.Optional;

import com.revature.models.User;
import com.revature.repositories.IUserDAO;
import com.revature.repositories.UserDAO;

/**
 * The UserService should handle the processing and retrieval of Users for the ERS application.
 *
 * {@code getByUsername} is the only method required;
 * however, additional methods can be added.
 *
 * Examples:
 * <ul>
 *     <li>Create User</li>
 *     <li>Update User Information</li>
 *     <li>Get Users by ID</li>
 *     <li>Get Users by Email</li>
 *     <li>Get All Users</li>
 * </ul>
 */
public class UserService {
	protected IUserDAO userDAO = new UserDAO();
	/**
	 *     Should retrieve a User with the corresponding username or an empty optional if there is no match.
     */
	public Optional<User> getByUsername(String username) {
		return userDAO.getByUsername(username);
	}
	
	public Optional<User> getByUserId(int id) {
		return userDAO.getById(id);
	}
	
	public List<User> getAllUser(){
		return userDAO.gettAllUser();
	}
	
	public User create(User userToBeRegistered) {
		return userDAO.create(userToBeRegistered);
	}
}
