//package com.triangularlake.constantine.triangularlake.data.dto;
//
//import com.j256.ormlite.field.DataType;
//import com.j256.ormlite.field.DatabaseField;
//import com.j256.ormlite.field.ForeignCollectionField;
//import com.j256.ormlite.table.DatabaseTable;
//
//import java.util.Collection;
//
//@DatabaseTable(tableName = ICommonDtoConstants.SIDE_TABLE_NAME)
//public class Side implements ICommonDtoConstants {
//
//    @DatabaseField(columnName = SIDE_ID)
//    private Long sideId;                          // id стороны
//
//    @DatabaseField(dataType = DataType.BYTE_ARRAY, columnName = SIDE_PHOTO)
//    private byte[] sidePhoto;                     // фото (не ссылка)
//
//    @ForeignCollectionField(eager = true)
//    private Collection<Problem> problems;         // ссылка oneToMany на Problem
//
//    public Side() {
//        // need for ormLite
//    }
//
//    public Side(Long sideId, byte[] sidePhoto, Collection<Problem> problems) {
//        this.sideId = sideId;
//        this.sidePhoto = sidePhoto;
//        this.problems = problems;
//    }
//
//    public Long getSideId() {
//        return sideId;
//    }
//
//    public void setSideId(Long sideId) {
//        this.sideId = sideId;
//    }
//
//    public byte[] getSidePhoto() {
//        return sidePhoto;
//    }
//
//    public void setSidePhoto(byte[] sidePhoto) {
//        this.sidePhoto = sidePhoto;
//    }
//
//    public Collection<Problem> getProblems() {
//        return problems;
//    }
//
//    public void setProblems(Collection<Problem> problems) {
//        this.problems = problems;
//    }
//}
