package com.spring.dongnae.bbs;

public class MapCommentsVO {
	
	String mapCommentIdx;
	String mapIdx;
	String writer;
	String content;
	String writeDate;
	String delYn;
	
	public String getMapCommentIdx() {
		return mapCommentIdx;
	}
	public void setMapCommentIdx(String mapCommentIdx) {
		this.mapCommentIdx = mapCommentIdx;
	}
	public String getMapIdx() {
		return mapIdx;
	}
	public void setMapIdx(String mapIdx) {
		this.mapIdx = mapIdx;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	
	@Override
	public String toString() {
		return "MapCommentsVO [mapCommentIdx=" + mapCommentIdx + ", mapIdx=" + mapIdx + ", writer=" + writer
				+ ", content=" + content + ", writeDate=" + writeDate + ", delYn=" + delYn + "]";
	}
	
}
