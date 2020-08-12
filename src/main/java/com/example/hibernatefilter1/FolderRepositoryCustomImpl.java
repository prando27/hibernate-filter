package com.example.hibernatefilter1;

import lombok.RequiredArgsConstructor;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class FolderRepositoryCustomImpl implements FolderRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public Optional<Folder> findByContext(Long id, String context, String externalId) {
        Filter filter = entityManager.unwrap(Session.class).enableFilter("blah");
        filter.setParameter("context", context);
        filter.setParameter("externalId", externalId);

        List<Folder> resultList = entityManager.createQuery("SELECT f " +
                "FROM Folder f " +
                "LEFT JOIN FETCH f.documents d " +
                "LEFT JOIN FETCH d.documentContext " +
                "WHERE f.id = :id ", Folder.class)
                .setParameter("id", id)
                .getResultList();

        entityManager.unwrap(Session.class).disableFilter("blah");

        if (resultList.isEmpty()) return Optional.empty();

        return Optional.ofNullable(folder(id));
    }

    public Folder folder(Long id) {
        entityManager.unwrap(Session.class).enableFilter("sourceFolderReference")
                .setParameter("folderReferenceType", "PROPONENT_TENANT");

        List<Folder> resultList = entityManager.createQuery("SELECT f " +
                "FROM Folder f " +
                "LEFT JOIN FETCH f.sourceFolderReferences sfr " +
                "LEFT JOIN FETCH sfr.folderReferenceType " +
                "WHERE f.id = :id ", Folder.class)
                .setParameter("id", id)
                .getResultList();

        entityManager.unwrap(Session.class).disableFilter("sourceFolderReference");

        if (resultList.isEmpty()) return null;

        return resultList.get(0);
    }

    @Override
    public Optional<Folder> findByContext2(Long id, String context) {
        List<Folder> resultList = entityManager.createQuery("SELECT f " +
                "FROM Folder f " +
                "LEFT JOIN FETCH f.documents d ON d.documentContext.name = :context " +
                "WHERE f.id = :id ", Folder.class)
                .setParameter("id", id)
                .setParameter("context", context)
                .getResultList();

        if (resultList.isEmpty()) return Optional.empty();

        return Optional.of(resultList.get(0));
    }
}
