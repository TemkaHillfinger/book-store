package bookstore.repository.book;

import bookstore.model.Book;
import bookstore.repository.spec.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    private static final String TITLE = "title";

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return ((root, query, criteriaBuilder) -> root.get(TITLE)
                .in(Arrays.stream(params).toArray()));
    }

    @Override
    public String getKeys() {
        return TITLE;
    }
}
