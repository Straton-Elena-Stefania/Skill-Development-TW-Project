package com.timetraveling.models;

import com.timetraveling.models.sections.Section;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;
import java.util.logging.Logger;

public class UseIdOrGenerate extends IdentityGenerator {
    private static final Logger log = Logger.getLogger(UseIdOrGenerate.class.getName());

    /*@Override
    public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
        if (obj == null) throw new HibernateException(new NullPointerException()) ;

        if ((((Section) obj).getId()) == 0) {
            Serializable id = super.generate(session, obj) ;
            return id;
        } else {
            return ((Section) obj).getId();

        }
    }*/
}