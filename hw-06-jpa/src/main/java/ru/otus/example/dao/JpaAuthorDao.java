package ru.otus.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.example.models.Author;
import java.util.List;
import java.util.Optional;

@Repository("authorDao")
public class JpaAuthorDao implements AuthorDao {

    @PersistenceContext
    private final EntityManager em;

    public JpaAuthorDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Author> findAllAuthors() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public Optional<Author> findById(long id) {
        Author author;
        try {
            author = em.find(Author.class, id);
        } catch (NoResultException e) {
            author = null;
        }
        return Optional.ofNullable(author);
    }
}
