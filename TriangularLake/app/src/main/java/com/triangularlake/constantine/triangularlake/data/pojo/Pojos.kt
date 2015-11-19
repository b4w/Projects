package com.triangularlake.constantine.triangularlake.data.pojo

import android.database.Cursor

data class Boulder(var id: Int, // id
                   var boulderDesc: String?, // описание камня на английском
                   var boulderDescRu: String?, // описание камня на русском
                   var boulderLat: String, // широта координаты камня
                   var boulderLon: String, // долгота координаты камня
                   var boulderName: String, // название на английском
                   var boulderNameRu: String, // название на русском
                   var sectorId: Int, // ссылка manyToOne на Sector
                   var photoId: Int) // ссылка oneToOne на Photo

data class Region(var id: Int, // id региона
                  var regionLat: String, // широта координаты центра региона
                  var regionLon: String, // долгота координаты центра рениона
                  var regionName: String, // название на английском (по умолчанию)
                  var regionNameRu: String, // название на русском
                  var regionPhoto: ByteArray) {// фотография

    fun getCountSectors(): Int = 4; //TODO: заменить на запрос к БД!
}

data class Photo(var id: Int, // id фотографии
                 var photoData: ByteArray, // фото
                 var boulderId: Int, // ссылка manyToOne на Boulder
                 var problemId: Int) // ссылка manyToOne на Problem

data class Problem(var id: Int, // id проблемы
                   var friendHelp: Boolean, // нужна ли помощь страховщика
                   var padCount: Int, // кол-во крэшпадов
                   var problemDesc: String?, // комментарий на английском
                   var problemDescRu: String?, // комментарий на русском
                   var problemGrade: String, // категория
                   var problemLetter: String, // буква трассы
                   var problemName: String, // название на английском
                   var problemNameRu: String, // название на русском
                   var warningLevel: Int, // уровень опасности (от 1 до 3)
                   var photoId: Int, // отношение oneToOne на Photo
                   var boulderId: Int, // ссылка manyToOne на Boulder
                   var sideId: Int, // ссылка manyToOne на Side
                   var favourite: Boolean) { // проблема добавлена в избранное
}

data class Sector(var id: Int, // id сектора
                  var maxGrade: String, // максимальная категория проблемы в секторе
                  var minGrade: String, // минимальная категория проблемы в секторе
                  var sectorPhoto: ByteArray, // фото
                  var regionId: Int, // ссылка manyToOne на Region
                  var sectorDesc: String, // описание сектора на английском
                  var sectorDescRu: String, // описание сектора на русском
                  var sectorLat: String, // широта центра
                  var sectorLon: String, // долгота центра
                  var sectorName: String, // название на английском
                  var sectorNameRu: String, // название на русском
                  var problemCount: Int) // кол-во проблем в секторе


data class Side(var id: Int, // id стороны
                var sidePhoto: ByteArray, // фото
                var boulderId: Int) // ссылка manyToOne на Boulder

data class BoulderProblems(var id: Int,
                           var photo: ByteArray,
                           var name: String,
                           var nameRu: String,
                           var problems: List<Problem>)

/**
 * Возвращает новый экземпляр проблемы из данных курсора.
 */
fun getNewProblemFromCursor(cursor: Cursor): Problem {
    return Problem(cursor.getInt(0),
            cursor.getInt(1) > 0,
            cursor.getInt(2),
            if (cursor.getString(3) != null) cursor.getString(3) else "",
            if (cursor.getString(4) != null) cursor.getString(4) else "",
            cursor.getString(5),
            cursor.getString(6),
            cursor.getString(7),
            cursor.getString(8),
            cursor.getInt(9),
            cursor.getInt(10),
            cursor.getInt(11),
            cursor.getInt(12),
            cursor.getInt(13) > 0);
}

/**
 * Возвращает новый экземпляр Региона из данных курсора.
 */
fun getNewRegionFromCursor(cursor: Cursor): Region {
    return Region(cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getBlob(5))
}

/**
 * Возвращает новый экземпляр Камня из данных курсора.
 */
fun getNewBoulderFromCursor(cursor: Cursor): Boulder {
    return Boulder(cursor.getInt(0),
            if (cursor.getString(1) != null) cursor.getString(1) else "",
            if (cursor.getString(2) != null) cursor.getString(2) else "",
            cursor.getString(3),
            cursor.getString(4),
            cursor.getString(5),
            cursor.getString(6),
            cursor.getInt(7),
            cursor.getInt(8))
}

/**
 * Возвращает новый экземпляр Сектора из данных курсора.
 */
fun getNewSectorFromCursor(cursor: Cursor): Sector {
    return Sector(cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getBlob(3),
            cursor.getInt(4),
            cursor.getString(5),
            cursor.getString(6),
            cursor.getString(7),
            cursor.getString(8),
            cursor.getString(9),
            cursor.getString(10),
            cursor.getInt(11))
}

/**
 * Возвращает новый экземпляр Стороны из данных курсора.
 */
fun getNewSideFromCursor(cursor: Cursor): Side {
    return Side(cursor.getInt(0),
            cursor.getBlob(1),
            cursor.getInt(2))
}

/**
 * Возвращает новый экземпляр Фотографии из данных курсора.
 */
fun getNewPhotoFromCursor(cursor: Cursor): Photo {
    return Photo(cursor.getInt(0),
            cursor.getBlob(1),
            cursor.getInt(2),
            cursor.getInt(3))
}
