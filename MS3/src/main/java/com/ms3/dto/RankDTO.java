package com.ms3.dto;

import org.apache.ibatis.type.Alias;

@Alias("rank")
public class RankDTO {
	private int grantNo;
	private String grantName;
	
	public RankDTO() {
	}

	public int getGrantNo() {
		return grantNo;
	}

	public void setGrantNo(int grantNo) {
		this.grantNo = grantNo;
	}

	public String getGrantName() {
		return grantName;
	}

	public void setGrantName(String grantName) {
		this.grantName = grantName;
	}

	@Override
	public String toString() {
		return "RankDTO [grantNo=" + grantNo + ", grantName=" + grantName + "]";
	}
	
	
	
	
}
