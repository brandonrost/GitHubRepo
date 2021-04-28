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
@Table(name = "ERS_REIMBURSEMENT_STATUS")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class StatusCode {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int status_code_id;

	@Column(name = "reimb_status",
			nullable = false)
	private String reimb_status;
	
	public StatusCode() {
		super();
	}

	public StatusCode(String reimb_status) {
		super();
		this.reimb_status = reimb_status;
	}

	public int getStatus_code_id() {
		return status_code_id;
	}

	public void setStatus_code_id(int status_code_id) {
		this.status_code_id = status_code_id;
	}

	public String getReimb_status() {
		return reimb_status;
	}

	public void setReimb_status(String reimb_status) {
		this.reimb_status = reimb_status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reimb_status == null) ? 0 : reimb_status.hashCode());
		result = prime * result + status_code_id;
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
		StatusCode other = (StatusCode) obj;
		if (reimb_status == null) {
			if (other.reimb_status != null)
				return false;
		} else if (!reimb_status.equals(other.reimb_status))
			return false;
		if (status_code_id != other.status_code_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StatusCode [status_code_id=" + status_code_id + ", reimb_status=" + reimb_status + "]";
	} 
	

}
