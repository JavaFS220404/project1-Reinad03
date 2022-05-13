package com.revature.models;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * This concrete Reimbursement class can include additional fields that can be used for
 * extended functionality of the ERS application.
 *
 * Example fields:
 * <ul>
 *     <li>Description</li>
 *     <li>Creation Date</li>
 *     <li>Resolution Date</li>
 *     <li>Receipt Image</li>
 * </ul>
 *
 */
public class Reimbursement extends AbstractReimbursement {
	
	private String description;
	private Timestamp submitted;
	private Timestamp resolved;
	private Type type;

    public Reimbursement() {
        super();
    }

    /**
     * This includes the minimum parameters needed for the {@link com.revature.models.AbstractReimbursement} class.
     * If other fields are needed, please create additional constructors.
     */
    public Reimbursement(int id, Status status, User author, User resolver, double amount) {
        super(id, status, author, resolver, amount);
    }

    public Reimbursement(int id, Status status, User author, User resolver, double amount,
    		String description, Timestamp submitted, Timestamp resolved, Type type) {
        super(id, status, author, resolver, amount);
        this.description = description;
        this.submitted = submitted;
        this.resolved = resolved;
        this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}

	public Timestamp getResolved() {
		return resolved;
	}

	public void setResolved(Timestamp resolved) {
		this.resolved = resolved;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(description, resolved, submitted, type);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		return Objects.equals(description, other.description) && Objects.equals(resolved, other.resolved)
				&& Objects.equals(submitted, other.submitted) && type == other.type;
	}

	@Override
	public String toString() {
		return "Reimbursement [description=" + description + ", submitted=" + submitted + ", resolved=" + resolved
				+ ", type=" + type + ", getId()=" + getId() + ", getStatus()=" + getStatus() + ", getAuthor()="
				+ getAuthor() + ", getResolver()=" + getResolver() + ", getAmount()=" + getAmount() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + "]";
	}
    
    
    
}
