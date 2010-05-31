/*******************************************************************
 * Copyright (c) 2010 Rohit Kumbhar
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************/
package net.yama.android.requests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Rohit Kumbhar
 *
 */
public abstract class AbstractRequest {

	private Map<String, String> parameterMap= new HashMap<String, String>();

	@Override
	public String toString() {
		return "AbstractRequest [parameterMap=" + parameterMap + "]";
	}

	public void setParameters(String... parameters){
		int len = parameters.length;
		for(int i = 0; i < len; i++){
			String key = parameters[i];
			String value = (i + 1) < len ? parameters[++i] : null;
			parameterMap.put(key, value);
		}
	}
	
	public void addParameter(String key, String value){
		parameterMap.put(key, value);
	}
	
	public Map<String, String> getParameterMap() {
		return parameterMap;
	}

	public String getURL() {
		return getRequestURL() + getRequestParameters();
	}

	private String getRequestParameters() {
		
		StringBuilder paramString = new StringBuilder("?");
		Iterator<Map.Entry<String, String>> requestParamsIter = parameterMap.entrySet().iterator(); 
		while (requestParamsIter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) requestParamsIter.next();
			paramString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		return paramString.toString();
	}

	public abstract String getRequestURL();

	public String getMethod() {
		return "GET";
	}
	
}
