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
import net.yama.android.managers.connection.ApplicationException;
import net.yama.android.managers.connection.ConnectionManagerFactory;
import net.yama.android.requests.write.WritePhotoRequest;
import net.yama.android.util.Constants;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

public class UploadPhotoActivity extends Activity implements OnClickListener {
	
	private static final String DATA = "data";

	private static final int UPLOAD_BUTTON_ID = 0xDEADBEEF;
	
	Bitmap imageToUpload;
	String eventId;

	private EditText captionEdit;

	private String tempImagePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		
		tempImagePath = b.getString(Constants.TEMP_IMAGE_FILE_PATH);
		this.eventId = b.getString(Constants.EVENT_ID_KEY);
		
		Bitmap bm = BitmapFactory.decodeFile(this.tempImagePath);
		TableLayout layout = new TableLayout(this);
		ImageView image = new ImageView(UploadPhotoActivity.this);
		image.setImageBitmap(Bitmap.createScaledBitmap(bm, 300, 300, false ));
		image.setScaleType(ImageView.ScaleType.CENTER);
		layout.addView(image);
		
		TextView captionLabel = new TextView(this);
		captionLabel.setText(R.string.photoCaption);
		captionLabel.setTypeface(Typeface.DEFAULT_BOLD);
		layout.addView(captionLabel);
		
		captionEdit = new EditText(this);
		layout.addView(captionEdit);
		
		Button upload = new Button(this);
		upload.setId(UPLOAD_BUTTON_ID);
		upload.setText(R.string.uploadPhoto);
		upload.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		upload.setPadding(15, 15, 15, 15);
		upload.setOnClickListener(this);
		upload.setGravity(Gravity.CENTER);
		
		layout.addView(upload);
		setContentView(layout);
		
	}

	public void onClick(View v) {

		if(v.getId() != UPLOAD_BUTTON_ID)
			return;
		
		WritePhotoRequest request = new WritePhotoRequest();
		request.addParameter(Constants.EVENT_ID_KEY, this.eventId);
		request.addParameter(Constants.TEMP_IMAGE_FILE_PATH, this.tempImagePath);
		request.addParameter(Constants.IMAGE_CAPTION, this.captionEdit.getText().toString());
		
		String response;
		try {
			response = ConnectionManagerFactory.getConnectionManager().uploadPhoto(request);
			System.out.println(response);
		} catch (ApplicationException e) {
			
			e.printStackTrace();
		}
	}

}
