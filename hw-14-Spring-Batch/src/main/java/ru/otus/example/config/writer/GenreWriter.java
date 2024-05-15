package ru.otus.example.config.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ru.otus.example.model.dto.GenreDto;

public class GenreWriter implements ItemWriter<GenreDto> {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreWriter(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public void write(Chunk<? extends GenreDto> chunk) throws Exception {
        var keyHolder = new GeneratedKeyHolder();
        var parameters = new MapSqlParameterSource();
        long genreId;
        for (int i = 0; i < chunk.size(); i++) {
            var genreDto = chunk.getItems().get(i);
            parameters.addValue("genreName", genreDto.getGenreName());
            var insertedRows = namedParameterJdbcOperations
                    .update("insert into Genres(genre_name) values(:genreName)", parameters, keyHolder);
            if (insertedRows != 0) {
                genreId = keyHolder.getKey().longValue();
                chunk.getItems().get(i).setGenreId(genreId);
            }
        }
    }
}
