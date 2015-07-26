package com.triangularlake.constantine.triangularlake.data.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "ZSIDE")
public class Side implements ICommonDtoConstants {

    @DatabaseField(columnName = Z_SIDE_ID)
    private String sideId;                          // id стороны

    @DatabaseField(columnName = Z_SIDE_PHOTO)
    private byte[] sidePhoto;                       // фото (не ссылка)

//    TODO: по БД не совсем понятно какая колонка используеся. Нет привязки в БД.
    private List<Problem> containProblems;          // содержит проблемы

    public Side() {
        // need for ormLite
    }

    public Side(String sideId, byte[] sidePhoto, List<Problem> containProblems) {
        this.sideId = sideId;
        this.sidePhoto = sidePhoto;
        this.containProblems = containProblems;
    }

    public String getSideId() {
        return sideId;
    }

    public void setSideId(String sideId) {
        this.sideId = sideId;
    }

    public byte[] getSidePhoto() {
        return sidePhoto;
    }

    public void setSidePhoto(byte[] sidePhoto) {
        this.sidePhoto = sidePhoto;
    }

    public List<Problem> getContainProblems() {
        return containProblems;
    }

    public void setContainProblems(List<Problem> containProblems) {
        this.containProblems = containProblems;
    }
}
