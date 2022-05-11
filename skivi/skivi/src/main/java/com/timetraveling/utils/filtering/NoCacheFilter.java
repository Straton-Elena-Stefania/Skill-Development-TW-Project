package com.timetraveling.utils.filtering;

import com.timetraveling.utils.validation.SessionValidator;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Acesta este un filtru care face in asa fel incat, atunci cand un user
 * se logheaza, sa nu se poata intoarce inapoi sa se mai logheze o data
 */
@WebFilter
public class NoCacheFilter implements Filter {

    /**
     * Metoda verifica daca userul vine de la pagina
     * de logare pe care o vede cand acceseaza prima oara aplicatia.
     * Daca da, atunci il redirectionam catre pagina de home.
     * Daca nu, atunci nu luam nicio actiune.
     * @param request O cerere captata pe pagina de logare
     * @param response Un raspuns ce este redirectionat controllerului pentru homepage
     * @param chain Filtrul prin care trec cererile
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getRequestURI().equals(
                req.getContextPath() + "/index.jsp")
                && SessionValidator.isLoggedIn(req)) {
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendRedirect(req.getContextPath()
                                + "/home");
        } else {
            /**
             * Lasam cererea sa functioneze normal.
             */
            chain.doFilter(request, response);
        }
    }
}