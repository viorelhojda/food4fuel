package com.atraxo.f4f.model.restaurant;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_RESTAURANT_MENU_ITEMS")
@NamedQueries(value = { 
		@NamedQuery(name="RestaurantMenuItem.findBetweenDates",
				query="SELECT u FROM RestaurantMenuItem u WHERE u.date >= :fromDate AND u.date <= :toDate AND u.restaurant.id = :restaurantId AND u.active IS TRUE")
		})
public class RestaurantMenuItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1955018054265932334L;

	public static final String FIND_BEETWEEN_DATES = "RestaurantMenuItem.findBetweenDates";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_RESTAURANT_MENU_ITEM")
	private Integer id;

	@Column(name="NO_NUMBER")
	private Integer number;
	
	@Column(name="LIB_DESCRIPTION")
	private String description;
	
	@Column(name="BOOL_ACTIVE")
	private Boolean active;
	
	@Column(name="DAT_DAY")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="ID_RESTAURANT")
	private Restaurant restaurant;
	
	@Column(name="NO_PRICE")
	private BigDecimal price;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((restaurant == null) ? 0 : restaurant.hashCode());
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
		RestaurantMenuItem other = (RestaurantMenuItem) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (restaurant == null) {
			if (other.restaurant != null)
				return false;
		} else if (!restaurant.equals(other.restaurant))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RestaurantMenuItem [id=" + id + ", number=" + number + ", description=" + description + ", active="
				+ active + ", date=" + date + ", restaurant=" + restaurant + "]";
	}

	
	
	
}
