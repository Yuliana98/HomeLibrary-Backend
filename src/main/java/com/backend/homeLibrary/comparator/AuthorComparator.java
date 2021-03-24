package com.backend.homeLibrary.comparator;

import com.backend.homeLibrary.model.Book;

//Класс компаратор сортировки по имени автора (затем по названию книги, номера полки и рейтингу),
//наследуется от интерфейса CustomBookComparator (объект интерфейса CustomBookComparator)
public class AuthorComparator implements CustomBookComparator {
    @Override
    public int compare(Book o1, Book o2) {
        if (o1.getAuthorName().compareTo(o2.getAuthorName()) == 0) {
            if (o1.getBookName().compareTo(o2.getBookName()) == 0) {
                if (o1.getShelfNumber().compareTo(o2.getShelfNumber()) == 0) {
                    return o2.getRate().compareTo(o1.getRate());
                } else {
                    return o1.getShelfNumber().compareTo(o2.getShelfNumber());
                }
            } else {
                return o1.getBookName().compareTo(o2.getBookName());
            }
        } else {
            return o1.getAuthorName().compareTo(o2.getAuthorName());
        }
    }
}
