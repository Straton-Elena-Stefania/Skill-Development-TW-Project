/*
 * API pentru utilizatori SkiVI
 * Prin acest API care e RESTful, un administrator sau un utilizator logat poate obtine si gestiona datele personale si ale cursurilor disponibile.
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for UserApi
 */
public class UserApiTest {

    private final UserApi api = new UserApi();

    
    /**
     * 
     *
     * Sterge user-ul aflat la acel id.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void usersIdDeleteTest() throws ApiException {
        Integer id = null;
        api.usersIdDelete(id);

        // TODO: test validations
    }
    
}
