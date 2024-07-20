package com.ms1.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("ai")
public class AiDTO {
	private int requestNo;
	private String requestDate;
	
	
	public AiDTO() {
	}


	public AiDTO(int requestNo, String requestDate) {
		this.requestNo = requestNo;
		this.requestDate = requestDate;
	}


	public int getRequestNo() {
		return requestNo;
	}


	public void setRequestNo(int requestNo) {
		this.requestNo = requestNo;
	}


	public String getRequestDate() {
		return requestDate;
	}


	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}


	@Override
	public String toString() {
		return "AiDTO [requestNo=" + requestNo + ", requestDate=" + requestDate + "]";
	}
	
	
}
