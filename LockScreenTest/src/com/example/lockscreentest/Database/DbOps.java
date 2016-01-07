package com.example.lockscreentest.Database;

import java.util.List;
import java.util.Map;

/** 
 * @author Chan Kong
 * 
 * 
 */
public interface DbOps {
	public boolean add(Object[] params);

	public boolean delete(Object[] params);

	public boolean update(Object[] params);

	public Map<String, Object> view(String[] selectionArgs, int code);
	
	public Map<String , Object> view(String query , String[] selectionArgs);
	
	public List<Map<String , Object>> list(String query , String[] selectionArgs);
}
