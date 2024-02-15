package ru.otus.example.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.example.dao.AuthorDao;
import ru.otus.example.dao.BookDao;
import ru.otus.example.dao.CommentDao;
import ru.otus.example.dao.GenreDao;
import ru.otus.example.models.Author;
import ru.otus.example.models.Book;
import ru.otus.example.models.Comment;
import ru.otus.example.models.Genre;

@ChangeLog
public class InitDB {

    private Author authorIvan;

    private Author authorIlya;

    private Author authorMikhail;

    private Genre genreFan;

    private Genre genreAdv;

    private Genre genreHor;

    private Book firstBook;

    private Book secondBook;

    private Book thirdBook;

    private Book fourthBook;

    @ChangeSet(order = "001", id = "DropDB", author = "DanilaTiulkov", runAlways = true)
    public void dropDB(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "InsertAuthors", author = "DanilaTiulkov", runAlways = true)
    public void insertAuthors(AuthorDao authorDao) {
         authorIvan = authorDao.save(new Author(null, "Ivan Sergeevich"));
         authorIlya = authorDao.save(new Author(null, "Ilya Abramov"));
         authorMikhail = authorDao.save(new Author(null, "Mikhail Andreevich"));
    }

    @ChangeSet(order = "003", id = "InsertGenres", author = "DanilaTiulkov", runAlways = true)
    public void insertGenres(GenreDao genreDao) {
        genreFan = genreDao.save(new Genre(null, "Fantastic"));
        genreAdv = genreDao.save(new Genre(null, "Adventure"));
        genreHor = genreDao.save(new Genre(null, "Horror"));
    }

    @ChangeSet(order = "004", id = "InsertBooks", author = "DanilaTiulkov", runAlways = true)
    public void insertBooks(BookDao bookDao) {
        firstBook = bookDao.save(new Book(null, "Three Planets", authorIvan, genreFan));
        secondBook = bookDao.save(new Book(null, "In search of the lost", authorIlya, genreAdv));
        thirdBook = bookDao.save(new Book(null, "Behind a closed door", authorMikhail, genreHor));
        fourthBook = bookDao.save(new Book(null, "Martians", authorIvan, genreFan));
    }

    @ChangeSet(order = "005", id = "InsertComments", author = "DanilaTiulkov", runAlways = true)
    public void insertComments(CommentDao commentDao) {
        commentDao.save(new Comment(null, "Не понравилось", firstBook));
        commentDao.save(new Comment(null, "Понравилось", secondBook));
        commentDao.save(new Comment(null, "Я очень испугался", thirdBook));
    }
}
