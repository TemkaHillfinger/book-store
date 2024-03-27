package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.BookDto;
import bookstore.dto.BookDtoWithoutCategoryIds;
import bookstore.dto.CreateBookRequestDto;
import bookstore.model.Book;
import bookstore.model.Category;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);

    BookDto toDto(Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    List<BookDtoWithoutCategoryIds> toDtosWithoutCategories(List<Book> books);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        List<Long> ids = book.getCategories().stream()
                .map(Category::getId)
                .toList();
        bookDto.setCategoryIds(ids);
    }
}
