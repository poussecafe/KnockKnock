package com.knockknock.dto.event;

import org.springframework.web.util.UriComponentsBuilder;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class PageMaker {
	private int totalCount;     // 게시판 전체 데이터 개수
	private int displayPageNum = 10;   // 게시판 화면에서 한번에 보여질 페이지 번호의 개수 ( 1,2,3,4,5,6,7,9,10)
	
	private int startPage;      // 현재 화면에서 보이는 startPage 번호
	private int endPage;        // 현재 화면에 보이는 endPage 번호
	private boolean prev;       // 페이징 이전 버튼 활성화 여부
	private boolean next;       // 페이징 다음 버튼 활서화 여부
	
	private Criteria cri;       // 앞서 생성한 Criteria를 주입받는다.
	
	public void setTotalCount(int totalcount) {
		this.totalCount = totalcount;
		calcData();
	}
	
	private void calcData() {
		endPage = (int) (Math.ceil(cri.getPage()/(double)displayPageNum)*displayPageNum);
		startPage = (endPage - displayPageNum)+1;
		int tempEndPage = (int) (Math.ceil(totalCount/(double)cri.getPerPageNum()));
		
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		prev = startPage == 1 ? false : true;
		next = endPage * cri.getPerPageNum() >= totalCount ? false : true;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	public String makeQuery(int page, boolean needSearch) {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
			.queryParam("page", page)
			.queryParam("perPageNum", this.cri.getPerPageNum());
		//검색 한 경우		
		if (this.cri.getSearchType() != null) {
			uriComponentsBuilder
				.queryParam("searchType", this.cri.getSearchType())
				.queryParam("keyword", this.cri.getKeyword());
		}
		return uriComponentsBuilder.build().encode().toString();
	}
	
}
