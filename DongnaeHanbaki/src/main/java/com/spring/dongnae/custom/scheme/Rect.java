package com.spring.dongnae.custom.scheme;

public class Rect {
    private String type;
    private Point sPoint;
    private Point ePoint;
    private String coordinate;
    private Options options;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Point getsPoint() {
		return sPoint;
	}
	public void setsPoint(Point sPoint) {
		this.sPoint = sPoint;
	}
	public Point getePoint() {
		return ePoint;
	}
	public void setePoint(Point ePoint) {
		this.ePoint = ePoint;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	public Options getOptions() {
		return options;
	}
	public void setOptions(Options options) {
		this.options = options;
	}
	@Override
	public String toString() {
		return "RectScheme [type=" + type + ", sPoint=" + sPoint + ", ePoint=" + ePoint + ", coordinate=" + coordinate
				+ ", options=" + options + "]";
	}
    
}
