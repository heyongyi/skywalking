package org.sum.example.provider.repository;

import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.sum.example.provider.domain.po.FileExtraceWord;

import java.util.List;

@Repository
public interface FileExtraceWordRepository extends JpaRepository<FileExtraceWord, Long> {

    @Query("select a.word from FileExtraceWord a where a.fileId = ?1 and a.wordTypeId > 100")
    List<String> findNerByFileId(Long fileId);

    @Query("select a.word from FileExtraceWord a where a.fileId = ?1 and a.wordTypeId = 2 and a.word not in (select b.word from ExcludeHotword b where b.status=0)")
    List<String> findHotByFileId(Long fileId);

    @Trace
    @Query("select a from FileExtraceWord a where a.fileId = ?1 and a.wordTypeId > 100")
    List<FileExtraceWord> findCompleteNerByFileId(Long fileId);

    @Query("select a from FileExtraceWord a where a.fileId = ?1 and a.wordTypeId = 2 and a.word not in (select b.word from ExcludeHotword b where b.status=0)")
    List<FileExtraceWord> findCompleteHotByFileId(Long fileId);
}
