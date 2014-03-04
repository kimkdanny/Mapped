package com.mapped;

import java.util.ArrayList;

public class ArrayListData {
	ArrayList<String> latitude;
	ArrayList<String> longitude;
	
	public ArrayListData(){
		
	}
	
	public ArrayListData(ArrayList<String> lat, ArrayList<String> lng){
		latitude = lat;
		longitude = lng;
	}
	
	public ArrayList<String> getLatitude(){
		return latitude;
	}
	
	public ArrayList<String> getLongitude(){
		return longitude;
	}
}	
