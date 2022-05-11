package com.timetraveling.utils.async.push;

import com.timetraveling.models.email.Email;

public class EmailBuilder implements Routing {
    public Email build(String routingKey, String subject, String body) {
        Email email = new Email();

        System.out.println(routingKey);
        if (routingKey.contains("timetraveling")) {
            email.setSkill("timetraveling");
        } else if (routingKey.contains("firstAid")) {
            email.setSkill("firstAid");
        } else {
            email.setSkill("cooking");
        }

        if (routingKey.contains("step")) {
            email.setUpdateType("step");
        } else {
            email.setUpdateType("subsection");
        }

        email.setSubject(subject);
        email.setBody(body);

        return email;
    }
}
