package com.example.hibernatefilter1;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Document extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "folder_id")
    @JsonIgnore
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "document_context_id")
    private DocumentContext documentContext;

    private String contextExternalId;

    public void setFolder(Folder folder) {
        if (this.folder == null) {
            this.folder = folder;
        }
    }
}
