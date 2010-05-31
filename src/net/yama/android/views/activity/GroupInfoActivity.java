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

import net.yama.android.R;
import net.yama.android.util.Constants;
import net.yama.android.views.contentfactory.GroupInfoContentFactory;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class GroupInfoActivity extends TabActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		String groupId = i.getExtras().getString(Constants.RESPONSE_PARAM_GROUP_ID);
		setContentView(R.layout.dashboard);
		setTitle("Group Details");
		GroupInfoContentFactory contentFactory = new GroupInfoContentFactory(GroupInfoActivity.this,groupId);
		TabHost mTabHost = getTabHost();
		mTabHost.addTab(mTabHost.newTabSpec(Constants.GROUP_INFO_TAB_ID).setIndicator("Info").setContent(contentFactory));
	    mTabHost.addTab(mTabHost.newTabSpec(Constants.GROUP_MEETUPS_TAB_ID).setIndicator("Meetups").setContent(contentFactory));
	    mTabHost.setCurrentTab(0);
	}

}
