package com.backend.homeLibrary.db;

import com.backend.homeLibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//2. Наследование от одного из интерфейсов Spring Data, в нашем случае от JpaRepository
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {  //наследуемся от (зависимость от) JPA репрозитория <класс-сущность, ID>

}
