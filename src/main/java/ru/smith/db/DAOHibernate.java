/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.smith.db;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import ru.smith.entity.Author;
import ru.smith.entity.Book;
import ru.smith.entity.Genre;
import ru.smith.entity.HibernateUtil;
import ru.smith.entity.Publisher;
import ru.smith.entity.Sessions;
import ru.smith.entity.Users;

/**
 *
 * @author smith
 */
public class DAOHibernate {

    private SessionFactory sessionFactory = null;
    private static DAOHibernate data;

    private DAOHibernate() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static DAOHibernate getInstance() {
        return data == null ? new DAOHibernate() : data;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<Book> getAllBooks(String user) {
        try {
            getSession().beginTransaction();
            List<Book> books = getSession().createCriteria(Book.class).
                    add(Restrictions.eq("users.userName", user))
                    .list();
            getSession().getTransaction().commit();
            return books;
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

    public void addBook(Book book) {
        try {
            getSession().beginTransaction();
            getSession().save(book);
            getSession().getTransaction().commit();
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

    public List<Genre> getAllGenres() {
        try {
            getSession().beginTransaction();
            List<Genre> genres = getSession().createCriteria(Genre.class).list();
            getSession().getTransaction().commit();
            return genres;
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

    public Genre getGenre(String genre) {
        try {
            getSession().beginTransaction();
            List<Genre> genres = getSession().createCriteria(Genre.class)
                    .add(Restrictions.eq("name", genre))
                    .list();
            getSession().getTransaction().commit();
            return genres.get(0);
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

    public boolean isGenre(String genre) {
        try {
            getSession().beginTransaction();
            List<Genre> genres = getSession().createCriteria(Genre.class)
                    .add(Restrictions.eq("name", genre))
                    .list();
            getSession().getTransaction().commit();
            return !(genres.isEmpty());
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

    public void addGenre(Genre genre) {
        try {
            getSession().beginTransaction();
            getSession().save(genre);
            getSession().getTransaction().commit();
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

    public List<Author> getAllAuthors() {
        try {
            getSession().beginTransaction();
            List<Author> authors = getSession().createCriteria(Author.class).list();
            getSession().getTransaction().commit();
            return authors;
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

    public boolean isAuthor(String author) {
        try {
            getSession().beginTransaction();
            List<Author> authors = getSession().createCriteria(Author.class)
                    .add(Restrictions.eq("fio", author))
                    .list();
            getSession().getTransaction().commit();
            return !(authors.isEmpty());
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

    public Author getAuthor(String author) {
        try {
            getSession().beginTransaction();
            List<Author> authors = getSession().createCriteria(Author.class)
                    .add(Restrictions.eq("fio", author))
                    .list();
            getSession().getTransaction().commit();
            return authors.get(0);
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

    public void addAuthor(Author author) {
        try {
            getSession().beginTransaction();
            getSession().save(author);
            getSession().getTransaction().commit();
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

    public List<Publisher> getAllPublishers() {
        try {
            getSession().beginTransaction();
            List<Publisher> publishers = getSession().createCriteria(Publisher.class).list();
            getSession().getTransaction().commit();
            return publishers;
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

    public boolean isPublisher(String publisher) {
        try {
            getSession().beginTransaction();
            List<Publisher> publishers = getSession().createCriteria(Publisher.class)
                    .add(Restrictions.eq("name", publisher))
                    .list();
            getSession().getTransaction().commit();
            return !(publishers.isEmpty());
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

    public Publisher getPublisher(String publisher) {
        try {
            getSession().beginTransaction();
            List<Publisher> publishers = getSession().createCriteria(Publisher.class)
                    .add(Restrictions.eq("name", publisher))
                    .list();
            getSession().getTransaction().commit();
            return publishers.get(0);
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

    public void addPublisher(Publisher publisher) {
        try {
            getSession().beginTransaction();
            getSession().save(publisher);
            getSession().getTransaction().commit();
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

    public boolean isUser_name(String name) {
        try {
            getSession().beginTransaction();
            List<Users> user = getSession().createCriteria(Users.class)
                    .add(Restrictions.eq("userName", name))
                    .list();
            getSession().getTransaction().commit();
            return !(user.isEmpty());
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }
    
    public boolean verification(String user, String pwd) {
        try {
            getSession().beginTransaction();
            List<Users> users = getSession().createCriteria(Users.class)
                    .add(Restrictions.eq("userName", user))
                    .add(Restrictions.eq("userPass", pwd))
                    .list();
            getSession().getTransaction().commit();
            return !(users.isEmpty());
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }
    
    public Users getUser(String user) {
        try {
            getSession().beginTransaction();
            List<Users> users = getSession().createCriteria(Users.class)
                    .add(Restrictions.eq("userName", user))
                    .list();
            return users.get(0);
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }
    
    public void addUser(Users user) {
        try {
            getSession().beginTransaction();
            getSession().save(user);
            getSession().getTransaction().commit();
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }
    
    public void addSessions(Sessions sessions) {
        try {
            getSession().beginTransaction();
            getSession().save(sessions);
            getSession().getTransaction().commit();
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }
    
    public boolean verificationSession (String user, String user_ses_id) {
        try {
            getSession().beginTransaction();
            List<Sessions> sessions = getSession().createCriteria(Sessions.class)
                    .add(Restrictions.eq("users.userName", user))
                    .add(Restrictions.eq("sesId", user_ses_id))
                    .list();
            getSession().getTransaction().commit();
            return !(sessions.isEmpty());
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }
    
    public void deleteSessions(Sessions sessions) {
        try {
            getSession().beginTransaction();
            getSession().delete(sessions);
            getSession().getTransaction().commit();
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }
    
    public Sessions getSession (String user, String user_ses_id) {
        try {
            getSession().beginTransaction();
            List<Sessions> sessions = getSession().createCriteria(Sessions.class)
                    .add(Restrictions.eq("users.userName", user))
                    .add(Restrictions.eq("sesId", user_ses_id))
                    .list();
            getSession().getTransaction().commit();
            return sessions.get(0);
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }
    
    public List<Sessions> getAllSessions() {
        try {
            getSession().beginTransaction();
            List<Sessions> sessions = getSession().createCriteria(Sessions.class).list();
            getSession().getTransaction().commit();
            return sessions;
        } catch (RuntimeException e) {
            getSession().getTransaction().rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

}
