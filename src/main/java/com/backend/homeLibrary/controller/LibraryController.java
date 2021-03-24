package com.backend.homeLibrary.controller;

import com.backend.homeLibrary.comparator.CustomBookComparator;
import com.backend.homeLibrary.enumeration.SortTypeEnum;
import com.backend.homeLibrary.model.Book;
import com.backend.homeLibrary.service.BookService;
import com.backend.homeLibrary.util.ComparatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// 4. Контроллер. Обрабатывает клиентские запросы и возвращает результат. Описывает точку входа.(????)
@RestController
@RequestMapping("/book")
public class LibraryController {

    @Autowired
    private BookService bookService;

    /* Добавление книги в БД. Запрос начинается с "/add" (после "/book"), на вход подаются данные,
     * обязательными являются только название книги bookName и номер полки shelf*/
    @PostMapping("/add")
    public String insertBook(@RequestParam(value = "bookName", defaultValue = "Name") String bookName,
                             @RequestParam(value = "authorName", required = false) String authorName,
                             @RequestParam(value = "date", required = false) Integer date,
                             @RequestParam(value = "rate", required = false) Integer rate,
                             @RequestParam(value = "comment", required = false) String comment,
                             @RequestParam(value = "shelf") Integer shelf) {

        //вызываем метод insertBook из bookService и передаем поступившие на вход значения
        bookService.insertBook(bookName, authorName, date, rate, comment, shelf);
        /**
         * сделать так, чтобы возвращалась не строка, а строка и объект
         */
        return "Book added!";
    }

    /* Поиск книги/книг по БД. Запрос начинается с "/search" (после "/book"), на вход подаются любые данные по выбору
     * можно искать по любому из параметров, либо сразу по нескольким, а также выбрать вариант сортировки sortBy (по
     * названию книги, автору, оценке или номеру полки, по умолчанию идет сортировка по названию книги). Если не указать
     * параметры, будут выведены все книги, хранящиеся в библиотетеке книги. Возвращает одну книгу, либо список всех
     * книг, подходящих под указанные параметры.*/
    @GetMapping("/search")
    public List<Book> libraryByAll(@RequestParam(value = "bookName", required = false) String bookName,
                                   @RequestParam(value = "authorName", required = false) String authorName,
                                   @RequestParam(value = "date", required = false) Integer date,
                                   @RequestParam(value = "rate", required = false) Integer rate,
                                   @RequestParam(value = "comment", required = false) String comment,
                                   @RequestParam(value = "shelf", required = false) Integer shelf,
                                   @RequestParam(value = "sortBy", required = false) SortTypeEnum sort) {

        //получаем необходимый компаратор для сортировки
        CustomBookComparator customBookComparator = ComparatorUtil.getComparator(sort);

        List<Book> books = bookService.getBooksByAll(); //получаем список всех книги в библиотетеке

        //фильтруем книги по каждому параметру
        List<Book> booksCollection = books.stream()
                .filter(book -> {    //поступает одна книга из коллекции books
                    if (bookName == null) {  //проверяем поступило ли название книги
                        return true; //если bookName пустой, пропускаем через фильтр каждую книгу, которая будет
                        //поступать в фильтр, так как все подходят
                    } else {
                        return book.getBookName().equals(bookName); //проверяем, совпадает ли параметр bookName с тем,
                        //что записано в книге. Если параметр совпадает, книга проходит фильтр.
                    }
                })
                .filter(book -> {
                    if (authorName == null) {
                        return true;
                    } else {
                        return book.getAuthorName().equals(authorName);
                    }
                })
                .filter(book -> {
                    if (date == null) {
                        return true;
                    } else {
                        return book.getDate().equals(date);
                    }
                })
                .filter(book -> {
                    if (rate == null) {
                        return true;
                    } else {
                        return book.getRate().equals(rate);
                    }
                })
                .filter(book -> {
                    if (comment == null) {
                        return true;
                    } else {
                        return book.getComment().equals(comment);
                    }
                })
                .filter(book -> {
                    if (shelf == null) {
                        return true;
                    } else {
                        return book.getShelfNumber().equals(shelf);
                    }
                })
                .sorted(customBookComparator) //сортировка
                .collect(Collectors.toList());   //собираем все подходящие книги в коллекцию
        //.limit(10).collect(Collectors.toList()); - чтобы вернуть только первые 10 книг
        return booksCollection;
    }

    /* Изменение выбранной (по id) книги. На вход передается id книги и любые параметры, которые необходимо изменить
     * Запрос начинается с "/changeBook" (после "/book").*/
    @PutMapping("/changeBook")
    public Book changeBook(@RequestParam(value = "id") Integer id,
                           @RequestParam(value = "bookName", required = false) String bookName,
                           @RequestParam(value = "authorName", required = false) String authorName,
                           @RequestParam(value = "date", required = false) Integer date,
                           @RequestParam(value = "rate", required = false) Integer rate,
                           @RequestParam(value = "comment", required = false) String comment,
                           @RequestParam(value = "shelf", required = false) Integer shelf) {

        Book bookChange = bookService.getBookById(id); //получаем книгу для изменения по ее ID

        //Проверяем, какие данные подаются на вход, если поле не пустое, то устанавливаем этому полю новое имя,
        //которое подается на вход
        if (bookName != null) {
            bookChange.setBookName(bookName);
        }
        if (authorName != null) {
            bookChange.setAuthorName(authorName);
        }
        if (date != null) {
            bookChange.setDate(date);
        }
        if (rate != null) {
            bookChange.setRate(rate);
        }
        if (comment != null) {
            bookChange.setComment(comment);
        }
        if (shelf != null) {
            bookChange.setShelfNumber(shelf);
        }

        bookService.saveBookChanges(bookChange); //сохраняем изменения

        return bookChange;
    }

    /* Удаление выбранной (по id) книги. На вход передается только id книги.
     * Запрос начинается с "/changeBook" (после "/book").*/
    @PutMapping("/deleteBook")
    public String changeBook(@RequestParam(value = "id") Integer id) {

        Book bookDelete = bookService.getBookById(id); //получаем id
        bookService.deleteBook(bookDelete); //удаляем книгу

        return "OK";
    }
}