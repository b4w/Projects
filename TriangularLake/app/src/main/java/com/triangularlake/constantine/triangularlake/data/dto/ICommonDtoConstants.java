package com.triangularlake.constantine.triangularlake.data.dto;

public interface ICommonDtoConstants {

    // Common
    String ID = "_id";
    String BOULDER_ID = "boulder_id";
    String SECTOR_ID = "sector_id";
    String BOULDER_NUMBERS = "boulder_numbers";
    String BOULDER_NAMES = "boulder_names";
    String SIDE_ID = "side_id";
    String PROBLEM_NUMBERS = "problem_numbers";
    String PROBLEM_ID = "problem_id";
    String SECTOR_DESCRIPTION = "sector_description";

    // REGION table
    String REGION_TABLE_NAME = "REGIONS";
    String REGION_LAT = "region_lat";
    String REGION_LON = "region_lon";
    String REGION_NAME = "region_name";
    String REGION_NAME_RU = "region_name_ru";
    String REGION_PHOTO = "region_photo";

    // SECTOR table
    String SECTOR_TABLE_NAME = "SECTORS";
    String PROBLEM_COUNT = "problem_count";
    String MAX_GRADE = "max_grade";
    String MIN_GRADE = "min_grade";
    String SECTOR_DESC = "sector_desc";
    String SECTOR_DESC_RU = "sector_desc_ru";
    String SECTOR_LAT = "sector_lat";
    String SECTOR_LON = "sector_lon";
    String SECTOR_NAME = "sector_name";
    String SECTOR_NAME_RU = "sector_name_ru";
    String SECTOR_PHOTO = "sector_photo";

    // Boulder table
    String BOULDER_TABLE_NAME = "BOULDERS";
    String BOULDER_DESC = "boulder_desc";
    String BOULDER_DESC_RU = "boulder_desc_ru";
    String BOULDER_LAT = "boulder_lat";
    String BOULDER_LON = "boulder_lon";
    String BOULDER_NAME = "boulder_name";
    String BOULDER_NAME_RU = "boulder_name_ru";

    // Side table
    String SIDE_TABLE_NAME = "SIDES";
    String SIDE_PHOTO = "side_photo";

    // Problem table
    String PROBLEM_TABLE_NAME = "PROBLEMS";
    String FRIEND_HELP = "friend_help";
    String PAD_COUNT = "pad_count";
    String WARNING_LEVEL = "warning_level";
    String PROBLEM_DESC = "problem_desc";
    String PROBLEM_DESC_RU = "problem_desc_ru";
    String PROBLEM_GRADE = "problem_grade";
    String PROBLEM_LETTER = "problem_letter";
    String PROBLEM_NAME = "problem_name";
    String PROBLEM_NAME_RU = "problem_name_ru";

    // Photo table
    String PHOTO_TABLE_NAME = "PHOTOS";
    String PHOTO_DATA = "photo_data";

    // DB name and version
    String TRIANGULAR_LAKE_DB = "triangular_lake_db";
    int TRIANGULAR_LAKE_DB_VERSION = 1;

    // Constants for loaders
    int SECTOR_LOADER_NUMBER = 0;
    int REGION_LOADER_NUMBER = 1;
    int PROBLEM_LOADER_NUMBER = 2;
}
