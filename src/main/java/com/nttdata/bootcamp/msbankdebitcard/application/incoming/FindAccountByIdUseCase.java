package com.nttdata.bootcamp.msbankdebitcard.application.incoming;

import com.nttdata.bootcamp.msbankdebitcard.infrastructure.web.rest.adapter.api.response.Account;

import reactor.core.publisher.Mono;

public interface FindAccountByIdUseCase {

	Mono<Account> findAccountById(String accountId);
}
