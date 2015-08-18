package com.triangularlake.constantine.triangularlake.data.dto;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = ICommonDtoConstants.PHOTO_TABLE_NAME)
public class Photo implements ICommonDtoConstants {

    @DatabaseField(id = true, canBeNull = false, generatedId = false, columnName = ID)
    private Long id;                           // id фотографии

    @DatabaseField(dataType = DataType.BYTE_ARRAY, columnName = PHOTO_DATA)
    private byte[] photoData;                  // фото

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Boulder boulder;                   // ссылка manyToOne на Boulder

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Problem problem;                   // ссылка manyToOne на Problem

    public Photo() {
        // need for ormLite
    }

    public Photo(Long id, byte[] photoData, Boulder boulder, Problem problem) {
        this.id = id;
        this.photoData = photoData;
        this.boulder = boulder;
        this.problem = problem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPhotoData() {
        return photoData;
    }

    public void setPhotoData(byte[] photoData) {
        this.photoData = photoData;
    }

    public Boulder getBoulder() {
        return boulder;
    }

    public void setBoulder(Boulder boulder) {
        this.boulder = boulder;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }
}
