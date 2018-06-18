/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.smith.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ru.smith.cache.CacheSessions;
import ru.smith.db.DAOHibernate;

/**
 *
 * @author ito
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter implements Filter {

    public AuthorizationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {

            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);

            Cookie[] userCookies = reqt.getCookies();
            String usersessionid = null;
            String user = null;

            if (userCookies != null && userCookies.length > 0) {
                for (int i = 0; i < userCookies.length; i++) {
                    if (userCookies[i].getName().equals("JSESSIONID")) {
                        usersessionid = userCookies[i].getValue();
                    }
                    if (userCookies[i].getName().equals("user")) {
                        user = userCookies[i].getValue();
                    }
                }
            }
            if (CacheSessions.getInstance()
                    .verificationSession(user, usersessionid)) {
                if (ses.getAttribute("username") == null) {
                    ses.setAttribute("username", user);
                }
            }

            String reqURI = reqt.getRequestURI();
            if (reqURI.indexOf("/login.xhtml") >= 0
                    || CacheSessions.getInstance()
                    .verificationSession(user, usersessionid)
                    || (ses != null && ses.getAttribute("username") != null)
                    || reqURI.indexOf("/public/") >= 0
                    || reqURI.contains("javax.faces.resource")) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect(reqt.getContextPath() + "/faces/login.xhtml");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
