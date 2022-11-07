package com.nttdata.bootcamp.msbankdebitcard.infrastructure.persistence.adapter.mongo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nttdata.bootcamp.msbankdebitcard.application.outgoing.CreateDebitCardPort;
import com.nttdata.bootcamp.msbankdebitcard.application.outgoing.FindDebitCardByNumberPort;
import com.nttdata.bootcamp.msbankdebitcard.domain.enums.States;
import com.nttdata.bootcamp.msbankdebitcard.domain.model.DebitCard;
import com.nttdata.bootcamp.msbankdebitcard.infrastructure.persistence.entity.DebitCardEntity;

import reactor.core.publisher.Mono;

@Component
public class DebitCardRepositoryAdapter implements CreateDebitCardPort,
												   FindDebitCardByNumberPort{

  @Autowired
  private ReactiveMongoDebitCardRepository reactiveMongoDB;

	@Override
	public Mono<DebitCard> findDebitCardByNumber(String debitCardNumber) {
		return reactiveMongoDB.findDebitCardByNumber(debitCardNumber,States.ACTIVE.getValue())
				 .map(DebitCardEntity::toDebitCard);
	}

	@Override
	public Mono<DebitCard> createDebitCard(DebitCard debitCard) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		LocalDateTime now = LocalDateTime.now();
	    String createDate = now.format(formatter);
	    debitCard.setDebitCardCreateDate(createDate);
	    debitCard.setDebitCardState(States.ACTIVE.getValue());
		return reactiveMongoDB.insert(DebitCardEntity.toDebitCardEntity(debitCard))
				  .map(DebitCardEntity::toDebitCard);
	}
	 

	


}
