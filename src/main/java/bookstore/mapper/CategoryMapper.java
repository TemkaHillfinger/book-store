package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.category.CategoryDto;
import bookstore.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface CategoryMapper {
    Category toModel(CategoryDto categoryDto);

    CategoryDto toDto(Category category);
}
