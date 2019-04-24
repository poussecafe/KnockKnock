package com.knockknock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knockknock.dto.branch.ReviewDTO;
import com.knockknock.mapper.ReviewMapper;

@Service
public class ReviewService {
	
	@Autowired
	ReviewMapper reviewMapper;

	public List<ReviewDTO> reviewListService(int branchNumber) {
		return reviewMapper.reviewList(branchNumber);
	}

	public int reviewInsertService(ReviewDTO reviewDTO, String email) {
		return reviewMapper.reviewInsert(reviewDTO, email);
	}

	public int reviewUpdateService(ReviewDTO reviewDTO) {
		return reviewMapper.reviewUpdate(reviewDTO);
	}

	public int reviewDeleteService(int writingNumber) {
		return reviewMapper.reviewDelete(writingNumber);
	}

}
