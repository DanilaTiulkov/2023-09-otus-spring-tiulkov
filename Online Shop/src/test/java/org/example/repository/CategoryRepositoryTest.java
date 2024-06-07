package org.example.repository;

import org.example.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager em;

    List<Category> dbCategories;

    @BeforeEach
    public void init() {
        dbCategories = getCategories();
    }

    @Test
    @DisplayName("Поиск всех Категорий")
    public void shouldReturnCategoriesList() {
        var expectedCategories = getCategories();
        var actualCategories = categoryRepository.findAll();
        assertThat(actualCategories).containsExactlyElementsOf(expectedCategories);
    }


    public List<Category> getCategories() {
        return List.of(em.find(Category.class, 1),
                em.find(Category.class, 2),
                em.find(Category.class, 3));
    }
}
