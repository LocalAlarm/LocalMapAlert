package com.spring.dongnae.custom.scheme;


public class Circle {
    private String type;
    private Point sPoint;
    private Point ePoint;
    private Point center;
    private String coordinate;
    private Options options;
    private double radius;
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
	public Point getCenter() {
		return center;
	}
	public void setCenter(Point center) {
		this.center = center;
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
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	@Override
	public String toString() {
		return "CircleScheme [type=" + type + ", sPoint=" + sPoint + ", ePoint=" + ePoint
				+ ", center=" + center + ", coordinate=" + coordinate + ", options=" + options + ", radius=" + radius
				+ "]";
	}

    
    
}
