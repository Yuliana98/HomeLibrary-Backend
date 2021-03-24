package com.backend.homeLibrary.model;

import lombok.*;
import javax.persistence.*;

//1. Создание класса-сущности
@Getter
@Setter
@Builder
@NoArgsConstructor //пустой конструктор
@AllArgsConstructor //конструктор
@Entity //помечает наш класс для проекта (это объект, который хранится в бд)
@Table(name = "library") //создается таблица с именем library
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //логика генерации id для БД
    private Integer id;

    @Column(name = "BOOK_NAME")
    private String bookName;

    @Column(name = "AUTHOR_NAME")
    private String authorName;

    @Column(name = "DATE")
    private Integer date;

    @Column(name = "RATE")
    private Integer rate;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "SHELF")
    private Integer shelfNumber;
}