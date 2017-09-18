package com.tools.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class CacheTools {
	
	private static Map<String, CacheObject> objMap = new ConcurrentHashMap<String, CacheObject>();
	private static ReentrantLock lock = new ReentrantLock(); 
	private static boolean timerStart = false; 
	
	static{
		autoClear(2);
	}
	
	public static void initSetCacheObj(CacheObject obj) {
		objMap.put(obj.getKey(), obj);
	}

	public static CacheObject initGetCacheObj(String key) {
		return objMap.get(key);
	}

	public static CacheObject initClearCacheObj(String key) {
		return objMap.remove(key);
	}

	public static void initAutoClear(long outTime) {
		System.out.println("defult clear...");
		long cur = System.currentTimeMillis();
		Set<String> keys = objMap.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			CacheObject obj = objMap.get(key);
			if((obj.getMillis() + outTime) < cur){
				objMap.remove(key);
			}
		}
	}
	
	public static void autoClear(int runTime) {
		if(!timerStart){
			System.out.println("start cache clear...");
			runTime = runTime <= 0 ? 2 * 3600 * 1000 : runTime * 1000;
			Timer timer = new Timer(true); 
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					lock.lock();
					initAutoClear(2 * 3600 * 1000);
					lock.unlock();
				}
			}, 0, runTime);
			timerStart = true;
		}
	}
	
	
	
	
	
	public static void setCacheObj(String key, Object value){
		CacheObject obj = new CacheObject(key, value);
		initSetCacheObj(obj);
	}
	
	public static CacheObject getCacheObj(String key){
		return initGetCacheObj(key);
	}
	
	public static boolean checkTimeout(String key, String obj){
		return checkTimeout(key, obj, 2 * 3600 * 1000 );
	}
	
	public static boolean checkTimeout(String key, String obj, long outTime){
		CacheObject tmp = getCacheObj(key);
		if(tmp == null){ 
			return false;
		}
		long cur = System.currentTimeMillis();
		if((tmp.getMillis() + outTime) < cur){
			//超时移除
			clearCacheObj(key);
			return false;
		}
		return true;
	}
	
	public static void clearCacheObj(String key){
		initClearCacheObj(key);
	}
	
	
	public static void main(String[] args) {
		CacheTools.setCacheObj("1", "2");
		try {
			Thread.sleep(10 * 3600 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
