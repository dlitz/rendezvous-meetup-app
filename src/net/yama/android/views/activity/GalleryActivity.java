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

import net.yama.android.managers.DataManager;
import net.yama.android.managers.connection.ApplicationException;
import net.yama.android.response.Photo;
import net.yama.android.util.Constants;
import net.yama.android.views.adapter.GalleryAdapter;
import net.yama.android.views.components.LoadingView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GalleryActivity extends Activity {

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
				Gallery g = new Gallery(GalleryActivity.this);
				g.setAdapter(new GalleryAdapter(albumPhotos,GalleryActivity.this));
//			    g.setOnItemClickListener(new OnItemClickListener() {
//			        public void onItemClick(AdapterView parent, View v, int position, long id) {
//			            Toast.makeText(GalleryActivity.this, "" + position, Toast.LENGTH_SHORT).show();
//			        }
//			    });
			    
			    return g;
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
}
