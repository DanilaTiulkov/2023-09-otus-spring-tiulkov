package ru.otus.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.example.mapper.BookMapper;
import ru.otus.example.models.Book;


@Repository
public class BookDaoCustomImpl implements BookDaoCustom {

    private final R2dbcEntityTemplate template;

    private final BookMapper bookMapper;

    @Autowired
    public BookDaoCustomImpl(R2dbcEntityTemplate template, BookMapper bookMapper) {
        this.template = template;
        this.bookMapper = bookMapper;
    }

    public Flux<Book> findAll() {
        String sqlAll = "select * from Books as b " +
                "inner join Authors as a on b.author_id = a.author_id " +
                "inner join Genres as g on b.genre_id = g.genre_id";
        return template.getDatabaseClient().sql(sqlAll).map(bookMapper::toModel).all();
    }

    public Mono<Book> findById(long id) {
        String sqlBookById = "select * from Books as b " +
                "inner join Authors as a on b.author_id = a.author_id " +
                "inner join Genres as g on b.genre_id = g.genre_id where b.book_id = " + id;
        return template.getDatabaseClient().sql(sqlBookById).map(bookMapper::toModel).first();
    }

    public Mono<Book> save(Book book) {
        if (book.getBookId() == 0) {
           return insert(book);
        }
        return update(book);
    }

    private Mono<Book> insert(Book book) {
        String sqlInsert = "insert into Books(title, author_id, genre_id) values('%s', %d, %d)"
                .formatted(book.getTitle(),
                        book.getAuthor().getAuthorId(), book.getGenre().getGenreId());
        return template.getDatabaseClient().sql(sqlInsert).map(bookMapper::toModel).first();
    }

    private Mono<Book> update(Book book) {
        String sqlUpdate = "update Books set title = '%s', author_id = %d, genre_id = %d where book_id = %d"
                .formatted(book.getTitle(),
                        book.getAuthor().getAuthorId(), book.getGenre().getGenreId(), book.getBookId());
        return template.getDatabaseClient().sql(sqlUpdate).map(bookMapper::toModel).first();
    }

    public Mono<Void> deleteById(long id) {
        String sqlDelete = "delete from Books where book_id = %d"
                .formatted(id);
        return template.getDatabaseClient().sql(sqlDelete).then();
    }
}
