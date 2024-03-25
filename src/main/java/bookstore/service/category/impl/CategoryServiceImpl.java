package bookstore.service.category.impl;

import bookstore.dto.category.CategoryDto;
import bookstore.dto.category.CreateCategoryRequestDto;
import bookstore.service.category.CategoryService;
import java.util.List;
import org.springframework.data.domain.Pageable;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public CategoryDto getById(Long id) {
        return null;
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto categoryDto) {
        return null;
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto categoryDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
