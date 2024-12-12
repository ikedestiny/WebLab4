package com.example.backend.service;

import com.example.backend.model.Result;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@Stateless
@Transactional
public class ResultService {
    @PersistenceContext
    private EntityManager em;

    // Add result (make sure the Result entity is properly mapped)
    public void addResult(Result result) {
        em.persist(result);
        em.flush();
    }

    // Get all results
    public List<Result> getAll() {
        return em.createQuery("select r from Result r", Result.class).getResultList();
    }

    public void clearAll(){
        //em.clear();
        em.createNativeQuery("DELETE FROM result").executeUpdate();
    }


}
