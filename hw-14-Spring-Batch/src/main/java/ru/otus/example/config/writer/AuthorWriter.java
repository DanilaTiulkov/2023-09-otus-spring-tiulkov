package ru.otus.example.config.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ru.otus.example.model.dto.AuthorDto;


public class AuthorWriter implements ItemWriter<AuthorDto> {


    private final NamedParameterJdbcOperations namedParameterJdbcOperations;


    public AuthorWriter(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public void write(Chunk<? extends AuthorDto> chunk) throws Exception {
        var keyHolder = new GeneratedKeyHolder();
        var parameters = new MapSqlParameterSource();
        long authorId;
        for (int i = 0; i < chunk.size(); i++) {
            var authorDto = chunk.getItems().get(i);
            parameters.addValue("fullName", authorDto.getFullName());
            var insertedRows = namedParameterJdbcOperations
                    .update("insert into Authors(full_Name) values(:fullName)", parameters, keyHolder);
            if (insertedRows != 0) {
                authorId = keyHolder.getKey().longValue();
                chunk.getItems().get(i).setAuthorId(authorId);
            }
        }
    }
}
