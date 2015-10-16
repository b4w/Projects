package com.triangularlake.constantine.triangularlake.data.dto;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = ICommonDtoConstants.PROBLEM_TABLE_NAME)
public class Problem implements ICommonDtoConstants {

    @DatabaseField(id = true, canBeNull = false, generatedId = false, columnName = ID)
    private Long id;                                // id проблемы

    @DatabaseField(columnName = FRIEND_HELP)
    private boolean friendHelp;                     // нужна ли помощь страховщика

    @DatabaseField(columnName = PAD_COUNT)
    private int padCount;                           // кол-во крэшпадов

    @DatabaseField(columnName = PROBLEM_DESC)
    private String problemDesc;                     // комментарий на английском

    @DatabaseField(columnName = PROBLEM_DESC_RU)
    private String problemDescRu;                   // комментарий на русском

    @DatabaseField(columnName = PROBLEM_GRADE)
    private String problemGrade;                    // категория

    @DatabaseField(columnName = PROBLEM_LETTER)
    private String problemLetter;                   // буква трассы (на экране камня линии трасс имеют буквы)

    @DatabaseField(columnName = PROBLEM_NAME)
    private String problemName;                     // название на английском

    @DatabaseField(columnName = PROBLEM_NAME_RU)
    private String problemNameRu;                   // название на русском

    @DatabaseField(columnName = WARNING_LEVEL)
    private int warningLevel;                       // уровень опасности (от 1 до 3)

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Photo photo;                            // отношение oneToOne на Photo

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Boulder boulder;                        // ссылка manyToOne на Boulder

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Side side;                              // ссылка manyToOne на Side

    @DatabaseField(columnName = FAVOURITE)
    private int favourite;                          // проблема добавлена в избранное

    public Problem() {
        // need for ormLite
    }

    public Problem(Long id, boolean friendHelp, int padCount, String problemDesc, String problemDescRu,
                   String problemGrade, String problemLetter, String problemName, String problemNameRu,
                   int warningLevel, Photo photo, Boulder boulder, Side side, int favourite) {
        this.id = id;
        this.friendHelp = friendHelp;
        this.padCount = padCount;
        this.problemDesc = problemDesc;
        this.problemDescRu = problemDescRu;
        this.problemGrade = problemGrade;
        this.problemLetter = problemLetter;
        this.problemName = problemName;
        this.problemNameRu = problemNameRu;
        this.warningLevel = warningLevel;
        this.photo = photo;
        this.boulder = boulder;
        this.side = side;
        this.favourite = favourite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getProblemDescRu() {
        return problemDescRu;
    }

    public void setProblemDescRu(String problemDescRu) {
        this.problemDescRu = problemDescRu;
    }

    public String getProblemGrade() {
        return problemGrade;
    }

    public void setProblemGrade(String problemGrade) {
        this.problemGrade = problemGrade;
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

    public String getProblemNameRu() {
        return problemNameRu;
    }

    public void setProblemNameRu(String problemNameRu) {
        this.problemNameRu = problemNameRu;
    }

    public int getWarningLevel() {
        return warningLevel;
    }

    public void setWarningLevel(int warningLevel) {
        this.warningLevel = warningLevel;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Boulder getBoulder() {
        return boulder;
    }

    public void setBoulder(Boulder boulder) {
        this.boulder = boulder;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }
}
