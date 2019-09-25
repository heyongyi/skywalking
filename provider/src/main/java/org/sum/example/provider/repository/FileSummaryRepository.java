package org.sum.example.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sum.example.provider.domain.po.FileSummary;

import java.util.Optional;

@Repository
public interface FileSummaryRepository extends JpaRepository<FileSummary, Long> {

    Optional<FileSummary> findByFileId(Long fileId);
}
