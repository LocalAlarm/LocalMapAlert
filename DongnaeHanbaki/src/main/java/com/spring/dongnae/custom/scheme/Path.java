package com.spring.dongnae.custom.scheme;

public class Path {
    private double La;
    private double Ma;
    
    public Path() {
    }
    
    public Path(double la, double ma) {
    	this.setLa(la);
    	this.setMa(ma);
    }
    
    public double getLa() {
    	return La;
    }
    public void setLa(double la) {
    	La = la;
    }
	public double getMa() {
		return Ma;
	}
	public void setMa(double ma) {
		Ma = ma;
	}

	@Override
	public String toString() {
		return "PathScheme [La=" + La + ", Ma=" + Ma + "]";
	}
}
