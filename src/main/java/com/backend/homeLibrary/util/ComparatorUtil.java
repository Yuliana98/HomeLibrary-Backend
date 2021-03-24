package com.backend.homeLibrary.util;

import com.backend.homeLibrary.comparator.*;
import com.backend.homeLibrary.enumeration.SortTypeEnum;

//Выбор метода сортировки, в зависимости от переданной константы, будет вызываться метод сортировки (компаратор)
public class ComparatorUtil {
    public static CustomBookComparator getComparator(SortTypeEnum sortTypeEnum) {
        if (sortTypeEnum != null) {

            switch (sortTypeEnum) {
                case BOOK:
                    return new BookComparator();
                case RATE:
                    return new RateComparator();
                case SHELF:
                    return new ShelfNumberComparator();
                case AUTHOR:
                    return new AuthorComparator();
            }
        }
        return new BookComparator();
    }
}
