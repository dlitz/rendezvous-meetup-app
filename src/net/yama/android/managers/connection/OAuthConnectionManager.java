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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.OAuthServiceProvider;
import net.oauth.client.OAuthClient;
import net.oauth.client.OAuthClient.ParameterStyle;
import net.oauth.client.httpclient4.HttpClient4;
import net.yama.android.managers.config.ConfigurationManager;
import net.yama.android.requests.AbstractRequest;
import net.yama.android.util.Constants;
import android.util.Log;

/**
 * Make requests using OAuth parameters 
 * @author Rohit Kumbhar
 */
public class OAuthConnectionManager implements ConnectionManager {

	public static final OAuthServiceProvider provider = new OAuthServiceProvider(Constants.OAUTH_REQUEST_URL, 
														            Constants.OAUTH_AUTHORIZE_URL,
														            Constants.OAUTH_ACCESS_URL);

	public static final OAuthConsumer consumer = new OAuthConsumer(Constants.CALLBACK_URL,
													  Constants.OAUTH_CONSUMER_KEY, 
													  Constants.OAUTH_CONSUMER_SECRET,
													  provider);

	public String makeRequest(AbstractRequest request) throws ApplicationException {
		
		OAuthAccessor accessor = new OAuthAccessor(OAuthConnectionManager.consumer);
		accessor.accessToken = ConfigurationManager.instance.getAccessToken();
		accessor.tokenSecret = ConfigurationManager.instance.getAccessTokenSecret();
		accessor.consumer.setProperty(OAuthClient.PARAMETER_STYLE,ParameterStyle.QUERY_STRING);
		
		ArrayList<Map.Entry<String, String>> params = new ArrayList<Map.Entry<String, String>>();
		convertRequestParamsToOAuth(params,request.getParameterMap());
		OAuthClient oAuthClient = new OAuthClient(new HttpClient4());
		OAuthMessage authMessage;
		try {
			authMessage = oAuthClient.invoke(accessor, request.getMethod(),request.getRequestURL(),params);
			String body = authMessage.readBodyAsString();
			return body;
		} catch (Exception e) {
			Log.e("OAuthConnectionManager", "Exception in makeRequest()", e);
			throw new ApplicationException(e);
		}
	}

	private void convertRequestParamsToOAuth(ArrayList<Entry<String, String>> params,Map<String, String> parameterMap) {
		Set<Entry<String, String>>  entries = parameterMap.entrySet();
		Iterator<Entry<String, String>> entryIter = entries.iterator();
		while (entryIter.hasNext()) {
			Map.Entry<java.lang.String, java.lang.String> entry = (Map.Entry<java.lang.String, java.lang.String>) entryIter.next();
			params.add(new OAuth.Parameter(entry.getKey(),entry.getValue()));
		}
		
	}

}
