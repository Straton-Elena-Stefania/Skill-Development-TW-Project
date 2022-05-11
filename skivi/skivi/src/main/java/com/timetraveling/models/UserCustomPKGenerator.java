package com.timetraveling.models;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;
import java.util.Properties;
import java.util.stream.Stream;

public class UserCustomPKGenerator extends IdentityGenerator {

    private String prefix;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Serializable id = session.getEntityPersister(null, object).getClassMetadata().getIdentifier(object, session);
        if ((int) id != 0) return id;
        return super.generate(session, object);
    }
}
