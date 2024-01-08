package ru.otus.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.example.models.Genre;
import java.util.List;
import java.util.Optional;

@Repository("genreDao")
public class JpaGenreDao implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public Optional<Genre> findById(long id) {
        Genre genre;
        try {
            TypedQuery<Genre> query = em.createQuery("select g " +
                    "from Genre g " +
                    "where g.genreId = :id", Genre.class);
            query.setParameter("id", id);
            genre = query.getSingleResult();
        } catch (NoResultException e) {
            genre = null;
        }
        return Optional.ofNullable(genre);
    }
}
