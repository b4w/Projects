package com.triangularlake.constantine.triangularlake.data.dto;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

@DatabaseTable(tableName = ICommonDtoConstants.BOULDER_TABLE_NAME)
public class Boulder implements ICommonDtoConstants {

    @DatabaseField(id = true, canBeNull = false, generatedId = false, columnName = ID)
    private int id;                                 // id камня

    @DatabaseField(columnName = BOULDER_DESC)
    private String boulderDesc;                     // описание камня на английском

    @DatabaseField(columnName = BOULDER_DESC_RU)
    private String boulderDescRu;                   // описание камня на русском

    @DatabaseField(columnName = BOULDER_LAT)
    private String boulderLat;                      // широта координаты камня

    @DatabaseField(columnName = BOULDER_LON)
    private String boulderLon;                      // долгота координаты камня

    @DatabaseField(columnName = BOULDER_NAME)
    private String boulderName;                     // название на английском

    @DatabaseField(columnName = BOULDER_NAME_RU)
    private String boulderNameRu;                   // название на русском

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Photo photo;                            // ссылка oneToOne на Photo (id Photo)

    @ForeignCollectionField(eager = false)
    private ForeignCollection<Problem> problems;           // ссылка oneToMany на Problems

    @ForeignCollectionField(eager = false)
    private ForeignCollection<Side> sides;                 // ссылка oneToMany на Side

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Sector sector;                          // ссылка manyToOne на Sector

    public Boulder() {
        // need to ornLite
    }

    public Boulder(int id, String boulderDesc, String boulderDescRu, String boulderLat,
                   String boulderLon, String boulderName, String boulderNameRu, Photo photo,
                   ForeignCollection<Problem> problems, ForeignCollection<Side> sides, Sector sector) {
        this.id = id;
        this.boulderDesc = boulderDesc;
        this.boulderDescRu = boulderDescRu;
        this.boulderLat = boulderLat;
        this.boulderLon = boulderLon;
        this.boulderName = boulderName;
        this.boulderNameRu = boulderNameRu;
        this.photo = photo;
        this.problems = problems;
        this.sides = sides;
        this.sector = sector;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBoulderDesc() {
        return boulderDesc;
    }

    public void setBoulderDesc(String boulderDesc) {
        this.boulderDesc = boulderDesc;
    }

    public String getBoulderDescRu() {
        return boulderDescRu;
    }

    public void setBoulderDescRu(String boulderDescRu) {
        this.boulderDescRu = boulderDescRu;
    }

    public String getBoulderLat() {
        return boulderLat;
    }

    public void setBoulderLat(String boulderLat) {
        this.boulderLat = boulderLat;
    }

    public String getBoulderLon() {
        return boulderLon;
    }

    public void setBoulderLon(String boulderLon) {
        this.boulderLon = boulderLon;
    }

    public String getBoulderName() {
        return boulderName;
    }

    public void setBoulderName(String boulderName) {
        this.boulderName = boulderName;
    }

    public String getBoulderNameRu() {
        return boulderNameRu;
    }

    public void setBoulderNameRu(String boulderNameRu) {
        this.boulderNameRu = boulderNameRu;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public ForeignCollection<Problem> getProblems() {
        return problems;
    }

    public void setProblems(ForeignCollection<Problem> problems) {
        this.problems = problems;
    }

    public ForeignCollection<Side> getSides() {
        return sides;
    }

    public void setSides(ForeignCollection<Side> sides) {
        this.sides = sides;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }
}
