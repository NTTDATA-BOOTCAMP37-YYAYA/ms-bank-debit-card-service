package com.nttdata.bootcamp.msbankdebitcard.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.msbankdebitcard.application.incoming.CreateDebitCardUseCase;
import com.nttdata.bootcamp.msbankdebitcard.application.incoming.FindAccountByIdUseCase;
import com.nttdata.bootcamp.msbankdebitcard.application.incoming.FindAccountByNumberUseCase;
import com.nttdata.bootcamp.msbankdebitcard.application.incoming.FindDebitCardByNumberUseCase;
import com.nttdata.bootcamp.msbankdebitcard.application.outgoing.CreateDebitCardPort;
import com.nttdata.bootcamp.msbankdebitcard.application.outgoing.FindAccountByIdPort;
import com.nttdata.bootcamp.msbankdebitcard.application.outgoing.FindAccountByNumberPort;
import com.nttdata.bootcamp.msbankdebitcard.application.outgoing.FindDebitCardByNumberPort;
import com.nttdata.bootcamp.msbankdebitcard.application.outgoing.SendDebitCardAssociatedAccountPort;
import com.nttdata.bootcamp.msbankdebitcard.domain.model.DebitCard;
import com.nttdata.bootcamp.msbankdebitcard.domain.model.DebitCardAssociatedAccount;
import com.nttdata.bootcamp.msbankdebitcard.infrastructure.web.rest.adapter.api.response.Account;

import reactor.core.publisher.Mono;

/**.*/
@Service
public class DebitCardService implements CreateDebitCardUseCase,
										 FindDebitCardByNumberUseCase,
										 FindAccountByNumberUseCase,
										 FindAccountByIdUseCase{
										 

final  Logger logger = LoggerFactory.getLogger(DebitCardService.class);


@Autowired
private CreateDebitCardPort createDebitCardPort;

@Autowired
private FindDebitCardByNumberPort findDebitCardByNumberPort;

@Autowired
private FindAccountByNumberPort findAccountByNumberPort;

@Autowired
private SendDebitCardAssociatedAccountPort sendDebitCardAssociatedAccountPort;


@Autowired
private FindAccountByIdPort findAccountByIdPort;

@Override
public Mono<DebitCard> createDebitCard(DebitCard debitCard) {
	return createDebitCardPort.createDebitCard(debitCard)
		   .flatMap(newDebitCard -> {
			   	 DebitCardAssociatedAccount debitCardAssociatedAccount = new DebitCardAssociatedAccount();
			   	 debitCardAssociatedAccount.setDebidCardId(newDebitCard.getDebidCardId());
			     debitCardAssociatedAccount.setAccountId(debitCard.getAccountIdMainAssociated());
			     debitCardAssociatedAccount.setMainAccount(Boolean.TRUE);
			     this.sendDebitCardAssociatedAccount(debitCardAssociatedAccount);
			     return Mono.just(newDebitCard);
	        });
}

@Override
public Mono<Account> findAccountByNumber(String accountNumber) {
	return findAccountByNumberPort.findAccountByNumber(accountNumber);
}

@Override
public Mono<DebitCard> findDebitCardByNumber(String debitCardNumber) {
	return findDebitCardByNumberPort.findDebitCardByNumber(debitCardNumber);
}

/**.*/
public DebitCardAssociatedAccount sendDebitCardAssociatedAccount(DebitCardAssociatedAccount debitCardAssociatedAccount) {
  if (debitCardAssociatedAccount != null) {
    logger.info("Send  DebitCardAssociatedAccount  {} ", debitCardAssociatedAccount);
    sendDebitCardAssociatedAccountPort.sendDebitCardAssociatedAccount(debitCardAssociatedAccount);
  }
  return debitCardAssociatedAccount;
}

@Override
public Mono<Account> findAccountById(String accountId) {
	return findAccountByIdPort.findAccountById(accountId);
}


}
