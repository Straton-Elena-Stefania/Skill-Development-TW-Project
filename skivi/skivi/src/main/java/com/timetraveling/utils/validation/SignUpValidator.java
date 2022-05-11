package com.timetraveling.utils.validation;


import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserHibernateRepository;

public class SignUpValidator implements UserValidator {
    private int PASSWORD_LENGTH_CHECK = 8;
    private User user;
    private UserHibernateRepository userDAO = new UserHibernateRepository();

    /**
     * Validare de backend
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {
        if (password.length() < PASSWORD_LENGTH_CHECK) {
            return false;
        }

        return true;
    }

    private boolean isEmailAvailable(String email) {
        this.user = userDAO.findByEmail(email);

        if (user == null) {
            return true;
        }

        return false;
    }

    private boolean isEmailValid(String email) {
        boolean isValid = true;

        if (!email.contains("@")) {
            isValid = false;
        }

        return isValid && isEmailAvailable(email);
    }

    public boolean isRegistrationValid(User user) {
        return isEmailValid(user.getEmail()) && isPasswordValid(user.getPassword());
    }
}
