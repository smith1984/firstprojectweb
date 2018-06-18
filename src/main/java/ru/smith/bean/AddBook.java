/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.smith.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;
import ru.smith.db.DAOHibernate;
import ru.smith.entity.Author;
import ru.smith.entity.Book;
import ru.smith.entity.Genre;
import ru.smith.entity.Publisher;
import ru.smith.entity.Users;
import ru.smith.util.SessionUtils;

/**
 *
 * @author ito
 */
@ManagedBean
@RequestScoped
public class AddBook implements Serializable {

    private String author;
    private String genre;
    private String publisher;
    private String name;
    private int pageCount;
    private String isbn;
    private int publishYear;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String add() {

        Genre gen = null;
        Author aut = null;
        Publisher pub = null;
        Users user;
        
        if (!(DAOHibernate.getInstance().isGenre(genre))) {
            gen = new Genre(genre);
            DAOHibernate.getInstance().addGenre(gen);
        }
            gen = DAOHibernate.getInstance().getGenre(genre);
 
        if (!(DAOHibernate.getInstance().isAuthor(author))) {
            aut = new Author(author);
            DAOHibernate.getInstance().addAuthor(aut);
        } 
            aut = DAOHibernate.getInstance().getAuthor(author);
        
        if (!(DAOHibernate.getInstance().isPublisher(publisher))) {
            pub = new Publisher(publisher);
            DAOHibernate.getInstance().addPublisher(pub);
        }
            pub = DAOHibernate.getInstance().getPublisher(publisher);
        
        HttpSession session = SessionUtils.getSession_http();
        user = DAOHibernate.getInstance().getUser((String) session
                        .getAttribute("username"));
        Book book = new Book(aut, gen, pub, user, name, pageCount, isbn, publishYear);
        DAOHibernate.getInstance().addBook(book);
        return "/pages/library?faces-redirect=true";
    }
}
