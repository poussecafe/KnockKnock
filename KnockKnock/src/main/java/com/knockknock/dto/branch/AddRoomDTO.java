package com.knockknock.dto.branch;

import java.util.List;

import lombok.Data;

@Data
public class AddRoomDTO {
	private List<Integer> newRoomNumber;
	private int branchNumber;
	private List<String> newRoomGender;
	private List<String> newAllowNumber;
	private List<String> newSpace;
	private List<Integer> newDeposit;
	private List<Integer> newMonthlyRent;
	private List<String> newRentableDate;
	private String roomFacility;
}
