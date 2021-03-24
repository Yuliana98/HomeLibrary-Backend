package com.backend.homeLibrary.comparator;

import com.backend.homeLibrary.model.Book;

//Сортировка по рейтингу
public class RateComparator implements CustomBookComparator {
    @Override
    public int compare(Book o1, Book o2) {
        if (o1.getRate().compareTo(o2.getRate()) == 0) {
            if (o1.getBookName().compareTo(o2.getBookName()) == 0) {
                if (o1.getShelfNumber().compareTo(o2.getShelfNumber()) == 0) {
                    return o1.getAuthorName().compareTo(o2.getAuthorName());
                } else {
                    return o1.getShelfNumber().compareTo(o2.getShelfNumber());
                }
            } else {
                return o1.getBookName().compareTo(o2.getBookName()); //сравниваем по книгам
            }
    } else {
            return o2.getRate().compareTo(o1.getRate());
        }
    }
}