package com.nttdata.bootcamp.msbankdebitcard.infrastructure.web.rest.adapter.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.bootcamp.msbankdebitcard.application.outgoing.FindAccountByIdPort;
import com.nttdata.bootcamp.msbankdebitcard.application.outgoing.FindAccountByNumberPort;
import com.nttdata.bootcamp.msbankdebitcard.infrastructure.web.rest.adapter.api.enums.HttpStatusCodes;
import com.nttdata.bootcamp.msbankdebitcard.infrastructure.web.rest.adapter.api.response.Account;
import com.nttdata.bootcamp.msbankdebitcard.infrastructure.web.rest.adapter.api.response.ResponseAccount;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import reactor.core.publisher.Mono;

/**.*/
@Component
public class AccountAdapter implements FindAccountByNumberPort,
									   FindAccountByIdPort{

  final  Logger logger = LoggerFactory.getLogger(AccountAdapter.class);

  @Value("${client.bank.account.url}")
  private String url;

  private WebClient client = WebClient.create(url);

  @Override
  @CircuitBreaker(name = "", fallbackMethod = "findAccountByNumberAlternative")
  public Mono<Account> findAccountByNumber(String accountNumber) {
    Mono<Account> response = client.get()
        .uri(url.concat("/findAccountByNumber/{accountNumber}"), accountNumber)
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError, clientResponse -> 
        Mono.error(new Exception("Error 400 findAccountByNumber")))
        .onStatus(HttpStatus::is5xxServerError, clientResponse ->  
        Mono.error(new Exception("Error 500 findAccountByNumber")))
        .toEntity(ResponseAccount.class)
        .flatMap(r -> r.getBody().getHttpStatus() == HttpStatusCodes.STATUS_NO_FOUND.getValue() 
            ? Mono.error(new Exception("Error 404 findAccountByNumber"))
            : Mono.just(r.getBody().getAccount()));
    return response;
  }
  

  public Mono<Account> findAccountByNumberAlternative(String accountNumber, Exception e) {
    return Mono.error(new Exception(e.getMessage()));
  }


@Override
@CircuitBreaker(name = "", fallbackMethod = "findAccountByIdAlternative")
public Mono<Account> findAccountById(String accountId) {
	 Mono<Account> response = client.get()
		        .uri(url.concat("/findAccountById/{accountId}"), accountId)
		        .retrieve()
		        .onStatus(HttpStatus::is4xxClientError, clientResponse -> 
		        Mono.error(new Exception("Error 400 findAccountById")))
		        .onStatus(HttpStatus::is5xxServerError, clientResponse ->  
		        Mono.error(new Exception("Error 500 findAccountById")))
		        .toEntity(ResponseAccount.class)
		        .flatMap(r -> r.getBody().getHttpStatus() == HttpStatusCodes.STATUS_NO_FOUND.getValue() 
	            ? Mono.error(new Exception("Error 404 findAccountByNumber"))
	            : Mono.just(r.getBody().getAccount()));
		    return response;
}


public Mono<Account> findAccountByIdAlternative(String accountNumber, Exception e) {
  return Mono.error(new Exception(e.getMessage()));
}

}
