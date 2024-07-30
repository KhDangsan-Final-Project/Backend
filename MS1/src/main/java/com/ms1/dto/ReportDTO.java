package com.ms1.dto;

import org.apache.ibatis.type.Alias;

@Alias("report")
public class ReportDTO {
    private String id;
    private int boardNo;
    private int boardCommentNo;
    
    public ReportDTO() {
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getBoardNo() {
        return boardNo;
    }
    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }
    public int getBoardCommentNo() {
        return boardCommentNo;
    }
    public void setBoardCommentNo(int boardCommentNo) {
        this.boardCommentNo = boardCommentNo;
    }
    
    @Override
    public String toString() {
        return "ReportDTO [id=" + id + ", boardNo=" + boardNo + ", boardCommentNo=" + boardCommentNo + "]";
    }
    
    
}