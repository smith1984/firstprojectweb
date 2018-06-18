/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.smith.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import ru.smith.db.DAOHibernate;
import ru.smith.entity.Sessions;
import ru.smith.entity.Users;
import ru.smith.util.SessionUtils;
import ru.smith.cache.CacheSessions;

/**
 *
 * @author ito
 */
@ManagedBean
@RequestScoped
public class Login implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;

    private String pwd;
    private String user;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String validateUsernamePassword() {
        boolean valid = DAOHibernate.getInstance().verification(user, pwd);
        if (valid) {
            HttpSession session = SessionUtils.getSession_http();
            session.setAttribute("username", user);
            String ses_id = session.getId();
            Users users = DAOHibernate.getInstance().getUser(user);
            Sessions sessions = new Sessions(ses_id, users);
            DAOHibernate.getInstance().addSessions(sessions);
            CacheSessions.getInstance().setSessions_cache(DAOHibernate.getInstance().getAllSessions());

            FacesContext.getCurrentInstance().getExternalContext()
                    .addResponseCookie("user", user, null);

            return "/pages/library?faces-redirect=true";
        } else {
            ResourceBundle bundle = ResourceBundle.getBundle("ru.smith.nls.messages");
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(bundle.getString("login_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("login_form", message);
        }
        return "/login";
    }

    public String logout() {
        HttpSession session = SessionUtils.getSession_http();

        Cookie user_cookie = (Cookie)FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestCookieMap().get("user");
        String user_name = user_cookie.getValue();
        
        Cookie sessionid = (Cookie)FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestCookieMap().get("JSESSIONID");
        String usersessionid  = sessionid.getValue();
        
        DAOHibernate.getInstance().deleteSessions(DAOHibernate.getInstance()
                .getSession(user_name, usersessionid));
        
        session.invalidate();
        return "/login?faces-redirect=true";
    }
}
