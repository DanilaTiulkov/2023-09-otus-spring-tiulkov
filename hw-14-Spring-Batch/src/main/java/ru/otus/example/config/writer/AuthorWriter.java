package ru.otus.example.config.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ru.otus.example.cache.AuthorCache;
import ru.otus.example.model.dto.AuthorDto;

import java.util.List;
import java.util.Map;


public class AuthorWriter implements ItemWriter<AuthorDto> {


    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private final AuthorCache authorCache;


    public AuthorWriter(NamedParameterJdbcOperations namedParameterJdbcOperations, AuthorCache authorCache) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.authorCache = authorCache;
    }

    @Override
    public void write(Chunk<? extends AuthorDto> chunk) throws Exception {
        var keyHolder = new GeneratedKeyHolder();
        var parameters = SqlParameterSourceUtils.createBatch(chunk.getItems());
        long authorId;
        namedParameterJdbcOperations.batchUpdate("insert into Authors(full_Name) values(:fullName)",
                parameters, keyHolder, new String[]{"author_id"});
        List<Map<String, Object>> keys = keyHolder.getKeyList();
        for (int i = 0; i < keys.size(); i++) {
            authorId = (Long)keys.get(i).get("AUTHOR_ID");
            chunk.getItems().get(i).setAuthorId(authorId);
        }
        chunk.forEach(authorDto -> authorCache.addAuthorIds(authorDto.getAuthorDocId(), authorDto.getAuthorId()));
    }
}
