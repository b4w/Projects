package com.triangularlake.constantine.triangularlake.data.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "ZREGION")
public class Region implements ICommonFieldsName {

    @DatabaseField(generatedId = false, columnName = Z_REGION_ID)
    private String regionId;                // id региона

    @DatabaseField(columnName = Z_REGION_LAT)
    private String regionLat;               // широта координаты центра региона

    @DatabaseField(columnName = Z_REGION_LON)
    private String regionLon;               // долгота координаты центра рениона

    @DatabaseField(columnName = Z_REGION_NAME)
    private String regionName;              // название на английском (по умолчанию)

//    TODO: в таблице БД нет такого поля, только - regionName
//    @DatabaseField(columnName = Z_REGION_NAME)
//    private String regionName_ru;           // название на русском

    @DatabaseField(columnName = Z_REGION_PHOTO)
    private byte[] regionPhoto;             // фотография (не указатель, а само фото)

//    TODO: по БД не совсем понятно какая колонка используеся
    private List<Sector> containSectors;    // связь один ко многим на сектора


    public Region() {
        // need for ormLite
    }

    public Region(String regionId, String regionLat, String regionLon, String regionName, byte[] regionPhoto, List<Sector> containSectors) {
        this.regionId = regionId;
        this.regionLat = regionLat;
        this.regionLon = regionLon;
        this.regionName = regionName;
        this.regionPhoto = regionPhoto;
        this.containSectors = containSectors;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionLat() {
        return regionLat;
    }

    public void setRegionLat(String regionLat) {
        this.regionLat = regionLat;
    }

    public String getRegionLon() {
        return regionLon;
    }

    public void setRegionLon(String regionLon) {
        this.regionLon = regionLon;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public byte[] getRegionPhoto() {
        return regionPhoto;
    }

    public void setRegionPhoto(byte[] regionPhoto) {
        this.regionPhoto = regionPhoto;
    }

    public List<Sector> getContainSectors() {
        return containSectors;
    }

    public void setContainSectors(List<Sector> containSectors) {
        this.containSectors = containSectors;
    }
}
