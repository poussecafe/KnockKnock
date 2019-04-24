package com.knockknock.dto.member;

import java.sql.Date;

import lombok.Data;

@Data
public class PayDTO {
	
	private int payCount;
	private int contractNumber;
	private Date payTime;
	private int payAmount;
	private int unpaid;
	
}
