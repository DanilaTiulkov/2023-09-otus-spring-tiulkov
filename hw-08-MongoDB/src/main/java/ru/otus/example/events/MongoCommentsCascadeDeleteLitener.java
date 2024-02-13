package ru.otus.example.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.example.dao.CommentDao;
import ru.otus.example.models.Book;

@Component
public class MongoCommentsCascadeDeleteLitener extends AbstractMongoEventListener<Book> {
    private final CommentDao commentDao;

    @Autowired
    public MongoCommentsCascadeDeleteLitener(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        var source = event.getSource();
        var id = source.get("_id").toString();
        commentDao.deleteByBookBookId(id);
    }
}
