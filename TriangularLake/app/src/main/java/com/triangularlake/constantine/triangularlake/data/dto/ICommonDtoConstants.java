package com.triangularlake.constantine.triangularlake.data.dto;

public interface ICommonDtoConstants {

    // Common
    String Z_PK = "Z_PK";
    String Z_ENT = "Z_ENT";
    String Z_OPT = "Z_OPT";
    String Z_IN_REGION = "ZINREGION";
    String Z_IN_SECTOR = "ZINSECTOR";
    String Z_IN_BOULDER = "ZINBOULDER";
    String Z_IN_SIDE = "ZINSIDE";
    String Z_IN_PROBLEM = "ZINPROBLEM";

    // REGION table
    String REGION_ID = "_id";
    String REGION_LAT = "region_lat";
    String REGION_LON = "region_lon";
    String REGION_NAME = "region_name";
    String REGION_NAME_RU = "region_name_ru";
    String REGION_PHOTO = "region_photo";

    // SECTOR table
    String PROBLEM_COUNT = "problem_count";
    String MAX_GRADE = "max_grade";
    String MIN_GRADE = "min_grade";
    String SECTOR_DESC = "sector_desc";
    String SECTOR_DESC_RU = "sector_desc_ru";
    String SECTOR_ID = "_id";
    String SECTOR_LAT = "sector_lat";
    String SECTOR_LON = "sector_lon";
    String SECTOR_NAME = "sector_name";
    String SECTOR_NAME_RU = "sector_name_ru";
    String SECTOR_PHOTO = "sector_photo";

    // Boulder table
    String BOULDER_PHOTO = "boulder_photo";
    String BOULDER_DESC = "boulder_desc";
    String BOULDER_DESC_RU = "boulder_desc_ru";
    String BOULDER_ID = "_id";
    String BOULDER_LAT = "boulder_lat";
    String BOULDER_LON = "boulder_lon";
    String BOULDER_NAME = "boulder_name";
    String BOULDER_NAME_RU = "boulder_name_ru";

    // Side table
    String SIDE_ID = "_id";
    String SIDE_PHOTO = "side_photo";

    // Problem table
    String Z_FRIEND_HELP = "ZFRIENDHELP";
    String Z_PAD_COUNT = "ZPADCOUNT";
    String Z_WARNING_LEVEL = "ZWARNINGLEVEL";
    String Z_PROBLEM_PHOTO = "ZPROBLEMPHOTO";
    String Z_PROBLEM_DESC = "ZPROBLEMDESC";
    String Z_PROBLEM_GRADE = "ZPROBLEMGRADE";
    String Z_PROBLEM_ID = "ZPROBLEMID";
    String Z_PROBLEM_LETTER = "ZPROBLEMLETTER";
    String Z_PROBLEM_NAME = "ZPROBLEMNAME";

    // Photo table
    String PHOTO_ID = "_id";
    String PHOTO_DATA = "ZPHOTODATA";

    // DB name and version
    String TRIANGULAR_LAKE_DB = "triangular_lake_db";
    int TRIANGULAR_LAKE_DB_VERSION = 1;

    // Constants for loaders
    int LIETLAHTI_LOADER_NUMBER = 0;
    int REGION_LOADER_NUMBER = 1;
}
