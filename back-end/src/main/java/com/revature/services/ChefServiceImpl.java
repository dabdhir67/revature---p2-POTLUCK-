package com.revature.services;

import com.revature.data.ChefDataDAO;
import com.revature.models.Chef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChefServiceImpl implements ChefService {
    @Autowired
    private ChefDataDAO chefData;

    @Override
    public Chef getChefById(int id) {
        if (id <= 0) return null;
        return chefData.getChefById(id);
    }

    @Override
    public Chef getChefByUsername(String username) {
        if (username == null || username.isEmpty()) return null;
        return chefData.getChefByUsername(username);
    }

    @Override
    public List<Chef> getAllChefs() {
        return null;
    }

    @Override
    public int addChef(Chef chef) {
        if (chef == null) return -1;
        return chefData.addChef(chef);
    }

    @Override
    public int updateChef(Chef chef) {
        return 0;
    }

    @Override
    public int deleteChef(Chef chef) {
        return 0;
    }
}
