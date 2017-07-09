package ro.vavedem.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ro.vavedem.persistence.entities.RequestDocument;
import ro.vavedem.projections.ProjWithFilename;

public interface RequestDocumentRepository extends CrudRepository<RequestDocument, Long> {

    Page<RequestDocument> findAll(Pageable pageable);

    RequestDocument findByFilenameAndFileType(String filename, String fileType);

    Page<ProjWithFilename> findByFilenameContains(String filename, Pageable pageable);

    Page<ProjWithFilename> findByFilenameContainsAndServerLocation(String filename, String serverLocation, Pageable pageable);

    Page<RequestDocument> findByFilename(String filename, Pageable pageable);

    Page<RequestDocument> findByFilenameAndFileType(String filename, String fileType, Pageable pageable);

    Page<RequestDocument> findByServerLocation(String serverLocation, Pageable pageable);
}
