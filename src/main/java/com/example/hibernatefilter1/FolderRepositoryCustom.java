package com.example.hibernatefilter1;

import java.util.Optional;

public interface FolderRepositoryCustom {

    Optional<Folder> findByContext(Long id, String context, String externalId);

    Optional<Folder> findByContext2(Long id, String context);
}
