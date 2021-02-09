package com.xiami.entity;

import org.apache.ibatis.type.Alias;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Alias("pd")
public class PageData<K,V> extends HashMap{
	
	private static final long serialVersionUID = 1L;

	public PageData(HttpServletRequest request){
		Map properties = request.getParameterMap();
		Iterator entries = properties.entrySet().iterator(); 
		Entry entry;
		String name = "";
		while (entries.hasNext()) {
			entry = (Entry) entries.next();
			name = (String) entry.getKey(); 
			Object valueObj = entry.getValue();
			String value = "";  
			if(null == valueObj){ 
				value = ""; 
			}else if(valueObj instanceof String[]){ 
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){ 
					 value += values[i] + ",";
				}
				value = value.substring(0, value.length()-1); 
			}else{
				value = valueObj.toString(); 
			}
			this.put(name, value);
		}
	}
	
	public PageData() {
	}
	
	public String getString(Object key) {
		return (String)get(key);
	}
}
