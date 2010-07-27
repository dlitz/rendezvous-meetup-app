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
package net.yama.android.managers.connection;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import net.oauth.OAuthProblemException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Generic exception
 * @author Rohit Kumbhar
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = -1468654093504656108L;
	
	private static Map<Class<? extends Exception>,String> excpetionMessages = null;
	
	static{
		excpetionMessages = new HashMap<Class<? extends Exception>, String>();
		excpetionMessages.put(UnknownHostException.class, "Network connection is not available.");
	}

	public ApplicationException(String detailMessage) {
		super(detailMessage);
	}

	public ApplicationException(Throwable throwable) {
		super(throwable);

	}

	public ApplicationException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getMessage() {
	
		Throwable cause = getCause();
		String message = null;
		
		
		if(cause != null && cause instanceof OAuthProblemException)
			message = getOAuthMessage((OAuthProblemException) cause);
		
		if(cause != null && message == null)
			message = getMessageForExceptionClass(cause);
			
		if(cause != null && message == null)
			message = cause.getMessage();
		
		if(message == null)
			message = super.getMessage();
		
		return message;
	}

	/**
	 * Returns a string from a static map of knows exceptions and causes.
	 * @param cause
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getMessageForExceptionClass(Throwable cause) {
		
		Class ec = (cause != null ) ? cause.getClass() : null;
		if(ec != null && excpetionMessages.get(ec) != null)
			return excpetionMessages.get(ec);
		
		return null;
	}

	/**
	 * Returns a string returned by meetup.com
	 * @param ex
	 * @return
	 */
	private String getOAuthMessage(OAuthProblemException ex) {
		String message = null;
		
		Map<String, Object> exParams =  ex.getParameters();
		for(Map.Entry<String, Object> entry : exParams.entrySet()){
			String k = entry.getKey();
			Object v = entry.getValue();
			if(k.startsWith("{")){
				JSONObject json;
				try {
					json = new JSONObject(k);
					String details = json.optString("details");
					message = details;
					break;
				} catch (JSONException e1) {
					
				}
			}
		}
		return message;
	}

}
