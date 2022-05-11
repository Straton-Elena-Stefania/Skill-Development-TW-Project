package com.timetraveling.utils.validation;

import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserHibernateRepository;
import com.timetraveling.utils.secure.PasswordHandler;

/**
 * Aceasta clasa are scopul de a valida ceea ce a fost introdus
 * de catre utilizator in formularul de login.
 */
public class LoginValidator implements UserValidator {
    private User user;
    private UserHibernateRepository userDAO = new UserHibernateRepository();

    /**
     * Functia verifica daca, criptand parola ce a venit si comparand-o cu ceea ce
     * se afla in baza de date (fara a considera salt-ul, care este generat random),
     * exista o potrivire.
     */
    private boolean isPasswordValid(String password) {
        String userPassword = user.getPassword();
        String userSalt = userPassword.substring(userPassword.length() - SALT_LENGTH);
        String securePassword = userPassword.substring(0, userPassword.length() - SALT_LENGTH);

        boolean passwordMatch = PasswordHandler.verifyUserPassword(password,
                                                                   securePassword, userSalt);

        return passwordMatch;
    }

    /**
     * Functia verifica intai daca userul si-a introdus emailul corespunzator
     * @param email Emailul introdus in formular
     * @param password Parola introdusa in formular
     */
    public boolean isAuthenticationValid(String email, String password) {
        this.user = userDAO.findByEmail(email);

        if (user == null) {
            return false;
        }

        return isPasswordValid(password);
    }
}
