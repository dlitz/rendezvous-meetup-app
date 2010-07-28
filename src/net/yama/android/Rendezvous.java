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
package net.yama.android;

import java.util.ArrayList;
import java.util.Map;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.client.OAuthClient;
import net.oauth.client.httpclient4.HttpClient4;
import net.yama.android.managers.DataManager;
import net.yama.android.managers.config.ConfigurationManager;
import net.yama.android.managers.connection.OAuthConnectionManager;
import net.yama.android.service.NotificationService;
import net.yama.android.util.Constants;
import net.yama.android.util.Helper;
import net.yama.android.views.activity.RendezvousPreferences;
import net.yama.android.views.contentfactory.MainContentFactory;
import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Rendezvous extends TabActivity {
	
	private static final String OAUTH_TOKEN_SECRET = "oauth_token_secret";
	private static final String OAUTH_TOKEN = "oauth_token";
	private static final String OAUTH_ACCESSOR_INSTANCE = "OAUTH_ACCESSOR_INSTANCE";
	private ConfigurationManager configurationManager;
	private MainContentFactory contentFactory;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		configurationManager = ConfigurationManager.init(this);
		contentFactory = new MainContentFactory(this);
		
		Object accessor = Helper.getFromCache(OAUTH_ACCESSOR_INSTANCE);
		if(accessor != null)
			finishAuthorize();
		
		// Do upgrade activities
		doUpgradeActivities();
		
		setContentView(R.layout.dashboard);
		populateDashboard();
		
	}
	
	/**
	 * Things to do after an upgrade
	 */
	private void doUpgradeActivities() {
		
		int version = -1;
		try {
			PackageInfo info = getApplication().getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
			version = info.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(configurationManager.haveAcess() && (configurationManager.getCurrentVersion() == null || 
				!configurationManager.getCurrentVersion().equals(Integer.toString(version)))){
	
			// Cleanup cache
			DataManager.nuke();
	
			// Start notifications
			if(configurationManager.isNotificationsFlag()){
				Intent notificationsIntent = new Intent(this, NotificationService.class);
				startService(notificationsIntent);
			}
			
			// Update version
			configurationManager.setCurrentVersion(Integer.toString(version));
		}
	}

	/**
	 * Populates the screen when everything is authorized.
	 */
	private void populateDashboard() {
		
		TabHost mTabHost = getTabHost();
		mTabHost.setDrawingCacheEnabled(false);
		mTabHost.clearAllTabs();
		
		mTabHost.addTab(mTabHost.newTabSpec(Constants.MEETUPS_TAB_ID).setIndicator(getText(R.string.meetupsTabLabel)).setContent(contentFactory));
		mTabHost.addTab(mTabHost.newTabSpec(Constants.GROUPS_TAB_ID).setIndicator(getText(R.string.groupsTabLabel)).setContent(contentFactory));
		mTabHost.addTab(mTabHost.newTabSpec(Constants.ACTIVITY_TAB_ID).setIndicator(getText(R.string.activityTabLabel)).setContent(contentFactory));
	    mTabHost.setCurrentTab(configurationManager.getDefaultStartupTab());
	}

	public View authorizeView(){
			
		TableLayout authLayout = new TableLayout(getApplicationContext());
		TextView notice = new TextView(getApplicationContext());
		notice.setText(R.string.authorizeMessage);
		notice.setPadding(10, 10, 10, 10);
		authLayout.addView(notice);
		
		Button authButton = new Button(getApplicationContext());
		authButton.setGravity(Gravity.CENTER);
		authButton.setText(R.string.authorize);
		authButton.setOnClickListener(new AuthorizeButtonListener());
		authButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
		authLayout.addView(authButton);
		return authLayout;
	}
	
	
	public class AuthorizeButtonListener implements View.OnClickListener {

		public void onClick(View v) {
			doAuthorize();
		}
	}
	
	/**
	 * OAuth Authorization
	 */
	private void doAuthorize() {
	    
	    OAuthAccessor accessor = new OAuthAccessor(OAuthConnectionManager.consumer);
	    OAuthClient client = new OAuthClient(new HttpClient4());

	    try {
			client.getRequestToken(accessor);
			Helper.storeInCache(OAUTH_ACCESSOR_INSTANCE, accessor);
		    Intent i = new Intent(Intent.ACTION_VIEW);
		    i.setData(Uri.parse(Constants.OAUTH_AUTHORIZE_URL + "?oauth_token=" + accessor.requestToken + "&oauth_callback=" + Constants.CALLBACK_URL));
		    startActivity(i);
		} catch (Exception e) {
			Toast t = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
			Log.e("Rendezvous", "Exception occured in doAuthorize()", e);
			t.show();

		} 
	}
	
	
	protected void finishAuthorize() {
	
		// extract the OAUTH access token if it exists
		Uri uri = this.getIntent().getData();
		if (uri != null) {
			
			OAuthAccessor accessor = (OAuthAccessor) Helper.getFromCache(OAUTH_ACCESSOR_INSTANCE);
			OAuthClient oAuthClient = new OAuthClient(new HttpClient4());
			
			String oauthToken = uri.getQueryParameter(OAUTH_TOKEN);
			
			if(oauthToken == null || oauthToken.length() == 0){
				Toast t = Toast.makeText(getApplicationContext(), "This application cannot be used without granting permission", Toast.LENGTH_LONG);
				t.show();
				return;
			}
			
			ArrayList<Map.Entry<String, String>> params = new ArrayList<Map.Entry<String, String>>();
			params.add(new OAuth.Parameter(OAUTH_TOKEN, oauthToken));
			

			try {
				// Get the access token
				OAuthMessage authMessage = oAuthClient.invoke(accessor, "POST",accessor.consumer.serviceProvider.accessTokenURL,params);
				String oAuthToken = authMessage.getParameter(OAUTH_TOKEN);
				String oAuthTokenSecret = authMessage.getParameter(OAUTH_TOKEN_SECRET);
				accessor.tokenSecret = oAuthTokenSecret;
				configurationManager.saveAccessToken(oAuthToken);
				configurationManager.saveAccessTokenSecret(oAuthTokenSecret);
				Helper.storeInCache(OAUTH_ACCESSOR_INSTANCE,null);
				
	
			}catch (Exception e) {
				Log.e("Rendezvous", "Exception in onResume()", e);
			}

		}

	}
	
	/* Creates the menu items */
	public boolean onCreateOptionsMenu(Menu menu) {
	    menu.add(0, Constants.PREFS_MENU, 0, R.string.prefSettings).setIcon(android.R.drawable.ic_menu_preferences);
	    menu.add(0, Constants.PREFS_RESET_CACHE, 0, R.string.prefResetCache).setIcon(android.R.drawable.ic_menu_rotate);
	    menu.add(0, Constants.RESET_ACC_MENU, 0, R.string.prefDeleteAcc).setIcon(android.R.drawable.ic_menu_delete);
	    return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case Constants.PREFS_MENU:
	        showPreferencesScreen();
	        return true;
	    case Constants.RESET_ACC_MENU:
	    	DataManager.nuke();
	    	configurationManager.nuke();
	    	finish();
	        return true;
	    case Constants.PREFS_RESET_CACHE:
	    	DataManager.nuke();
	        return true;
	    }
	    return false;
	}
	
	private void showPreferencesScreen() {
		Intent prefsIntent = new Intent(getBaseContext(),RendezvousPreferences.class);
		startActivity(prefsIntent);
	}

	public void reloadTabs() {
		DataManager.nuke();
		getTabHost().invalidate();
	}

}