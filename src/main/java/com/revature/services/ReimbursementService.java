package com.revature.services;

import com.revature.exceptions.RegistrationUnsuccessfulException;
import com.revature.exceptions.ReimbursementUpdateFailedException;
import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.repositories.IReimbursementDAO;
import com.revature.repositories.IUserDAO;
import com.revature.repositories.ReimbursementDAO;
import com.revature.repositories.UserDAO;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The ReimbursementService should handle the submission, processing,
 * and retrieval of Reimbursements for the ERS application.
 *
 * {@code process} and {@code getReimbursementsByStatus} are the minimum methods required;
 * however, additional methods can be added.
 *
 * Examples:
 * <ul>
 *     <li>Create Reimbursement</li>
 *     <li>Update Reimbursement</li>
 *     <li>Get Reimbursements by ID</li>
 *     <li>Get Reimbursements by Author</li>
 *     <li>Get Reimbursements by Resolver</li>
 *     <li>Get All Reimbursements</li>
 * </ul>
 */
public class ReimbursementService {
		protected IReimbursementDAO reimbDao = new ReimbursementDAO();
		protected IUserDAO userDao = new UserDAO();
    /**
     * <ul>
     *     <li>Should ensure that the user is logged in as a Finance Manager</li>
     *     <li>Must throw exception if user is not logged in as a Finance Manager</li>
     *     <li>Should ensure that the reimbursement request exists</li>
     *     <li>Must throw exception if the reimbursement request is not found</li>
     *     <li>Should persist the updated reimbursement status with resolver information</li>
     *     <li>Must throw exception if persistence is unsuccessful</li>
     * </ul>
     *
     * Note: unprocessedReimbursement will have a status of PENDING, a non-zero ID and amount, and a non-null Author.
     * The Resolver should be null. Additional fields may be null.
     * After processing, the reimbursement will have its status changed to either APPROVED or DENIED.
     */
    public Reimbursement process(Reimbursement unprocessedReimbursement, Status finalStatus, User resolver) {
    	
    	User reimbResolver = userDao.getById(resolver.getId()).get();
    	Role userRole = reimbResolver.getRole();
    	if(userRole== Role.FINANCE_MANAGER) {
    		Optional<Reimbursement> reimbursement = reimbDao.getOneById(unprocessedReimbursement.getId());
    		if(reimbursement.isPresent()) {
    			unprocessedReimbursement.setStatus(finalStatus);
    			unprocessedReimbursement.setResolver(resolver);
    			reimbDao.update(unprocessedReimbursement);
    		}else {
    			throw new ReimbursementUpdateFailedException("Couldn't update the status. Try again!");
    		}
    	}
        return null;
    }

    /**
     * Should retrieve all reimbursements with the correct status.
     */
    public List<Reimbursement> getReimbursementsByStatus(Status status) {
        return reimbDao.getByStatus(status);
    }
    
    public List<Optional<Reimbursement>> getReimbursementList(int id){
    	return reimbDao.getById(id);
    }
    
    public boolean newReimbursement(Reimbursement reimbursement) {
    		return reimbDao.addNewReimbursement(reimbursement);
    	
    }
}
