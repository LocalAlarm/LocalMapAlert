package com.spring.dongnae.custom.scheme;

public class Marker {
    private int id;
    private Path path;
    private String content;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Marker [id=" + id + ", path=" + path + ", content=" + content + "]";
	}

    
}
