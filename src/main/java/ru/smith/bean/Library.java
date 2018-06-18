/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.smith.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import ru.smith.db.DAOHibernate;
import ru.smith.entity.Book;
import ru.smith.util.SessionUtils;

/**
 *
 * @author smith
 */
@ManagedBean
@ViewScoped
public class Library implements Serializable {

    private List<Book> currentBookList;

    public List<Book> getCurrentBookList() {
        fillBook();
        return currentBookList;
    }

    public Library() {
    }

    private void fillBook() {
        HttpSession session = SessionUtils.getSession_http();
       
        currentBookList = DAOHibernate.getInstance()
                .getAllBooks((String)session.getAttribute("username"));
    }
}
