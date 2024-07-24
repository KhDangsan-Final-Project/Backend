package com.ms1.dto;

import org.apache.ibatis.type.Alias;

@Alias("comment")
public class BoardCommentDTO {
	private int bno;
	private int cno;
	private String id;
	private String comment;
	private String cdate;
	
	public BoardCommentDTO() {
	}

	public BoardCommentDTO(int bno, String id, String comment) {
		this.bno = bno;
		this.id = id;
		this.comment = comment;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public int getCno() {
		return cno;
	}

	public void setCno(int cno) {
		this.cno = cno;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	@Override
	public String toString() {
		return "BoardCommentDTO [bno=" + bno + ", cno=" + cno + ", id=" + id + ", comment=" + comment + ", cdate="
				+ cdate + "]";
	}

	
	
}
