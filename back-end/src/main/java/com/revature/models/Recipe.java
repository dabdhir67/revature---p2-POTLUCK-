package com.revature.models;

import com.revature.utilities.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;

@Component
@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @Column(name = "r_id")
    private int r_id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Autowired
    @ManyToOne
    @JoinColumn(name = "c_id")
    private Chef c_id;

    public Recipe() {
        super();
        r_id = SecurityUtil.getId();
    }

    public Recipe(int r_id, String title, String body, Timestamp dateCreated, Chef c_id) {
        this.r_id = r_id;
        this.title = title;
        this.body = body;
        this.dateCreated = dateCreated;
        this.c_id = c_id;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Chef getC_id() {
        return c_id;
    }

    public void setC_id(Chef c_id) {
        this.c_id = c_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (r_id != recipe.r_id) return false;
        if (!title.equals(recipe.title)) return false;
        if (!body.equals(recipe.body)) return false;
        if (!dateCreated.equals(recipe.dateCreated)) return false;
        return c_id.equals(recipe.c_id);
    }

    @Override
    public int hashCode() {
        int result = r_id;
        result = 31 * result + title.hashCode();
        result = 31 * result + body.hashCode();
        result = 31 * result + dateCreated.hashCode();
        result = 31 * result + c_id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "r_id=" + r_id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", dateCreated=" + dateCreated +
                ", c_id=" + c_id +
                '}';
    }
}
