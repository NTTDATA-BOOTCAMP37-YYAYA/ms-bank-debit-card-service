package com.nttdata.bootcamp.msbankdebitcard.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**.*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitCardAssociatedAccount {
	
	private String debitCardAssociatedAccountId;
	private String debidCardId;
	private String accountId;
	private boolean isMainAccount;
	private String debitCardAssociationSate;
	private String debitCardAssociationCreateDate;
	
}
