package com.knockknock.dto.member;

import java.sql.Date;

import lombok.Data;

@Data
public class MemberContractVDTO {
	
	private int memberNumber;
	private String grade;
	private String favorite1;
	private String favorite2;
	private String favorite3;
	private String email;
	private String password;
	private String name;
	private String nickname;
	private Date birth;
	private String gender;
	private String address;
	private String addressDetail;
	private String addressName;
	private String phoneNumber;
	private Date joinTime;
	private Date leaveTime;
	private String imageProfile;
	private int contractNumber;
	private int branchNumber;
	private int roomNumber;
	private String isPet;
	private String emergencyNumber;
	private String bankName;
	private String depositor;
	private String memberAccount;
	private Date contractDate;
	private int period;
	private String idNumber;
	private int payDelayAmount;
	private int paneltyAmount;
	private int returnAmount;
	private String memo;
	private int payCount;
	private Date payTime;
	private int payAmount;
	private int unpaid;
}
