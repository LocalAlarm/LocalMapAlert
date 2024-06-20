package com.spring.dongnae.custom.scheme;

import java.util.List;

public class Line {
	private int id;
	private String arrive;
	private String depart;
	private String via;
	private Object line;
    private List<Path> path;
    private Style style;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getArrive() {
		return arrive;
	}
	public void setArrive(String arrive) {
		this.arrive = arrive;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public Object getLine() {
		return line;
	}
	public void setLine(Object line) {
		this.line = line;
	}
	public List<Path> getPath() {
		return path;
	}
	public void setPath(List<Path> path) {
		this.path = path;
	}
	public Style getStyle() {
		return style;
	}
	public void setStyle(Style style) {
		this.style = style;
	}
	@Override
	public String toString() {
		return "Line [id=" + id + ", arrive=" + arrive + ", depart=" + depart + ", via=" + via + ", line=" + line
				+ ", path=" + path + ", style=" + style + "]";
	}
    
    
}
