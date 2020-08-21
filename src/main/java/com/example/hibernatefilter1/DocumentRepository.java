package com.example.hibernatefilter1;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long>, DocumentRepositoryCustom {
}
