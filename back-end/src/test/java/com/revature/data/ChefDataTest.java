package com.revature.data;

import com.revature.models.Chef;
import com.revature.utilities.SecurityUtil;
import com.revature.utilities.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import java.io.FileReader;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class ChefDataTest {
    private static final ChefDataDAO chefData = new ChefData();

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
    void getExistingChefId() {
        Chef expected = new Chef(1, "user1", "pass", "Chef", "Boyardee", "boyardee@chef.com");
        Chef actual = chefData.getChefById(1);
        assertEquals(expected, actual);
    }

    @Test
    void getNonexistentChefId() {
        assertNull(chefData.getChefById(-6));
    }

    @Test
    void addValidChef() {
        Chef chef = new Chef(SecurityUtil.getId(), "user", "pass", "Chef", "Chef", "chef@chef.com");
        int expected = chef.getC_id();
        int actual = chefData.addChef(chef);
        assertEquals(expected, actual);
    }

    @Test
    void addInvalidChef() {
        int expected = -1;
        int actual = chefData.addChef(new Chef());
        assertEquals(expected, actual);
    }

    @Test
    void addNullChef() {
        int expected = -1;
        int actual = chefData.addChef(null);
        assertEquals(expected, actual);
    }

    @Test
    void addDuplicateChef() {
        Chef chef = new Chef(4, "user4", "pass", "Chef", "Madl", "madl@chef.com");
        int expected = -1;
        int actual = chefData.addChef(chef);
        assertEquals(expected, actual);
    }

    @Test
    void getExistingChefByUsername() {
        Chef expected = new Chef(1, "user1", "pass", "Chef", "Boyardee", "boyardee@chef.com");
        Chef actual = chefData.getChefByUsername("user1");
        assertEquals(expected, actual);
    }

    @Test
    void getNonexistentChefByUsername() {
        assertNull(chefData.getChefByUsername("nonexistent"));
    }

    @Test
    void getEmptyStringChefUsername() {
        assertNull(chefData.getChefByUsername(""));
    }

    @Test
    void getNullChefUsername() {
        assertNull(chefData.getChefByUsername(null));
    }
}
