package com.ms3.dto;

import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;

@Alias("mail")
public class MailDTO {
	
	private int mailNo;
	private String sender;
    private String receiver;
    private String subject;
    private String content;
    private LocalDateTime timestamp;
    
	public MailDTO(int mailNo, String sender, String receiver, String subject, String content,
			LocalDateTime timestamp) {
		this.mailNo = mailNo;
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.content = content;
		this.timestamp = timestamp;
	}

	public int getMailNo() {
		return mailNo;
	}

	public void setMailNo(int mailNo) {
		this.mailNo = mailNo;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
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

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "MailDTO [mailNo=" + mailNo + ", sender=" + sender + ", receiver=" + receiver + ", subject=" + subject
				+ ", content=" + content + ", timestamp=" + timestamp + "]";
	}
    
	
	
}
