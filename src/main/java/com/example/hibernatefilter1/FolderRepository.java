package com.example.hibernatefilter1;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long>, FolderRepositoryCustom {
}
