package com.revature.controllers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import com.revature.models.Chef;
import com.revature.models.Recipe;
import com.revature.utilities.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;

import java.text.ParseException;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;


public class RecipeControllerIT {

    private final String BASE_URL = "http://localhost:8080/BackEnd/recipe";
    private final RestTemplate restTemplate = new RestTemplate();

    @BeforeAll
    static void setup() {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            char[] buf = new char[1400];
            int i = new FileReader("src/test-integration/resources/basic-setup.sql").read(buf);
            if (i == 0) System.exit(i);
            session.createSQLQuery(String.valueOf(buf).trim()).executeUpdate();
            transaction.commit();
            transaction = session.beginTransaction();
            buf = new char[1400];
            i = new FileReader("src/test-integration/resources/recipe-test-setup.sql").read(buf);
            if (i == 0) System.exit(i);
            session.createSQLQuery(String.valueOf(buf).trim()).executeUpdate();
            transaction.commit();
            SessionUtil.closeFactory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    @DisplayName("Get all recipes")
    public void testGetAllRecipes(){
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/BackEnd/recipe",
                List.class);
        assertAll(
                ()->assertTrue(response.getBody().size()>0),
                ()->assertEquals(response.getStatusCode(), HttpStatus.OK)
        );
    }


    @Test
    @DisplayName("Get all recipes by Chef Id")
    void testGetAllRecipesByChef() {
        int id = 2;
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/BackEnd/recipe/{id}",
                List.class,id);
        assertAll(
                ()->assertTrue(response.getBody().size()>0),
                ()->assertEquals(response.getStatusCode(), HttpStatus.OK)
        );

    }


    @Test
    @DisplayName("Add a new recipe to the database")
    void addRecipe() {
        Chef chef = new Chef (1, "user1", "password", "One", "First", "Email1");
        Recipe recipe = new Recipe(777, "Recipe777", "A new Body", Timestamp.valueOf(LocalDateTime.now()), chef);

        HttpHeaders important = new HttpHeaders();
        important.set("passkey", chef.getPasskey());
        important.set("id", String.valueOf(chef.getC_id()));
        String token = restTemplate.postForObject("http://localhost:8080/BackEnd/init",
                new HttpEntity<>(chef, important), String.class);

        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", token);
        HttpEntity<Recipe> entity = new HttpEntity<>(recipe, header);

        ResponseEntity<Recipe> response = restTemplate.postForEntity(BASE_URL, entity, Recipe.class);
        assertAll(
                () -> assertSame(response.getStatusCode(), HttpStatus.CREATED),
                () -> assertNotNull(response.getBody())
        );
    }

    @Test
    @DisplayName("Update a recipe from the database")
    void testUpdateExistingRecipe() throws ParseException {
        Chef chef = new Chef (1, "user1", "password", "One", "First", "Email1");
        Recipe recipe = new Recipe(1, "Recipe1", "A new Body", Timestamp.valueOf(LocalDateTime.now()), chef);

        HttpHeaders important = new HttpHeaders();
        important.set("passkey", chef.getPasskey());
        important.set("id", String.valueOf(chef.getC_id()));
        String token = restTemplate.postForObject("http://localhost:8080/BackEnd/init",
                                                        new HttpEntity<>(chef, important), String.class);
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", token);
        HttpEntity<Recipe> entity = new HttpEntity<>(recipe, header);

        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, entity, String.class);
        assertAll(
                () -> assertSame(response.getStatusCode(), HttpStatus.OK),
                () -> assertNotNull(response.getHeaders())
        );
    }

    @Test
    @DisplayName("Update a nonexisting recipe from the database")
    void testUpdateNonexistingRecipe() throws ParseException {
        Chef chef = new Chef (1, "user1", "password", "One", "First", "Email1");
        Recipe recipe = new Recipe(151613231, "Recipe1", "A new Body", Timestamp.valueOf(LocalDateTime.now()), chef);

        HttpHeaders important = new HttpHeaders();
        important.set("passkey", chef.getPasskey());
        important.set("id", String.valueOf(chef.getC_id()));
        String token = restTemplate.postForObject("http://localhost:8080/BackEnd/init",
                new HttpEntity<>(chef, important), String.class);
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", token);
        HttpEntity<Recipe> entity = new HttpEntity<>(recipe, header);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, entity, String.class);
        assertAll(
                () -> assertSame(response.getStatusCode(), HttpStatus.NO_CONTENT),
                () -> assertNotNull(response.getHeaders())
        );
    }

    @Test
    @DisplayName("Update a null recipe from the database")
    void testUpdateNullRecipe() throws ParseException {
        Chef chef = new Chef (1, "user1", "password", "One", "First", "Email1");
        Recipe recipe = null;
        HttpHeaders important = new HttpHeaders();
        important.set("passkey", chef.getPasskey());
        important.set("id", String.valueOf(chef.getC_id()));
        String token = restTemplate.postForObject("http://localhost:8080/BackEnd/init",
                new HttpEntity<>(chef, important), String.class);
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", token);
        HttpEntity<Recipe> entity = new HttpEntity<>(recipe, header);
        assertThrows(
                HttpClientErrorException.class, () -> restTemplate.exchange(BASE_URL, HttpMethod.PUT, entity, String.class)
        );
    }

    @Test
    @DisplayName("Update an empty recipe from the database")
    void testUpdateEmptyRecipe() throws ParseException {
        Chef chef = new Chef (1, "user1", "password", "One", "First", "Email1");
        Recipe recipe = new Recipe();
        HttpHeaders important = new HttpHeaders();
        important.set("passkey", chef.getPasskey());
        important.set("id", String.valueOf(chef.getC_id()));
        String token = restTemplate.postForObject("http://localhost:8080/BackEnd/init",
                new HttpEntity<>(chef, important), String.class);
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", token);
        HttpEntity<Recipe> entity = new HttpEntity<>(recipe, header);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, entity, String.class);
        assertAll(
                () -> assertSame(response.getStatusCode(), HttpStatus.NO_CONTENT),
                () -> assertNotNull(response.getHeaders())
        );
    }

    @Test
    @DisplayName("Deleting a recipe from the database")
    void testDeleteExistingRecipe() throws ParseException {
        Chef chef = new Chef (1, "user1", "password", "One", "First", "Email1");
        HttpHeaders important = new HttpHeaders();
        important.set("passkey", chef.getPasskey());
        important.set("id", String.valueOf(chef.getC_id()));
        String token = restTemplate.postForObject("http://localhost:8080/BackEnd/init",
                                                        new HttpEntity<>(chef, important), String.class);
        Recipe recipe = new Recipe(1, "Recipe1", "A new Body", Timestamp.valueOf(LocalDateTime.now()), chef);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<Recipe> entity = new HttpEntity<>(recipe, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.DELETE, entity, String.class);
        assertAll(
                () -> assertSame(response.getStatusCode(), HttpStatus.OK),
                () -> assertNotNull(response.getHeaders())
        );
    }



    @Test
    @DisplayName("Deleting a nonexisting recipe from the database")
    void testDeleteNonexistingRecipe() throws ParseException {
        Chef chef = new Chef (1, "user1", "password", "One", "First", "Email1");
        HttpHeaders important = new HttpHeaders();
        important.set("passkey", chef.getPasskey());
        important.set("id", String.valueOf(chef.getC_id()));
        String token = restTemplate.postForObject("http://localhost:8080/BackEnd/init",
                new HttpEntity<>(chef, important), String.class);
        Recipe recipe = new Recipe(2336523, "Recipe1", "A new Body", Timestamp.valueOf(LocalDateTime.now()), chef);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<Recipe> entity = new HttpEntity<>(recipe, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.DELETE, entity, String.class);
        assertAll(
                () -> assertSame(response.getStatusCode(), HttpStatus.NO_CONTENT),
                () -> assertNotNull(response.getHeaders())
        );
    }

    @Test
    @DisplayName("Deleting a null recipe from the database")
    void testDeleteNullRecipe() throws ParseException {
        Chef chef = new Chef (1, "user1", "password", "One", "First", "Email1");
        HttpHeaders important = new HttpHeaders();
        important.set("passkey", chef.getPasskey());
        important.set("id", String.valueOf(chef.getC_id()));
        String token = restTemplate.postForObject("http://localhost:8080/BackEnd/init",
                new HttpEntity<>(chef, important), String.class);
        Recipe recipe = null;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<Recipe> entity = new HttpEntity<>(recipe, headers);

        assertThrows(
                HttpClientErrorException.class, () -> restTemplate.exchange(BASE_URL, HttpMethod.DELETE, entity, String.class)
        );
    }

}

