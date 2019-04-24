package com.knockknock.dto.member;

import java.sql.Date;

import lombok.Data;

@Data
public class ContractDTO {
	private int contractNumber;
	private int memberNumber;
	private int branchNumber;
	private int roomNumber;
	private int period;
	private String isPet;
	private String emergencyNumber;
	private String bankName;
	private String depositor;
	private String memberAccount;
	private Date contractDate;
	private String idNumber;
	private int payDelayAmount;
	private int paneltyAmount;
	private int returnAmount;
	private String memo;
}