package com.spring.dongnae.bbs;

public class CommentVO {
	String coment_idx;
	String bbs_idx;
	String writer;
	String content;
	String write_date;
	public String getComent_idx() {
		return coment_idx;
	}
	public void setComent_idx(String coment_idx) {
		this.coment_idx = coment_idx;
	}
	public String getBbs_idx() {
		return bbs_idx;
	}
	public void setBbs_idx(String bbs_idx) {
		this.bbs_idx = bbs_idx;
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
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	@Override
	public String toString() {
		return "CommentVO [coment_idx=" + coment_idx + ", bbs_idx=" + bbs_idx + ", writer=" + writer + ", content="
				+ content + ", write_date=" + write_date + "]";
	}
	
}
