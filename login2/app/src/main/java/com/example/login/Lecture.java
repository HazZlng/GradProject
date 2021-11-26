package com.example.login;

public class Lecture {
    String subjctNm;
    String credit;
    String lctreTime;
    String lctreRoom;
    String subjctCode;

    public Lecture(String subjctNm, String credit, String lctreTime, String lctreRoom, String subjctCode) {
        this.subjctNm = subjctNm;
        this.credit = credit;
        this.lctreTime = lctreTime;
        this.lctreRoom = lctreRoom;
        this.subjctCode = subjctCode;
    }

    public String getSubjctNm() {
        return subjctNm;
    }

    public String getCredit() {
        return credit;
    }

    public String getLctreTime() {
        return lctreTime;
    }

    public String getSubjctCode() { return subjctCode; }

    public String getLctreRoom() {
        return lctreRoom;
    }

    public void setSubjctNm(String subjctNm) {
        this.subjctNm = subjctNm;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public void setLctreTime(String lctreTime) {
        this.lctreTime = lctreTime;
    }

    public void setLctreRoom(String lctreRoom) {
        this.lctreRoom = lctreRoom;
    }

    public void setSubjctCode(String subjctCode) { this.subjctCode = subjctCode; }
}
