package com.nttdata.bootcamp.msbankdebitcard.infrastructure.persistence.entity;

import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.nttdata.bootcamp.msbankdebitcard.domain.model.DebitCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**.*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document (collection = "DebitCard")
public class DebitCardEntity {
	
	@Id
	private String debidCardId;
	private String debitCardNumber;
	private String debitCardExpirationDate;
	private String debitCardVerificationCode;
	private String accountIdMainAssociated;
	private String debitCardState;
	private String debitCardCreateDate;


	public static DebitCard toDebitCard(DebitCardEntity debitCardEntity){

		DebitCard debitCard = new DebitCard();
		BeanUtils.copyProperties(debitCardEntity, debitCard);
		return debitCard;
	}

	public static DebitCardEntity toDebitCardEntity(DebitCard debitCard){
		DebitCardEntity debitCardEntity = new DebitCardEntity();
		BeanUtils.copyProperties(debitCard, debitCardEntity);
		return debitCardEntity;
	}


}
