package com.tools.utils;

import java.util.HashMap;
import java.util.Map;

public class MapTools {
	
	public static final Map<String, Object> DEFAULT = new Builder().build();
	
	public static MapTools.Builder custom(){
		return new Builder();
	}

	public static class Builder {

		private Map<String, Object> map = null;

        Builder() {
            super();
            this.map =  new HashMap<>();
        }
        
        public Builder set(String key, Object value){
    		this.map.put(key, value);
            return this;
        }
        public Map<String, Object> build() {
        	return this.map;
        }
	}
	
	public static void main(String[] args) {
		Map<String, Object> map = MapTools.custom().set("test1", "test1").set("test2", "test2").set("test3", "test3").set("test1", "test4").build();
		Map<String, Object> map2 = MapTools.custom().build();
		MapTools.DEFAULT.put("2", "3");
		Map<String, Object> map3 = MapTools.DEFAULT;
		map3.put("1", "2");
		Map<String, Object> map4 = MapTools.DEFAULT;
		System.out.println(map.toString());
		System.out.println(map2.toString());
		System.out.println(map3.toString());
		System.out.println(map4.toString());
	}
}
