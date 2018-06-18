/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.smith.cache;

import java.util.Date;
import java.util.List;
import ru.smith.db.DAOHibernate;
import ru.smith.entity.Sessions;

public class CacheSessions {
    
    private static CacheSessions cache;
    
    private List<Sessions> sessions_cache;
    private Date date;
    
    private CacheSessions() {
        date = new Date();
        sessions_cache = DAOHibernate.getInstance().getAllSessions();
    }
    
    public static CacheSessions getInstance() {
        return cache == null ? new  CacheSessions(): cache;
    }
    
    private List<Sessions> getSessions_cache() {
        return sessions_cache;
    }

    public void setSessions_cache(List<Sessions> sessions_cache) {
        this.sessions_cache = sessions_cache;
    }
    
    public boolean verificationSession (String user, String user_ses_id){
        Date date_now = new Date();
        if (date_now.getTime() - date.getTime()> 900000){
            date = new Date();
            setSessions_cache(DAOHibernate.getInstance().getAllSessions());
        }
        
        for(Sessions ses: sessions_cache){
        if (ses.getSesId().equals(user_ses_id) && ses.getUsers().getUserName().equals(user))
                return true;}
        return false;
    }
}
