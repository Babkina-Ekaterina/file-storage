package caselab.test.file_storage.data.repository;

import caselab.test.file_storage.data.entity.FileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    Page<FileEntity> findAll(Pageable pageable);
}
