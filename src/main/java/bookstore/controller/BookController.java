package bookstore.controller;

import bookstore.dto.BookDto;
import bookstore.dto.CreateBookRequestDto;
import bookstore.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping
    public BookDto createBook(@RequestBody CreateBookRequestDto createBookRequestDto) {
        return bookService.createBook(createBookRequestDto);
    }

    @GetMapping("/{id}")
    public BookDto findByid(@PathVariable Long id) {
        return bookService.getBookById(id);
    }
}
