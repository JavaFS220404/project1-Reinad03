package com.revature.repositories;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO implements IUserDAO{

    /**
     * Should retrieve a User from the DB with the corresponding username or an empty optional if there is no match.
     */
    public Optional<User> getByUsername(String username) {
    	
    	try(Connection conn = ConnectionFactory.getInstance().getConnection()){
    		String sql = "SELECT * FROM ers_users WHERE ers_username=?;";
    		
    		PreparedStatement statement = conn.prepareStatement(sql);
    		
    		statement.setString(1, username);
    		
    		ResultSet result = statement.executeQuery();
    		
    		User user = new User();
    		if(result.next()) {
    			user.setId(result.getInt("ers_users_id"));
    			user.setUsername(result.getString("ers_username"));
				user.setPassword(result.getString("ers_password"));
				user.setFirstName(result.getString("user_first_name"));
				user.setLastName(result.getString("user_last_name"));
				user.setEmail(result.getString("user_email"));
				int role_id = result.getInt("user_role_id");
				if(role_id ==1) {
					user.setRole(Role.EMPLOYEE);
				}else {
					user.setRole(Role.FINANCE_MANAGER);
				}
				Optional<User> optional = Optional.of(user);
				return optional;
    		}
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
        return Optional.empty();
    }
    
    public Optional<User> getById(int id) {
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM ers_users WHERE ers_users_id= ?;";
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, id);

			ResultSet result = statement.executeQuery();

			while (result.next()) {
				User user = new User();
				user.setId(result.getInt("ers_users_id"));
				user.setUsername(result.getString("ers_username"));
				user.setPassword(result.getString("ers_password"));
				int role_id = result.getInt("user_role_id");
				if (role_id ==1) {
					user.setRole(Role.EMPLOYEE);
				}else {
					user.setRole(Role.FINANCE_MANAGER);
				}
				user.setFirstName(result.getString("user_first_name"));
				user.setLastName(result.getString("user_last_name"));
				user.setEmail(result.getString("user_email"));
				
				return Optional.of(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Optional.empty();
	}
    
    public  List<User> gettAllUser() {
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "SELECT * FROM ers_users;";

			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);

			List<User> userList = new ArrayList<User>();

			while (result.next()) {
				User user = new User();
				user.setId(result.getInt("ers_users_id"));
				user.setUsername(result.getString("ers_username"));
				user.setPassword(result.getString("ers_password"));
				int role_id = result.getInt("user_role_id");
				if (role_id ==1) {
					user.setRole(Role.EMPLOYEE);
				}else {
					user.setRole(Role.FINANCE_MANAGER);
				}
				user.setFirstName(result.getString("user_first_name"));
				user.setLastName(result.getString("user_last_name"));
				user.setEmail(result.getString("user_email"));
				userList.add(user);
			}
			return userList;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
    

    /**
     * <ul>
     *     <li>Should Insert a new User record into the DB with the provided information.</li>
     *     <li>Should throw an exception if the creation is unsuccessful.</li>
     *     <li>Should return a User object with an updated ID.</li>
     * </ul>
     *
     * Note: The userToBeRegistered will have an id=0, and username and password will not be null.
     * Additional fields may be null.
     */
    public User create(User userToBeRegistered) {
    	try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "INSERT INTO ers_users (ers_username, ers_password, user_first_name,"
					+ " user_last_name, user_email, user_role_id) VALUES (?, ?, ?, ?, ?, ?);";
			PreparedStatement statement = conn.prepareStatement(sql);
			int count = 0;

			statement.setString(++count, userToBeRegistered.getUsername());
			statement.setString(++count, userToBeRegistered.getPassword());
			switch(userToBeRegistered.getRole()){
			case EMPLOYEE:
				statement.setInt(++count, 1);
				break;
			case FINANCE_MANAGER:
				statement.setInt(++count, 2);
				break;
			}
			
			statement.setString(++count, userToBeRegistered.getFirstName());
			statement.setString(++count, userToBeRegistered.getLastName());
			statement.setString(++count, userToBeRegistered.getEmail());

			statement.execute();
			Optional<User> newUser = getByUsername(userToBeRegistered.getUsername());
			return newUser.get();

		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
}
