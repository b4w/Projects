package com.triangularlake.constantine.triangularlake.data.dto;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Arrays;
import java.util.Collection;

@DatabaseTable(tableName = ICommonDtoConstants.SIDE_TABLE_NAME)
public class Side implements ICommonDtoConstants {

    @DatabaseField(id = true, canBeNull = false, generatedId = false, columnName = ID)
    private Long id;                              // id стороны

    @DatabaseField(dataType = DataType.BYTE_ARRAY, columnName = SIDE_PHOTO)
    private byte[] sidePhoto;                     // фото (не ссылка)

    @ForeignCollectionField(eager = false)
    private Collection<Problem> problems;         // ссылка oneToMany на Problem

//    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
//    private Boulder boulder;

    public Side() {
        // need for ormLite
    }

    public Side(Long id, byte[] sidePhoto, Collection<Problem> problems
//            , Boulder boulder
    ) {
        this.id = id;
        this.sidePhoto = sidePhoto;
        this.problems = problems;
//        this.boulder = boulder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getSidePhoto() {
        return sidePhoto;
    }

    public void setSidePhoto(byte[] sidePhoto) {
        this.sidePhoto = sidePhoto;
    }

    public Collection<Problem> getProblems() {
        return problems;
    }

    public void setProblems(Collection<Problem> problems) {
        this.problems = problems;
    }

//    public Boulder getBoulder() {
//        return boulder;
//    }
//
//    public void setBoulder(Boulder boulder) {
//        this.boulder = boulder;
//    }
}
