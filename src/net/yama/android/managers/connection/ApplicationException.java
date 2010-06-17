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
	
		Class ec = (getCause() != null ) ? getCause().getClass() : null;
		if(ec != null && excpetionMessages.get(ec) != null)
			return excpetionMessages.get(ec);
		
		return super.getMessage();
	}

}
