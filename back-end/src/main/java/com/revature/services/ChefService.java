package com.revature.services;

import com.revature.models.Chef;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public interface ChefService {
    Logger logger = LogManager.getLogger(ChefServiceImpl.class);
    Chef getChefById(int id);
    Chef getChefByUsername(String username);
    List<Chef> getAllChefs();
    int addChef(Chef chef);
    int updateChef(Chef chef);
    int deleteChef(Chef chef);
}
