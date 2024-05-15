package ru.otus.example.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.example.model.mongo.AuthorDoc;
import ru.otus.example.model.mongo.BookDoc;
import ru.otus.example.model.mongo.GenreDoc;

@ChangeLog
public class InitDB {

    private AuthorDoc authorIvan;

    private AuthorDoc authorIlya;

    private AuthorDoc authorMikhail;

    private GenreDoc genreFan;

    private GenreDoc genreAdv;

    private GenreDoc genreHor;

    private BookDoc firstBook;

    private BookDoc secondBook;

    private BookDoc thirdBook;

    private BookDoc fourthBook;

    @ChangeSet(order = "001", id = "DropDB", author = "DanilaTiulkov", runAlways = true)
    public void dropDB(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "InsertAuthors", author = "DanilaTiulkov", runAlways = true)
    public void insertAuthors(MongockTemplate template) {
        authorIvan = template.save(new AuthorDoc(null, "Ivan Sergeevich"));
        authorIlya = template.save(new AuthorDoc(null, "Ilya Abramov"));
        authorMikhail = template.save(new AuthorDoc(null, "Mikhail Andreevich"));
    }

    @ChangeSet(order = "003", id = "InsertGenres", author = "DanilaTiulkov", runAlways = true)
    public void insertGenres(MongockTemplate template) {
        genreFan = template.save(new GenreDoc(null, "Fantastic"));
        genreAdv = template.save(new GenreDoc(null, "Adventure"));
        genreHor = template.save(new GenreDoc(null, "Horror"));
    }

    @ChangeSet(order = "004", id = "InsertBooks", author = "DanilaTiulkov", runAlways = true)
    public void insertBooks(MongockTemplate template) {
        firstBook = template.save(new BookDoc(null, "Three Planets", authorIvan, genreFan));
        secondBook = template.save(new BookDoc(null, "In search of the lost", authorIlya, genreAdv));
        thirdBook = template.save(new BookDoc(null, "Behind a closed door", authorMikhail, genreHor));
        fourthBook = template.save(new BookDoc(null, "Martians", authorIvan, genreFan));
    }
}
