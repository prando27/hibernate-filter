package com.example.hibernatefilter1;

import java.util.List;

public interface DocumentRepositoryCustom {

    List<Document> findByAttribute(String name, String value);
}
