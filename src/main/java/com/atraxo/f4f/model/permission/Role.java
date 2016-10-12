package com.atraxo.f4f.model.permission;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.atraxo.f4f.model.commons.AbstractTimestamp;

@Entity
@Table(name="T_ROLE")
@NamedQueries(value = { 
		@NamedQuery(name="Role.findAllActive", 
				query="SELECT r FROM Role r WHERE r.active = TRUE "),
		@NamedQuery(name="Role.findRoleByCode", 
				query="SELECT r FROM Role r WHERE r.code = :code ")
	})
public class Role extends AbstractTimestamp {

	private static final long serialVersionUID = 8591744644547361349L;
	
	public static final String FIND_ALL_ACTIVE = "Role.findAllActive";
	public static final String FIND_BY_CODE = "Role.findRoleByCode";
	public static final String FIND_BY_PROFILE_AND_APPLICATION = "Role.findRoleByProfileAndApplication";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_ROLE")
	private Integer id;
	
	@Column(name="COD_ROLE",length=50)
	private String code;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "role", orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<RoleRight> rights;
	
	@Basic
	@Column(name="BOO_ACTIVE")
	private Boolean active;
	
	public Role(){
		active = true;
		rights = new ArrayList<>();
	}
	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public List<RoleRight> getRights() {
		return rights;
	}

	public void setRights(List<RoleRight> rights) {
		this.rights = rights;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		Role other = (Role) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", code=" + code + ", active=" + active + "]";
	}

}
