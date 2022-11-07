package com.nttdata.bootcamp.msbankdebitcard.application.incoming;

import com.nttdata.bootcamp.msbankdebitcard.domain.model.DebitCard;

import reactor.core.publisher.Mono;

public interface FindDebitCardByNumberUseCase {

	Mono<DebitCard> findDebitCardByNumber(String debitCardNumber);
}
