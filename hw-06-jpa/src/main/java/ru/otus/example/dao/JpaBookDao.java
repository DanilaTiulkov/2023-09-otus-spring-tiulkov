package ru.otus.example.dao;

import jakarta.persistence.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import ru.otus.example.exceptions.EntityNotFoundException;
import ru.otus.example.models.Book;

import java.util.List;
import java.util.Optional;

@Repository("bookDao")
public class JpaBookDao implements BookDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Optional<Book> findById(long id) {
        Book book;
        try {
            EntityGraph <?> entityGraph = em.getEntityGraph("book-entity-graph");
            TypedQuery<Book> query = em.createQuery("select b " +
                    "from Book b " +
                    "where bookId = :id", Book.class);
            query.setParameter("id", id);
            query.setHint("javax.persistence.fetchgraph",entityGraph);
            book = query.getSingleResult();
        } catch (EmptyResultDataAccessException e) {
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
        try {
            TypedQuery<Integer> query = em.createQuery("select 1 from Book b where bookId = :id", Integer.class);
            query.setParameter("id", book.getBookId());
            query.getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Book not found");
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        var book = em.find(Book.class, id);
        em.remove(book);
    }
}
