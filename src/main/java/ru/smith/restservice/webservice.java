/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.smith.restservice;

import java.util.List;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import ru.smith.db.DAOHibernate;
import ru.smith.entity.Author;
import ru.smith.entity.Book;
import ru.smith.entity.Genre;
import ru.smith.entity.Publisher;
import ru.smith.entity.Users;

@Path("/rest")
public class webservice {

    public webservice() {
    }

    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public String validateUsernamePassword(@QueryParam("user") String user,
            @QueryParam("pwd") String pwd) {
        boolean valid = DAOHibernate.getInstance().verification(user, pwd);
        if (valid) {
            return "true";
        }
        return "false";
    }

    @GET
    @Path("/library")
    @Produces(MediaType.APPLICATION_XML)
    public List<Book> currentBookList(@QueryParam("user") String user) {
        List<Book> current = DAOHibernate.getInstance().getAllBooks(user);
        return current;
    }

    @GET
    @Path("/registration")
    @Produces(MediaType.APPLICATION_XML)
    public Users addUser(@QueryParam("user") String user,
            @QueryParam("pwd") String pwd) {
        if (!(DAOHibernate.getInstance().isUser_name(user))) {
            Users new_user = new Users(user, pwd);
            DAOHibernate.getInstance().addUser(new_user);
            return new_user;
        }
        return null;
    }

    @GET
    @Path("/addbook")
    @Produces(MediaType.APPLICATION_XML)
    public Book addBook(@QueryParam("user") String username, @QueryParam("author") String author,
            @QueryParam("genre") String genre, @QueryParam("publisher") String publisher,
            @QueryParam("name") String name, @QueryParam("pageCount") int pageCount,
            @QueryParam("isbn") String isbn, @QueryParam("publishYear") int publishYear) {

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

        user = DAOHibernate.getInstance().getUser(username);
        Book book = new Book(aut, gen, pub, user, name, pageCount, isbn, publishYear);
        DAOHibernate.getInstance().addBook(book);

        return book;
    }
    
    @GET
    @Path("/genre")
    @Produces(MediaType.APPLICATION_XML)
    @Transactional
    public List<Genre> currentGenreList() {
        List<Genre> current = DAOHibernate.getInstance().getAllGenres();
        return current;
    }

}
