package ru.otus.example.config.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ru.otus.example.cache.GenreCache;
import ru.otus.example.model.dto.GenreDto;

import java.util.List;
import java.util.Map;

public class GenreWriter implements ItemWriter<GenreDto> {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private final GenreCache genreCache;


    public GenreWriter(NamedParameterJdbcOperations namedParameterJdbcOperations, GenreCache genreCache) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.genreCache = genreCache;
    }

    @Override
    public void write(Chunk<? extends GenreDto> chunk) throws Exception {
        var keyHolder = new GeneratedKeyHolder();
        var parameters = SqlParameterSourceUtils.createBatch(chunk.getItems());
        long genreId;
        namedParameterJdbcOperations.batchUpdate("insert into Genres(genre_name) values(:genreName)",
                parameters, keyHolder, new String[]{"genre_id"});
        List<Map<String, Object>> keys = keyHolder.getKeyList();
        for (int i = 0; i < keys.size(); i++) {
            genreId = (Long)keys.get(i).get("GENRE_ID");
            chunk.getItems().get(i).setGenreId(genreId);
        }
        chunk.forEach(genreDto -> genreCache.addGenreIds(genreDto.getGenreDocId(), genreDto.getGenreId()));
    }
}
