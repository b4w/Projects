package com.photoasgift.constantine.photoasgift.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = ICommonDtoConstants.MAIN_MENUS)
public class MainMenu implements ICommonDtoConstants {

    @DatabaseField(id = true, canBeNull = false, generatedId = false, columnName = ID)
    private String id;                                // id пункта меню

    @DatabaseField(columnName = NAME)
    private String name;                              // название пункта меню

    @DatabaseField(columnName = NAME_RU)
    private String nameRu;                            // название пункта меню на русском

    @DatabaseField(columnName = PATH_PHOTO)
    private String pathPhoto;                         // путь к фотографии пункта меню

    public MainMenu() {
        // need for OrmLite
    }

    public MainMenu(String id, String name, String nameRu, String pathPhoto) {
        this.id = id;
        this.name = name;
        this.nameRu = nameRu;
        this.pathPhoto = pathPhoto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getPathPhoto() {
        return pathPhoto;
    }

    public void setPathPhoto(String pathPhoto) {
        this.pathPhoto = pathPhoto;
    }
}
