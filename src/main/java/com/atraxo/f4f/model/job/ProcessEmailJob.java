package com.atraxo.f4f.model.job;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue("EMAIL_JOB")
@NamedQueries(value = { 
		@NamedQuery(name="ProcessEmailJob.findByProcessIDAndType",
				query="SELECT job FROM ProcessEmailJob job " +
						"WHERE job.mailType = :mailType " +
						"AND job.id = :processID")
	})
public class ProcessEmailJob extends ProcessJob {
	/**
	 * 
	 */
	private static final long serialVersionUID = -721226490182012818L;

	public static final String FIND_BY_PROCESS_ID_AND_MAIL_TYPE = "ProcessEmailJob.findByProcessIDAndType";
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="TXT_CONTENT",length=1500)
	protected String content;
	
	@Column(name="TXT_MAIL_CC",length=500)
	private String emailCc;
	
	@Column(name="TXT_MAIL_BCC",length=500)
	private String emailBcc;
	
	@Column(name="TXT_MAIL_SENDER")
	private String emailSender;
	
	@Column(name="TXT_MAIL_RECEIVER",length=500)
	private String emailReceiver;
	
	@Column(name="TXT_MAIL_SUBJECT")
	private String emailSubject;
	
	@Enumerated(EnumType.STRING)
	@Column(name="LIB_MAIL_TYPE", length=50)
	private EmailTypeEnum mailType;
	
	@Column(name="TXT_MAIL_ATTACHMENTS", length=2000)
	private String emailAttachments;
	
	public ProcessEmailJob() {
		super();
	}


	public String getEmailCc() {
		return emailCc;
	}


	public void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}


	public String getEmailSender() {
		return emailSender;
	}


	public void setEmailSender(String emailSender) {
		this.emailSender = emailSender;
	}


	public String getEmailReceiver() {
		return emailReceiver;
	}


	public void setEmailReceiver(String emailReceiver) {
		this.emailReceiver = emailReceiver;
	}


	public String getEmailBcc() {
		return emailBcc;
	}


	public void setEmailBcc(String emailBcc) {
		this.emailBcc = emailBcc;
	}


	public String getEmailSubject() {
		return emailSubject;
	}


	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

	public EmailTypeEnum getMailType() {
		return mailType;
	}


	public void setMailType(EmailTypeEnum mailType) {
		this.mailType = mailType;
	}

	
	public String getEmailAttachments() {
		return emailAttachments;
	}

	public void setEmailAttachments(String emailAttachments) {
		this.emailAttachments = emailAttachments;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime
				* result
				+ ((emailAttachments == null) ? 0 : emailAttachments.hashCode());
		result = prime * result
				+ ((emailBcc == null) ? 0 : emailBcc.hashCode());
		result = prime * result + ((emailCc == null) ? 0 : emailCc.hashCode());
		result = prime * result
				+ ((emailReceiver == null) ? 0 : emailReceiver.hashCode());
		result = prime * result
				+ ((emailSender == null) ? 0 : emailSender.hashCode());
		result = prime * result
				+ ((emailSubject == null) ? 0 : emailSubject.hashCode());
		result = prime * result
				+ ((mailType == null) ? 0 : mailType.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ProcessEmailJob other = (ProcessEmailJob) obj;
		if (content == null) {
			if (other.content != null) {
				return false;
			}
		} else if (!content.equals(other.content)) {
			return false;
		}
		if (emailAttachments == null) {
			if (other.emailAttachments != null) {
				return false;
			}
		} else if (!emailAttachments.equals(other.emailAttachments)) {
			return false;
		}
		if (emailBcc == null) {
			if (other.emailBcc != null) {
				return false;
			}
		} else if (!emailBcc.equals(other.emailBcc)) {
			return false;
		}
		if (emailCc == null) {
			if (other.emailCc != null) {
				return false;
			}
		} else if (!emailCc.equals(other.emailCc)) {
			return false;
		}
		if (emailReceiver == null) {
			if (other.emailReceiver != null) {
				return false;
			}
		} else if (!emailReceiver.equals(other.emailReceiver)) {
			return false;
		}
		if (emailSender == null) {
			if (other.emailSender != null) {
				return false;
			}
		} else if (!emailSender.equals(other.emailSender)) {
			return false;
		}
		if (emailSubject == null) {
			if (other.emailSubject != null) {
				return false;
			}
		} else if (!emailSubject.equals(other.emailSubject)) {
			return false;
		}
		if (mailType == null) {
			if (other.mailType != null) {
				return false;
			}
		} else if (!mailType.equals(other.mailType)) {
			return false;
		}
		return true;
	}

	@Override
	public String getDataJobElement() {
		return mailType.name();
	}

	/* (non-Javadoc)
	 * @see com.dso.model.job.ProcessJob#getGroupElementId()
	 */
	@Override
	public String getGroupElementId() {
		return "EMAIL_";
	}


	@Override
	public String toString() {
		return "ProcessEmailJob [content=" + content + ", emailCc=" + emailCc + ", emailBcc=" + emailBcc
				+ ", emailSender=" + emailSender + ", emailReceiver=" + emailReceiver + ", emailSubject=" + emailSubject
				+ ", mailType=" + mailType + ", emailAttachments=" + emailAttachments + "]";
	}

	
}
