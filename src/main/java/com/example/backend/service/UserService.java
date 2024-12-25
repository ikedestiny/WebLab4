package com.example.backend.service;

import com.example.backend.authenticaction.BCrypt;
import com.example.backend.model.Users;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;

@Stateless
@Transactional
public class UserService {
    @PersistenceContext(unitName = "mongoPU")
    private EntityManager em;

    public String register(Users user) {
        em.persist(user);
        return "successfully registered user " + user.toString();
    }


    public boolean useExists(Users user) {
        List<Users> all = em.createQuery("select r from Users r", Users.class).getResultList();
        for (Users s : all) {
            if (Objects.equals(s.getEmail(), user.getEmail()) && BCrypt.checkpw(user.getPassword(), s.getPassword())) {
                return true;
            }
        }

        return false;
    }
}
