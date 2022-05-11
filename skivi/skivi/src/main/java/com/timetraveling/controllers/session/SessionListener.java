package com.timetraveling.controllers.session;

import com.timetraveling.models.session.SessionStore;
import com.timetraveling.models.session.SessionStoreHibernateRepository;
import com.timetraveling.models.session.SessionStoreRepository;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        System.out.println("session destroyed");
        int userId = (int) sessionEvent.getSession().getAttribute("id");

        SessionStoreRepository sessionStoreRepository = new SessionStoreHibernateRepository();
        SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);

        sessionStoreRepository.remove(sessionStore);
    }
}
