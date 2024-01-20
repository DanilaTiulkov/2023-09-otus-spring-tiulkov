package ru.otus.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.example.models.Author;
import ru.otus.example.models.Book;
import ru.otus.example.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository("bookDao")
public class JdbcBookDao implements BookDao {

    private final JdbcOperations jdbcOperations;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Autowired
    public JdbcBookDao(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbcOperations = namedParameterJdbcOperations.getJdbcOperations();
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Optional<Book> findById(long id) {
        Book book;
        try {
            var params = new MapSqlParameterSource();
            params.addValue("bookId", id);
            book = namedParameterJdbcOperations.queryForObject(
                    "select book_id, title, b.author_id, full_name, g.genre_id, genre_name " +
                    "from Books as b " +
                    "inner join Authors as a on b.author_id = a.author_id " +
                    "inner join Genres as g on g.genre_id = b.genre_id " +
                    "where book_id = :bookId", params, new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            book = null;
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        return jdbcOperations.query(
                "select book_id, title, b.author_id, full_name, g.genre_id, genre_name " +
                "from Books as b " +
                "inner join Authors as a on b.author_id = a.author_id " +
                "inner join Genres as g on g.genre_id = b.genre_id", new BookMapper());
    }

    @Override
    public Book save(Book book) {
        if (book.getBookId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        var params = new MapSqlParameterSource();
        params.addValue("bookId", id);
        namedParameterJdbcOperations.update("delete from Books where book_id = :bookId", params);
    }

    private Book insert(Book book) {
        KeyHolder keyholder = new GeneratedKeyHolder();
        var params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getAuthorId());
        params.addValue("genre_id", book.getGenre().getGenreId());
        namedParameterJdbcOperations.update("insert into Books(title, author_id, genre_id) " +
                "values(:title, :author_id, :genre_id)",params, keyholder);
        long key = keyholder.getKey().longValue();
        book.setBookId(keyholder.getKey().longValue());
        return book;
    }

    private Book update(Book book) {
        var params = new MapSqlParameterSource();
        params.addValue("bookId", book.getBookId());
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthor().getAuthorId());
        params.addValue("genreId", book.getGenre().getGenreId());
        namedParameterJdbcOperations.update(
                "IF (select book_id from Books where book_id = :bookId) > 0 " +
                "BEGIN " +
                "update Books " +
                "set title = :title,author_id = :authorId,genre_id = :genreId " +
                "END", params);
        return book;
    }


    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long bookId = rs.getLong("book_id");
            String title = rs.getString("title");
            long authorId = rs.getLong("author_id");
            String authorName = rs.getString("full_name");
            long genreId = rs.getLong("genre_id");
            String genreName = rs.getString("genre_name");
            Author author = new Author(authorId, authorName);
            Genre genre = new Genre(genreId, genreName);
            return new Book(bookId, title, author, genre);
        }
    }
}
