package com.spring.dongnae.custom.scheme;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Document(collection = "customMarkers")
@JsonIgnoreProperties(ignoreUnknown = true) //알수없는 값 무시
public class CustomMarker {
	@Id
	private int mapIdx;
	private List<Marker> markers;
	
	// 기본 생성자 추가
    public CustomMarker() {
    }
	
	public CustomMarker (int mapIdx, List<Marker> markers){
		System.out.println(">> custommarker 객체 생성!");
		this.mapIdx = mapIdx;
		this.markers = markers;
	}
	
	
	public int getMapIdx() {
		return mapIdx;
	}
	public void setMapIdx(int mapIdx) {
		this.mapIdx = mapIdx;
	}
	public List<Marker> getMarkers() {
		return markers;
	}
	public void setMarkers(List<Marker> markers) {
		this.markers = markers;
	}


	public static class Marker {
		private int id;
		private MarkerPath path;
		private String info; // content 안에 p태그 안에 내용가져와야됨 임시로 String
		
		public Marker() {
			
		}
	}
	
	public static class MarkerPath {
		private double la;
		private double ma;
		
		public MarkerPath() {
			
		}
		
		public MarkerPath(double la, double ma) {
			 this.la = la;
		     this.ma = ma;
		}

		public double getLa() {
			return la;
		}

		public void setLa(double la) {
			this.la = la;
		}

		public double getMa() {
			return ma;
		}

		public void setMa(double ma) {
			this.ma = ma;
		}
	}
//	class MarkerInfo {
//	
//}
	@Override
	public String toString() {
		return "CustomMarker [mapIdx=" + mapIdx + ", markers=" + markers + "]";
	}
	
	
	
	
}
