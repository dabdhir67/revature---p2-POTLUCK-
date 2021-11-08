package com.revature.services;

import com.revature.data.ChefDataDAO;
import com.revature.models.Chef;
import com.revature.utilities.SecurityUtil;
import com.revature.utilities.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.FileReader;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ChefServiceImplTest {
    @Mock
    private ChefDataDAO chefData;

    @InjectMocks
    private ChefServiceImpl chefService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void setup() {
        try(Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            char[] buf = new char[1400];
            int i = new FileReader("src/test/resources/basic-setup.sql").read(buf);
            if (i==0) System.exit(i);
            session.createSQLQuery(String.valueOf(buf).trim()).executeUpdate();
            transaction.commit();

            transaction = session.beginTransaction();
            buf = new char[1400];
            i = new FileReader("src/test/resources/chef-test-setup.sql").read(buf);
            if (i==0) System.exit(i);
            session.createSQLQuery(String.valueOf(buf).trim()).executeUpdate();
            transaction.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Insert a valid Chef object")
    void addValidChef() throws Exception {
        Chef chef = new Chef(SecurityUtil.getId(), "user", "pass", "chef", "chef", "chef@chef.com");
        int expected = chef.getC_id();
        when(chefData.addChef(chef)).thenReturn(chef.getC_id());
        int actual = chefService.addChef(chef);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Insert a null value")
    void addNullChef() throws Exception {
        int expected = -1;
        int actual = chefService.addChef(null);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get Chef by a null username")
    void getNullChefUsername() throws Exception {
        assertNull(chefService.getChefByUsername(null));
    }

    @Test
    @DisplayName("Get Chef by an empty username")
    void getEmptyStringChefUsername() throws Exception {
        assertNull(chefService.getChefByUsername(""));
    }

    @Test
    @DisplayName("Get an existing Chef")
    void getExistingChef() throws Exception {
        Chef expected = new Chef(2, "user2", "pass", "Chef", "Remy", "remy@chef.com");
        when(chefData.getChefByUsername("user2")).thenReturn(expected);
        Chef actual = chefService.getChefByUsername("user2");
        assertEquals(expected, actual);
    }
}
