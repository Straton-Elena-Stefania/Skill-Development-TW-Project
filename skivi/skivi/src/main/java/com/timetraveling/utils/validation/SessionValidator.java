package com.timetraveling.utils.validation;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Clasa interpreteaza o cerere http, pentru a gasi un id
 */
public final class SessionValidator {
    private SessionValidator() {}

    public static boolean isLoggedIn(HttpServletRequest request) {
        /**
         * getSession(false) inseamna ca nu va crea o noua sesiune
         * la acest apel. In sesiune sunt pastrate datele despre
         * user pe care serverul le verifica in propriul sau mediu
         * de stocare daca sunt valide sau nu.
         */
        if (request.getSession(false) != null && request.getSession().getAttribute("id") != null) {
            return true;
        }

        //System.out.println("Nu esti logat");
        return false;
    }
}
