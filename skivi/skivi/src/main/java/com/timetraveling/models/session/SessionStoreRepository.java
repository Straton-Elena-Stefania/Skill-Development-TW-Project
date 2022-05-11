package com.timetraveling.models.session;

import com.timetraveling.exceptions.AlreadyExistsException;
import com.timetraveling.exceptions.DuplicateResourceException;
import com.timetraveling.models.users.User;

public interface SessionStoreRepository {
    SessionStore findByUserID(int id);

    void save(SessionStore sessionStore) throws DuplicateResourceException;

    void update(SessionStore sessionStore) throws AlreadyExistsException;

    void remove(SessionStore sessionStore);
}
