package ru.otus.example.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.example.model.h2.temp.GenreTemp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcGenreTemp implements GenreTempRepository {

    private final JdbcOperations jdbcOperations;


    public JdbcGenreTemp(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<GenreTemp> findAll() {
        return jdbcOperations.query("select id, genre_doc_id, genre_id from Genres_temp", new GenreTempMapper());
    }


    private static class GenreTempMapper implements RowMapper<GenreTemp> {

        @Override
        public GenreTemp mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getLong("id");
            var genreDocId = rs.getString("genre_doc_id");
            var genreId = rs.getLong("genre_id");
            return new GenreTemp(id, genreDocId, genreId);
        }
    }
}
