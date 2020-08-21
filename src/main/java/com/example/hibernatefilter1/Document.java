package com.example.hibernatefilter1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@TypeDefs(@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class))
@FilterDef(
        name = "jsonBlah",
        parameters = {
                @ParamDef(
                        name = "paramName",
                        type = "string"),
                @ParamDef(
                        name = "paramValue",
                        type = "string")
        }
)
@Filter(name = "jsonBlah", condition = "attributes ->> :paramName = :paramValue")
public class Document extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "folder_id")
    @JsonIgnore
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "document_context_id")
    private DocumentContext documentContext;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> attributes;

    private String contextExternalId;

    public void setFolder(Folder folder) {
        if (this.folder == null) {
            this.folder = folder;
        }
    }
}
