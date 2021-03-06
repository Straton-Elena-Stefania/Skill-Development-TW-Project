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
import org.openapitools.client.model.User;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for UsersApi
 */
@Ignore
public class UsersApiTest {

    private final UsersApi api = new UsersApi();

    
    /**
     * 
     *
     * Actualizeaza user-ul aflat la acel id partial conform a ceea ce este descris in corpul cererii in format JSON.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void usersIdPatchTest() throws ApiException {
        Integer id = null;
        User user = null;
        api.usersIdPatch(id, user);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * O suprascriere totala a utilizatorului aflat la acel id folosindu-se de ceea ce este in corpul cererii (un User in JSON)
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void usersIdPutTest() throws ApiException {
        Integer id = null;
        User user = null;
        api.usersIdPut(id, user);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void usersPostTest() throws ApiException {
        User user = null;
        api.usersPost(user);

        // TODO: test validations
    }
    
}
