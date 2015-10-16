package com.triangularlake.constantine.triangularlake.data.dto;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;
import java.util.List;

@DatabaseTable(tableName = ICommonDtoConstants.REGION_TABLE_NAME)
public class Region implements ICommonDtoConstants {

    @DatabaseField(id = true, canBeNull = false, generatedId = false, columnName = ID)
    private Long id;                        // id региона

    @DatabaseField(columnName = REGION_LAT)
    private String regionLat;               // широта координаты центра региона

    @DatabaseField(columnName = REGION_LON)
    private String regionLon;               // долгота координаты центра рениона

    @DatabaseField(columnName = REGION_NAME)
    private String regionName;              // название на английском (по умолчанию)

    @DatabaseField(columnName = REGION_NAME_RU)
    private String regionNameRu;            // название на русском

    @DatabaseField(dataType = DataType.BYTE_ARRAY, columnName = REGION_PHOTO)
    private byte[] regionPhoto;             // фотография (не указатель, а само фото)

    @ForeignCollectionField(eager = false)
    private Collection<Sector> sectors;     // ссылка oneToMany на Sector

    public int getCountSectors() {
        return sectors != null ? sectors.size() : 0;
    }

    public Region() {
        // need for ormLite
    }

    public Region(Long id, String regionLat, String regionLon, String regionName,
                  byte[] regionPhoto, List<Sector> sectors) {
        this.id = id;
        this.regionLat = regionLat;
        this.regionLon = regionLon;
        this.regionName = regionName;
        this.regionPhoto = regionPhoto;
        this.sectors = sectors;
    }

//    GET & SET

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Collection<Sector> getSectors() {
        return sectors;
    }

    public void setSectors(Collection<Sector> sectors) {
        this.sectors = sectors;
    }

    public String getRegionNameRu() {
        return regionNameRu;
    }

    public void setRegionNameRu(String regionNameRu) {
        this.regionNameRu = regionNameRu;
    }
}
