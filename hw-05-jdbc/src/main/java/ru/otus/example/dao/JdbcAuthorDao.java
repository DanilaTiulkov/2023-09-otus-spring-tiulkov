package ru.otus.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.example.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("authorDao")
public class JdbcAuthorDao implements AuthorDao {

    private final JdbcOperations jdbcOperations;

    @Autowired
    public JdbcAuthorDao(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Author> findAllAuthors() {
        return jdbcOperations.query("select author_id, full_name from Authors", new AuthorMapper());
    }

    @Override
    public Optional<Author> findById(long id) {
        Author author;
        try {
            author = jdbcOperations.queryForObject("select author_id, full_name " +
                            "from Authors " +
                            "where author_id = ?",
                    new AuthorMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            author = null;
        }
        return Optional.ofNullable(author);
    }


    private static class AuthorMapper implements RowMapper<Author> { // Почему класс должен быть статическим?

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long authorId = rs.getLong("author_id");
            String authorName = rs.getString("full_name");
            return new Author(authorId, authorName);
        }
    }
}
