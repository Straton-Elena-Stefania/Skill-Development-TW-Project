package com.timetraveling.utils.async.push;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;

/**
 * Clasa aceasta asculta pentru momentul potrivit in care sa porneasca
 * threadul cu rol de daemon pentru emailuri.
 */
@WebListener
public class EmailSubscriberListener implements ServletContextAttributeListener {
    private EmailTask emailTask = null;

    /**
     * Clasa este declansata atunci cand este adaugat un atribut
     * ServletContext-ului de la serverul nostru.
     */
    @Override
    public void attributeAdded(ServletContextAttributeEvent attributeEvent) {
        if (emailTask == null) {
            this.emailTask = new EmailTask();
            emailTask.run();
        }
    }
}
