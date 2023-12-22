package ru.itgirl.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itgirl.libraryproject.model.entity.Book;

public interface BookRepository extends JpaRepository <Book,Long> {
}
