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
import net.yama.android.managers.DataManager;
import net.yama.android.managers.connection.ApplicationException;
import net.yama.android.requests.write.WritePhotoComment;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author Rohit Kumbhar
 */
public class AddPhotoCommentActivity extends Activity {

	private String photoId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		photoId = getIntent().getExtras().getString("photo_id");
		setContentView(R.layout.photocomment);
		
		Button submit = (Button) findViewById(R.id.submitPhotoComment);
		submit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				EditText commentView = (EditText) findViewById(R.id.photoCommentText);
				
				WritePhotoComment request = new WritePhotoComment();
				request.addParameter("photo_id", photoId);
				request.addParameter("comment", commentView.getText().toString());
				try {
					DataManager.submitPhotoComment(request);
					Toast.makeText(AddPhotoCommentActivity.this, "Comment posted.", Toast.LENGTH_LONG).show();
					finish();
				} catch (ApplicationException e) {
					Log.e("AddPhotoCommentActivity", e.getMessage());
					Toast.makeText(AddPhotoCommentActivity.this, "Comment could not be posted: "+ e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
