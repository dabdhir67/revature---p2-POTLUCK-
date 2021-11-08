package com.revature.controllers;

import  com.revature.models.Chef;
import com.revature.utilities.SecurityUtil;
import com.revature.utilities.SessionUtil;
import org.h2.tools.Server;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ChefControllerIT {

    private final String BASE_URL = "http://localhost:8089/BackEnd/chef";
    private final RestTemplate restTemplate = new RestTemplate();

    @BeforeAll
    static void setup() {
        try(Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            char[] buf = new char[1400];
            int i = new FileReader("src/test-integration/resources/basic-setup.sql").read(buf);
            if (i==0) System.exit(i);
            session.createSQLQuery(String.valueOf(buf).trim()).executeUpdate();
            transaction.commit();

            transaction = session.beginTransaction();
            buf = new char[1400];
            i = new FileReader("src/test-integration/resources/chef-test-setup.sql").read(buf);
            if (i==0) System.exit(i);
            session.createSQLQuery(String.valueOf(buf).trim()).executeUpdate();
            transaction.commit();
            SessionUtil.closeFactory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Add a new chef to the database")
    void registerValidChef() {
        Chef chef = new Chef("chef", "chef", "chef", "chef", "chef@chef.com");
        HttpHeaders header = new HttpHeaders();
        header.add("password", "chef");
        HttpEntity<Chef> req = new HttpEntity<>(chef, header);
        ResponseEntity<Chef> response = restTemplate.postForEntity(BASE_URL, req, Chef.class);
        assertAll(
                () -> assertSame(response.getStatusCode(), HttpStatus.CREATED),
                () -> assertNotNull(response.getBody()),
                () -> assertNotEquals(0, Objects.requireNonNull(response.getBody()).getC_id()),
                () -> assertNotNull(Objects.requireNonNull(response.getHeaders().get("Authorization")).get(0))
        );
    }

    @Test
    @DisplayName("Add null chef")
    void registerInvalidChef() {
        assertThrows(HttpClientErrorException.class, () -> restTemplate.postForEntity(BASE_URL, null, Chef.class));
    }

    @Test
    @DisplayName("Add a chef with duplicate username")
    void addDuplicateChef() {
        Chef chef = new Chef("chef1", "chef", "chef", "chef", "chef@chef.com");
        HttpHeaders headers = new HttpHeaders();
        headers.add("password", "chef");
        HttpEntity<Chef> req = new HttpEntity<>(chef, headers);
        ResponseEntity<Chef> response = restTemplate.postForEntity(BASE_URL, req, Chef.class);
        assertThrows(HttpServerErrorException.InternalServerError.class, () -> restTemplate.postForEntity(BASE_URL, req, Chef.class));
    }

    @Test
    @DisplayName("Get an existing chef with valid password")
    void getExistingChef() {
        Chef expected = new Chef(4, "user4", "ZUNCt1riI847BDYWT1CofXLK9Z6R4g60jf4pZMcR4k2kBAzGgnubgS6qPA92oWzdTCdn+W9Kd1wkTSqeL/fwog==wd6xnFOyJCS5Iv00tuhyTjo8aIYg8L8BZnQRVba8nsM/Gjz/njTy9AmINGaR3CxeGifSIev/K5oprxbb8O4+69hCcVbidDoh3y6bpDNpbvavq5pa1sRf/UWRGgXe+3Cuo7c/wFy1dG+tXavltebrdPyBP4rnhfGpLdMaEUDFfdSFUOoTYYysHnFDjxh6+zz1b6W96oyY6y7qMFRMJdTgwaOOBIfPVWtwpjuy/pl7X++19XC72/xWEAWZNPE/sj7k4bTox4/79yXu8FcZbYOfofrC1yn8+HWDCiA7ZJhs/h9aER860xx4ol5IN8km0uk5Xe8ULsv0NtWdKp+EniUokVNd4kU/5+l65JSg/9I3U75IsTgXhITEe6hIeS3FFFbKTVd2ARC/h9ljbNOcDlkBzmSIYfmMV1w6YCmkFAcGwSGgH/8Npw6Fuz4rOyurMMoTvNbQmN7O4zjHGSpy1vxPrOnXPNMUtv9S97O9aKCYLYAdIgEWLm1GULHHBjolLWXTYrlJ3nNUjt1TmwGE9VWQTQ0/dycUYUj9dK5BU1NEXWkCDKMC3s1jrd3XI3oXglfyWj4/wzNumH4oLukCkPriwZ8UHYwbo767KLumEipsSTNpoLm4k4VdqyFBxJiTUcVP12ru1lOM/QUi/ippfWMPSOi99dT5AjVDRd9hNnCF7eI=", "Chef", "Madl", "madl@chef.com");
        HttpHeaders headers = new HttpHeaders();
        headers.set("password", "pass");
        HttpEntity<String> req = new HttpEntity<>(null, headers);
        ResponseEntity<Chef> response = restTemplate.exchange(BASE_URL + "/user4", HttpMethod.GET, req, Chef.class);
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertNotNull(response.getHeaders()),
                () -> assertNotNull(response.getHeaders().get("Access-Control-Expose-Headers")),
                () -> assertEquals("Authorization", Objects.requireNonNull(response.getHeaders().get("Access-Control-Expose-Headers")).get(0)),
                () -> assertNotNull(response.getHeaders().get("Authorization")),
                () -> assertNotNull(Objects.requireNonNull(response.getHeaders().get("Authorization")).get(0)),
                () -> assertEquals(expected.getUsername(), Objects.requireNonNull(response.getBody()).getUsername()),
                () -> assertEquals(expected.getFirstName(), Objects.requireNonNull(response.getBody()).getFirstName()),
                () -> assertEquals(expected.getLastName(), Objects.requireNonNull(response.getBody()).getLastName()),
                () -> assertEquals(expected.getEmail(), expected.getEmail())
        );
    }

    @Test
    @DisplayName("Get Chef with invalid username and password")
    void getEmptyUsernameInvalidPasswordChef() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("password", null);
        HttpEntity<String> req = new HttpEntity<>(null, headers);
        assertThrows(HttpClientErrorException.MethodNotAllowed.class,
                () -> restTemplate.exchange(BASE_URL + "/", HttpMethod.GET, req, Chef.class));
    }

    @Test
    @DisplayName("Get Chef with invalid username and password")
    void getInvalidPasswordChef() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("password", "");
        HttpEntity<String> req = new HttpEntity<>(null, headers);
        assertThrows(HttpClientErrorException.MethodNotAllowed.class,
                () -> restTemplate.exchange(BASE_URL + "/", HttpMethod.GET, req, Chef.class));
    }

    @Test
    @DisplayName("Get Nonexistent Chef")
    void getNonexistentChef() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("password", "supersecret");
        HttpEntity<String> req = new HttpEntity<>(null, headers);
        assertThrows(HttpClientErrorException.NotFound.class,
                () -> restTemplate.exchange(BASE_URL + "/svaeviuhosf35", HttpMethod.GET, req, Chef.class));
    }

    @Test
    @DisplayName("Get an Existing Chef with the incorrect password")
    void getExistingChefBadPassword() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("password", "supersecret");
        HttpEntity<String> req = new HttpEntity<>(null, headers);
        assertThrows(HttpClientErrorException.Unauthorized.class,
                () -> restTemplate.exchange(BASE_URL + "/user3", HttpMethod.GET, req, Chef.class));
    }
}
