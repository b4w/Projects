package com.triangularlake.constantine.triangularlake.data.dto;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

@DatabaseTable(tableName = "SECTOR")
public class Sector implements ICommonDtoConstants {

    @DatabaseField(generatedId = false, columnName = SECTOR_ID)
    private Long sectorId;                  // id сектора

    @DatabaseField(columnName = MAX_GRADE)
    private String maxGrade;                // максимальная категория проблемы в секторе

    @DatabaseField(columnName = MIN_GRADE)
    private String minGrade;                // минимальная категория проблемы в секторе

    @DatabaseField(columnName = PROBLEM_COUNT)
    private int problemCount;               // кол-во проблем в секторе

    @DatabaseField(columnName = SECTOR_DESC)
    private String sectorDesc;              // описание сектора на английском

    @DatabaseField(columnName = SECTOR_DESC_RU)
    private String sectorDescRu;            // описание сектора на русском

    @DatabaseField(columnName = SECTOR_LAT)
    private String sectorLat;               // широта центра

    @DatabaseField(columnName = SECTOR_LON)
    private String sectorLon;               // долгота центра

    @DatabaseField(columnName = SECTOR_NAME)
    private String sectorName;              // название на английском

    @DatabaseField(columnName = SECTOR_NAME_RU)
    private String sectorNameRu;            // название на русском

    @DatabaseField(dataType = DataType.BYTE_ARRAY, columnName = SECTOR_PHOTO)
    private byte[] sectorPhoto;             // фото (не указатель!)

    @ForeignCollectionField(eager = true)
    private Collection<Boulder> boulders;   // ссылка oneToMany на Boulder

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true) // maxForeignAutoRefreshLevel = 3
    private Region region;

    public Sector() {
        // need for ormLite
    }

    public Sector(Long sectorId, String maxGrade, String minGrade, int problemCount, String sectorDesc,
                  String sectorDescRu, String sectorLat, String sectorLon, String sectorName,
                  String sectorNameRu, byte[] sectorPhoto, Collection<Boulder> boulders, Region region) {
        this.sectorId = sectorId;
        this.maxGrade = maxGrade;
        this.minGrade = minGrade;
        this.problemCount = problemCount;
        this.sectorDesc = sectorDesc;
        this.sectorDescRu = sectorDescRu;
        this.sectorLat = sectorLat;
        this.sectorLon = sectorLon;
        this.sectorName = sectorName;
        this.sectorNameRu = sectorNameRu;
        this.sectorPhoto = sectorPhoto;
        this.boulders = boulders;
        this.region = region;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
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

    public String getSectorDescRu() {
        return sectorDescRu;
    }

    public void setSectorDescRu(String sectorDescRu) {
        this.sectorDescRu = sectorDescRu;
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

    public String getSectorNameRu() {
        return sectorNameRu;
    }

    public void setSectorNameRu(String sectorNameRu) {
        this.sectorNameRu = sectorNameRu;
    }

    public byte[] getSectorPhoto() {
        return sectorPhoto;
    }

    public void setSectorPhoto(byte[] sectorPhoto) {
        this.sectorPhoto = sectorPhoto;
    }

    public Collection<Boulder> getBoulders() {
        return boulders;
    }

    public void setBoulders(Collection<Boulder> boulders) {
        this.boulders = boulders;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
