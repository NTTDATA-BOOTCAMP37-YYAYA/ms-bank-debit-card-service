package com.nttdata.bootcamp.msbankdebitcard.infrastructure.web.rest.adapter.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**.*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAccount {
  
  private int httpStatus;
  private Account account;
  private String message;

}