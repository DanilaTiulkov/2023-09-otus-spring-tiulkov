package ru.otus.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.example.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository("genreDao")
public class JdbcGenreDao implements GenreDao {

    private final JdbcOperations jdbcOperations;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Autowired
    public JdbcGenreDao(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.jdbcOperations = namedParameterJdbcOperations.getJdbcOperations();
    }

    @Override
    public List<Genre> findAll() {
        return jdbcOperations.query("select genre_id, genre_name from Genres", new GenreMapper());
    }

    @Override
    public Optional<Genre> findById(long id) {
        Genre genre;
        try {
            var params = new MapSqlParameterSource();
            params.addValue("genreId", id);
            genre = namedParameterJdbcOperations.queryForObject("select genre_id, genre_name " +
                            "from Genres " +
                            "where genre_id = :genreId",
                             params, new GenreMapper());
        } catch (EmptyResultDataAccessException e) {
            genre = null;
        }
        return Optional.ofNullable(genre);
    }


    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            long genreId = rs.getLong("genre_id");
            String genreName = rs.getString("genre_name");
            return new Genre(genreId, genreName);
        }
    }
}
