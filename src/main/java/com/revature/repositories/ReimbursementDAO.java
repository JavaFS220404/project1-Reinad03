package com.revature.repositories;

import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.models.Type;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ReimbursementDAO implements IReimbursementDAO{
		IUserDAO userDao = new UserDAO();
	/**
	 * Should retrieve a Reimbursement from the DB with the corresponding id or an
	 * empty optional if there is no match.
	 */
	public List<Optional<Reimbursement>> getById(int id) {
		
		List<Optional<Reimbursement>> list = new ArrayList<>();
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			String sql = "SELECT ers_reimbursement.*,ers_users.ers_username ,ers_users.user_first_name, ers_users.user_last_name"
					+ " FROM ers_reimbursement"
					+ " JOIN ers_users ON ers_reimbursement.reimb_author = ers_users.ers_users_id"
					+ " WHERE ers_reimbursement.reimb_author=" + id + ";";
			
			Statement statement = conn.createStatement();

			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()) {
    			Reimbursement reimbursement = new Reimbursement();
    			reimbursement.setId(result.getInt("reimb_id"));
    			reimbursement.setAmount(result.getDouble("reimb_amount"));
    			reimbursement.setSubmitted(result.getTimestamp("reimb_submitted"));
    			reimbursement.setDescription(result.getString("reimb_description"));
    			int statusId = result.getInt("reimb_status_id");
    			switch(statusId) {
    			case 1:
    				reimbursement.setStatus(Status.PENDING);
    				break;
    			case 2:
    				reimbursement.setStatus(Status.APPROVED);
    				break;
    			case 3:
    				reimbursement.setStatus(Status.DENIED);
    				break;
    			}
    			int typeId = result.getInt("reimb_type_id");
    			switch(typeId) {
    			case 1:
    				reimbursement.setType(Type.LODGING);
    			case 2:
    				reimbursement.setType(Type.TRAVEL);
    			case 3:
    				reimbursement.setType(Type.FOOD);
    			case 4:
    				reimbursement.setType(Type.OTHER);
    			}
    			
    			User user = new User(id,
    								result.getString("ers_username"),
    								null,
    								null,
    								result.getString("user_first_name"),
    								result.getString("user_last_name"),
    								null);
    			reimbursement.setAuthor(user);
    			//reimbursement.setResolver(user);
    			
    			list.add(Optional.of(reimbursement));
    			
    		}
    		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public Optional<Reimbursement> getOneById(int id) {
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			String sql = "SELECT ers_reimbursement.*,ers_users.ers_username ,ers_users.user_first_name, ers_users.user_last_name"
					+ " FROM ers_reimbursement"
					+ " JOIN ers_users ON ers_reimbursement.reimb_author = ers_users.ers_users_id"
					+ " WHERE ers_reimbursement.reimb_author=" + id + ";";
			
			Statement statement = conn.createStatement();

			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
    			Reimbursement reimbursement = new Reimbursement();
    			reimbursement.setId(result.getInt("reimb_id"));
    			reimbursement.setAmount(result.getDouble("reimb_amount"));
    			reimbursement.setSubmitted(result.getTimestamp("reimb_submitted"));
    			reimbursement.setDescription(result.getString("reimb_description"));
    			int statusId = result.getInt("reimb_status_id");
    			switch(statusId) {
    			case 1:
    				reimbursement.setStatus(Status.PENDING);
    				break;
    			case 2:
    				reimbursement.setStatus(Status.APPROVED);
    				break;
    			case 3:
    				reimbursement.setStatus(Status.DENIED);
    				break;
    			}
    			int typeId = result.getInt("reimb_type_id");
    			switch(typeId) {
    			case 1:
    				reimbursement.setType(Type.LODGING);
    			case 2:
    				reimbursement.setType(Type.TRAVEL);
    			case 3:
    				reimbursement.setType(Type.FOOD);
    			case 4:
    				reimbursement.setType(Type.OTHER);
    			}
    			
    			User user = new User(id,
    								result.getString("ers_username"),
    								null,
    								null,
    								result.getString("user_first_name"),
    								result.getString("user_last_name"),
    								null);
    			reimbursement.setAuthor(user);
    			
    			return Optional.of(reimbursement);
    		}
    		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.of(null);
	}

	/**
	 * Should retrieve a List of Reimbursements from the DB with the corresponding
	 * Status or an empty List if there are no matches.
	 */
	public List<Reimbursement> getByStatus(Status status) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
    		String sql = "SELECT * FROM ers_reimbursement_status where reimb_status_id = ?;";
    		PreparedStatement statement = conn.prepareStatement(sql);
    		int status_id =0;
    		switch(status){
    		case APPROVED:
				status_id = 1;
				break;
			case DENIED:
				status_id = 2;
				break;
			case PENDING:
				status_id = 3;
				break;
			default:
				status_id = 3;
				break; 
    		}
    		statement.setInt(1, status_id);
    		
    		ResultSet result = statement.executeQuery();
    		List<Reimbursement> reimbursementList = new ArrayList<>();
    		while(result.next()) {
    			Reimbursement reimbursement = new Reimbursement();
    			
    			reimbursement.setId(result.getInt("reimb_id"));
    			reimbursement.setAmount(result.getDouble("reimb_amount"));
    			reimbursement.setSubmitted(result.getTimestamp("reimb_submitted"));
    			reimbursement.setResolved(result.getTimestamp("reimb_resolved"));
    			reimbursement.setDescription(result.getString("reimb_description"));
    			reimbursement.setAuthor(userDao.getById(result.getInt("reimb_author")).get());
    			if(result.getInt("reimb_resolver")!=0) {
    				reimbursement.setResolver(userDao.getById(result.getInt("reimb_resolver")).get());
    			} else {
    				reimbursement.setResolved(null);
    			}
    			int type_id = result.getInt("reimb_type_id");
    			setTypeById(type_id, reimbursement);
    			status_id = result.getInt("reimb_status_id");
    			setStatusById(status_id, reimbursement);
    			reimbursementList.add(reimbursement);
    		}
    		return reimbursementList;
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
		return Collections.emptyList();
	}
	
	public boolean addNewReimbursement(Reimbursement reimbursement) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "INSERT INTO ers_reimbursement(reimb_amount, reimb_submitted, reimb_description , reimb_author, reimb_type_id, reimb_status_id)"
					+ " VALUES(?, ?, ?, ?, ?, ?);";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int count =0;
			statement.setDouble(++count, reimbursement.getAmount());
			statement.setTimestamp(++count, reimbursement.getSubmitted());
			statement.setString(++count, reimbursement.getDescription());
			statement.setInt(++count, 2);
			System.out.println(reimbursement);
			System.out.println(reimbursement.getType());
			switch(reimbursement.getType()) {
			case LODGING:
				statement.setInt(++count, 1);
				break;
			case TRAVEL:
				statement.setInt(++count, 2);
				break;
			case FOOD:
				statement.setInt(++count, 3);
				break;
			case OTHER:
				statement.setInt(++count, 4);
				break;
			}
			switch(reimbursement.getStatus()) {
			case PENDING:
				statement.setInt(++count, 1);
				break;
			case APPROVED:
				statement.setInt(++count, 2);
				break;
			case DENIED:
				statement.setInt(++count, 3);
				break;
			}
			statement.execute();
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * <ul>
	 * <li>Should Update an existing Reimbursement record in the DB with the
	 * provided information.</li>
	 * <li>Should throw an exception if the update is unsuccessful.</li>
	 * <li>Should return a Reimbursement object with updated information.</li>
	 * </ul>
	 */
	public Reimbursement update(Reimbursement unprocessedReimbursement) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
    		String sql = "UPDATE ers_reimbursement set reimb_amount = ?, "
    				+ "reimb_sumitted = ?, reimb_resolved = ?, reimb_description = ?, "
    				+ "reimb_author_id = ?, reimb_resolver_id = ?,"
    				+ "reimb_status_id = ?, reimb_type_id = ? WHERE reimb_id ="+unprocessedReimbursement.getId()+";";
    		PreparedStatement statement = conn.prepareStatement(sql);
    		
    		int count = 0;
    		
    		statement.setDouble(++count, unprocessedReimbursement.getAmount());
    		statement.setTimestamp(++count, unprocessedReimbursement.getSubmitted());
    		statement.setTimestamp(++count, unprocessedReimbursement.getResolved());
    		statement.setString(++count, unprocessedReimbursement.getDescription());
    		statement.setInt(++count, unprocessedReimbursement.getAuthor().getId());
    		statement.setInt(++count, unprocessedReimbursement.getResolver().getId());
    		switch(unprocessedReimbursement.getStatus()){
    		case PENDING:
    			statement.setInt(++count, 1);
    			break;
    		case APPROVED:
    			statement.setInt(++count, 2);
    			break;
    		case DENIED:
    			statement.setInt(++count, 3);
    			break;
    		}
    		switch(unprocessedReimbursement.getType()) {
    		case LODGING:
    			statement.setInt(++count, 1);
    			break;
    		case TRAVEL:
    			statement.setInt(++count, 2);
    			break;
    		case FOOD:
    			statement.setInt(++count, 3);
    			break;
    		case OTHER:
    			statement.setInt(++count, 4);
    			break;
    		}
    		statement.setInt(++count, unprocessedReimbursement.getId());
    		statement.execute();
    		
    		return unprocessedReimbursement;
    		
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
		return null;
	}
	
	public void setStatusById(int status_id, Reimbursement reimbursement) {
    	if(status_id == 1) {
			reimbursement.setStatus(Status.PENDING);
		} else if (status_id == 2)
		{
			reimbursement.setStatus(Status.APPROVED);
		}
		else if (status_id == 3)
		{
			reimbursement.setStatus(Status.DENIED);
		}
    }
    public void setTypeById(int type_id, Reimbursement reimbursement) {
    	if(type_id == 1) {
			reimbursement.setType(Type.LODGING);
		} else if(type_id == 2) {
			reimbursement.setType(Type.TRAVEL);
		} else if(type_id == 3) {
			reimbursement.setType(Type.FOOD);
		} else {
			reimbursement.setType(Type.OTHER);
		}
    }
}
