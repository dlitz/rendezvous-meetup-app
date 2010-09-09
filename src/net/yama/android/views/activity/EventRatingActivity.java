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
import net.yama.android.requests.write.WriteEventRating;
import net.yama.android.response.Event;
import net.yama.android.response.EventRating;
import net.yama.android.util.Constants;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Rohit Kumbhar
 *
 */
public class EventRatingActivity extends Activity {

	private Event event;
	private EventRating currentRating;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		event = (Event) getIntent().getExtras().getSerializable(Constants.EVENT_ID_KEY);
		
		try {
			currentRating = DataManager.getMyEventRating(event.getId());
		} catch (ApplicationException e) {
			// Dont know yet
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eventrating);
		
		TextView titleView = (TextView) findViewById(R.id.eventTitle);
		titleView.setText(event.getName());
		
		if(currentRating != null){
			RatingBar ratingBar = (RatingBar) findViewById(R.id.eventRatingBar);
			ratingBar.setRating(currentRating.getRating());
			
			TextView review = (TextView) findViewById(R.id.shareThoughts);
			review.setText(currentRating.getReview());
		}
		
		Button submitButton = (Button) findViewById(R.id.submitRating);
		submitButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				RatingBar ratingBar = (RatingBar) findViewById(R.id.eventRatingBar);
				EditText review = (EditText) findViewById(R.id.shareThoughts); 
				EditText count = (EditText) findViewById(R.id.noOfAttendees);
				
				WriteEventRating request = new WriteEventRating();
				request.addParameter(Constants.EVENT_ID_KEY, event.getId());
				request.addParameter(EventRating.EVENT_RATING, Integer.toString((int) ratingBar.getRating()));
				request.addParameter(Constants.RESPONSE_PARAM_COMMENT, review.getText().toString());
				request.addParameter("attendee_count", count.getText().toString());
				try {
					DataManager.submitEventRating(request);
					Toast.makeText(EventRatingActivity.this, "Event rating saved!" , Toast.LENGTH_LONG).show();
				} catch (ApplicationException e) {
					Log.e("EventRatingActivity", "Failed to submit event rating: " + e.getMessage());
					Toast.makeText(EventRatingActivity.this, "Failed to submit event rating: " + e.getMessage() , Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
	}
}
