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
package net.yama.android.views.listeners;

import java.util.ArrayList;
import java.util.List;

import net.yama.android.response.Photo;
import net.yama.android.util.Constants;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Generic listener. Passes the Id of the clicked view 
 * to the activity specified by the class
 * @author Rohit Kumbhar
 *
 */
public class PhotoAlbumClickListener implements OnClickListener {

	Context context;
	Class activityClass;
	String groupId;
	List<Photo> data;
	
	public PhotoAlbumClickListener(Context context, Class actvityClass, String groupId, List data) {
		this.context = context;
		this.activityClass = actvityClass;
		this.groupId = groupId;
		this.data = data;
	}
	
	public void onClick(View v) {
		
		int id = v.getId();
		Intent i = new Intent(context,activityClass);
		i.putExtra(Constants.SELECTED_ALBUM_ID, String.valueOf(id));
		i.putExtra(Constants.SELECTED_GROUP_ID, String.valueOf(groupId));
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);

	}

}
