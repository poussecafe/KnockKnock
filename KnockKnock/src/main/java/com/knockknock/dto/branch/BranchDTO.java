package com.knockknock.dto.branch;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BranchDTO {
	private int branchNumber;
	private String theme;
	private String bankName;
	private String depositor;
	private String branchAccount;
	private String gender;
	private String branchComment;
	private String address;
	private String addressDetail;
	private String addressName;
	private String branchType;
	private String isParking;
	private String isElevator;
	private String facility;
	private String pet;
	private int maximumResident;
	private String rule;
	private String imageLiving;
	private String imageKitchin;
	private String imageBathroom;
	private String imageETC;
	
	private List<MultipartFile> branchImages;
}

