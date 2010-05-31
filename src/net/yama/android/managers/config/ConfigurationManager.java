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
package net.yama.android.managers.config;

import net.yama.android.Rendezvous;
import net.yama.android.managers.connection.ConnectionManagerFactory.ConnectionType;
import net.yama.android.util.Constants;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

/**
 * Provides storage and access methods for configuration parameters
 * @author Rohit Kumbhar
 */
public class ConfigurationManager {

	public static ConfigurationManager instance;
	private Rendezvous activity;

	private String apiKey;
	private String accessToken;
	private String accessTokenSecret;
	private String memberId;
	private String requestBeforePeriod;
	private String requestAfterPeriod;
	private int defaultStartupTab;
	
	private ConfigurationManager() {
	}

	public static ConfigurationManager init(Rendezvous yama) {
		instance = new ConfigurationManager();
		instance.activity = yama;
		loadConfiguration(yama);
		return instance;
	}

	public static void loadConfiguration(final Rendezvous yama) {
		SharedPreferences prefs = instance.activity.getSharedPreferences(Constants.PREF_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences defaultPrefs = PreferenceManager.getDefaultSharedPreferences(yama);
		defaultPrefs.registerOnSharedPreferenceChangeListener(new OnSharedPreferenceChangeListener() {
			public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {
				
				if(Constants.FETCH_EVENTS_FROM_PREF_KEY.equals(key) || Constants.FETCH_EVENTS_TO_PREF_KEY.equals(key))
					yama.reloadTabs();
			}
		});
		instance.apiKey = prefs.getString(Constants.API_KEY, null);
		instance.accessToken = prefs.getString(Constants.ACCESS_TOKEN, null);
		instance.accessTokenSecret = prefs.getString(Constants.ACCESS_TOKEN_SECRET, null);
		instance.memberId = prefs.getString(Constants.MEMBER_ID, null);
		instance.defaultStartupTab = Integer.valueOf(defaultPrefs.getString(Constants.STARTUP_TAB, "0"));
		instance.requestAfterPeriod = defaultPrefs.getString(Constants.FETCH_EVENTS_FROM_PREF_KEY, Constants.DEFAULT_BEFORE_PERIOD);
		instance.requestBeforePeriod = defaultPrefs.getString(Constants.FETCH_EVENTS_TO_PREF_KEY, Constants.DEFAULT_AFTER_PERIOD);
	}

	public String getApiKey() {
		return apiKey;
	}

	public void saveApiKey(String apiKey) {
		addPairToPrefs(Constants.API_KEY, apiKey);
		this.apiKey = apiKey;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void saveAccessToken(String accessToken) {
		addPairToPrefs(Constants.ACCESS_TOKEN, accessToken);
		this.accessToken = accessToken;
	}
	
	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void saveAccessTokenSecret(String accessTokenSecret) {
		addPairToPrefs(Constants.ACCESS_TOKEN_SECRET, accessTokenSecret);
		this.accessTokenSecret = accessTokenSecret;
	}

	public String getMemberId() {
		return memberId;
	}

	public void saveMemberId(String memberId) {
		addPairToPrefs(Constants.MEMBER_ID, memberId);
		this.memberId = memberId;
	}

	private void addPairToPrefs(String key, String value) {
		SharedPreferences prefs = activity.getSharedPreferences(Constants.PREF_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public boolean haveAcess() {
		return (this.accessToken != null);
	}

	public ConnectionType getConnectionType() {

		if (this.accessToken != null)
			return ConnectionType.OAUTH;
		else if (this.apiKey != null)
			return ConnectionType.APIKEY;
		
		return null;
	}

	public String getRequestAfterPeriod() {
		return requestAfterPeriod;
	}
	
	public String getRequestBeforePeriod() {
		return requestBeforePeriod;
	}

	public void saveRequestAfterPeriod(String requestAfterPeriod){
		addPairToPrefs(Constants.REQUEST_AFTER_PERIOD_KEY, requestAfterPeriod);
		this.requestAfterPeriod = requestAfterPeriod;
	}

	public int getDefaultStartupTab() {
		return defaultStartupTab;
	}

	public void nuke() {
		saveApiKey(null);
		saveAccessToken(null);
		saveAccessTokenSecret(null);
		saveMemberId(null);
	}

}
