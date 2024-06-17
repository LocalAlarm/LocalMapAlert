package com.spring.dongnae.custom.scheme;

public class Marker {
    private int id;
    private Path path;
    private String info;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	@Override
	public String toString() {
		return "MarkerScheme [id=" + id + ", path=" + path + ", info=" + info + "]";
	}
    
    
}
