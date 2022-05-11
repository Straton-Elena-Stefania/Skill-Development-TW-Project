package com.timetraveling.models.users;

import com.timetraveling.exceptions.AlreadyExistsException;
import com.timetraveling.exceptions.DuplicateResourceException;

public interface UserRepository {
    User findByUsername(String username);

    User findByEmail(String email);

    User findByID(int id);

    void save(User user) throws DuplicateResourceException;

    void update(User user) throws AlreadyExistsException;
}
