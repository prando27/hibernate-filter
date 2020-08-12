package com.example.hibernatefilter1;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
public class FolderReference extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "source_folder_id")
    @JsonIgnore
    private Folder sourceFolder;

    @ManyToOne
    @JoinColumn(name = "target_folder_id")
    @JsonIgnore
    private Folder targetFolder;

    @ManyToOne
    @JoinColumn(name = "folder_reference_type_id")
    private FolderReferenceType folderReferenceType;
}
