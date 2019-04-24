package com.knockknock.dto.branch;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RoomDTO {
	private List<Integer> roomNumber;
	private int branchNumber;
	private List<String> roomGender;
	private List<String> allowNumber;
	private List<String> space;
	private List<Integer> deposit;
	private List<Integer> monthlyRent;
	private List<String> rentableDate;
	private String roomFacility;
//	private String imageRoom;
	
	private List<MultipartFile> roomImages;
}
