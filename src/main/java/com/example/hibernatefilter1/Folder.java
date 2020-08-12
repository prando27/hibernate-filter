package com.example.hibernatefilter1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@FilterDef(
        name = "blah",
        parameters = {
                @ParamDef(
                        name = "context",
                        type = "string"),
                @ParamDef(
                        name = "externalId",
                        type = "string"
                )
        }
)
@FilterDef(
        name = "sourceFolderReference",
        parameters = {
                @ParamDef(
                        name = "folderReferenceType",
                        type = "string")
        }
)
public class Folder extends BaseEntity {

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    @Filter(
            name = "blah",
            condition = "document_context_id = (select document_context.id from document_context where document_context.name = :context) " +
                    "and context_external_id = :externalId "
    )
    private List<Document> documents = new ArrayList<>();

    @Filter(
            name = "sourceFolderReference",
            condition = "folder_reference_type_id = (select frt.id from folder_reference_type frt where frt.name = :folderReferenceType) "
    )
    @OneToMany(mappedBy = "sourceFolder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FolderReference> sourceFolderReferences = new ArrayList<>();

    public void addDocument(Document document) {
        document.setFolder(this);
        this.documents.add(document);
    }
}
