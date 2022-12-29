package com.test.code.accounts.repositories;

import com.test.code.accounts.domains.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM History s WHERE s.account_id = :accountId ORDER BY id DESC LIMIT 10")
    List<History> findHistoryByAccountId(Long accountId);
}
