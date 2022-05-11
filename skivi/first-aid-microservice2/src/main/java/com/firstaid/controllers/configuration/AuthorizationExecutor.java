package com.firstaid.controllers.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class AuthorizationExecutor implements Authorization {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public int checkAuthorizationStatus(HttpServletRequest request,
                                                        HttpServletResponse response,
                                                        String role) {
        int userId = request.getIntHeader("userId");
        String session = request.getHeader("Authorization");

        String authorizationURL = "";

        if (Configuration.DEPLOYMENT_MODE.equals("docker")) {
            authorizationURL = AUTHORIZATION_SERVER_DOCKER;
        } else {
            authorizationURL = AUTHORIZATION_SERVER_LOCAL;
        }

        if (role.equals(ENROLLED_ROLE)) {
            authorizationURL = authorizationURL + '/' + SKILL_NAME;
        }

        HttpPost authorizationRequest = new HttpPost(authorizationURL);
        authorizationRequest.addHeader("userId", String.valueOf(userId));
        authorizationRequest.addHeader("Authorization", session);

        authorizationRequest.addHeader("content-type", "application/json");

        try (CloseableHttpResponse authorizationResponse = httpClient.execute(authorizationRequest)) {
            /**if (authorizationResponse.getStatusLine().getStatusCode() != (HttpServletResponse.SC_OK)) {
                response.sendError(authorizationResponse.getStatusLine().getStatusCode());
                return;
            }*/
            return authorizationResponse.getStatusLine().getStatusCode();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }
}
