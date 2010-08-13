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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.yama.android.Rendezvous;
import net.yama.android.managers.config.ConfigurationManager;
import net.yama.android.response.BaseResponse;
import net.yama.android.response.Rsvp;
import net.yama.android.response.Rsvp.RsvpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.util.Log;


public class Helper{

	private static Map<String, Object> cache = new HashMap<String, Object>(); 
	
	public static void storeInCache(String key, Object value){
		cache.put(key, value);
	}
	
	public static Object getFromCache(String key){
		return cache.get(key);
	}
	
	/**
	 * Converts the JSON string into entity classes
	 * @param responseBody
	 * @param clazz
	 * @return
	 * @throws JSONException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
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
	
	/**
	 * Split the list of RSVPs into three maps,  YES, NO, MAYBE
	 * @param allRsvps
	 * @return
	 */
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

	/**
	 * Create a temporary storage on the sdcard
	 * @return
	 */
	public static File getTempStorageDirectory() {

		String tempStorageDir = ConfigurationManager.instance.getTempImageStoragePath();
		if(tempStorageDir != null && new File(tempStorageDir).exists())
			return new File(tempStorageDir);
		
		// All or nothing
		String tempStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "rendezvous";
		File f = new File(tempStorageDirectory);
		if(!f.exists() && f.mkdirs()){
			f =  new File(tempStorageDirectory + File.separator + "temp");
			if(!f.exists() && f.mkdirs()) {
				ConfigurationManager.instance.saveTempImageStoragePath(f.getAbsolutePath());
				return f;
			}
		}
			
		return Environment.getExternalStorageDirectory();
	}
	
	private static String getCrashReport(Throwable e, Context application) {
		
		StringBuffer body = new StringBuffer();
		body.append("Timestamp: " + new Date().toString());
		body.append("\n** Crash Report **\n");
		try {
			PackageInfo pi = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
			body.append("Package Name: ").append(pi.packageName).append("\n");
			body.append("Package Version: ").append(pi.versionCode).append("\n");
			body.append("Phone Model: ").append(android.os.Build.MODEL).append("\n");
			body.append("Phone Manufacturer: ").append(android.os.Build.MANUFACTURER).append("\n");
			body.append("Android Version:").append(android.os.Build.VERSION.RELEASE).append("\n");
		} catch (NameNotFoundException e1) {
		}
		
		StringWriter stack = new StringWriter();
		PrintWriter writer = new PrintWriter(stack);
		e.printStackTrace(writer);
		
		body.append("\n\nStacktrace:\n\n");
		body.append(stack.toString()).append("\n");
		
		if(e.getCause() != null){
			Throwable cause = e.getCause();
			stack = new StringWriter();
			writer = new PrintWriter(stack);
			cause.printStackTrace(writer);
			
			body.append("\n\nCause Stacktrace:\n\n");
			body.append(stack.toString()).append("\n");
		}
		
		body.append("** Crash Report **\n");
		return body.toString();
	}

	/**
	 * Logs crash report.
	 * @param ex
	 * @param application
	 * @return
	 */
	public static String logCrashReport(Throwable ex, Context application) {
		String reportContent = getCrashReport(ex, application);
		File crashReportsDir = getCrashReportsDirectory();
		File report = new File(crashReportsDir, "crashreport-" + System.currentTimeMillis() + ".log");
		
		try {
			PrintWriter writer = new PrintWriter(report);
			writer.write(reportContent);
			writer.close();
		} catch (FileNotFoundException e) {
			Log.e("Crash Report", "Could not create crash report because of:" + e.getMessage());
		}
		
		return null;
	}

	public static File getCrashReportsDirectory() {
		File dir = new File(getTempStorageDirectory().getAbsolutePath() + File.separator + "logs");
		if(!dir.exists())
			dir.mkdirs();
		
		return dir;
		
	}

}
