package com.example.hibernatefilter1;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@SpringBootApplication
public class HibernateFilter1Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HibernateFilter1Application.class, args);
    }

    private final FolderRepository folderRepository;

    private final DocumentContextRepository documentContextRepository;

    private final FolderReferenceTypeRepository folderReferenceTypeRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var proponentTenantFolderReferenceType = new FolderReferenceType("PROPONENT_TENANT");
        var ownerFolderReferenceType = new FolderReferenceType("OWNER");
        folderReferenceTypeRepository.save(proponentTenantFolderReferenceType);
        folderReferenceTypeRepository.save(ownerFolderReferenceType);

        Folder tenantHouseFolder = new Folder();
        folderRepository.save(tenantHouseFolder);

        Folder ownerHouseFolder = new Folder();
        folderRepository.save(ownerHouseFolder);

        var tenantContext = new DocumentContext("TENANT");
        documentContextRepository.save(tenantContext);

        var ownerContext = new DocumentContext("OWNER");
        documentContextRepository.save(ownerContext);

        var blaContext = new DocumentContext("Blah");
        documentContextRepository.save(blaContext);

        var folder = new Folder();
        folder.getSourceFolderReferences().add(new FolderReference(folder, tenantHouseFolder, proponentTenantFolderReferenceType));
        folder.getSourceFolderReferences().add(new FolderReference(folder, ownerHouseFolder, ownerFolderReferenceType));

        for (int i = 0; i < 10; i++) {
            var document = new Document();

            if (i % 2 == 0) {
                document.setDocumentContext(tenantContext);
                document.setContextExternalId(String.valueOf(i));
            } else {
                document.setDocumentContext(ownerContext);
                document.setContextExternalId(String.valueOf(i));

            }

            folder.addDocument(document);
        }

        folderRepository.save(folder);
    }

    @GetMapping("/1")
    @Transactional
    public ResponseEntity<Folder> findByContext(@RequestParam Long folderId,
                                                @RequestParam String context,
                                                @RequestParam String externalId) {
//        var blaContext = documentContextRepository.findAll().stream().filter(d -> "Blah".equals(d.getName())).findFirst().orElseThrow();
        Folder folder = folderRepository.findByContext(folderId, context, externalId).orElseThrow();

//        folder.getDocuments().forEach(document -> document.setFolder(null));
//        folder.getDocuments().clear();

        return ResponseEntity.ok(folder);
    }

    @GetMapping("/2")
    @Transactional
    public ResponseEntity<Void> findByContext2() {
        //var blaContext = documentContextRepository.findAll().stream().filter(d -> "Blah".equals(d.getName())).findFirst().orElseThrow();
        Folder folder = folderRepository.findByContext2(1L, "Blah").orElseThrow();

        return ResponseEntity.ok().build();
    }
}
