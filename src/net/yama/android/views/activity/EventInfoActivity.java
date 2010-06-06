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
package net.yama.android.views.activity;

import java.io.File;

import net.yama.android.R;
import net.yama.android.util.Constants;
import net.yama.android.util.Helper;
import net.yama.android.views.contentfactory.EventInfoContentFactory;
import android.app.TabActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

/**
 * @author Rohit Kumbhar
 */
public class EventInfoActivity extends TabActivity {

	private File tempImage;
	private Intent cameraIntent;
	private String eventId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		eventId = i.getExtras().getString(Constants.EVENT_ID_KEY);
		setContentView(R.layout.dashboard);
		EventInfoContentFactory contentFactory = new EventInfoContentFactory(EventInfoActivity.this,eventId);
		TabHost mTabHost = getTabHost();
		mTabHost.addTab(mTabHost.newTabSpec(Constants.MEETUP_INFO_TAB_ID).setIndicator("Info").setContent(contentFactory));
	    mTabHost.addTab(mTabHost.newTabSpec(Constants.MEETUP_RSVP_TAB_ID).setIndicator("RSVP").setContent(contentFactory));
	    mTabHost.addTab(mTabHost.newTabSpec(Constants.MEETUP_TALK_TAB_ID).setIndicator("Talk").setContent(contentFactory));
	    mTabHost.addTab(mTabHost.newTabSpec(Constants.MEETUP_ALL_RSVP_ID).setIndicator("Responses").setContent(contentFactory));
	    mTabHost.setCurrentTab(0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, Constants.TAKE_A_PICTURE, 0, R.string.takePhoto).setIcon(android.R.drawable.ic_menu_camera);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		
		switch (item.getItemId()) {
		 case Constants.TAKE_A_PICTURE:
		    	startCamera();
		        return true;
		}
		return false;
	}

	private void startCamera() {
		
			tempImage = new File(Helper.getTempStorageDirectory(),Constants.TEMP_IMAGE);
			cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);	
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(tempImage));
			startActivityForResult(	cameraIntent, Constants.CAMERA_INTENT_ID);
	}
		
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(this,R.string.cancelPhoto,Toast.LENGTH_SHORT).show();
			return;
		}
		switch (requestCode) {		
			case Constants.CAMERA_INTENT_ID: 
				Intent uploadPhoto = new Intent(this, UploadPhotoActivity.class);
				uploadPhoto.putExtra(Constants.TEMP_IMAGE_FILE_PATH,tempImage.getAbsolutePath());
				uploadPhoto.putExtra(Constants.EVENT_ID_KEY, eventId);
				startActivity(uploadPhoto);
				break;
		}
	}
	
	
}
