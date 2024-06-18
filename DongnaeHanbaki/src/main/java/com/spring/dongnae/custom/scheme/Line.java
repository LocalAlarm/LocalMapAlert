package com.spring.dongnae.custom.scheme;

import java.util.List;

public class Line {
    private List<Path> path;
    private Style style;
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
		return "LineScheme [path=" + path + ", style=" + style + "]";
	}
}
