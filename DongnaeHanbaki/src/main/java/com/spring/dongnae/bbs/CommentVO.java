package com.spring.dongnae.bbs;

public class CommentVO {
	String commentIdx;
	String bbsIdx;
	String writer;
	String content;
	String writeDate;
	String delYn;
	
	public String getCommentIdx() {
		return commentIdx;
	}

	public void setCommentIdx(String commentIdx) {
		this.commentIdx = commentIdx;
	}

	public String getBbsIdx() {
		return bbsIdx;
	}

	public void setBbsIdx(String bbsIdx) {
		this.bbsIdx = bbsIdx;
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
		return "CommentVO [commentIdx=" + commentIdx + ", bbsIdx=" + bbsIdx + ", writer=" + writer + ", content="
				+ content + ", writeDate=" + writeDate + ", delYn=" + delYn + "]";
	}
	
}
