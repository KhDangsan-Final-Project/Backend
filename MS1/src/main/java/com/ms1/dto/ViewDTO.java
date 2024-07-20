package com.ms1.dto;

import org.apache.ibatis.type.Alias;

@Alias("view")
public class ViewDTO {
	private int viewNo;
	private String ipAddress;
	
	public ViewDTO() {
	}

	public ViewDTO(int viewNo, String ipAddress) {
		this.viewNo = viewNo;
		this.ipAddress = ipAddress;
	}

	public int getViewNo() {
		return viewNo;
	}

	public void setViewNo(int viewNo) {
		this.viewNo = viewNo;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		return "ViewDTO [viewNo=" + viewNo + ", ipAddress=" + ipAddress + "]";
	}

	
	
}
