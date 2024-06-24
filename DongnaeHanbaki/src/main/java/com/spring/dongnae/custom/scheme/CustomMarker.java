package com.spring.dongnae.custom.scheme;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "customMarkers")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"openYn"})
public class CustomMarker {
	
	@Id
	private int mapIdx;
    private List<Marker> markers;
    private List<Line> lines;
    private String address;
    private String center;
    private String level;
    private String title;
    private String content;
    
    // 아이디 리턴
    public int getMapIdx() {
    	return mapIdx;
    }
	
    public void setMapIdx(int mapIdx) {
		this.mapIdx = mapIdx;
    	
    }
    
    // 마커에 관한 메서드
    public List<Marker> getMarkers() {
		return markers;
	}
	public void setMarkers(List<Marker> markers) {
		this.markers = markers;
	}
	public void addMarker(Marker marker) {
		this.markers.add(marker);
	}
	
	// 라인에 관한 메서드
	public List<Line> getLines() {
		return lines;
	}
	public void setLines(List<Line> lines) {
		this.lines = lines;
	}
	public void addLine(Line line) {
		this.lines.add(line);
	}
	
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCenter() {
		return center;
	}
	public void setCenter(String center) {
		this.center = center;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CustomMarker [mapIdx=" + mapIdx + ", markers=" + markers + ", lines=" + lines + ", address=" + address
				+ ", center=" + center + ", level=" + level + ", title=" + title + ", content=" + content + "]";
	}




	
}
