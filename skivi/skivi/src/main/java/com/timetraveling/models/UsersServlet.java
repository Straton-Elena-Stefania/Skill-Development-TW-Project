package com.timetraveling.models;

import com.timetraveling.models.management.EntityManagerFactoryHandle;
import com.timetraveling.models.users.User;
import com.timetraveling.utils.presentation.JSONPresenter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "users", value = "/UsersServlet")
public class UsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONPresenter<User> userJSONPresenter = new JSONPresenter<>();

        //int id = Integer.parseInt(request.getParameter("user"));

        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(User.class);

        List<User> userList = entityManager.createQuery(
                "SELECT g FROM User g", User.class).getResultList();

        userJSONPresenter.sendAsJson(response, userList);
    }
}
