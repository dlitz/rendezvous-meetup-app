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

import net.yama.android.managers.config.ConfigurationManager;
import net.yama.android.requests.AbstractRequest;
import net.yama.android.requests.write.WritePhotoRequest;
import net.yama.android.util.Constants;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Making requests using API Key provided by meetup.com
 * @author Rohit Kumbhar
 */
public class ApiKeyConnectionManager implements ConnectionManager {

	public String makeRequest(AbstractRequest request) throws ApplicationException {

		String apiKey = ConfigurationManager.instance.getApiKey();
		request.addParameter(Constants.PARAM_KEY, apiKey);
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(request.getURL());

		// Create a response handler
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = null;

		try {
			
			responseBody = httpclient.execute(httpget, responseHandler);
			httpclient.getConnectionManager().shutdown();
			
		} catch (Exception e) {
			throw new ApplicationException(e);
		}

		return responseBody;
	}

	public String uploadPhoto(WritePhotoRequest request) {
		throw new RuntimeException("Not implemented");
	}

}
