/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.smith.bean;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import ru.smith.db.DAOHibernate;
import ru.smith.entity.Users;

/**
 *
 * @author ito
 */
@ManagedBean
@RequestScoped
public class Registration implements Serializable {

    private String user;
    private String pwd;
    private String pwdconf;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwdconf() {
        return pwdconf;
    }

    public void setPwdconf(String pwdconf) {
        this.pwdconf = pwdconf;
    }

    public String registr() {
        
        ResourceBundle bundle = ResourceBundle.getBundle("ru.smith.nls.messages");
        FacesContext context = FacesContext.getCurrentInstance();
            
        if (DAOHibernate.getInstance().isUser_name(user)) {
            FacesMessage message = new FacesMessage(bundle.getString("user_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("registr_form", message);
            
            return "/public/registration";
        }
        
        if (pwd.length()< 6) {
            FacesMessage message = new FacesMessage(bundle.getString("length_pass_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("registr_form", message);
            return "/public/registration";
        }
        if (!(pwd.equals(pwdconf))) {
            FacesMessage message = new FacesMessage(bundle.getString("pass_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("registr_form", message);
            return "/public/registration";
        }
        Users new_user = new Users(user, pwd);
        DAOHibernate.getInstance().addUser(new_user);
        return "/login?faces-redirect=true";
    }
}
