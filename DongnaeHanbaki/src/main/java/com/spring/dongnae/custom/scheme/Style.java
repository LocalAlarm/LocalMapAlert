package com.spring.dongnae.custom.scheme;

public class Style {
    private String strokeColor;
    private int strokeWeight;
    private String strokeStyle;
    private double strokeOpacity;
    private String fillColor;
    private double fillOpacity;
	public String getStrokeColor() {
		return strokeColor;
	}
	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}
	public int getStrokeWeight() {
		return strokeWeight;
	}
	public void setStrokeWeight(int strokeWeight) {
		this.strokeWeight = strokeWeight;
	}
	public String getStrokeStyle() {
		return strokeStyle;
	}
	public void setStrokeStyle(String strokeStyle) {
		this.strokeStyle = strokeStyle;
	}
	public double getStrokeOpacity() {
		return strokeOpacity;
	}
	public void setStrokeOpacity(double strokeOpacity) {
		this.strokeOpacity = strokeOpacity;
	}
	public String getFillColor() {
		return fillColor;
	}
	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}
	public double getFillOpacity() {
		return fillOpacity;
	}
	public void setFillOpacity(double fillOpacity) {
		this.fillOpacity = fillOpacity;
	}
	@Override
	public String toString() {
		return "StyleScheme [strokeColor=" + strokeColor + ", strokeWeight=" + strokeWeight + ", strokeStyle="
				+ strokeStyle + ", strokeOpacity=" + strokeOpacity + ", fillColor=" + fillColor + ", fillOpacity="
				+ fillOpacity + "]";
	}
}

    
