package com.triangularlake.constantine.triangularlake.pojo;

public class FavouriteProblemDTO {
    // TODO: Оставил DTO без GET & SET для увеличения скорости доступа к полям.
    // Может и не правильно. Надо подумать.
    // + id поставил для адаптера, что бы удобно было строить view

    public Long id;          // id проблемы
    public String name;      // название проблемы
    public String nameRu;    // название на русском проблемы
    public String grade;     // категория выбранной проблемы

    public FavouriteProblemDTO(Long id, String name, String nameRu, String grade) {
        this.id = id;
        this.name = name;
        this.nameRu = nameRu;
        this.grade = grade;
    }
}