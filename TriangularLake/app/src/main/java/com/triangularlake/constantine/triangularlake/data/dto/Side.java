package com.triangularlake.constantine.triangularlake.data.dto;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = ICommonDtoConstants.SIDE_TABLE_NAME)
public class Side implements ICommonDtoConstants {

    @DatabaseField(id = true, canBeNull = false, generatedId = false, columnName = ID)
    private int id;                               // id стороны

    @DatabaseField(dataType = DataType.BYTE_ARRAY, columnName = SIDE_PHOTO)
    private byte[] sidePhoto;                     // фото (не ссылка)

    @ForeignCollectionField(eager = false)
    private ForeignCollection<Problem> problems;         // ссылка oneToMany на Problem

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Boulder boulder;

    public Side() {
        // need for ormLite
    }

    public Side(int id, byte[] sidePhoto, ForeignCollection<Problem> problems, Boulder boulder
    ) {
        this.id = id;
        this.sidePhoto = sidePhoto;
        this.problems = problems;
        this.boulder = boulder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getSidePhoto() {
        return sidePhoto;
    }

    public void setSidePhoto(byte[] sidePhoto) {
        this.sidePhoto = sidePhoto;
    }

    public ForeignCollection<Problem> getProblems() {
        return problems;
    }

    public void setProblems(ForeignCollection<Problem> problems) {
        this.problems = problems;
    }

    public Boulder getBoulder() {
        return boulder;
    }

    public void setBoulder(Boulder boulder) {
        this.boulder = boulder;
    }
}
