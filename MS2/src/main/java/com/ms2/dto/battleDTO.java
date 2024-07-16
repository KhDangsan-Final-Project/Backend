package com.ms2.dto;

import org.apache.ibatis.type.Alias;

@Alias("battle")
public class battleDTO {

	private int cardNo;
	private String id;
	private int matchNo;
	private int matchWin;
	private int matchLoss;
	
	public battleDTO() {
	}

	public int getCardNo() {
		return cardNo;
	}

	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMatchWin() {
		return matchWin;
	}

	public void setMatchWin(int matchWin) {
		this.matchWin = matchWin;
	}

	public int getMatchLoss() {
		return matchLoss;
	}

	public void setMatchLoss(int matchLoss) {
		this.matchLoss = matchLoss;
	}

	@Override
	public String toString() {
		return "battleDTO [cardNo=" + cardNo + ", id=" + id + ", matchWin=" + matchWin + ", matchLoss=" + matchLoss
				+ "]";
	}

	
	
	
}
