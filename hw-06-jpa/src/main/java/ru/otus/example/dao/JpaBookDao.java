package ru.otus.example.dao;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import ru.otus.example.models.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("bookDao")
public class JpaBookDao implements BookDao {

    @PersistenceContext
    private final EntityManager em;

    public JpaBookDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Book> findById(long id) {
        Book book;
        try {
            EntityGraph <?> entityGraph = em.getEntityGraph("book-entity-graph");
            Map<String,Object> properties = new HashMap<>();
            properties.put("javax.persistence.fetchgraph",entityGraph);
            book = em.find(Book.class, id, properties);
        } catch (NoResultException e) {
            book = null;
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b ", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getBookId() == 0) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        var book = em.find(Book.class, id);
        em.remove(book);
    }
}
