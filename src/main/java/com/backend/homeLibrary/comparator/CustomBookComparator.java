package com.backend.homeLibrary.comparator;

import com.backend.homeLibrary.model.Book;

import java.util.Comparator;

/*Интерфейс CustomBookComparator позволяет создавать объекты, которые будут управлять процессом нашей сортировки.
 *Объединяет все компараторы*/
public interface CustomBookComparator extends Comparator<Book> {

}
