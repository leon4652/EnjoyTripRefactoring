package com.sas.refactoring.freeBoard.dto;

import java.time.LocalDateTime;

public class FreeBoardDto {
    int boardNo;
    int boardUserNo;
    int boardTitle;
    String boardContent;
    int boardHit;
    int boardLike;
    LocalDateTime registDate;
    LocalDateTime removeDate;

    public int getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }

    public int getBoardUserNo() {
        return boardUserNo;
    }

    public void setBoardUserNo(int boardUserNo) {
        this.boardUserNo = boardUserNo;
    }

    public int getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(int boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getBoardContent() {
        return boardContent;
    }

    public void setBoardContent(String boardContent) {
        this.boardContent = boardContent;
    }

    public int getBoardHit() {
        return boardHit;
    }

    public void setBoardHit(int boardHit) {
        this.boardHit = boardHit;
    }

    public int getBoardLike() {
        return boardLike;
    }

    public void setBoardLike(int boardLike) {
        this.boardLike = boardLike;
    }

    public LocalDateTime getRegistDate() {
        return registDate;
    }

    public void setRegistDate(LocalDateTime registDate) {
        this.registDate = registDate;
    }

    public LocalDateTime getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(LocalDateTime removeDate) {
        this.removeDate = removeDate;
    }
}
