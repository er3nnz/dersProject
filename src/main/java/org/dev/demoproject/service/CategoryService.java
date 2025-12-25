package org.dev.demoproject.service;

import org.dev.demoproject.entity.Category;
import org.dev.demoproject.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    public Category create(Category category) {
        // basit durum: id null ise kaydet
        category.setId(null);
        return repository.save(category);
    }

    public Category update(Long id, Category updated) {
        Category existing = findById(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        // kitap ilişkisi genelde yönetilmiyor burada; gerektiğinde ek mantık ekleyin
        return repository.save(existing);
    }

    public void delete(Long id) {
        Category existing = findById(id);
        repository.delete(existing);
    }
}

