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

import java.util.List;

import net.yama.android.R;
import net.yama.android.managers.DataManager;
import net.yama.android.managers.connection.ApplicationException;
import net.yama.android.response.PhotoComment;
import net.yama.android.views.adapter.PhotoCommentsListAdapter;
import net.yama.android.views.components.LoadingView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class PhotoCommentListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Long photoId = getIntent().getExtras().getLong("photo_id");
		LoadingView view = new LoadingView(this) {
			
			@Override
			public View getResultsView() throws ApplicationException {
				
				List<PhotoComment> comments = DataManager.getPhotoComments(photoId.toString());
				
				if(!comments.isEmpty()){
					ListView commentsList = new ListView(PhotoCommentListActivity.this);
					commentsList.setAdapter(new PhotoCommentsListAdapter(comments,PhotoCommentListActivity.this));
					return commentsList;
				} else {
					TextView noComments = new TextView(PhotoCommentListActivity.this);
					noComments.setText(R.string.noCommentsOnPhoto);
					return noComments;
				}
			}
		};
		setContentView(view);
	}
	
}
