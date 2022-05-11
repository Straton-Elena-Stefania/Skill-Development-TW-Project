package com.timetraveling.utils.async.push;

import com.timetraveling.models.article.Subsection;
import com.timetraveling.models.email.Email;

public class EmailRouter implements Routing {
    private EmailTask emailTask;

    public EmailRouter(EmailTask emailTask) {
        this.emailTask = emailTask;
    }

    private void routeTimetraveling(Email email) {
        System.out.println("rutam timetraveling");
        emailTask.sendEmail(email, "teodoradamian811@gmail.com");
    }

    private void routeCooking(Email email) {
        emailTask.sendEmail(email, "teodoradamian811@gmail.com");
    }

    private void routeFirstAid(Email email) {
        emailTask.sendEmail(email, "teodoradamian811@gmail.com");
    }

    public void route(Email email) {
        System.out.println("vedem in route ce mathciueste");
        if (email.getSkill().matches("timetraveling")) {
            System.out.println("rutam timetraveling in route()");
            routeTimetraveling(email);
        } else if (email.getSkill().matches("firstAid")) {
            routeFirstAid(email);
        } else if (email.getSkill().matches("cooking")) {
            routeCooking(email);
        }
    }
}
