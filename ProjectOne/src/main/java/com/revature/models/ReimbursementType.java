package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ERS_REIMBURSEMENT_TYPE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ReimbursementType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reimb_id; 

	@Column(name = "reimb_type",
			nullable = false)
	private String reimb_type;
	
	public ReimbursementType() {
		super();
	}

	public ReimbursementType(String reimb_type) {
		super();
		this.reimb_type = reimb_type;
	}

	public int getReimb_id() {
		return reimb_id;
	}

	public void setReimb_id(int reimb_id) {
		this.reimb_id = reimb_id;
	}

	public String getReimb_type() {
		return reimb_type;
	}

	public void setReimb_type(String reimb_type) {
		this.reimb_type = reimb_type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + reimb_id;
		result = prime * result + ((reimb_type == null) ? 0 : reimb_type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimbursementType other = (ReimbursementType) obj;
		if (reimb_id != other.reimb_id)
			return false;
		if (reimb_type == null) {
			if (other.reimb_type != null)
				return false;
		} else if (!reimb_type.equals(other.reimb_type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReimbursementType [reimb_id=" + reimb_id + ", reimb_type=" + reimb_type + "]";
	} 
	
}
