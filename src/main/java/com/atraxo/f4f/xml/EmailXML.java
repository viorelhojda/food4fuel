package com.atraxo.f4f.xml;

import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;


public class EmailXML {
	private String sender;
	private String cc;
	private String bcc;
	private String receiver;
	private String subject;
	private String content;
	
	public EmailXML() {
		//constructor
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getActualContent(Map<String, String> replaceMap) {
		StrSubstitutor sub = new StrSubstitutor(replaceMap);
		return sub.replace(getContent());
	}
	
	public String getActualSubject(Map<String, String> replaceMap) {
		StrSubstitutor sub = new StrSubstitutor(replaceMap);
		return sub.replace( getSubject() );
	}
	
	public String getActualSender(Map<String, String> replaceMap) {
		StrSubstitutor sub = new StrSubstitutor(replaceMap);
		return sub.replace( getSender() );
	}
	
	public String getActualReceiver(Map<String, String> replaceMap) {
		StrSubstitutor sub = new StrSubstitutor(replaceMap);
		return sub.replace( getReceiver() );
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bcc == null) ? 0 : bcc.hashCode());
		result = prime * result + ((cc == null) ? 0 : cc.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result
				+ ((receiver == null) ? 0 : receiver.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		EmailXML other = (EmailXML) obj;
		if (bcc == null) {
			if (other.bcc != null) {
				return false;
			}
		} else if (!bcc.equals(other.bcc)) {
			return false;
		}
		if (cc == null) {
			if (other.cc != null) {
				return false;
			}
		} else if (!cc.equals(other.cc)) {
			return false;
		}
		if (content == null) {
			if (other.content != null) {
				return false;
			}
		} else if (!content.equals(other.content)) {
			return false;
		}
		if (receiver == null) {
			if (other.receiver != null) {
				return false;
			}
		} else if (!receiver.equals(other.receiver)) {
			return false;
		}
		if (sender == null) {
			if (other.sender != null) {
				return false;
			}
		} else if (!sender.equals(other.sender)) {
			return false;
		}
		if (subject == null) {
			if (other.subject != null) {
				return false;
			}
		} else if (!subject.equals(other.subject)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "EmailXML [sender=" + sender + ", cc=" + cc + ", bcc=" + bcc
				+ ", receiver=" + receiver + ", subject=" + subject
				+ ", content=" + content + "]";
	}

	
}
