package bookstore.repository.book;

import bookstore.dto.BookSearchParameters;
import bookstore.model.Book;
import bookstore.repository.spec.SpecificationBuilder;
import bookstore.repository.spec.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters bookSearchParameters) {
        Specification<Book> spec = Specification.where(null);
        if (bookSearchParameters.author() != null && bookSearchParameters.author().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(bookSearchParameters.author()));
        }
        if (bookSearchParameters.price() != null && bookSearchParameters.price().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider("price")
                    .getSpecification(bookSearchParameters.price()));
        }
        if (bookSearchParameters.title() != null && bookSearchParameters.title().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(bookSearchParameters.title()));
        }
        if (bookSearchParameters.isbn() != null && bookSearchParameters.isbn().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider("isbn")
                    .getSpecification(bookSearchParameters.isbn()));
        }
        return spec;
    }
}
