package bookstore.repository.spec;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    Specification<T> getSpecification(String [] params);

    String getKeys();
}
