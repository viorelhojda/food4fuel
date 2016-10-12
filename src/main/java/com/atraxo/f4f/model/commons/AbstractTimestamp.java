package com.atraxo.f4f.model.commons;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class AbstractTimestamp implements Serializable{
	private static final long serialVersionUID = 2007144094831117913L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DAT_CREATE", nullable = false)
	private Date timestampCreate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DAT_UPDATE", nullable = false)
	private Date timestampUpdate;
	
	@PrePersist
	protected void onCreate() {
		timestampUpdate = timestampCreate = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		timestampUpdate = new Date();
	}

	public Date getTimestampCreate() {
		return timestampCreate;
	}

	public void setTimestampCreate(Date timestampCreate) {
		this.timestampCreate = timestampCreate;
	}

	public Date getTimestampUpdate() {
		return timestampUpdate;
	}

	public void setTimestampUpdate(Date timestampUpdate) {
		this.timestampUpdate = timestampUpdate;
	}


}
