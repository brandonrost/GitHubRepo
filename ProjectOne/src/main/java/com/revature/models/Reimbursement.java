package com.revature.models;

import java.sql.Blob;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ERS_REIMBURSEMENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Reimbursement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reimb_id")
	int reimb_id; 
	
	@Column(name = "reimb_amount",
			nullable = false)
	private float reimb_amount; 
	
	@Column(name = "reimb_submitted",
			nullable = false)
	private Date reimb_submit_time; 
	
	@Column(name = "reimb_resolved")
	private Date reimb_resolve_time; 
	
	@Column(name = "reimb_description")
	private String reimb_desc; 
	
	@Column(name = "reimb_receipt")
	private Blob reimb_receipt; 
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
	private User reimb_author; 
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
	private User reimb_resolver;
	
	@OneToOne (cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
	@JoinColumn(name = "reimb_status_id")
	private StatusCode reimb_status; 

	@OneToOne (cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
	@JoinColumn(name = "reimb_type_id")
	private ReimbursementType reimb_type; 
	
	public Reimbursement() {
		super();
	}
	
	public Reimbursement(float reimb_amount, Date reimb_submit_time, User reimb_author,
			StatusCode reimb_status, ReimbursementType reimb_type) {
		super();
		this.reimb_amount = reimb_amount;
		this.reimb_submit_time = reimb_submit_time;
		this.reimb_author = reimb_author;
		this.reimb_status = reimb_status;
		this.reimb_type = reimb_type;
	}

	@Override
	public String toString() {
		return "Reimbursement [reimb_id=" + reimb_id + ", reimb_amount=" + reimb_amount + ", reimb_submit_time="
				+ reimb_submit_time + ", reimb_resolve_time=" + reimb_resolve_time + ", reimb_desc=" + reimb_desc
				+ ", reimb_receipt=" + reimb_receipt + ", reimb_author=" + reimb_author + ", reimb_resolver="
				+ reimb_resolver + ", reimb_status=" + reimb_status + ", reimb_type=" + reimb_type + "]";
	}

	public int getReimb_id() {
		return reimb_id;
	}

	public void setReimb_id(int reimb_id) {
		this.reimb_id = reimb_id;
	}

	public float getReimb_amount() {
		return reimb_amount;
	}

	public void setReimb_amount(float reimb_amount) {
		this.reimb_amount = reimb_amount;
	}

	public Date getReimb_submit_time() {
		return reimb_submit_time;
	}

	public void setReimb_submit_time(Date reimb_submit_time) {
		this.reimb_submit_time = reimb_submit_time;
	}

	public Date getReimb_resolve_time() {
		return reimb_resolve_time;
	}

	public void setReimb_resolve_time(Date reimb_resolve_time) {
		this.reimb_resolve_time = reimb_resolve_time;
	}

	public String getReimb_desc() {
		return reimb_desc;
	}

	public void setReimb_desc(String reimb_desc) {
		this.reimb_desc = reimb_desc;
	}

	public Blob getReimb_receipt() {
		return reimb_receipt;
	}

	public void setReimb_receipt(Blob reimb_receipt) {
		this.reimb_receipt = reimb_receipt;
	}

	public User getReimb_author() {
		return reimb_author;
	}

	public void setReimb_author(User reimb_author) {
		this.reimb_author = reimb_author;
	}

	public User getReimb_resolver() {
		return reimb_resolver;
	}

	public void setReimb_resolver(User reimb_resolver) {
		this.reimb_resolver = reimb_resolver;
	}

	public StatusCode getReimb_status() {
		return reimb_status;
	}

	public void setReimb_status(StatusCode reimb_status) {
		this.reimb_status = reimb_status;
	}

	public ReimbursementType getReimb_type() {
		return reimb_type;
	}

	public void setReimb_type(ReimbursementType reimb_type) {
		this.reimb_type = reimb_type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(reimb_amount);
		result = prime * result + ((reimb_author == null) ? 0 : reimb_author.hashCode());
		result = prime * result + ((reimb_desc == null) ? 0 : reimb_desc.hashCode());
		result = prime * result + reimb_id;
		result = prime * result + ((reimb_receipt == null) ? 0 : reimb_receipt.hashCode());
		result = prime * result + ((reimb_resolve_time == null) ? 0 : reimb_resolve_time.hashCode());
		result = prime * result + ((reimb_resolver == null) ? 0 : reimb_resolver.hashCode());
		result = prime * result + ((reimb_status == null) ? 0 : reimb_status.hashCode());
		result = prime * result + ((reimb_submit_time == null) ? 0 : reimb_submit_time.hashCode());
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
		Reimbursement other = (Reimbursement) obj;
		if (Float.floatToIntBits(reimb_amount) != Float.floatToIntBits(other.reimb_amount))
			return false;
		if (reimb_author == null) {
			if (other.reimb_author != null)
				return false;
		} else if (!reimb_author.equals(other.reimb_author))
			return false;
		if (reimb_desc == null) {
			if (other.reimb_desc != null)
				return false;
		} else if (!reimb_desc.equals(other.reimb_desc))
			return false;
		if (reimb_id != other.reimb_id)
			return false;
		if (reimb_receipt == null) {
			if (other.reimb_receipt != null)
				return false;
		} else if (!reimb_receipt.equals(other.reimb_receipt))
			return false;
		if (reimb_resolve_time == null) {
			if (other.reimb_resolve_time != null)
				return false;
		} else if (!reimb_resolve_time.equals(other.reimb_resolve_time))
			return false;
		if (reimb_resolver == null) {
			if (other.reimb_resolver != null)
				return false;
		} else if (!reimb_resolver.equals(other.reimb_resolver))
			return false;
		if (reimb_status == null) {
			if (other.reimb_status != null)
				return false;
		} else if (!reimb_status.equals(other.reimb_status))
			return false;
		if (reimb_submit_time == null) {
			if (other.reimb_submit_time != null)
				return false;
		} else if (!reimb_submit_time.equals(other.reimb_submit_time))
			return false;
		if (reimb_type == null) {
			if (other.reimb_type != null)
				return false;
		} else if (!reimb_type.equals(other.reimb_type))
			return false;
		return true;
	}
	
}
