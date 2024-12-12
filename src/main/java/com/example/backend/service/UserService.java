package com.example.backend.service;

import com.example.backend.model.Users;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Stateless
@Transactional
public class UserService {
    @PersistenceContext
    private EntityManager em;

    public String register(Users user){
        em.persist(user);
        return "successfully registered user "+user.toString();
    }
}
