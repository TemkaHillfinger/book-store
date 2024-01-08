package bookstore.repository;

import bookstore.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book createBook(Book book);

    List<Book> findAll();

    Optional<Book> findById(Long id);
}
