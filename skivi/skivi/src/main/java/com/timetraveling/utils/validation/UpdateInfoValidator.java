package com.timetraveling.utils.validation;

import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserHibernateRepository;

public class UpdateInfoValidator {
    private User user;
    private UserHibernateRepository userRepository = new UserHibernateRepository();

    public boolean isUpdateValid(User user) {
        this.user = userRepository.findByEmail(user.getEmail());
        if (this.user == null) {
            return true;
        }
        if (this.user.getId() == user.getId()) {
            return true;
        }
        return false;
    }
}