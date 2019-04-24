package com.knockknock.service;

import java.io.File;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.knockknock.dto.branch.AddRoomDTO;
import com.knockknock.dto.branch.BranchDTO;
import com.knockknock.dto.branch.RoomDTO;
import com.knockknock.dto.branch.roomVDTO;
import com.knockknock.dto.event.EventDTO;
import com.knockknock.dto.event.EventJoinMemberVDTO;
import com.knockknock.dto.event.EventVDTO;
import com.knockknock.dto.member.ContractDTO;
import com.knockknock.dto.member.MemberContractVDTO;
import com.knockknock.dto.member.MemberDTO;
import com.knockknock.dto.member.VisitVDTO;
import com.knockknock.mapper.AdminMapper;

@Service
public class AdminService {

	@Autowired
	public AdminMapper adminMapper;

	// event

	public ArrayList<EventVDTO> eventList() {
		for (int i = 0; i < adminMapper.eventListView().size(); i++) {
			if (adminMapper.eventListView().get(i).getCancelReason() == null) {
				adminMapper.eventListView().get(i).setCancelReason("");
			} else {
				adminMapper.eventListView().get(i).setCancelReason("취소됨");
			}
		}
		return adminMapper.eventListView();
	}

	public void eventWrite(String title, String content, Date eventStartTime, Date eventEndTime, Date acceptStartTime,
			Date acceptEndTime, int recruitMaxNumber, List<MultipartFile> eventImage, Authentication authentication) {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String email = user.getUsername();
		int memberNumber = adminMapper.getMemberNumber(email);
		// 이메일로 멤버넘버 가져와서 넘겨줘야함
		adminMapper.eventWrite(memberNumber, title, content, eventStartTime, eventEndTime, acceptStartTime,
				acceptEndTime, recruitMaxNumber);

		int writingNumber = Collections.max(adminMapper.getWritingNumber());

		String resourceToString;
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.indexOf("nux") >= 0) {
			resourceToString = "/project/knockknock/knockknock/KnockKnock/src/main/resources/static/images/event/"
					+ writingNumber;
		} else {
			resourceToString = System.getProperty("user.dir") + "/src/main/resources/static/images/event/"
					+ writingNumber;
		}

		File EventUploadPath = new File(resourceToString);

		if (EventUploadPath.exists() == false) {
			EventUploadPath.mkdirs();
		}

		int Numbering = 0;
		String imageName = "";

		for (MultipartFile multipartFile : eventImage) {
			if (multipartFile.getOriginalFilename().lastIndexOf(".") >= 0 ? true : false) {
				String extension = multipartFile.getOriginalFilename()
						.substring(multipartFile.getOriginalFilename().lastIndexOf("."));
				if (extension.equals(".jpg") || extension.equals(".png") || extension.equals(".jpeg")
						|| extension.equals(".gif")) {
					String uploadFileName;
					if (Numbering == 0) {
						uploadFileName = "mainImage" + extension;
						Numbering++;
					} else {
						uploadFileName = Numbering++ + extension;
					}
					try {
						File saveFile = new File(EventUploadPath, uploadFileName);
						imageName = uploadFileName;
						multipartFile.transferTo(saveFile);
					} catch (Exception e) {
						System.out.println("실패");
						e.printStackTrace();
					} // end catch
					adminMapper.setEventImageName(writingNumber, imageName);
				}
			}
		}
	}
//	public void eventImageUpload(int writingNumber, EventDTO eventDTO) {
//		int writingNumber = Collections.max(adminMapper.getWritingNumber());
//		
//		String resourceToString;
//		String OS = System.getProperty("os.name").toLowerCase();
//		if (OS.indexOf("nux") >= 0) {
//			resourceToString = "/project/knockknock/knockknock/KnockKnock/src/main/resources/static/images/event/"
//					+ writingNumber;
//		} else {
//			resourceToString = System.getProperty("user.dir") + "/src/main/resources/static/images/event/"
//					+ writingNumber;
//		}
//
//		File EventUploadPath = new File(resourceToString);
//
//		if (EventUploadPath.exists() == false) {
//			EventUploadPath.mkdirs();
//		}
//
//		int Numbering = 0;
//
//		for (MultipartFile multipartFile : eventDTO.getEventImage()) {
//			String extension = multipartFile.getOriginalFilename()
//					.substring(multipartFile.getOriginalFilename().lastIndexOf("."));
//			String uploadFileName;
//			if (Numbering == 0) {
//				uploadFileName = "mainImage" + extension;
//				Numbering++;
//			} else {
//				uploadFileName = Numbering++ + extension;
//			}
//			try {
//				File saveFile = new File(EventUploadPath, uploadFileName);
//				multipartFile.transferTo(saveFile);
//			} catch (Exception e) {
//				System.out.println("실패");
//				e.printStackTrace();
//			} // end catch
//		}
//	}

	public EventVDTO eventView(int writingNumber) {
		adminMapper.eventViewHit(writingNumber);
		return adminMapper.eventView(writingNumber);
	}

	public String eventImagePath(int writingNumber) {
		String path;
		String name = adminMapper.getEventImageName(writingNumber);
		if (name != null) {
			path = "/images/event/" + writingNumber + "/" + name;
		} else {
			path = null;
		}
		return path;
	}

	public ArrayList<EventJoinMemberVDTO> getEventJoinMember(int writingNumber) {
		return adminMapper.getEventJoinMember(writingNumber);
	}

	public EventDTO eventModifyView(int writingNumber) {
		return adminMapper.eventModifyView(writingNumber);
	}

	public void eventModify(int writingNumber, String title, String content, Date eventStartTime, Date eventEndTime,
			Date acceptStartTime, Date acceptEndTime, int recruitMaxNumber, List<MultipartFile> eventImage,
			Authentication authentication) {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		String email = user.getUsername();
		int memberNumber = adminMapper.getMemberNumber(email);

		adminMapper.eventModify(writingNumber, memberNumber, title, content, eventStartTime, eventEndTime,
				acceptStartTime, acceptEndTime, recruitMaxNumber);

		String resourceToString;
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.indexOf("nux") >= 0) {
			resourceToString = "/project/knockknock/knockknock/KnockKnock/src/main/resources/static/images/event/"
					+ writingNumber;
		} else {
			resourceToString = System.getProperty("user.dir") + "/src/main/resources/static/images/event/"
					+ writingNumber;
		}

		File EventUploadPath = new File(resourceToString);

		if (EventUploadPath.exists() == false) {
			EventUploadPath.mkdirs();
		}

		int Numbering = 0;
		String imageName = "";

		for (MultipartFile multipartFile : eventImage) {
			if (multipartFile.getOriginalFilename().lastIndexOf(".") >= 0 ? true : false) {
				String extension = multipartFile.getOriginalFilename()
						.substring(multipartFile.getOriginalFilename().lastIndexOf("."));
				if (extension.equals(".jpg") || extension.equals(".png") || extension.equals(".jpeg")
						|| extension.equals(".gif")) {
					String uploadFileName;
					if (Numbering == 0) {
						uploadFileName = "mainImage" + extension;
						Numbering++;
					} else {
						uploadFileName = Numbering++ + extension;
					}
					try {
						File saveFile = new File(EventUploadPath, uploadFileName);
						imageName = uploadFileName;
						multipartFile.transferTo(saveFile);
					} catch (Exception e) {
						System.out.println("실패");
						e.printStackTrace();
					} // end catch
					adminMapper.setEventImageName(writingNumber, imageName);
				}
			}
		}

	}

	public void eventCancel(int writingNumber, String cancelReason) {
		adminMapper.eventCancel(writingNumber, cancelReason);
	}

	public void eventJoinDelete(int writingNumber) {
		adminMapper.eventJoinDelete(writingNumber);
	}

	public void eventDelete(int writingNumber) {
		adminMapper.eventDelete(writingNumber);
	}

	// member

	public ArrayList<MemberDTO> memberListView(String keyword) {
		return adminMapper.memberListView(keyword);
	}

	public MemberContractVDTO memberView(int memberNumber) {
		return adminMapper.memberView(memberNumber);
	}

	public void contractRegist(int memberNumber, int branchNumber, int roomNumber, int period, String isPet,
			String emergencyNumber, String bankName, String depositor, String memberAccount, Date contractDate,
			String idNumber, String memo) {
		int payDelayAmount = 0;
		int paneltyAmount = 0;
		int returnAmount = 0;
		adminMapper.contractRegist(memberNumber, branchNumber, roomNumber, period, isPet, emergencyNumber, bankName,
				depositor, memberAccount, contractDate, idNumber, payDelayAmount, paneltyAmount, returnAmount, memo);
	}

	public ContractDTO contractModifyView(int contractNumber) {
		return adminMapper.contractModifyView(contractNumber);
	}

	public void contractModify(ContractDTO contractDTO) {
		adminMapper.contractModify(contractDTO);
	}

	public int getContractNumber() {
		ArrayList<Integer> contractNumber = adminMapper.getContractNumber();
		int maxContractNumber = Collections.max(contractNumber);
		return maxContractNumber;
	}

	public int getDeposit(int roomNumber, int branchNumber) {
		int deposit = adminMapper.getDeposit(roomNumber, branchNumber);
		return deposit;
	}

	public void setReturnAmount(int contractNumber, int returnAmount) {
		adminMapper.setReturnAmount(contractNumber, returnAmount);
	}

	// visit

	public ArrayList<VisitVDTO> visitList() {
		return adminMapper.visitListView();
	}

	public ArrayList<VisitVDTO> visitListOfMember(int memberNumber) {
		return adminMapper.visitListViewOfMember(memberNumber);
	}

	public VisitVDTO visitView(int writingNumber) {
		return adminMapper.visitView(writingNumber);
	}

	public void visitCheck(int writingNumber) {
		adminMapper.visitCheck(writingNumber);
	}

	// Branch

	public void branchRegist(BranchDTO branchDTO, RoomDTO roomDTO) {
		int maximumResident = 0;
//		List<String> allowNumber = roomDTO.getAllowNumber();
		for (int i = 0; i < roomDTO.getRoomNumber().size()
				&& !(roomDTO.getRoomNumber().get(i) == null || roomDTO.getDeposit().get(i) == null
						|| roomDTO.getMonthlyRent().get(i) == null || roomDTO.getRentableDate().equals(null)
						|| roomDTO.getSpace().get(i).equals(null) || roomDTO.getSpace().get(i) == ""); i++) {
			switch (roomDTO.getAllowNumber().get(i).toString()) {
			case "1인실":
				maximumResident += 1;
				break;
			case "2인실":
				maximumResident += 2;
				break;
			case "3인실":
				maximumResident += 3;
				break;
			case "4인실":
				maximumResident += 4;
				break;
			default:
				break;
			}
		}
		branchDTO.setMaximumResident(maximumResident);
		adminMapper.branchRegist(branchDTO);
	}

	public int getBranchNumber() {
		ArrayList<Integer> branchNumber = adminMapper.getBranchNumber();
		int maxBranchNumber = Collections.max(branchNumber);
		return maxBranchNumber;
	}

	public void roomRegist(int branchNumber, RoomDTO roomDTO) {
		String dateForm = "^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$";

		// String으로 받은 날짜
		Iterator<String> iterator = roomDTO.getRentableDate().iterator();
		List<Date> rentableDate = new ArrayList<Date>();

		// String으로 받은 날짜를 -> util.date -> sql.date로 변환. String -> sql.date는 잘 안되더라
		while (iterator.hasNext()) {
			String tmp = iterator.next();
			if (Pattern.matches(dateForm, tmp)) {
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				try {
					java.util.Date parsed = (java.util.Date) formatter.parse(tmp);
					rentableDate.add(new Date(parsed.getTime()));
				} catch (ParseException e) {
					rentableDate.add(null);
				}
			} else {
				rentableDate.add(null);
			}
		}

		// 날짜를 배열에 넣고 입력 안한 날짜는 null로 처리
		Iterator<Date> sqlDate = rentableDate.iterator();
		Date[] roomRentableDate = new Date[roomDTO.getRoomNumber().size()];
		for (int i = 0; i < roomRentableDate.length; i++) {
			if (sqlDate.hasNext()) {
				roomRentableDate[i] = sqlDate.next();
			} else {
				roomRentableDate[i] = null;
			}
		}

		// 입력한 줄까지만 방 정보가 들어감. 누락된 입력란이 존재하면 입력 안됨
		for (int i = 0; i < roomDTO.getRoomNumber().size()
				&& !(roomDTO.getRoomNumber().get(i) == null || roomDTO.getDeposit().get(i) == null
						|| roomDTO.getMonthlyRent().get(i) == null || roomDTO.getRentableDate().equals(null)
						|| roomDTO.getSpace().get(i).equals(null) || roomDTO.getSpace().get(i) == ""); i++) {
			adminMapper.roomRegist(branchNumber, roomDTO.getRoomNumber().get(i), roomDTO.getRoomGender().get(i),
					roomDTO.getAllowNumber().get(i), roomDTO.getSpace().get(i), roomDTO.getDeposit().get(i),
					roomDTO.getMonthlyRent().get(i), roomRentableDate[i], roomDTO.getRoomFacility());
		}

	}

	public void branchImageRegist(int branchNumber, BranchDTO branchDTO) {

		String resourceToString;
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.indexOf("nux") >= 0) {
			resourceToString = "/project/knockknock/knockknock/KnockKnock/src/main/resources/static/images/branch/"
					+ branchNumber;
		} else {
			resourceToString = System.getProperty("user.dir") + "/src/main/resources/static/images/branch/"
					+ branchNumber;
		}

		File BranchUploadPath = new File(resourceToString);

		if (BranchUploadPath.exists() == false) {
			BranchUploadPath.mkdirs();
		}

		int Numbering = 0;

		for (MultipartFile multipartFile : branchDTO.getBranchImages()) {
			String extension = multipartFile.getOriginalFilename()
					.substring(multipartFile.getOriginalFilename().lastIndexOf("."));
			String uploadFileName;
			if (Numbering == 0) {
				uploadFileName = "mainImage" + extension;
				Numbering++;
			} else {
				uploadFileName = Numbering++ + extension;
			}
			try {
				File saveFile = new File(BranchUploadPath, uploadFileName);
				// 경로를 파일화시킨다.(실제파일생성)
				multipartFile.transferTo(saveFile);
				// DB에 저장하기 위해 상대경로명에 유저아이디를 섞은 파일명을 합쳐서 finalImage라는 DB용 경로명을 만든다.
//				String finalImage = BranchUploadFolder2 + uploadFileName;
				// 이미지경로를 저장한다.
//				memberService.saveImageDir(finalImage,username);
				// 이미지 경로를 불러온다.(뷰에서 받아 쓰기 위한 용도)
//				model.addAttribute("image",memberService.getImageDir(username));
			} catch (Exception e) {
				System.out.println("실패");
				e.printStackTrace();
			} // end catch
		}

	}

	public void roomImageRegist(int branchNumber, RoomDTO roomDTO) {

		String resourceToString;
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.indexOf("nux") >= 0) {
			resourceToString = "/project/knockknock/knockknock/KnockKnock/src/main/resources/static/images/branch/"
					+ branchNumber + "room";
		} else {
			resourceToString = System.getProperty("user.dir") + "/src/main/resources/static/images/branch/"
					+ branchNumber + "room";
		}

		File RoomUploadPath = new File(resourceToString);

		if (RoomUploadPath.exists() == false) {
			RoomUploadPath.mkdirs();
		}

		int Numbering = 0;

		for (MultipartFile multipartFile : roomDTO.getRoomImages()) {
			String extension = multipartFile.getOriginalFilename()
					.substring(multipartFile.getOriginalFilename().lastIndexOf("."));
			String uploadFileName;
			if (Numbering == 0) {
				uploadFileName = "mainImage" + extension;
				Numbering++;
			} else {
				uploadFileName = Numbering++ + extension;
			}
			try {
				File saveFile = new File(RoomUploadPath, uploadFileName);
				// 경로를 파일화시킨다.(실제파일생성)
				multipartFile.transferTo(saveFile);
				// DB에 저장하기 위해 상대경로명에 유저아이디를 섞은 파일명을 합쳐서 finalImage라는 DB용 경로명을 만든다.
//				String finalImage = BranchUploadFolder2 + uploadFileName;
				// 이미지경로를 저장한다.
//				memberService.saveImageDir(finalImage,username);
				// 이미지 경로를 불러온다.(뷰에서 받아 쓰기 위한 용도)
//				model.addAttribute("image",memberService.getImageDir(username));
			} catch (Exception e) {
				System.out.println("실패");
				e.printStackTrace();
			} // end catch
		}

	}

	public BranchDTO branchModifyView(int branchNumber) {
		return adminMapper.branchModifyView(branchNumber);
	}

	public ArrayList<roomVDTO> roomModifyView(int branchNumber) {
		return adminMapper.roomModifyView(branchNumber);
	}

	public List<String> branchModifyViewImages(int branchNumber) {
		String path;
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.indexOf("nux") >= 0) {
			path = "/project/knockknock/knockknock/KnockKnock/src/main/resources/static/images/branch/" + branchNumber;
		} else {
			path = System.getProperty("user.dir") + "/src/main/resources/static/images/branch/" + branchNumber;
		}

		File f = new File(path);
		File[] files = f.listFiles();
		// files
		int count = 0;
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < files.length - 1; i++) {

			if (files[i].isFile()) {
				count++;
				list.add(files[i].getName());
				System.out.println("파일 : " + files[i].getName());
			} else {
				System.out.println("디렉토리명 : " + files[i].getName());
			}
		} // end of for
		count = count - 1; // main.jpg 제외
		return list;
	}

	public List<String> roomModifyViewImages(int branchNumber) {
		String pathRoom;
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.indexOf("nux") >= 0) {
			pathRoom = "/project/knockknock/knockknock/KnockKnock/src/main/resources/static/images/branch/"
					+ branchNumber + "room";
		} else {
			pathRoom = System.getProperty("user.dir") + "/src/main/resources/static/images/branch/" + branchNumber
					+ "room";
		}

		File fRoom = new File(pathRoom);
		File[] filesRoom = fRoom.listFiles();

		List<String> roomList = new ArrayList<String>();

		for (int i = 0; i < filesRoom.length - 1; i++) {
			if (filesRoom[i].isFile()) {
				roomList.add(filesRoom[i].getName());
				System.out.println("파일 : " + filesRoom[i].getName());
			} else {
				System.out.println("디렉토리명 : " + filesRoom[i].getName());
			}
		}
		return roomList;
	}

	public void BranchModify(BranchDTO branchDTO, RoomDTO roomDTO, AddRoomDTO addRoomDTO) {
		int maximumResident = 0;
		for (int i = 0; i < roomDTO.getRoomNumber().size()
				&& !(roomDTO.getRoomNumber().get(i) == null || roomDTO.getDeposit().get(i) == null
						|| roomDTO.getMonthlyRent().get(i) == null || roomDTO.getRentableDate().equals(null)
						|| roomDTO.getSpace().get(i).equals(null) || roomDTO.getSpace().get(i) == ""); i++) {
			switch (roomDTO.getAllowNumber().get(i).toString()) {
			case "1인실":
				maximumResident += 1;
				break;
			case "2인실":
				maximumResident += 2;
				break;
			case "3인실":
				maximumResident += 3;
				break;
			case "4인실":
				maximumResident += 4;
				break;
			default:
				break;
			}
		}
		for (int i = 0; i < addRoomDTO.getNewRoomNumber().size() && !(addRoomDTO.getNewRoomNumber().get(i) == null
				|| addRoomDTO.getNewDeposit().get(i) == null || addRoomDTO.getNewMonthlyRent().get(i) == null
				|| addRoomDTO.getNewRentableDate().equals(null) || addRoomDTO.getNewSpace().get(i).equals(null)
				|| addRoomDTO.getNewSpace().get(i) == ""); i++) {
			switch (addRoomDTO.getNewAllowNumber().get(i).toString()) {
			case "1인실":
				maximumResident += 1;
				break;
			case "2인실":
				maximumResident += 2;
				break;
			case "3인실":
				maximumResident += 3;
				break;
			case "4인실":
				maximumResident += 4;
				break;
			default:
				break;
			}
		}
		branchDTO.setMaximumResident(maximumResident);
		adminMapper.branchModify(branchDTO);
//		adminMapper.roomModifyReady(branchDTO.getBranchNumber());
//		adminMapper.roomModify(roomDTO);
//		adminMapper.addRoomModify(addRoomDTO);
	}
	
	public void roomModify(BranchDTO branchDTO, RoomDTO roomDTO, AddRoomDTO addRoomDTO) {
		adminMapper.roomModifyReady(branchDTO.getBranchNumber());
		String dateForm = "^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$";

		// String으로 받은 날짜
		Iterator<String> iterator = roomDTO.getRentableDate().iterator();
		List<Date> rentableDate = new ArrayList<Date>();

		// String으로 받은 날짜를 -> util.date -> sql.date로 변환. String -> sql.date는 잘 안되더라
		while (iterator.hasNext()) {
			String tmp = iterator.next();
			if (Pattern.matches(dateForm, tmp)) {
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				try {
					java.util.Date parsed = (java.util.Date) formatter.parse(tmp);
					rentableDate.add(new Date(parsed.getTime()));
				} catch (ParseException e) {
					rentableDate.add(null);
				}
			} else {
				rentableDate.add(null);
			}
		}

		// 날짜를 배열에 넣고 입력 안한 날짜는 null로 처리
		Iterator<Date> sqlDate = rentableDate.iterator();
		Date[] roomRentableDate = new Date[roomDTO.getRoomNumber().size()];
		for (int i = 0; i < roomRentableDate.length; i++) {
			if (sqlDate.hasNext()) {
				roomRentableDate[i] = sqlDate.next();
			} else {
				roomRentableDate[i] = null;
			}
		}

		// 입력한 줄까지만 방 정보가 들어감. 누락된 입력란이 존재하면 입력 안됨
		for (int i = 0; i < roomDTO.getRoomNumber().size()
				&& !(roomDTO.getRoomNumber().get(i) == null || roomDTO.getDeposit().get(i) == null
						|| roomDTO.getMonthlyRent().get(i) == null || roomDTO.getRentableDate().equals(null)
						|| roomDTO.getSpace().get(i).equals(null) || roomDTO.getSpace().get(i) == ""); i++) {
			adminMapper.roomRegist(branchDTO.getBranchNumber(), roomDTO.getRoomNumber().get(i), roomDTO.getRoomGender().get(i),
					roomDTO.getAllowNumber().get(i), roomDTO.getSpace().get(i), roomDTO.getDeposit().get(i),
					roomDTO.getMonthlyRent().get(i), roomRentableDate[i], roomDTO.getRoomFacility());
		}
		
		
		
		// String으로 받은 날짜
		Iterator<String> newIterator = addRoomDTO.getNewRentableDate().iterator();
		List<Date> newRentableDate = new ArrayList<Date>();

		// String으로 받은 날짜를 -> util.date -> sql.date로 변환. String -> sql.date는 잘 안되더라
		while (newIterator.hasNext()) {
			String tmp = newIterator.next();
			if (Pattern.matches(dateForm, tmp)) {
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				try {
					java.util.Date parsed = (java.util.Date) formatter.parse(tmp);
					newRentableDate.add(new Date(parsed.getTime()));
				} catch (ParseException e) {
					newRentableDate.add(null);
				}
			} else {
				newRentableDate.add(null);
			}
		}

		// 날짜를 배열에 넣고 입력 안한 날짜는 null로 처리
		Iterator<Date> newSqlDate = newRentableDate.iterator();
		Date[] newRoomRentableDate = new Date[addRoomDTO.getNewRoomNumber().size()];
		for (int i = 0; i < newRoomRentableDate.length; i++) {
			if (newSqlDate.hasNext()) {
				newRoomRentableDate[i] = newSqlDate.next();
			} else {
				newRoomRentableDate[i] = null;
			}
		}

		// 입력한 줄까지만 방 정보가 들어감. 누락된 입력란이 존재하면 입력 안됨
		for (int i = 0; i < addRoomDTO.getNewRoomNumber().size()
				&& !(addRoomDTO.getNewRoomNumber().get(i) == null || addRoomDTO.getNewDeposit().get(i) == null
						|| addRoomDTO.getNewMonthlyRent().get(i) == null || addRoomDTO.getNewRentableDate().equals(null)
						|| addRoomDTO.getNewSpace().get(i).equals(null) || addRoomDTO.getNewSpace().get(i) == ""); i++) {
			adminMapper.roomRegist(branchDTO.getBranchNumber(), addRoomDTO.getNewRoomNumber().get(i), addRoomDTO.getNewRoomGender().get(i),
					addRoomDTO.getNewAllowNumber().get(i), addRoomDTO.getNewSpace().get(i), addRoomDTO.getNewDeposit().get(i),
					addRoomDTO.getNewMonthlyRent().get(i), newRoomRentableDate[i], addRoomDTO.getRoomFacility());
		}
		
	}

//	public void roomModify(RoomDTO roomDTO) {
//		String dateForm = "^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$";
//
//		// String으로 받은 날짜
//		Iterator<String> iterator = roomDTO.getRentableDate().iterator();
//		List<Date> rentableDate = new ArrayList<Date>();
//
//		// String으로 받은 날짜를 -> util.date -> sql.date로 변환. String -> sql.date는 잘 안되더라
//		while (iterator.hasNext()) {
//			String tmp = iterator.next();
//			if (Pattern.matches(dateForm, tmp)) {
//				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//				try {
//					java.util.Date parsed = (java.util.Date) formatter.parse(tmp);
//					rentableDate.add(new Date(parsed.getTime()));
//				} catch (ParseException e) {
//					rentableDate.add(null);
//				}
//			} else {
//				rentableDate.add(null);
//			}
//		}
//
//		// 새로 추가되는 방 정보
//		// 날짜를 배열에 넣고 입력 안한 날짜는 null로 처리
//		Iterator<Date> sqlDate = rentableDate.iterator();
//		Date[] roomRentableDate = new Date[roomDTO.getRoomNumber().size()];
//		for (int i = 0; i < roomRentableDate.length; i++) {
//			if (sqlDate.hasNext()) {
//				roomRentableDate[i] = sqlDate.next();
//			} else {
//				roomRentableDate[i] = null;
//			}
//		}
//
//		// 입력한 줄까지만 방 정보가 들어감. 누락된 입력란이 존재하면 입력 안됨
//		for (int i = 0; i < roomDTO.getRoomNumber().size()
//				&& !(roomDTO.getRoomNumber().get(i) == null || roomDTO.getDeposit().get(i) == null
//						|| roomDTO.getMonthlyRent().get(i) == null || roomDTO.getRentableDate().equals(null)
//						|| roomDTO.getSpace().get(i).equals(null) || roomDTO.getSpace().get(i) == ""); i++) {
//			adminMapper.roomRegist(roomDTO.getBranchNumber(), roomDTO.getRoomNumber().get(i), roomDTO.getRoomGender().get(i),
//					roomDTO.getAllowNumber().get(i), roomDTO.getSpace().get(i), roomDTO.getDeposit().get(i),
//					roomDTO.getMonthlyRent().get(i), roomRentableDate[i], roomDTO.getRoomFacility());
//		}
//
//	}

	public Date dateChanger(String string) {
		String dateForm = "^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$";
		Date date;
		if (Pattern.matches(dateForm, string)) {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				java.util.Date parsed = (java.util.Date) formatter.parse(string);
				date = new Date(parsed.getTime());
			} catch (ParseException e) {
				date = null;
				e.printStackTrace();
			}
		} else {
			date = null;
		}
		return date;
	}
}
