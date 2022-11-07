package com.nttdata.bootcamp.msbankdebitcard.application.outgoing;

import com.nttdata.bootcamp.msbankdebitcard.domain.model.DebitCardAssociatedAccount;

public interface SendDebitCardAssociatedAccountPort {

	void sendDebitCardAssociatedAccount(DebitCardAssociatedAccount debitCardAssociatedAccount);
}
