package com.timetraveling.models;

import com.timetraveling.exceptions.DuplicateResourceException;
import com.timetraveling.models.session.SessionStore;
import com.timetraveling.models.session.SessionStoreHibernateRepository;
import com.timetraveling.models.session.SessionStoreRepository;
import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserHibernateRepository;
import com.timetraveling.utils.secure.PasswordHandler;
import com.timetraveling.utils.validation.SessionValidator;
import com.timetraveling.utils.validation.SignUpValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.SecureRandom;

@WebServlet(name = "SignUpServlet", value = "/sign-up")
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserHibernateRepository userDAO = new UserHibernateRepository();

    private static final SecureRandom RAND = new SecureRandom();

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            /**
             * Aceasta este pentru cand cineva introduce
             * manual url-ul pentru index.jsp sau pentru /,
             * ca sa il redirectionam catre pagina de home.
             *
             * Pentru a ajunge la pagina aceasta, userul trebuie
             * ori sa apeleze explicit logout, ori sesiunea sa
             * expire.
             */
            if (SessionValidator.isLoggedIn(request)) {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            String password = request.getParameter("password");

            String salt = PasswordHandler.getSalt(SignUpValidator.SALT_LENGTH);

            String securePassword = PasswordHandler.generateSecurePassword(password, salt);

            securePassword = securePassword + salt;

            User user = new User(request.getParameter("username"),
                                 request.getParameter("registrationEmail"),
                                 securePassword);

            SignUpValidator signUpValidator = new SignUpValidator();
            if (signUpValidator.isRegistrationValid(user)) {
                userDAO.save(user);
                System.err.println("registration valid");
                /**
                 * Aici setam parametrii de sesiune, precum timpul
                 * maxim de inactivitate pana delogam userul si un id
                 * ca sa stim cu cine avem de-a face.
                 */
                request.getSession(true).setMaxInactiveInterval(3000);
                request.getSession(true).setAttribute("id", user.getId());

                SessionStoreRepository sessionStoreRepository = new SessionStoreHibernateRepository();

                SessionStore sessionStore = new SessionStore();
                sessionStore.setUserId(user.getId());
                sessionStore.setSessionId(request.getSession().getId());

                sessionStoreRepository.update(sessionStore);

                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }

        } catch (DuplicateResourceException duplicateResourceException) {
            duplicateResourceException.printStackTrace();
        } finally {
            System.out.println("finally");
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
