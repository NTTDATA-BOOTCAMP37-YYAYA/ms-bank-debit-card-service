package com.nttdata.bootcamp.msbankdebitcard.application.outgoing;

import com.nttdata.bootcamp.msbankdebitcard.infrastructure.web.rest.adapter.api.response.Account;

import reactor.core.publisher.Mono;

public interface FindAccountByIdPort {

	Mono<Account> findAccountById(String accountId);
}
