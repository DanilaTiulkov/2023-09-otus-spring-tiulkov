package ru.otus.example.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.example.model.h2.temp.AuthorTemp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcAuthorTemp implements AuthorTempRepository {

    private final JdbcOperations jdbcOperations;


    public JdbcAuthorTemp(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<AuthorTemp> findAll() {
        return jdbcOperations.query("select id, author_doc_id, author_id from Authors_temp", new AuthorTempMapper());
    }


    private static class AuthorTempMapper implements RowMapper<AuthorTemp> {

        @Override
        public AuthorTemp mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getLong("id");
            var authorDocId = rs.getString("author_doc_id");
            var authorId = rs.getLong("author_id");
            return new AuthorTemp(id, authorDocId, authorId);
        }
    }
}
