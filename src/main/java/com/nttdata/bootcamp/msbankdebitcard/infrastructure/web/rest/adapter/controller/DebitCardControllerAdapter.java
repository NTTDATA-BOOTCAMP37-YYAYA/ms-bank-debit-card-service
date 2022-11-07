package com.nttdata.bootcamp.msbankdebitcard.infrastructure.web.rest.adapter.controller;


import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.msbankdebitcard.application.incoming.CreateDebitCardUseCase;
import com.nttdata.bootcamp.msbankdebitcard.application.incoming.FindAccountByIdUseCase;
import com.nttdata.bootcamp.msbankdebitcard.application.incoming.FindDebitCardByNumberUseCase;
import com.nttdata.bootcamp.msbankdebitcard.domain.model.DebitCard;
import com.nttdata.bootcamp.msbankdebitcard.infrastructure.web.rest.adapter.controller.response.ResponseDebitCard;

import reactor.core.publisher.Mono;

/**.*/
@RestController
@RequestMapping("/debitCard")
public class DebitCardControllerAdapter {

  final Logger logger = LoggerFactory.getLogger(DebitCardControllerAdapter.class);
  
  @Autowired
  private  CreateDebitCardUseCase createDebitCardUseCase;
  @Autowired
  private  FindDebitCardByNumberUseCase findDebitCardByNumberUseCase;
  @Autowired
  private FindAccountByIdUseCase findAccountByIdUseCase;

  /**.*/
  @PostMapping()
  public Mono<ResponseEntity<ResponseDebitCard>> createDebitCard(@RequestBody DebitCard debitCard) {
	  
	  
    return   findDebitCardByNumberUseCase.findDebitCardByNumber(debitCard.getDebitCardNumber())
    		 .flatMap(findDebitCard ->Mono.just(new ResponseEntity<ResponseDebitCard>(
    	               new ResponseDebitCard(HttpStatus.SC_OK, findDebitCard, "Debit card already exists"),
    	               null, HttpStatus.SC_OK)))
    		 .switchIfEmpty(
    				 findAccountByIdUseCase.findAccountById(debitCard.getAccountIdMainAssociated())
    				 .flatMap(c -> createDebitCardUseCase.createDebitCard(debitCard)
    						 	  .flatMap(newDebitCard-> Mono.just(new ResponseEntity<ResponseDebitCard>(
    				    					new ResponseDebitCard(HttpStatus.SC_OK, newDebitCard, "Debit has been created"),
    					                    null, HttpStatus.SC_OK)))
    						 	  .switchIfEmpty(Mono.just(new ResponseEntity<ResponseDebitCard>(
									        new ResponseDebitCard(HttpStatus.SC_NOT_FOUND, null, "Debit has not beean created"),
									        null, HttpStatus.SC_NOT_FOUND))))
    				 .switchIfEmpty(Mono.just(new ResponseEntity<ResponseDebitCard>(
									        new ResponseDebitCard(HttpStatus.SC_NOT_FOUND, null, "Account not found"),
									        null, HttpStatus.SC_NOT_FOUND))))
    		 .onErrorResume(e->Mono.just(new ResponseEntity<ResponseDebitCard>(
                     new ResponseDebitCard(HttpStatus.SC_INTERNAL_SERVER_ERROR, null, e.getMessage()),
                     null, HttpStatus.SC_INTERNAL_SERVER_ERROR)));
  }

  @GetMapping("/findDebitCardByNumber/{debitCardNumber}")
  public Mono<ResponseEntity<ResponseDebitCard>> findDebitCardByNumber( @PathVariable("debitCardNumber") String debitCardNumber) {
	  return findDebitCardByNumberUseCase.findDebitCardByNumber(debitCardNumber)
			  .flatMap(findDebitCard ->Mono.just(new ResponseEntity<ResponseDebitCard>(
   	               new ResponseDebitCard(HttpStatus.SC_OK, findDebitCard, "Debit card has been found"),
   	               null, HttpStatus.SC_OK)))
			  .switchIfEmpty(Mono.just(new ResponseEntity<ResponseDebitCard>(
	   	               new ResponseDebitCard(HttpStatus.SC_NOT_FOUND, null, "Debit card has not been found"),
	   	               null, HttpStatus.SC_NOT_FOUND)))
			  .onErrorResume(e->Mono.just(new ResponseEntity<ResponseDebitCard>(
	                     new ResponseDebitCard(HttpStatus.SC_INTERNAL_SERVER_ERROR, null, e.getMessage()),
	                     null, HttpStatus.SC_INTERNAL_SERVER_ERROR)));
  }

}
