package bookstore.service;

import bookstore.dto.BookDto;
import bookstore.dto.BookSearchParameters;
import bookstore.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto createBook(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll();

    BookDto getBookById(Long id);

    void deleteById(Long id);

    BookDto updateBook(CreateBookRequestDto bookDto, Long id);

    List<BookDto> search(BookSearchParameters searchParameters);
}
