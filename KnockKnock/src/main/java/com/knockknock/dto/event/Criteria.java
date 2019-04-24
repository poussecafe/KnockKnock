package com.knockknock.dto.event;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
/**
 * 
 * 게시판 페이징 전용 클래스
 *
 */

@Getter
public class Criteria {
	private int page; // 보여줄 페이지 번호
	private int perPageNum;  // 페이지당 보여줄 게시글의 개수
	private String searchType;
	private String keyword;
	
	public Criteria(){
		this.page = 1;
		this.perPageNum = 10;
	this.searchType = null;
	this.keyword = null;
	}
	
	public void setPage(int page) {
		if(page <= 0) {
			this.page = 1;
			return;
		}
		this.page = page;
	}
	
	public void setPerPageNum(int perPageNum) {
		if(perPageNum <= 0 || perPageNum > 100) {
			this.perPageNum = 10;
			return;
		}	
		this.perPageNum = perPageNum;
	}
	
	public int getPageStart() {
		return (this.page -1) * perPageNum;
	}
	
	
	public String makeQuery() {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", this.perPageNum);
				
		if (searchType!=null) {
			uriComponentsBuilder
					.queryParam("searchType", this.searchType)
					.queryParam("keyword", this.keyword);
		}
		return uriComponentsBuilder.build().encode().toString();
	}
	
//	@Override
//	public String toString() {
//		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + "]";
//	}
	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + ", searchType=" + searchType + ", keyword="
				+ keyword + "]";
	}
}
