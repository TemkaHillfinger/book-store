package bookstore.repository.book;

import bookstore.model.Book;
import bookstore.repository.spec.SpecificationProvider;
import bookstore.repository.spec.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private static final String CANT_FIND_PROVIDER_FOR_KEY =
            "Can't find correct specification provider for key:";
    private final List<SpecificationProvider<Book>> bookSpecificationProvider;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProvider.stream()
                .filter(p -> p.getKeys().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(CANT_FIND_PROVIDER_FOR_KEY + key));
    }
}
