package ru.otus.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.example.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository("authorDao")
public class JdbcAuthorDao implements AuthorDao {

    private final JdbcOperations jdbcOperations;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Autowired
    public JdbcAuthorDao(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.jdbcOperations = namedParameterJdbcOperations.getJdbcOperations();
    }

    @Override
    public List<Author> findAllAuthors() {
        return jdbcOperations.query("select author_id, full_name from Authors", new AuthorMapper());
    }

    @Override
    public Optional<Author> findById(long id) {
        Author author;
        try {
            var params = new MapSqlParameterSource();
            params.addValue("authorId", id);
            author = namedParameterJdbcOperations.queryForObject("select author_id, full_name " +
                            "from Authors " +
                            "where author_id = :authorId",
                             params, new AuthorMapper());
        } catch (EmptyResultDataAccessException e) {
            author = null;
        }
        return Optional.ofNullable(author);
    }


    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long authorId = rs.getLong("author_id");
            String authorName = rs.getString("full_name");
            return new Author(authorId, authorName);
        }
    }
}
