package com.aditya_shrikhande.electronic_store.services.impl;

import com.aditya_shrikhande.electronic_store.exceptions.ResourceNotFoundException;
import com.aditya_shrikhande.electronic_store.repositories.CategoryRepository;
import com.aditya_shrikhande.electronic_store.dtos.CategoryDto;
import com.aditya_shrikhande.electronic_store.dtos.PageableResponse;
import com.aditya_shrikhande.electronic_store.entities.Category;
import com.aditya_shrikhande.electronic_store.helper.Helper;
import com.aditya_shrikhande.electronic_store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    /**
     * Create a new category.
     *
     * @param categoryDto The CategoryDto object containing category information.
     * @return The created CategoryDto.
     */
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        String categoryID = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryID);

        Category category = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return mapper.map(savedCategory, CategoryDto.class);
    }

    /**
     * Update an existing category.
     *
     * @param categoryDto The updated CategoryDto.
     * @param categoryId  The ID of the category to update.
     * @return The updated CategoryDto.
     */
    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with given ID"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(category.getCoverImage());

        Category updatedCategory = categoryRepository.save(category);

        return mapper.map(updatedCategory, CategoryDto.class);
    }

    /**
     * Delete a category by ID.
     *
     * @param categoryId The ID of the category to delete.
     */
    @Override
    public void delete(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with given ID"));
        categoryRepository.delete(category);
    }

    /**
     * Get a paginated list of categories.
     *
     * @param pageNumber The page number to retrieve.
     * @param pageSize   The number of categories per page.
     * @param sortBy     The field to sort by.
     * @param sortDir    The sort direction (asc or desc).
     * @return A PageableResponse containing a list of CategoryDto objects.
     */
    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);

        return pageableResponse;
    }

    /**
     * Get a category by ID.
     *
     * @param categoryId The ID of the category to retrieve.
     * @return The CategoryDto for the specified ID.
     */
    @Override
    public CategoryDto get(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with given ID"));
        return mapper.map(category, CategoryDto.class);
    }
}
