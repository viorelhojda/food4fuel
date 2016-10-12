package com.atraxo.f4f.model.restaurant;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.atraxo.f4f.model.commons.AbstractTimestamp;

@Entity
@Table(name = "T_RESTAURANT")
@NamedQueries(value = { 
		@NamedQuery(name="Restaurant.findRestaurantByName",
				query="SELECT u FROM Restaurant u WHERE u.name = :name")
		})
public class Restaurant extends AbstractTimestamp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3819632156741844991L;

	
	public static final String FIND_BY_NAME = "Restaurant.findRestaurantByName";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_RESTAURANT")
	private Integer id;
	
	@Basic
	@Column(name="LIB_NAME", length=50)
	private String name;
	
	@Basic
	@Column(name="LIB_ADDRESS")
	private String address;
	
	@Basic
	@Column(name="LIB_PHONE",length=15)
	private String phone;
	
	@Basic
	@Column(name="LIB_EMAIL",length=50)
	private String email;
	
	@Basic
	@Column(name="LIB_DESCRIPTION")
	private String description; 
	
	@Column(name="NO_DEFAULT_PRICE")
	private BigDecimal defaultPrice;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "restaurant", orphanRemoval = true, fetch=FetchType.EAGER)
	private List<RestaurantMenuItem> menuItems = new ArrayList<>();
	
	/**
	 * 
	 */
	public Restaurant() {
		super();
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<RestaurantMenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<RestaurantMenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public BigDecimal getDefaultPrice() {
		return defaultPrice;
	}


	public void setDefaultPrice(BigDecimal defaultPrice) {
		this.defaultPrice = defaultPrice;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((defaultPrice == null) ? 0 : defaultPrice.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((menuItems == null) ? 0 : menuItems.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
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
		Restaurant other = (Restaurant) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (defaultPrice == null) {
			if (other.defaultPrice != null)
				return false;
		} else if (!defaultPrice.equals(other.defaultPrice))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (menuItems == null) {
			if (other.menuItems != null)
				return false;
		} else if (!menuItems.equals(other.menuItems))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}
	
	
	
}
