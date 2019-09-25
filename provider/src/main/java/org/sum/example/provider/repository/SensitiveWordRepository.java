package org.sum.example.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.sum.example.provider.domain.po.SensitiveWord;

import java.util.List;

@Repository
public interface SensitiveWordRepository extends JpaRepository<SensitiveWord,Long> {
    @Query("select a.word from SensitiveWord a where a.status = 0")
    List<String> findAllword();
}
