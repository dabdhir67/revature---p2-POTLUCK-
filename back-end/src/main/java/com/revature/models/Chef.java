package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.utilities.SecurityUtil;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Component
@Entity
@Table(name = "chef")
public class Chef {
    @Id
    @Column(name = "c_id")
    @JsonIgnore
    private int c_id;

    @Column(name = "username")
    private String username;

    @Column(name = "passkey")
    @JsonIgnore
    private String passkey;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    public Chef() {
        super();
        c_id = SecurityUtil.getId();
    }

    public Chef(String username, String passkey, String firstName, String lastName, String email) {
        this.username = username;
        this.passkey = passkey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Chef(int c_id, String username, String passkey, String firstName, String lastName, String email) {
        this.c_id = c_id;
        this.username = username;
        this.passkey = passkey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasskey() {
        return passkey;
    }

    public void setPasskey(String passkey) {
        this.passkey = passkey;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chef chef = (Chef) o;

        if (c_id != chef.c_id) return false;
        if (!username.equals(chef.username)) return false;
        if (!passkey.equals(chef.passkey)) return false;
        if (!firstName.equals(chef.firstName)) return false;
        if (!lastName.equals(chef.lastName)) return false;
        return email.equals(chef.email);
    }

    @Override
    public int hashCode() {
        int result = c_id;
        result = 31 * result + username.hashCode();
        result = 31 * result + passkey.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Chef{" +
                "c_id=" + c_id +
                ", username='" + username + '\'' +
                ", passkey='" + passkey + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public boolean isValid() {
        return username == null || username.isEmpty() ||
                firstName == null || firstName.isEmpty() ||
                lastName == null || lastName.isEmpty() ||
                email == null || email.isEmpty();
    }
}
