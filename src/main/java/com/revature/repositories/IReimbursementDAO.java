package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import com.revature.models.Reimbursement;
import com.revature.models.Status;

public interface IReimbursementDAO {
	
	public List<Optional<Reimbursement>> getById(int id);
	public Optional<Reimbursement> getOneById(int id);
	public List<Reimbursement> getByStatus(Status status);
	public Reimbursement update(Reimbursement unprocessedReimbursement);
	public boolean addNewReimbursement(Reimbursement reimbursement);
	

}
