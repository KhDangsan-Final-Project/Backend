package com.ms1.dto;

import org.apache.ibatis.type.Alias;

@Alias("board")
public class BoardDTO {
	private int boardNo;
	private String id;
	private String boardTitle;
	private String boardContent;
	private	String boardWrite;
	private int boardCount;
	private String boardCategory;
	private String profileUrl;
	
	
	public BoardDTO() {
	}



	public BoardDTO(int boardNo, String id, String boardTitle, String boardContent, String boardWrite, int boardCount,
			String boardCategory, String profileUrl) {
		this.boardNo = boardNo;
		this.id = id;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWrite = boardWrite;
		this.boardCount = boardCount;
		this.boardCategory = boardCategory;
		this.profileUrl = profileUrl;
	}



	public int getBoardNo() {
		return boardNo;
	}



	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getBoardTitle() {
		return boardTitle;
	}



	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}



	public String getBoardContent() {
		return boardContent;
	}



	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}



	public String getBoardWrite() {
		return boardWrite;
	}



	public void setBoardWrite(String boardWrite) {
		this.boardWrite = boardWrite;
	}



	public int getBoardCount() {
		return boardCount;
	}



	public void setBoardCount(int boardCount) {
		this.boardCount = boardCount;
	}



	public String getBoardCategory() {
		return boardCategory;
	}



	public void setBoardCategory(String boardCategory) {
		this.boardCategory = boardCategory;
	}
	
	public String getProfileUrl() {
		return profileUrl;
	}



	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}



	@Override
	public String toString() {
		return "BoardDTO [boardNo=" + boardNo + ", id=" + id + ", boardTitle=" + boardTitle + ", boardContent="
				+ boardContent + ", boardWrite=" + boardWrite + ", boardCount=" + boardCount + ", boardCategory="
				+ boardCategory + ", profileUrl + ";
	}

	

	
	
}