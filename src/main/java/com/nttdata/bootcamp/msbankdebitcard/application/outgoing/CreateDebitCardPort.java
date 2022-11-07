package com.nttdata.bootcamp.msbankdebitcard.application.outgoing;

import com.nttdata.bootcamp.msbankdebitcard.domain.model.DebitCard;

import reactor.core.publisher.Mono;

public interface CreateDebitCardPort {

	Mono<DebitCard> createDebitCard(DebitCard debitCard);
}
