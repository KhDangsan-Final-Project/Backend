package com.ms3.dto;

import org.apache.ibatis.type.Alias;

@Alias("board")
public class BoardDTO {
	private int boardNo;
	private String id;
	private String boardTitle;
	private String boardContent;
	private	String boardWrite;
	private int boardCount;

	public BoardDTO() {
	}

	public BoardDTO(int boardNo, String id, String boardTitle, String boardContent, String boardWrite, int boardCount) {
		super();
		this.boardNo = boardNo;
		this.id = id;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWrite = boardWrite;
		this.boardCount = boardCount;
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

	@Override
	public String toString() {
		return "BoardDTO [boardNo=" + boardNo + ", id=" + id + ", boardTitle=" + boardTitle + ", boardContent="
				+ boardContent + ", boardWrite=" + boardWrite + ", boardCount=" + boardCount + "]";
	}
	
	
	
	
	
	
}
