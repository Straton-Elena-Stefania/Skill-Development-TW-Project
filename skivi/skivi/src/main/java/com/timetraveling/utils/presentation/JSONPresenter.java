package com.timetraveling.utils.presentation;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Clasa aceasta o folosim pentru a prezenta obiectele in format JSON
 * @param <ModelType>
 */
public class JSONPresenter<ModelType> {
    private Gson gson = new Gson();

    public void sendAsJson(
            HttpServletResponse response,
            ModelType model) throws IOException {

        response.setContentType("application/json");

        String res = gson.toJson(model);

        PrintWriter out = response.getWriter();

        out.print(res);
        out.flush();
    }

    public void sendAsJson(
            HttpServletResponse response,
            ModelType model, Gson customGson) throws IOException {

        response.setContentType("application/json");

        String res = customGson.toJson(model);

        PrintWriter out = response.getWriter();

        out.print(res);
        out.flush();
    }

    public void sendAsJson(
            HttpServletResponse response,
            List<ModelType> model) throws IOException {
        response.setContentType("application/json");

        String res = gson.toJson(model);

        PrintWriter out = response.getWriter();

        out.print(res);
        out.flush();
    }

    public void sendAsJson(
            HttpServletResponse response,
            List<ModelType> model, Gson customGson) throws IOException {
        response.setContentType("application/json");

        String res = customGson.toJson(model);

        PrintWriter out = response.getWriter();

        out.print(res);
        out.flush();
    }
}
