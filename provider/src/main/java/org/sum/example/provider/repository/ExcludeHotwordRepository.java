package org.sum.example.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sum.example.provider.domain.po.ExcludeHotword;


@Repository
public interface ExcludeHotwordRepository extends JpaRepository<ExcludeHotword, Long> {

}
