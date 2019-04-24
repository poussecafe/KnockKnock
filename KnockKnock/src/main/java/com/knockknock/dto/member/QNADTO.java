package com.knockknock.dto.member;

import java.sql.Date;

import lombok.Data;

@Data
public class QNADTO {
	private int writingNumber;
	private int memberNumber;
	private String category;
	private String title;
	private Date writeTime;
	private String content;
	private String reply;
}