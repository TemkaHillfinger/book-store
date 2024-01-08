package bookstore.repository.impl;

import bookstore.exeption.EntityNotFoundException;
import bookstore.model.Book;
import bookstore.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    @Override
    public Book createBook(Book book) {
        Session session = null;
        Transaction transaction = null;
        try {
            sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(book);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t save book to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Book", Book.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can`t get list of books from DB", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Book> bookQueue = session.createQuery("FROM Book b WHERE b.id = :id", Book.class);
            bookQueue.setParameter("id", id);
            return Optional.ofNullable(bookQueue.getSingleResult());
        } catch (Exception e) {
            throw new EntityNotFoundException("Can`t find book by id" + id, e);
        }
    }
}
