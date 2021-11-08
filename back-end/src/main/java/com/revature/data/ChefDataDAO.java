package com.revature.data;

import com.revature.models.Chef;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public interface ChefDataDAO {
    Logger logger = LogManager.getLogger(ChefData.class);
    Chef getChefById(int id);
    Chef getChefByUsername(String username);
    List<Chef> getAllChefs();
    int addChef(Chef chef);
    int updateChef(Chef chef);
    int deleteChef(Chef chef);
}
