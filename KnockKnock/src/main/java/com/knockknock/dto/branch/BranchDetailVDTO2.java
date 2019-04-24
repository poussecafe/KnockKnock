package com.knockknock.dto.branch;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;
//from 인재 categoryRoomSearch
@Data
public class BranchDetailVDTO2 implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String address;
	private String theme;
	private String gender;
	private String isParking;
	private String isElevator;
	private String pet;
	private String branchType;
		
	private ArrayList<String> searchKeyWord;
	private ArrayList<String> genderCategory;
	private ArrayList<String> petCategory;
	private ArrayList<String> branchTypeCategory;
}
