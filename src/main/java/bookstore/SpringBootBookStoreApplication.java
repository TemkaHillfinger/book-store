package bookstore;

import bookstore.model.Book;
import bookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootBookStoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book theLordOfTheRings = new Book();
            theLordOfTheRings.setTitle("The Lord of the Rings ");
            theLordOfTheRings.setAuthor("John Ronald Reuel Tolkien");
            theLordOfTheRings.setIsbn("978-88-452-9261-3");
            theLordOfTheRings.setPrice(BigDecimal.valueOf(600));
            bookService.save(theLordOfTheRings);
            System.out.println(bookService.findAll());
        };
    }
}
