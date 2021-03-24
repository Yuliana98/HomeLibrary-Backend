package com.backend.homeLibrary.service;

import com.backend.homeLibrary.db.BookRepository;
import com.backend.homeLibrary.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//3. Класс-сервис. Использование в клиенте (сервисе) нового интерфейса для операций с данными
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    //метод для получения всех книг, хранящихся в БД
    public List<Book> getBooksByAll() {

        List<Book> books = bookRepository.findAll();
        /**
         * написать что-то, если список пуст
         */
        return books;
    }

    //метод для получение книги по ее ID
    public Book getBookById(Integer id) {

        Optional<Book> book = bookRepository.findById(id);

        return book.orElse(null); //book.isPresent() ? book.get() : null;
    }

    //метод для сохранения изменений
    public void saveBookChanges(Book book) {

        bookRepository.save(book);
    }

    //метод для удаления книги
    public void deleteBook(Book book) {

        bookRepository.delete(book);
    }

    //метод, добавляющий книгу в БД. Ипользуем петтер builder, который генерируется для этого класса через lombok
    public void insertBook(String bookName, String authorName, Integer date, Integer rate, String comment, Integer shelf) {

        //создание объекта и занесение результата в переменную book
        Book book = Book.builder()
                .bookName(bookName)
                .authorName(authorName)
                .date(date)
                .rate(rate)
                .comment(comment)
                .shelfNumber(shelf)
                .build();

        bookRepository.save(book);
    }
}
