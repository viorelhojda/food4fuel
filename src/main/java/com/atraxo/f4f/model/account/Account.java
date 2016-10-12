package com.atraxo.f4f.model.account;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.atraxo.f4f.model.commons.AbstractTimestamp;
import com.atraxo.f4f.model.commons.IDescriptor;

@Entity
@Table(name="T_ACCOUNTS")
@NamedQueries(value = { 
		@NamedQuery(name="Account.findByUserName",
			query="SELECT a FROM Account a WHERE a.username = :username")
})
public class Account extends AbstractTimestamp implements IDescriptor{

	private static final long serialVersionUID = 8363283776651488551L;
	
	public static final String FIND_BY_USERNAME = "Account.findByUserName";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_ACCOUNT")
	private int id;
	
	@Basic
	@Column(name="LIB_FIRST_NAME",length=50)
	private String firstName;

	@Basic
	@Column(name="LIB_LAST_NAME",length=50)
	private String lastName;
	
	@Basic
	@Column(name="LIB_USERNAME",length=50)
	private String username;
	
	@Basic
	@Column(name="LIB_PASSWORD",length=50)
	private String password;
	
	@Basic
	@Column(name="LIB_EMAIL",length=100)
	private String email;
	
	@Basic
	@Column(name="LIB_PHONE",length=100)
	private String phone;
	
	@Basic
	@Column(name="LIB_PROJECT",length=100)
	private String project;
	
	public Account() {
		super();
	}
	
	public Account(Account account) {
		super();
		firstName = account.getFirstName();
		lastName = account.getLastName();
		username = account.getUsername();
		password = account.getPassword();
		email = account.getEmail();
	}
	
	public String getFirstNameLastName(){
		return getFirstName() + " " + getLastName();
	}
	
	public String getLastNameFirstName(){
		return getLastName() + " " + getFirstName();
	}
	
	@Transient
	public String getDescriptor() {
		String descriptor = getFirstNameLastName();
		descriptor += " [" + getUsername() + "]";
		descriptor += " [" + getEmail() + "]";
		descriptor += " [" + getPhone() + "]";
		descriptor += " [" + getProject() + "]";
		return descriptor;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		Account other = (Account) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName.toUpperCase();
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
	
	

}
