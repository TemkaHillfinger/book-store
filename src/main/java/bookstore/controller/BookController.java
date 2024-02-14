package bookstore.controller;

import bookstore.dto.BookDto;
import bookstore.dto.BookSearchParameters;
import bookstore.dto.CreateBookRequestDto;
import bookstore.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/books")
@RestController
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDto createBook(@RequestBody CreateBookRequestDto createBookRequestDto) {
        return bookService.createBook(createBookRequestDto);
    }

    @GetMapping("/{id}")
    public BookDto findByid(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PostMapping("/{id}")
    public BookDto updateBook(@RequestBody CreateBookRequestDto bookDto, @PathVariable Long id) {
        return bookService.updateBook(bookDto, id);
    }

    @GetMapping("/search")
    public List<BookDto> search(BookSearchParameters bookSearchParameters) {
        return bookService.search(bookSearchParameters);
    }
}
