package com.knockknock.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.knockknock.dto.event.MeetingVDTO;
import com.knockknock.mapper.MeetingAndEventMapper;

@Service
public class MeetingAndEventService {

	@Autowired
	public MeetingAndEventMapper meMapper;
	
	public void meetingImageUpload(int writingNumber, MeetingVDTO meetingVDTO) { //모임글쓰기 파일업로드
		writingNumber = Collections.max(meMapper.getWritingNumber()); //글번호 불러오기
		String resourceToString;
		String OS = System.getProperty("os.name").toLowerCase(); //------상대경로 지정
		if (OS.indexOf("nux") >= 0) {
			resourceToString = "/project/knockknock/knockknock/KnockKnock/src/main/resources/static/images/meeting/"
					+ writingNumber;
		} else {
			resourceToString = System.getProperty("user.dir") + "/src/main/resources/static/images/meeting/"
					+ writingNumber;
		}

		File MeetingUploadPath = new File(resourceToString);

		if (MeetingUploadPath.exists() == false) {
			MeetingUploadPath.mkdirs();
		}

		int Numbering = 0;
		String imageName = "";

		for (MultipartFile multipartFile : meetingVDTO.getMeetingImage()) {
			if(multipartFile.getOriginalFilename().lastIndexOf(".")>=0 ? true : false) {
				String extension = multipartFile.getOriginalFilename()
						.substring(multipartFile.getOriginalFilename().lastIndexOf("."));
				if(extension.equals(".jpg") || extension.equals(".png") || extension.equals(".jpeg") || extension.equals(".gif")) {
					String uploadFileName;
					if(Numbering == 0) {
						uploadFileName = "mainImage"+extension;
						Numbering++;
					} else {
						uploadFileName = Numbering++ + extension;
					}
					try {
						File saveFile = new File(MeetingUploadPath, uploadFileName);
						imageName = uploadFileName;
						// 경로를 파일화시킨다.(실제파일생성)
						multipartFile.transferTo(saveFile);
					} catch (Exception e) {
						System.err.println("실패");
						e.printStackTrace();
					} // end catch
					meMapper.setMeetingImageName(writingNumber, imageName);
				}
			}
		}
	}
}
