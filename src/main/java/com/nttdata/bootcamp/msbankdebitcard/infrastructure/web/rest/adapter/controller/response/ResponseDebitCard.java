package com.nttdata.bootcamp.msbankdebitcard.infrastructure.web.rest.adapter.controller.response;

import com.nttdata.bootcamp.msbankdebitcard.domain.model.DebitCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**.*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDebitCard {
  
  private int httpStatus;
  private DebitCard debitCard;
  private String message;

}