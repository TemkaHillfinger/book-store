package bookstore.repository.book;

import bookstore.repository.spec.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PriceSpecificationProvider implements SpecificationProvider {
    private static final String PRICE = "price";

    @Override
    public Specification getSpecification(String[] params) {
        return ((root, query, criteriaBuilder) -> root.get(PRICE)
                .in(Arrays.stream(params).toArray()));
    }

    @Override
    public String getKeys() {
        return PRICE;
    }
}
