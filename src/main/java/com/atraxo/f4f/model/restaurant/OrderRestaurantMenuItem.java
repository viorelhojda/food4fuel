package com.atraxo.f4f.model.restaurant;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.atraxo.f4f.model.commons.AbstractTimestamp;
import com.atraxo.f4f.model.user.User;

@Entity
@Table(name = "T_ORDER_RESTAURANT_MENU_ITEMS")
@NamedQueries(value = { 
		@NamedQuery(name="OrderRestaurantMenuItem.findByUser",
				query="SELECT o FROM OrderRestaurantMenuItem o WHERE o.user.id = :userId AND o.menuItem.active IS TRUE"),
		
		@NamedQuery(name="OrderRestaurantMenuItem.findByDate",
				query="SELECT o FROM OrderRestaurantMenuItem o WHERE o.menuItem.date = :date AND o.menuItem.active IS TRUE"),
		
		@NamedQuery(name="OrderRestaurantMenuItem.findBetweenDatesAndStatuses",
				query="SELECT o FROM OrderRestaurantMenuItem o WHERE o.menuItem.date >= :fromDate AND o.menuItem.date <= :toDate AND o.status IN (:statuses) AND o.menuItem.active IS TRUE"),
		
		@NamedQuery(name="OrderRestaurantMenuItem.findBetweenDatesAndUser",
			query="SELECT o FROM OrderRestaurantMenuItem o WHERE o.menuItem.date >= :fromDate AND o.menuItem.date <= :toDate AND o.user.id = :userID AND o.menuItem.active IS TRUE"),
		
		@NamedQuery(name="OrderRestaurantMenuItem.findByDateAndStatus",
				query="SELECT o FROM OrderRestaurantMenuItem o WHERE o.menuItem.date = :date AND o.status = :status AND o.menuItem.active IS TRUE"),
	
		@NamedQuery(name="OrderRestaurantMenuItem.findByDateAndStatusesAndUser",
				query="SELECT o FROM OrderRestaurantMenuItem o WHERE o.menuItem.date = :date AND o.status IN (:statuses) AND o.user.id = :userID AND o.menuItem.active IS TRUE")
})
public class OrderRestaurantMenuItem extends AbstractTimestamp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4894823342220206392L;

	public static final String FIND_BY_USER = "OrderRestaurantMenuItem.findByUser";
	public static final String FIND_BY_DATE = "OrderRestaurantMenuItem.findByDate";
	public static final String FIND_BY_DATE_AND_STATUS = "OrderRestaurantMenuItem.findByDateAndStatus";
	public static final String FIND_BY_DATE_AND_STATUSES_AND_USER = "OrderRestaurantMenuItem.findByDateAndStatusesAndUser";
	public static final String FIND_BETWEEN_DATES_AND_STATUSES = "OrderRestaurantMenuItem.findBetweenDatesAndStatuses";
	public static final String FIND_BETWEEN_DATES_AND_USER = "OrderRestaurantMenuItem.findBetweenDatesAndUser";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_ORDER_RESTAURANT_MENU_ITEM")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="ID_RESTAURANT_MENU_ITEM")
	private RestaurantMenuItem menuItem;
	
	@Enumerated(EnumType.STRING)
	@Column(name="LIB_STATUS")
	private MenuItemStatus status;
	
	@ManyToOne
	@JoinColumn(name="ID_USER")
	private User user;

	/**
	 * 
	 */
	public OrderRestaurantMenuItem(){
		status = MenuItemStatus.NEW;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RestaurantMenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(RestaurantMenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public MenuItemStatus getStatus() {
		return status;
	}

	public void setStatus(MenuItemStatus status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((menuItem == null) ? 0 : menuItem.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		OrderRestaurantMenuItem other = (OrderRestaurantMenuItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (menuItem == null) {
			if (other.menuItem != null)
				return false;
		} else if (!menuItem.equals(other.menuItem))
			return false;
		if (status != other.status)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderRestaurantMenuItem [id=" + id + ", menuItem=" + menuItem + ", status=" + status + ", user=" + user
				+ "]";
	}
	
	
}
