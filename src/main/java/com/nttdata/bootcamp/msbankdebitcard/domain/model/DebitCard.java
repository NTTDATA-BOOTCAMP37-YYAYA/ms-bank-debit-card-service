package com.nttdata.bootcamp.msbankdebitcard.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**.*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitCard {
	
	private String debidCardId;
	private String debitCardNumber;
	private String debitCardExpirationDate;
	private String debitCardVerificationCode;
	private String accountIdMainAssociated;
	private String debitCardState;
	private String debitCardCreateDate;
}
