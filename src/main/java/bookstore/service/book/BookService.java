package bookstore.service.book;

import bookstore.dto.BookDto;
//import bookstore.dto.BookDtoWithoutCategoryIds;
import bookstore.dto.BookDtoWithoutCategoryIds;
import bookstore.dto.BookSearchParameters;
import bookstore.dto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto createBook(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto getBookById(Long id);

    void deleteById(Long id);

    BookDto updateBook(CreateBookRequestDto bookDto, Long id);

    List<BookDto> search(BookSearchParameters searchParameters);

    List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long id);

}
