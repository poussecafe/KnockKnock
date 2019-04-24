package com.knockknock.dto.event;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileUploadDTO {
	private String CKEditorFuncNum;
	private MultipartFile upload;
	private String newFilename;
	private String imageUrl;
}
