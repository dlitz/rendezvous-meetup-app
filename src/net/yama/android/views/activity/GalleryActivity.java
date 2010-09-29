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

import java.util.Iterator;
import java.util.List;

import net.yama.android.R;
import net.yama.android.managers.DataManager;
import net.yama.android.managers.connection.ApplicationException;
import net.yama.android.response.Photo;
import net.yama.android.util.Constants;
import net.yama.android.views.adapter.GalleryAdapter;
import net.yama.android.views.components.LoadingView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Gallery;

/*
 * Displayes all the pictures in a meetup
 */
public class GalleryActivity extends Activity {

	private static final int VIEW_COMMENTS = 1;
	private static final int ADD_COMMENT = 2;
	private Gallery galleryView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent i = getIntent();
		final String albumId = i.getExtras().getString(Constants.SELECTED_ALBUM_ID);
		final String groupId = i.getExtras().getString(Constants.SELECTED_GROUP_ID);
		LoadingView loadingView = new LoadingView(GalleryActivity.this) {
			
			

			@SuppressWarnings("unchecked")
			@Override
			public View getResultsView() throws ApplicationException {
				List photos = DataManager.getPhotosForGroup(groupId);
				Photo albumPhotos = getPhotosForAlbum(photos,albumId);
				galleryView = new Gallery(GalleryActivity.this);
				galleryView.setSpacing(10);
				galleryView.setAdapter(new GalleryAdapter(albumPhotos,GalleryActivity.this));
			    return galleryView;
			}

			private Photo getPhotosForAlbum(List photos, String albumId) {
				Iterator iter = photos.iterator();
				while (iter.hasNext()) {
					Photo p = (Photo) iter.next();
					if(p.getAlbumId().equals(albumId))
						return p;
				}	
				
				return null;
			}
		};
		setContentView(loadingView);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
//		menu.add(0, Constants.PREFS_MENU, 0, R.string.prefSettings).setIcon(android.R.drawable.ic_menu_preferences);
		menu.add(0,VIEW_COMMENTS,0,R.string.viewComments).setIcon(android.R.drawable.ic_menu_view);
		menu.add(0,ADD_COMMENT,0,R.string.addPhotoCommentMenu).setIcon(android.R.drawable.ic_menu_add);
		return true;
	}
	
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	
		long photoId = galleryView.getSelectedItemId();
	
		if(VIEW_COMMENTS == item.getItemId()){
			Intent i = new Intent(this,PhotoCommentListActivity.class);
			i.putExtra("photo_id", photoId);
			startActivity(i);
		} else if (ADD_COMMENT == item.getItemId()){
			Intent i = new Intent(this,AddPhotoCommentActivity.class);
			i.putExtra("photo_id", Long.toString(photoId));
			startActivity(i);
		}
		return true;
	}
	
}
