package com.nttdata.bootcamp.msbankdebitcard.infrastructure.producer.adapter.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.nttdata.bootcamp.msbankdebitcard.application.outgoing.SendDebitCardAssociatedAccountPort;
import com.nttdata.bootcamp.msbankdebitcard.domain.model.DebitCardAssociatedAccount;

import lombok.RequiredArgsConstructor;

/**.*/
@Component
@RequiredArgsConstructor
public class DebitCardAssociatedAccountProducerAdapter implements SendDebitCardAssociatedAccountPort {
  
  final  Logger logger = LoggerFactory.getLogger(DebitCardAssociatedAccountProducerAdapter.class);
  
  @Value("${kafka.topic.bank.debit-card-associated-account.create.name}")
  private String topic;

  private  final KafkaTemplate<String, DebitCardAssociatedAccount> kafkaTemplate;
  
  @Override
  public void sendDebitCardAssociatedAccount(DebitCardAssociatedAccount debitCardAssociatedAccount) {
    
    ListenableFuture<SendResult<String, DebitCardAssociatedAccount>> future = kafkaTemplate.send(this.topic, debitCardAssociatedAccount);
    
    future.addCallback(new ListenableFutureCallback<SendResult<String, DebitCardAssociatedAccount>>() {

      @Override
      public void onSuccess(SendResult<String, DebitCardAssociatedAccount> result) {
        logger.info("Message {} has been sent", result);
      }

      @Override
      public void onFailure(Throwable ex) {
        logger.error("Something went wrong with the account balance");
        
      }

    });
  }

}
