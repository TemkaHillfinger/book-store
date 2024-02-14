package bookstore.repository.book;

import bookstore.repository.spec.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider {
    private static final String ISBN = "isbn";

    @Override
    public Specification getSpecification(String[] params) {
        return ((root, query, criteriaBuilder) -> root.get(ISBN)
                .in(Arrays.stream(params).toArray()));
    }

    @Override
    public String getKeys() {
        return ISBN;
    }
}
