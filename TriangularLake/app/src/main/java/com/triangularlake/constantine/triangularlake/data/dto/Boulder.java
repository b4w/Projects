package com.triangularlake.constantine.triangularlake.data.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "ZBOULDER")
public class Boulder implements ICommonDtoConstants {

    @DatabaseField(columnName = Z_BOULDER_DESC)
    private String boulderDesc;                     // описание камня на английском

//    TODO: в таблице БД нет такого поля, только - boulderDesc
//    @DatabaseField(columnName = Z_BOULDER_DESC)
//    private String boulderDesc_ru;                  // описание камня на русском

    @DatabaseField(columnName = Z_BOULDER_ID)
    private String boulderId;                       // id камня

    @DatabaseField(columnName = Z_BOULDER_LAT)
    private String boulderLat;                      // широта координаты камня

    @DatabaseField(columnName = Z_BOULDER_LON)
    private String boulderLon;                      // долгота координаты камня

    @DatabaseField(columnName = Z_BOULDER_NAME)
    private String boulderName;                     // название на английском

//    TODO: в таблице БД нет такого поля, только - boulderName
//    @DatabaseField(columnName = Z_BOULDER_NAME)
//    private String boulderName_ru;                  // название на русском

    @DatabaseField(columnName = Z_BOULDER_PHOTO)
    private Photo boulderPhoto;                     // содержит фото

//    TODO: по БД не совсем понятно какая колонка используеся. Нет привязки в БД.
    private List<Problem> containProblems;          // содержит проблемы
//    TODO: по БД не совсем понятно какая колонка используеся. Нет привязки в БД.
    private List<Side> containSides;                // содержит стороны

    public Boulder() {
        // need to ornLite
    }

    public Boulder(String boulderDesc, String boulderId, String boulderLat, String boulderLon,
                   String boulderName, Photo boulderPhoto, List<Problem> containProblems,
                   List<Side> containSides) {
        this.boulderDesc = boulderDesc;
        this.boulderId = boulderId;
        this.boulderLat = boulderLat;
        this.boulderLon = boulderLon;
        this.boulderName = boulderName;
        this.boulderPhoto = boulderPhoto;
        this.containProblems = containProblems;
        this.containSides = containSides;
    }

    public String getBoulderDesc() {
        return boulderDesc;
    }

    public void setBoulderDesc(String boulderDesc) {
        this.boulderDesc = boulderDesc;
    }

    public String getBoulderId() {
        return boulderId;
    }

    public void setBoulderId(String boulderId) {
        this.boulderId = boulderId;
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

    public Photo getBoulderPhoto() {
        return boulderPhoto;
    }

    public void setBoulderPhoto(Photo boulderPhoto) {
        this.boulderPhoto = boulderPhoto;
    }

    public List<Problem> getContainProblems() {
        return containProblems;
    }

    public void setContainProblems(List<Problem> containProblems) {
        this.containProblems = containProblems;
    }

    public List<Side> getContainSides() {
        return containSides;
    }

    public void setContainSides(List<Side> containSides) {
        this.containSides = containSides;
    }
}
