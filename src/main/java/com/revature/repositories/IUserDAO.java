package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import com.revature.models.User;

public interface IUserDAO {
	
	public Optional<User> getByUsername(String username);
	public  Optional<User> getById(int id);
	public  List<User> gettAllUser();
	public User create(User userToBeRegistered);

}
