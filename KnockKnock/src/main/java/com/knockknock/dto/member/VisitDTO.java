package com.knockknock.dto.member;

import java.sql.Date;

import lombok.Data;

@Data
public class VisitDTO {
	private int writingNumber;
	private int memberNumber;
	private int branchNumber;
	private Date writeTime;
	private Date reservationTime;
	private String memo;

	private String ischeck;
}
