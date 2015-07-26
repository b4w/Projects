package com.triangularlake.constantine.triangularlake.data.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "ZSECTOR")
public class Sector implements ICommonFieldsName {

    @DatabaseField(columnName = Z_MAX_GRADE)
    private String maxGrade;                // максимальная категория проблемы в секторе

    @DatabaseField(columnName = Z_MIN_GRADE)
    private String minGrade;                // минимальная категория проблемы в секторе

    @DatabaseField(columnName = Z_PROBLEM_COUNT)
    private int problemCount;               // кол-во проблем в секторе

    @DatabaseField(columnName = Z_SECTOR_DESC)
    private String sectorDesc;              // описание сектора на английском

//    TODO: в таблице БД нет такого поля, только - sectorDesc
//    @DatabaseField(columnName = Z_SECTOR_DESC)
//    private String sectorDesc_ru;           // описание сектора на русском

    @DatabaseField(columnName = Z_SECTOR_ID)
    private String sectorId;                // id сектора

    @DatabaseField(columnName = Z_SECTOR_LAT)
    private String sectorLat;               // широта центра

    @DatabaseField(columnName = Z_SECTOR_LON)
    private String sectorLon;               // долгота центра

    @DatabaseField(columnName = Z_SECTOR_NAME)
    private String sectorName;              // название на английском

//    TODO: в таблице БД нет такого поля, только sectorName
//    @DatabaseField(columnName = Z_SECTOR_NAME)
//    private String sectorName_ru;           // название на русском

    @DatabaseField(columnName = Z_SECTOR_PHOTO)
    private byte[] sectorPhoto;             // фото (не указатель!)

//    TODO: по БД не совсем понятно какая колонка используеся. Нет привязки в БД.
    private List<Boulder> boulders;         // cвязь один ко многим - содержит камни

    public Sector() {
        // need for ormLite
    }

    public Sector(String maxGrade, String minGrade, int problemCount, String sectorDesc,
                  String sectorId, String sectorLat, String sectorLon, String sectorName,
                  byte[] sectorPhoto, List<Boulder> boulders) {
        this.maxGrade = maxGrade;
        this.minGrade = minGrade;
        this.problemCount = problemCount;
        this.sectorDesc = sectorDesc;
        this.sectorId = sectorId;
        this.sectorLat = sectorLat;
        this.sectorLon = sectorLon;
        this.sectorName = sectorName;
        this.sectorPhoto = sectorPhoto;
        this.boulders = boulders;
    }

    public String getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(String maxGrade) {
        this.maxGrade = maxGrade;
    }

    public String getMinGrade() {
        return minGrade;
    }

    public void setMinGrade(String minGrade) {
        this.minGrade = minGrade;
    }

    public int getProblemCount() {
        return problemCount;
    }

    public void setProblemCount(int problemCount) {
        this.problemCount = problemCount;
    }

    public String getSectorDesc() {
        return sectorDesc;
    }

    public void setSectorDesc(String sectorDesc) {
        this.sectorDesc = sectorDesc;
    }

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public String getSectorLat() {
        return sectorLat;
    }

    public void setSectorLat(String sectorLat) {
        this.sectorLat = sectorLat;
    }

    public String getSectorLon() {
        return sectorLon;
    }

    public void setSectorLon(String sectorLon) {
        this.sectorLon = sectorLon;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public byte[] getSectorPhoto() {
        return sectorPhoto;
    }

    public void setSectorPhoto(byte[] sectorPhoto) {
        this.sectorPhoto = sectorPhoto;
    }

    public List<Boulder> getBoulders() {
        return boulders;
    }

    public void setBoulders(List<Boulder> boulders) {
        this.boulders = boulders;
    }
}
