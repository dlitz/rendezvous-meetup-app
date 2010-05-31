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
package net.yama.android.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.yama.android.response.BaseResponse;
import net.yama.android.response.Rsvp;
import net.yama.android.response.Rsvp.RsvpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Helper{

	private static Map<String, Object> cache = new HashMap<String, Object>(); 
	
	public static void storeInCache(String key, Object value){
		cache.put(key, value);
	}
	
	public static Object getFromCache(String key){
		return cache.get(key);
	}
	
	
	@SuppressWarnings("unchecked")
	public static List getListFromResult(String responseBody, Class clazz ) throws JSONException, IllegalAccessException, InstantiationException{
		
		List results = new ArrayList();
		JSONObject responseJSON = new JSONObject(responseBody);
		JSONArray resultsArray = responseJSON.getJSONArray("results");
		
		int numResults = resultsArray.length();
		for(int i = 0; i < numResults; i++){
			JSONObject resultItem = resultsArray.getJSONObject(i);
			BaseResponse base = (BaseResponse) clazz.newInstance();
			base.convertFromJSON(base, resultItem);
			results.add(base);
		}
		
		return results;
	}
	
	public static Map<RsvpResponse, List<Rsvp>> splitRsvps(List allRsvps) {

		Map<RsvpResponse, List<Rsvp>> rsvpMap = new HashMap<RsvpResponse, List<Rsvp>>();
		List<Rsvp> yesRsvp = new ArrayList<Rsvp>();
		List<Rsvp> noRsvp = new ArrayList<Rsvp>();
		List<Rsvp> mayBeRsvp = new ArrayList<Rsvp>();
		
		Iterator iter = allRsvps.iterator();
		while (iter.hasNext()) {
			Rsvp rsvp = (Rsvp) iter.next();
			if(RsvpResponse.YES.equals(rsvp.getResponse()))
				yesRsvp.add(rsvp);
			else if(RsvpResponse.NO.equals(rsvp.getResponse()))
				noRsvp.add(rsvp);
			else if(RsvpResponse.MAYBE.equals(rsvp.getResponse()))
				mayBeRsvp.add(rsvp);
		}
		
		rsvpMap.put(RsvpResponse.YES, yesRsvp);
		rsvpMap.put(RsvpResponse.NO, noRsvp);
		rsvpMap.put(RsvpResponse.MAYBE, mayBeRsvp);
		
		return rsvpMap;
	}

}
