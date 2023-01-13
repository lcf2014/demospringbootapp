package com.luafaria.demorestapi.repository;

import com.luafaria.demorestapi.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<TransactionItem, String> {

  @Query(" $gt: new Date(dateq.getTime() - 6000")
  List<Transaction> findAllForLast60Seconds();
}
