package com.nttdata.bootcamp.msbankdebitcard.infrastructure.web.rest.adapter.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**.*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
  
  private String accountId;
  private String accountNumber;
  private String customerId;
  private String productId;
  private String customerTypeId;
  private String customerCategoryId;
  private String accountDayMovement;
  
}
