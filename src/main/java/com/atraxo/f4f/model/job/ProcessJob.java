package com.atraxo.f4f.model.job;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.quartz.JobKey;

import com.atraxo.f4f.model.commons.AbstractTimestamp;

@Entity
@Table(name="T_PROCESS_JOB")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "LIB_DISCRIMINATOR_JOB", discriminatorType = DiscriminatorType.STRING, length = 50)
@DiscriminatorValue("PROCESS_JOB")
@NamedQueries( value = {
		@NamedQuery(name="ProcessJob.findJobsByStatus",
				query="SELECT job FROM ProcessJob job WHERE job.status = :status " +
						"ORDER BY job.timestampCreate DESC"),
		@NamedQuery(name="ProcessJob.findJobsByType",
				query="SELECT job FROM ProcessJob job WHERE job.type = :type " +
						"ORDER BY job.timestampCreate DESC"),
		@NamedQuery(name="ProcessJob.findJobsByStatusAndType",
			query="SELECT job FROM ProcessJob job WHERE job.status=:status and job.type=:type " +
					"ORDER BY job.timestampCreate DESC")
})
public abstract class ProcessJob extends AbstractTimestamp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1616621028828208721L;
	
	public static final String FIND_ALL_JOBS_BY_STATUS = "ProcessJob.findJobsByStatus";
	public static final String FIND_ALL_JOBS_BY_STATUS_AND_TYPE = "ProcessJob.findJobsByStatusAndType";
	public static final String FIND_ALL_JOBS_BY_TYPE = "ProcessJob.findJobsByType";

	public static final String EXCEPTION_KEY = "e-x-c-e-p-t-i-o-n";

	
	/**
	 * 
	 * @return
	 */
	public abstract String getDataJobElement();
	
	/**
	 * @return
	 */
	public abstract String getGroupElementId();
	
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DAT_START_DATE")
	protected Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DAT_EXPIRE_DATE")
	protected Date expireDate;

	@Basic
	@Enumerated(EnumType.STRING)
	@Column(name="COD_STATUS",length=20)
	protected ProcessJobStatusEnum status;

	@Basic
	@Enumerated(EnumType.STRING)
	@Column(name="COD_TYPE",length=50)
	protected ProcessJobTypeEnum type;
	
	@Basic
	@Column(name="NO_ATTEMPT")
	private Integer attempt;
	
	@Basic
	@Column(name="TXT_FAIL_MESSAGE", length=2000)
	private String failMessage;	
	
	/**
	 * 
	 */
	public ProcessJob(){
		attempt = Integer.valueOf(1);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public ProcessJobStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ProcessJobStatusEnum status) {
		this.status = status;
	}

	public ProcessJobTypeEnum getType() {
		return type;
	}

	public void setType(ProcessJobTypeEnum type) {
		this.type = type;
	}

	public Integer getAttempt() {
		return attempt;
	}

	public void setAttempt(Integer attempt) {
		this.attempt = attempt;
	}

	public String getFailMessage() {
		return failMessage;
	}

	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}

	/**
	 * @return
	 */
	@Transient
	public JobKey formJobKeyFromProcessJob(){
		String name = id + "";
		String group = getType().getName() + "_" + getGroupElementId();
		return new JobKey(name, group);
	}
	
	/**
	 * 
	 */
	public void incrementAttempt() {
		if ( attempt == null ) {
			attempt = Integer.valueOf(0);
		}
		attempt++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attempt == null) ? 0 : attempt.hashCode());
		result = prime * result + ((expireDate == null) ? 0 : expireDate.hashCode());
		result = prime * result + ((failMessage == null) ? 0 : failMessage.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ProcessJob other = (ProcessJob) obj;
		if (attempt == null) {
			if (other.attempt != null)
				return false;
		} else if (!attempt.equals(other.attempt))
			return false;
		if (expireDate == null) {
			if (other.expireDate != null)
				return false;
		} else if (!expireDate.equals(other.expireDate))
			return false;
		if (failMessage == null) {
			if (other.failMessage != null)
				return false;
		} else if (!failMessage.equals(other.failMessage))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (status != other.status)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
}
