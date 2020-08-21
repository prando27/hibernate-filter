package com.example.hibernatefilter1;

import lombok.RequiredArgsConstructor;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class DocumentRepositoryCustomImpl implements DocumentRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public List<Document> findByAttribute(String name, String value) {
        entityManager.unwrap(Session.class).enableFilter("jsonBlah")
                .setParameter("paramName", name)
                .setParameter("paramValue", value);

        List<Document> resultList = entityManager.createQuery("SELECT d FROM Document d", Document.class).getResultList();

        entityManager.unwrap(Session.class).disableFilter("jsonBlah");

        if (resultList.isEmpty()) return null;

        return resultList;
    }
}
