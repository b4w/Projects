//package com.triangularlake.constantine.triangularlake.data.dto;
//
//
//import com.j256.ormlite.field.DatabaseField;
//import com.j256.ormlite.table.DatabaseTable;
//
//@DatabaseTable(tableName = ICommonDtoConstants.PROBLEM_TABLE_NAME)
//public class Problem implements ICommonDtoConstants {
//
//    @DatabaseField(columnName = PROBLEM_ID)
//    private Long problemId;                         // id проблемы
//
//    @DatabaseField(columnName = FRIEND_HELP)
//    private boolean friendHelp;                     // нужна ли помощь страховщика
//
//    @DatabaseField(columnName = PAD_COUNT)
//    private int padCount;                           // кол-во крэшпадов
//
//    @DatabaseField(columnName = PROBLEM_DESC)
//    private String problemDesc;                     // комментарий на английском
//
//    @DatabaseField(columnName = PROBLEM_DESC_RU)
//    private String problemDescRu;                   // комментарий на русском
//
//    @DatabaseField(columnName = PROBLEM_GRADE)
//    private String problemGrade;                    // категория
//
//    @DatabaseField(columnName = PROBLEM_LETTER)
//    private String problemLetter;                   // буква трассы (на экране камня линии трасс имеют буквы)
//
//    @DatabaseField(columnName = PROBLEM_NAME)
//    private String problemName;                     // название на английском
//
//    @DatabaseField(columnName = PROBLEM_NAME_RU)
//    private String problemNameRu;                   // название на русском
//
//    @DatabaseField(columnName = WARNING_LEVEL)
//    private int warningLevel;                       // уровень опасности (от 1 до 3)
//
//    @DatabaseField(columnName = PROBLEM_PHOTO)
//    private Photo problemPhoto;                     // отношение oneToOne на Photo
//
//    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
//    private Boulder boulder;                        // ссылка manyToOne на Boulder
//
//    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
//    private Side side;                              // ссылка manyToOne на Side
//
//    public Problem() {
//        // need for ormLite
//    }
//
//    public Long getProblemId() {
//        return problemId;
//    }
//
//    public void setProblemId(Long problemId) {
//        this.problemId = problemId;
//    }
//
//    public boolean isFriendHelp() {
//        return friendHelp;
//    }
//
//    public void setFriendHelp(boolean friendHelp) {
//        this.friendHelp = friendHelp;
//    }
//
//    public int getPadCount() {
//        return padCount;
//    }
//
//    public void setPadCount(int padCount) {
//        this.padCount = padCount;
//    }
//
//    public String getProblemDesc() {
//        return problemDesc;
//    }
//
//    public void setProblemDesc(String problemDesc) {
//        this.problemDesc = problemDesc;
//    }
//
//    public String getProblemDescRu() {
//        return problemDescRu;
//    }
//
//    public void setProblemDescRu(String problemDescRu) {
//        this.problemDescRu = problemDescRu;
//    }
//
//    public String getProblemGrade() {
//        return problemGrade;
//    }
//
//    public void setProblemGrade(String problemGrade) {
//        this.problemGrade = problemGrade;
//    }
//
//    public String getProblemLetter() {
//        return problemLetter;
//    }
//
//    public void setProblemLetter(String problemLetter) {
//        this.problemLetter = problemLetter;
//    }
//
//    public String getProblemName() {
//        return problemName;
//    }
//
//    public void setProblemName(String problemName) {
//        this.problemName = problemName;
//    }
//
//    public String getProblemNameRu() {
//        return problemNameRu;
//    }
//
//    public void setProblemNameRu(String problemNameRu) {
//        this.problemNameRu = problemNameRu;
//    }
//
//    public int getWarningLevel() {
//        return warningLevel;
//    }
//
//    public void setWarningLevel(int warningLevel) {
//        this.warningLevel = warningLevel;
//    }
//
//    public Photo getProblemPhoto() {
//        return problemPhoto;
//    }
//
//    public void setProblemPhoto(Photo problemPhoto) {
//        this.problemPhoto = problemPhoto;
//    }
//
//    public Boulder getBoulder() {
//        return boulder;
//    }
//
//    public void setBoulder(Boulder boulder) {
//        this.boulder = boulder;
//    }
//
//    public Side getSide() {
//        return side;
//    }
//
//    public void setSide(Side side) {
//        this.side = side;
//    }
//}
