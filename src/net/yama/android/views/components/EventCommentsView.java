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
package net.yama.android.views.components;

import java.util.List;

import net.yama.android.R;
import net.yama.android.managers.DataManager;
import net.yama.android.managers.connection.ApplicationException;
import net.yama.android.requests.write.WriteEventComment;
import net.yama.android.response.Event;
import net.yama.android.response.Rsvp;
import net.yama.android.util.Constants;
import net.yama.android.views.adapter.EventCommentListAdapter;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * @author Rohit Kumbhar
 *
 */
public class EventCommentsView extends TableLayout {

	private EditText editComments;
	@SuppressWarnings("unchecked")
	private List eventComments;
	
	public EventCommentsView(Context context, final Event event) throws ApplicationException {
		super(context);
		this.eventComments = DataManager.getAllEventComments(event.getId());
		Rsvp rsvp = DataManager.getMyRsvp(event.getId());
		
		TextView titleView = new TextView(context);
		titleView.setText(event.getName());
		titleView.setTypeface(Typeface.DEFAULT_BOLD);
		titleView.setTextSize(19.0F);
		titleView.setPadding(2, 2, 2, 2);
		titleView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
		this.addView(titleView);
		
		if(rsvp != null){
			TextView commentsLabel = new TextView(context);
			commentsLabel.setText(R.string.addComment);
			commentsLabel.setTypeface(Typeface.DEFAULT_BOLD);
			this.addView(commentsLabel);
			
			editComments = new EditText(context);
			editComments.setLines(3);
			this.addView(editComments);
			
			RelativeLayout rel = new RelativeLayout(context);
			rel.setGravity(Gravity.CENTER);
			Button addComment = new Button(context);
			addComment.setText(R.string.addCommentBtn);
			addComment.setMinimumWidth(100);
			addComment.setLayoutParams(new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			addComment.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					WriteEventComment request = new WriteEventComment();
					request.addParameter(Constants.EVENT_ID_KEY, event.getId());
					request.addParameter(Constants.RESPONSE_PARAM_COMMENT, editComments.getText().toString());
					try {
						DataManager.addEventComment(request);
						postInvalidate();
					} catch (ApplicationException e) {
						Log.e("EventCommentsView::onClick", "Exception occured:",e);
					}
				}
			});
			
			rel.addView(addComment);
			RelativeLayout.LayoutParams uploadBtnParams = new RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT,android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
			uploadBtnParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			this.addView(rel);
		}
		
		if(!eventComments.isEmpty()) {
			ListView eventCommentsListView = new ListView(context);
			eventCommentsListView.setAdapter(new EventCommentListAdapter(eventComments, context));
			this.addView(eventCommentsListView);
		} else {
			TextView nullView = new TextView(context);
			nullView.setText(R.string.noCommentsYet);
			this.addView(nullView);
		}
	}

}
