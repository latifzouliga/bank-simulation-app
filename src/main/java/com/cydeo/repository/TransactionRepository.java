package com.cydeo.repository;

import com.cydeo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query(value = "SELECT * FROM transations ORDER BY creation_date DESC LIMIT 10",nativeQuery = true)
    List<Transaction> findLast10Transactions();

    @Query("SELECT t FROM Transaction t where t.sender.id = ?1 OR t.receiver.id = ?1")
    List<Transaction> findTransactionListByAccount(Long id);
}
