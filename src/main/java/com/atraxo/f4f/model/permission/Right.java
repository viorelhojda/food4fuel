package com.atraxo.f4f.model.permission;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.atraxo.f4f.model.commons.AbstractTimestamp;

@Entity
@Table(name="T_RIGHT")
@NamedQueries(value = { 
		@NamedQuery(name="Right.findByCode",
				query="SELECT r FROM Right r WHERE r.code = :code")
})
public class Right extends AbstractTimestamp {

	private static final long serialVersionUID = 4646310537201422990L;
	
	public static final String FIND_ALL_ACTIVE = "Right.findAllActive";
	public static final String FIND_BY_CODE = "Right.findByCode";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_RIGHT")
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name="COD_RIGHT",length=50)
	private RightEnum code;

	@Basic
	@Column(name="BOO_ACTIVE")
	private Boolean active;

	public Right(){
		super();
		active = true;
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

	public RightEnum getCode() {
		return code;
	}

	public void setCode(RightEnum code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Right other = (Right) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (code != other.code)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Right [id=" + id + ", code=" + code + ", active=" + active + "]";
	}


}
