package com.triangularlake.constantine.triangularlake.data.dto;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ZPROBLEM")
public class Problem implements ICommonFieldsName {

    @DatabaseField(columnName = Z_FRIEND_HELP)
    private boolean friendHelp;                     // нужна ли помощь страховщика

    @DatabaseField(columnName = Z_PAD_COUNT)
    private int padCount;                           // кол-во крэшпадов

    @DatabaseField(columnName = Z_PROBLEM_DESC)
    private String problemDesc;                     // комментарий на английском

//    TODO: в таблице БД нет такого поля, только problemDesc
//    @DatabaseField(columnName = Z_PROBLEM_DESC)
//    private String problemDesc_ru;                  // комментарий на русском

    @DatabaseField(columnName = Z_PROBLEM_GRADE)
    private String problemGrade;                    // категория

    @DatabaseField(columnName = Z_PROBLEM_ID)
    private String problemId;                       // id пробдемы

    @DatabaseField(columnName = Z_PROBLEM_LETTER)
    private String problemLetter;                   // буква трассы; (на экране камня линии трасс имеют буквы)

    @DatabaseField(columnName = Z_PROBLEM_NAME)
    private String problemName;                     // название на английском

//    TODO: в таблице БД нет такого поля, только problemDesc
//    @DatabaseField(columnName = Z_PROBLEM_NAME)
//    private String problemName_ru;                  // название на русском

    @DatabaseField(columnName = Z_WARNING_LEVEL)
    private int warningLevel;                       // уровень опасности (от 1 до 3)

    @DatabaseField(columnName = Z_PROBLEM_PHOTO)
    private Photo problemPhoto;                     // отношение 1 к 1 - “фото”

    public Problem() {
        // need for ormLite
    }

    public Problem(boolean friendHelp, int padCount, String problemDesc, String problemGrade,
                   String problemId, String problemLetter, String problemName, int warningLevel,
                   Photo problemPhoto) {
        this.friendHelp = friendHelp;
        this.padCount = padCount;
        this.problemDesc = problemDesc;
        this.problemGrade = problemGrade;
        this.problemId = problemId;
        this.problemLetter = problemLetter;
        this.problemName = problemName;
        this.warningLevel = warningLevel;
        this.problemPhoto = problemPhoto;
    }

    public boolean isFriendHelp() {
        return friendHelp;
    }

    public void setFriendHelp(boolean friendHelp) {
        this.friendHelp = friendHelp;
    }

    public int getPadCount() {
        return padCount;
    }

    public void setPadCount(int padCount) {
        this.padCount = padCount;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
    }

    public String getProblemGrade() {
        return problemGrade;
    }

    public void setProblemGrade(String problemGrade) {
        this.problemGrade = problemGrade;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getProblemLetter() {
        return problemLetter;
    }

    public void setProblemLetter(String problemLetter) {
        this.problemLetter = problemLetter;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public int getWarningLevel() {
        return warningLevel;
    }

    public void setWarningLevel(int warningLevel) {
        this.warningLevel = warningLevel;
    }

    public Photo getProblemPhoto() {
        return problemPhoto;
    }

    public void setProblemPhoto(Photo problemPhoto) {
        this.problemPhoto = problemPhoto;
    }
}
