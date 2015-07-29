package com.triangularlake.constantine.triangularlake.data.dto;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "PHOTO")
public class Photo implements ICommonDtoConstants {

    @DatabaseField(columnName = PHOTO_ID)
    private Long photoId;                   // id фотографии

    @DatabaseField(dataType = DataType.BYTE_ARRAY, columnName = PHOTO_DATA)
    private byte[] photoData;                   // фото

    public Photo() {
        // need for ormLite
    }

    public Photo(byte[] photoData) {
        this.photoData = photoData;
    }

    public byte[] getPhotoData() {
        return photoData;
    }

    public void setPhotoData(byte[] photoData) {
        this.photoData = photoData;
    }
}
