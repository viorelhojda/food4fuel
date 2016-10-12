package com.atraxo.f4f.model.permission;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.atraxo.f4f.model.commons.AbstractTimestamp;

@Entity
@Table(name="T_ROLE_RIGHT")
@NamedQueries(value = { 
		@NamedQuery(name="RoleRight.findByRoleAndRight", 
				query="SELECT rr FROM RoleRight rr WHERE rr.role.id = :roleID And rr.right.id = :rightID ")
	})
public class RoleRight extends AbstractTimestamp {

	private static final long serialVersionUID = 2192711516941291546L;
	
	public static final String FIND_BY_ROLE_RIGHT = "RoleRight.findByRoleAndRight";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_ROLE_RIGHT")
	private Integer id;
	
	@ManyToOne()
	@JoinColumn(name="ID_ROLE")
	private Role role;
	
	@ManyToOne()
	@JoinColumn(name="ID_RIGHT")
	private Right right;

	public RoleRight() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Right getRight() {
		return right;
	}

	public void setRight(Right right) {
		this.right = right;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
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
		RoleRight other = (RoleRight) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RoleRight [id=" + id + ", role=" + role + ", right=" + right + "]";
	}
	
	
	
}
