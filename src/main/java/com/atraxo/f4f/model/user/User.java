package com.atraxo.f4f.model.user;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.atraxo.f4f.model.account.Account;
import com.atraxo.f4f.model.commons.AbstractTimestamp;
import com.atraxo.f4f.model.commons.IDescriptor;
import com.atraxo.f4f.model.permission.Role;

@Entity
@Table(name="T_USERS")
@NamedQueries(value = { 
		@NamedQuery(name="User.findUserByUserName",
				query="SELECT u FROM User u WHERE u.account.username = :username"),
		@NamedQuery(name="User.findUsersActive",
			query="SELECT u FROM User u WHERE u.active IS TRUE"),
		@NamedQuery(name="User.findUserByRole",
				query="SELECT u FROM User u WHERE u.role.id = :roleID"),
		@NamedQuery(name="User.findUserByRight",
			query="SELECT u FROM User u INNER JOIN u.role.rights r WHERE r.right.code = :right")
		
})
public class User extends AbstractTimestamp implements IDescriptor {

	private static final long serialVersionUID = -3618651028874411845L;
	
	public static final String FIND_BY_USERNAME = "User.findUserByUserName";
	public static final String FIND_BY_ROLE = "User.findUserByRole";
	public static final String FIND_BY_RIGHT = "User.findUserByRight";
	public static final String FIND_ALL_ACTIVE = "User.findUsersActive";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_USER")
	private Integer id;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ID_ACCOUNT")
	private Account account;
	
	@ManyToOne()
	@JoinColumn(name="ID_ROLE")
	private Role role;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DAT_LAST_LOGIN")
	private Date lastLogin;
	
	@Basic
	@Column(name="BOO_ACTIVE")
	private Boolean active;
	
	@Embedded
	private UserPreferences preferences;
	
	/**
	 * 
	 */
	public User() {
		super();
		active = true;
		preferences = new UserPreferences();
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getLastLogin() {
		if( lastLogin == null ){
			lastLogin = new Date();
		}
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", account=" + account + ", role=" + role
				+ ", lastLogin=" + lastLogin + ", active=" + active + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastLogin == null) ? 0 : lastLogin.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		User other = (User) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastLogin == null) {
			if (other.lastLogin != null)
				return false;
		} else if (!lastLogin.equals(other.lastLogin))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}

	@Override
	public String getDescriptor() {
		return account.getDescriptor();
	}

	public UserPreferences getPreferences() {
		return preferences;
	}

	public void setPreferences(UserPreferences preferences) {
		this.preferences = preferences;
	}
	

}
