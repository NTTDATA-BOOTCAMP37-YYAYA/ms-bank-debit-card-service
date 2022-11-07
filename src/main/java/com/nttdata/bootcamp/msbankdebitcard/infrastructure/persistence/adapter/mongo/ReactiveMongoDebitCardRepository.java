package com.nttdata.bootcamp.msbankdebitcard.infrastructure.persistence.adapter.mongo;


import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bootcamp.msbankdebitcard.infrastructure.persistence.entity.DebitCardEntity;

import reactor.core.publisher.Mono;

public interface ReactiveMongoDebitCardRepository extends ReactiveMongoRepository<DebitCardEntity, String>{

  @Query("{'debitCardNumber': ?0, 'debitCardState' : ?1}")
  public Mono<DebitCardEntity> findDebitCardByNumber(String debitCardNumber,String debitCardState);

}