package com.globaroman.bookshopproject.repository.impl;

import com.globaroman.bookshopproject.exception.DataProcessingException;
import com.globaroman.bookshopproject.model.Book;
import com.globaroman.bookshopproject.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    @Autowired
    private final SessionFactory sessionFactory;

    @Override
    public Book save(Book book) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw new DataProcessingException("Can't create book" + book, e);
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Book> getOrder = session.createQuery("FROM Book ", Book.class);
            return getOrder.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Error. No books found", e);
        }
    }
}
