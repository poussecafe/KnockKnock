package com.knockknock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.knockknock.dto.event.ReplyDTO;

@Mapper
public interface ReplyMapper {
	
	// 미팅 댓글 목록
	public List<ReplyDTO> meetingReplyList(int writingNumber);
	//미팅 댓글 작성
	public int meetingReplyInsert(@Param("replyDTO")ReplyDTO replyDTO, @Param("email") String email);
	//미팅 댓글수정
	public int meetingReplyUpdate(ReplyDTO replyDTO);
	//댓글 삭제
	public int meetingReplyDelete(int replyNumber);
	
	
	// 이벤트 댓글 목록
	public List<ReplyDTO> eventReplyList(int writingNumber);
	//이벤트 댓글 작성
	public int eventReplyInsert(@Param("replyDTO") ReplyDTO replyDTO, @Param("email") String email);
	//이벤트 댓글수정
	public int eventReplyUpdate(ReplyDTO replyDTO);
	//이벤트 댓글 삭제
	public int eventReplyDelete(int replyNumber);
	
}