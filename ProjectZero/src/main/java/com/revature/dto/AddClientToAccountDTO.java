package com.revature.dto;

public class AddClientToAccountDTO {
	private String clientID; 
	
	public AddClientToAccountDTO() {
		super();
	}
	
	public AddClientToAccountDTO(String clientID) {
		super();
		this.clientID = clientID; 
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientID == null) ? 0 : clientID.hashCode());
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
		AddClientToAccountDTO other = (AddClientToAccountDTO) obj;
		if (clientID == null) {
			if (other.clientID != null)
				return false;
		} else if (!clientID.equals(other.clientID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AddClientToAccountDTO [clientID=" + clientID + "]";
	}

}
